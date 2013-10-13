package ar.edu.itba.paw.web;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.HashtagService;
import ar.edu.itba.paw.service.MessageService;
import ar.edu.itba.paw.service.TwattService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.utils.HashtagBundle;
import ar.edu.itba.paw.utils.exceptions.DuplicatedUserException;
import ar.edu.itba.paw.web.command.UserForm;
import ar.edu.itba.paw.web.command.validator.UserFormValidator;

import com.google.common.base.Strings;

@Controller
public class UserController {
	
	private HashtagService hashtagmanager;
	private UserService usermanager;
	private TwattService twattmanager;
	private MessageService messagemanager;
	private UserFormValidator validator;
	
	@Autowired
	public UserController(HashtagService hastagService, UserService userService, TwattService twattService, MessageService messagemanager, UserFormValidator userFormValidator){
			this.hashtagmanager = hastagService;
			this.usermanager = userService;
			this.twattmanager = twattService;
			this.validator = userFormValidator;
			this.messagemanager = messagemanager;
	}
	
	@RequestMapping(value="/profile/{username}", method = RequestMethod.GET)
	public ModelAndView user(@PathVariable String username){
		ModelAndView mav = new ModelAndView("user/userdetail");
		User user = usermanager.getUserByUsername(username);
		if(user != null){
			mav.addObject("searchuser", user);
			List<Twatt> twatts = twattmanager.getTwattsByUsername(user.getUsername());
			for(Twatt twatt: twatts){
				twatt.setMessage(messagemanager.prepareMessage("/", twatt.getMessage()));
			}
			mav.addObject("twatts", twatts);
			return mav;
		}else{
			mav = new ModelAndView("forward:/bin/find");
			mav.addObject("error", "No existe ningun usuario con ese nombre");
			return mav;
//			req.setAttribute("error", "No existe ningun usuario con ese nombre");
//			req.getRequestDispatcher("/find").forward(req, resp);
			
		}
	}
	
	@RequestMapping(value="/find", method = RequestMethod.GET)
	public ModelAndView find(){
		ModelAndView mav = new ModelAndView("user/find");
		mav.addObject("users", usermanager.getAll());
		return mav;
	}
	
	@RequestMapping(value="/find", method = RequestMethod.POST)
	public ModelAndView find(@RequestParam("username") String username){
		ModelAndView mav = new ModelAndView("user/find");
		mav.addObject("users", usermanager.find(username));
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView settings(HttpSession seq){
		ModelAndView mav = new ModelAndView();
		Integer user_id = (Integer)seq.getAttribute("user_id");
		User user = usermanager.find(user_id);
		mav.addObject("userForm", new UserForm(user));
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String settings(UserForm editForm, Errors errors, HttpSession seq){
		validator.validate(editForm, errors);
		if(errors.hasErrors()){
			return null;
		}
		try{
			usermanager.updateUser(editForm.build());
		}catch(IllegalArgumentException e){
			errors.rejectValue("username", "invalid");
			return null;
		}
		return "redirect:/bin/profile/"+editForm.getUsername();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView	login() {
		return new ModelAndView();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String login(@RequestParam("username") User user,
			@RequestParam("password") String password, HttpSession seq) {
		if(user.checkPassword(password)){
			seq.setAttribute("user_id", user.getId());
			seq.setAttribute("user_username", user.getUsername());
			return "redirect:/bin/home";
		}
		return "forward:login";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView	register() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("userForm", new UserForm());
		return mav;
	}

	
	@RequestMapping(method = RequestMethod.POST)
	public String register(UserForm userForm, Errors errors ) {
		System.out.println(userForm.getUsername());
		validator.validate(userForm, errors);
		if(errors.hasErrors()){
			return null;
		}
		try{
			usermanager.registerUser(userForm.build());
		}catch(DuplicatedUserException e){
			errors.rejectValue("username", "duplicated");
			return null;
		}
		return "redirect:login";	
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String logout(HttpSession seq) {
		seq.removeAttribute("user_id");
		seq.removeAttribute("user_username");
		return "redirect:/bin/user/login";
	}
	
	
}
