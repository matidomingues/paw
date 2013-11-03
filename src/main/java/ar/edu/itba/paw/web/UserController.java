package ar.edu.itba.paw.web;

import ar.edu.itba.paw.domain.notification.FollowingNotification;
import ar.edu.itba.paw.domain.notification.Notification;
import ar.edu.itba.paw.domain.notification.NotificationRepo;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twatt.TwattRepo;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.utils.Report;
import ar.edu.itba.paw.utils.exceptions.DuplicatedUserException;
import ar.edu.itba.paw.web.command.UserForm;
import ar.edu.itba.paw.web.command.validator.UserFormValidator;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

import org.apache.tools.ant.taskdefs.condition.Http;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class UserController {

	public static final String LOCAL_USER_REFERENCER = "local_user";
	public static final String SESSION_USERNAME = "user_username";
	public static final String SESSION_USER_ID = "user_id";

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private TwattRepo twattRepo;

	@Autowired
	private UserFormValidator validator;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private NotificationRepo notificationRepo;

	@RequestMapping(value = "/profile/{username}", method = RequestMethod.GET)
	public ModelAndView user(@PathVariable String username, HttpSession seq) {
		TwattUser localUser;

		if (seq.getAttribute(SESSION_USERNAME) == null) {
			localUser = null;
		} else {
			localUser = userRepo.getUserByUsername((String) seq
					.getAttribute(SESSION_USERNAME));
		}
		TwattUser user = userRepo.getUserByUsername(username);

		if (user != null) {

			if (localUser == null && user.getPrivacy()) {
				return new ModelAndView("user/private");
			}

			ModelAndView mav = new ModelAndView("user/userdetail");
			mav.addObject("searchuser", user);

			if (localUser != null && !user.equals(localUser)) {
				if (user.isFollowedBy(localUser)) {
					mav.addObject("follow", false);
				} else {
					mav.addObject("follow", true);
				}
			}
			if (!user.equals(localUser)) {
				user.addAccess();
			}

			List<Twatt> twatts = twattRepo.getTwattsByUsername(user
					.getUsername());
			mav.addObject("twatts", twatts);
			mav.addObject(LOCAL_USER_REFERENCER, localUser);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("forward:/bin/find");
			mav.addObject("error", "No existe ningun usuario con ese nombre");
			return mav;
		}
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public ModelAndView find(HttpSession seq) {
		TwattUser localUser;
		if (seq.getAttribute(SESSION_USERNAME) == null) {
			localUser = null;
		} else {
			localUser = userRepo.getUserByUsername((String) seq
					.getAttribute(SESSION_USERNAME));
		}
		ModelAndView mav = new ModelAndView("user/find");
		mav.addObject("users", userRepo.getAll());
		mav.addObject(LOCAL_USER_REFERENCER, localUser);
		return mav;
	}

	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public ModelAndView find(@RequestParam("username") String username,
			HttpSession seq) {
		TwattUser localUser;
		if (seq.getAttribute(SESSION_USERNAME) == null) {
			localUser = null;
		} else {
			localUser = userRepo.getUserByUsername((String) seq
					.getAttribute(SESSION_USERNAME));
		}
		ModelAndView mav = new ModelAndView("user/find");
		mav.addObject("users", userRepo.find(username));
		mav.addObject(LOCAL_USER_REFERENCER, localUser);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView settings(HttpSession seq) {
		TwattUser localUser;
		localUser = userRepo.getUserByUsername((String) seq
				.getAttribute(SESSION_USERNAME));
		ModelAndView mav = new ModelAndView();
		Integer user_id = (Integer) seq.getAttribute(SESSION_USER_ID);
		TwattUser user = userRepo.find(user_id);
		mav.addObject("userForm", new UserForm(user));
		mav.addObject(LOCAL_USER_REFERENCER, localUser);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView settings(UserForm editForm, Errors errors,
			HttpSession seq) {
		ModelAndView mav = null;
		TwattUser localUser;
		validator.validate(editForm, errors);
		if (errors.hasErrors()) {
			return mav;
		}
		try {
			localUser = editForm.build();
			userRepo.updateUser(localUser);
		} catch (IllegalArgumentException e) {
			System.out.println("error");
			errors.rejectValue("username", "invalid");
			return mav;
		}
		mav = new ModelAndView("redirect:/bin/profile/"
				+ seq.getAttribute(SESSION_USERNAME));
		mav.addObject(LOCAL_USER_REFERENCER, localUser);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView login(@RequestParam("username") TwattUser user,
			@RequestParam("password") String password, HttpSession seq) {
		if (user != null && user.checkPassword(password)) {
			ModelAndView mav = new ModelAndView("redirect:/bin/home");
			mav.addObject(LOCAL_USER_REFERENCER, user);
			seq.setAttribute(SESSION_USER_ID, user.getId());
			seq.setAttribute(SESSION_USERNAME, user.getUsername());
			return mav;
		}
		return new ModelAndView("redirect:login");
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("userForm", new UserForm());
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String register(@ModelAttribute("userForm") UserForm userForm,
			Errors errors) {
		validator.validate(userForm, errors);
		if (errors.hasErrors()) {
			return null;
		}
		try {
			userRepo.registerUser(userForm.build());
		} catch (DuplicatedUserException e) {
			errors.rejectValue("username", "duplicated");
			return null;
		}
		return "redirect:/bin/user/login";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String logout(HttpSession seq) {
		seq.invalidate();
		return "redirect:/bin/user/login";
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView restore() {
		return new ModelAndView();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView restore(@RequestParam("username") String username) {
		return new ModelAndView("redirect:restore/" + username);
	}

	@RequestMapping(value = "restore/{username}", method = RequestMethod.GET)
	public ModelAndView restoreQuestion(@PathVariable String username) {
		TwattUser user = userRepo.getUserByUsername(username);
		if (user != null) {
			ModelAndView mav = new ModelAndView("user/restoreq");
			mav.addObject("restoreuser", user);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("forward: restore");
			mav.addObject("error", "Usuario Inexistente");
			return mav;
		}
	}

	@RequestMapping(value = "restore/{username}", method = RequestMethod.POST)
	public String restoreQuestion(@PathVariable String username,
			@RequestParam("secretanswer") String answer,
			@RequestParam("password") String password,
			@RequestParam("confirmpassword") String newPassword) {
		if (password.equals(newPassword)) {
			try {
				userRepo.userRestore(username, answer, password);
				return "redirect:/bin/user/login";
			} catch (Exception e) {
				return "redirect:/bin/restore";
			}
		}
		return "redirect:/bin/user/restore";
	}

	@RequestMapping(value = "/image/{username}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getFile(@PathVariable String username) {
		TwattUser user = userRepo.getUserByUsername(username);
		ResponseEntity<byte[]> responseEntity = null;
		if (user == null) {
			return responseEntity;
		}
		byte[] photo = null;
		if ((photo = user.getPhoto()) == null || photo.length == 0) {
			Path path = Paths.get(servletContext
					.getRealPath("/img/default_user_icon.png"));
			try {
				photo = Files.readAllBytes(path);
			} catch (IOException e) {
				e.printStackTrace(); // To change body of catch statement use
										// File | Settings | File Templates.
			}
		}
		String[] contentType = null;
		try {
			contentType = Magic.getMagicMatch(photo).getMimeType().split("/");
		} catch (MagicParseException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		} catch (MagicMatchNotFoundException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		} catch (MagicException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(new MediaType(contentType[0],
				contentType[1]));
		responseEntity = new ResponseEntity<byte[]>(photo, responseHeaders,
				HttpStatus.CREATED);
		return responseEntity;
	}

	@RequestMapping(value = "/follow/{user}", method = RequestMethod.GET)
	public ModelAndView follow(@PathVariable TwattUser user, HttpSession seq) {
		TwattUser localUser = userRepo.getUserByUsername((String) seq
				.getAttribute(SESSION_USERNAME));
		ModelAndView mav = new ModelAndView("redirect:/bin/profile/"
				+ localUser.getUsername());
		localUser.addFollowing(user);
		Notification notification = new FollowingNotification(user, localUser);
		notificationRepo.save(notification);
		user.notify(notificationRepo.find(notification));
		mav.addObject(LOCAL_USER_REFERENCER, localUser);
		return mav;
	}

	@RequestMapping(value = "/unfollow/{user}", method = RequestMethod.GET)
	public ModelAndView unfollow(@PathVariable TwattUser user, HttpSession seq) {
		TwattUser localUser = userRepo.getUserByUsername((String) seq
				.getAttribute(SESSION_USERNAME));
		ModelAndView mav = new ModelAndView("redirect:/bin/profile/"
				+ localUser.getUsername());
		localUser.removeFollowing(user);
		mav.addObject(LOCAL_USER_REFERENCER, localUser);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView notifications(HttpSession seq) {
		TwattUser localUser = userRepo.getUserByUsername((String) seq
				.getAttribute(SESSION_USERNAME));
		ModelAndView mav = new ModelAndView("user/notifications");
		mav.addObject(LOCAL_USER_REFERENCER, localUser);
		mav.addObject("notifications", localUser.getNotifications());
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView favourites(HttpSession seq) {
		TwattUser localUser = userRepo.getUserByUsername((String) seq
				.getAttribute(SESSION_USERNAME));
		ModelAndView mav = new ModelAndView("user/favourites");
		mav.addObject(LOCAL_USER_REFERENCER, localUser);
		mav.addObject("favourites", localUser.getFavourites());
		return mav;
	}

	@RequestMapping(value = "/social/{user}", method = RequestMethod.GET)
	public ModelAndView social(@PathVariable TwattUser user, HttpSession seq) {
		TwattUser localUser = userRepo.getUserByUsername((String) seq
				.getAttribute(SESSION_USERNAME));
		ModelAndView mav = new ModelAndView("user/social");
		mav.addObject(LOCAL_USER_REFERENCER, localUser);
		if(user != null){
			mav.addObject("localUser", user);
		}
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView report(HttpSession seq) throws JSONException {
		TwattUser localUser = userRepo.getUserByUsername((String) seq
				.getAttribute(SESSION_USERNAME));
		ModelAndView mav = new ModelAndView();
		mav.addObject(LOCAL_USER_REFERENCER, localUser);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String getreport(
			@RequestParam(value = "time", required = true) String days,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate,
			HttpSession seq) throws JSONException {
		DateTime startTime, endTime;
		if (startDate.compareTo("0") == 0) {
			startTime = new DateTime(0);
		} else {
			startTime = new DateTime(Long.parseLong(startDate));
		}
		if (endDate.compareTo("0") == 0) {
			endTime = new DateTime();
		} else {
			endTime = new DateTime(Long.parseLong(endDate));
		}

		JSONArray arr = new JSONArray();
		TwattUser localUser;
		localUser = userRepo.getUserByUsername((String) seq
				.getAttribute(SESSION_USERNAME));

		for (Report report : twattRepo.getTwattReportByDate(localUser,
				startTime, endTime, days)) {
			arr.put(new JSONObject().put("label", report.getHeader()).put("y",
					report.getValue()));
		}
		return new JSONObject().put("datagrams", arr).toString();
	}

}
