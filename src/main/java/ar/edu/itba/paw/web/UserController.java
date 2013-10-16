package ar.edu.itba.paw.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.MessageService;
import ar.edu.itba.paw.service.TwattService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.utils.exceptions.DuplicatedUserException;
import ar.edu.itba.paw.web.command.UserForm;
import ar.edu.itba.paw.web.command.validator.UserFormValidator;

@Controller
public class UserController {

    @Autowired
	private UserService usermanager;

    @Autowired
	private TwattService twattmanager;

    @Autowired
	private MessageService messagemanager;

    @Autowired
	private UserFormValidator validator;

    @Autowired
    private ServletContext servletContext;

	@RequestMapping(value = "/profile/{username}", method = RequestMethod.GET)
	public ModelAndView user(@PathVariable String username) {
		ModelAndView mav = new ModelAndView("user/userdetail");
		User user = usermanager.getUserByUsername(username);
		if (user != null) {
			mav.addObject("searchuser", user);
			List<Twatt> twatts = twattmanager.getTwattsByUsername(user
					.getUsername());
			for (Twatt twatt : twatts) {
				twatt.setMessage(messagemanager.prepareMessage("/",
						twatt.getMessage()));
			}
			mav.addObject("twatts", twatts);
			return mav;
		} else {
			mav = new ModelAndView("forward:/bin/find");
			mav.addObject("error", "No existe ningun usuario con ese nombre");
			return mav;
		}
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public ModelAndView find() {
		ModelAndView mav = new ModelAndView("user/find");
		mav.addObject("users", usermanager.getAll());
		return mav;
	}

	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public ModelAndView find(@RequestParam("username") String username) {
		ModelAndView mav = new ModelAndView("user/find");
		mav.addObject("users", usermanager.find(username));
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView settings(HttpSession seq) {
		ModelAndView mav = new ModelAndView();
		Integer user_id = (Integer) seq.getAttribute("user_id");
		User user = usermanager.find(user_id);
		mav.addObject("userForm", new UserForm(user));
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String settings(UserForm editForm, Errors errors, HttpSession seq) {
		validator.validate(editForm, errors);
		if (errors.hasErrors()) {
			return null;
		}
		try {
			usermanager.updateUser(editForm.build());
		} catch (IllegalArgumentException e) {
			errors.rejectValue("username", "invalid");
			return null;
		}
		return "redirect:/bin/profile/" + editForm.getUsername();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String login(@RequestParam("username") User user,
			@RequestParam("password") String password, HttpSession seq) {
		if (user.checkPassword(password)) {
			seq.setAttribute("user_id", user.getId());
			seq.setAttribute("user_username", user.getUsername());
			return "redirect:/bin/home";
		}
		return "forward:login";
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
			usermanager.registerUser(userForm.build());
		} catch (DuplicatedUserException e) {
			errors.rejectValue("username", "duplicated");
			return null;
		}
		return "redirect:/bin/user/login";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String logout(HttpSession seq) {
		seq.removeAttribute("user_id");
		seq.removeAttribute("user_username");
		return "redirect:/bin/user/login";
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView restore() {
		return new ModelAndView();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView restore(@RequestParam("username") String username) {
		return new ModelAndView("redirect:restore/"+username);
	}

	@RequestMapping(value = "restore/{username}", method = RequestMethod.GET)
	public ModelAndView restoreQuestion(@PathVariable String username) {
		User user = usermanager.getUserByUsername(username);
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
		if(password.equals(newPassword)){
			try{
				usermanager.userRestore(username, answer, password);
				return "redirect:/bin/user/login";
			}catch(Exception e){
				return "redirect:/bin/restore";
			}
		}
		return "redirect:/bin/user/restore";
	}

    @RequestMapping(value = "/image/{username}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getFile(@PathVariable String username)  {
        User user = usermanager.getUserByUsername(username);
        ResponseEntity<byte[]> responseEntity = null;
        if (user == null) {
            return responseEntity;
        }
        byte[] photo = null;
        if ((photo = user.getPhoto()) == null || photo.length == 0) {
            Path path = Paths.get(servletContext.getRealPath("/img/default_user_icon.png"));
            try {
                photo = Files.readAllBytes(path);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        String[] contentType = null;
        try {
            contentType = Magic.getMagicMatch(photo).getMimeType().split("/");
        } catch (MagicParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (MagicMatchNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (MagicException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(new MediaType(contentType[0], contentType[1]));
        responseEntity = new ResponseEntity<byte[]>(photo, responseHeaders, HttpStatus.CREATED);
        return responseEntity;
    }
}
