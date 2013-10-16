package ar.edu.itba.paw.web.command.validator;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.web.command.UserForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserFormValidator implements Validator{

	public boolean supports(Class<?> arg0) {
		return User.class.equals(arg0);
	}

	public void validate(Object arg0, Errors arg1) {
		UserForm form = (UserForm)arg0;
		if(!form.getPassword().equals(form.getExtrapassword())){
			arg1.reject("password","notequal");
		}
		
	}

}
