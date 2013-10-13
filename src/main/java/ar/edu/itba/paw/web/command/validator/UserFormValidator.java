package ar.edu.itba.paw.web.command.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.web.command.UserForm;

@Component
public class UserFormValidator implements Validator{

	public boolean supports(Class<?> arg0) {
		return User.class.equals(arg0);
	}

	public void validate(Object arg0, Errors arg1) {
		UserForm form = (UserForm)arg0;
		System.out.println(form.getUsername());
		if(!form.getPassword().equals(form.getExtrapassword())){
			arg1.reject("password","notequal");
		}
		
	}

}
