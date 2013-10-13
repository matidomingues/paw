package ar.edu.itba.paw.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.utils.exceptions.DuplicatedUserException;
import ar.edu.itba.paw.web.command.UserForm;
import ar.edu.itba.paw.web.command.validator.UserFormValidator;

@Controller
@RequestMapping("/welcome")
public class WelcomeController {

	private UserService userService;
	private UserFormValidator validator;
	
	@Autowired
	public WelcomeController(UserFormValidator registerFormValidator, UserService userService){
		this.validator = registerFormValidator;
		this.userService = userService;
	}
	

}
