package ar.edu.itba.paw.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;

@Component
public class UserConverter implements Converter<String, User>{
	
	private UserService service;
	
	@Autowired
	public UserConverter(UserService service) {
		this.service = service;
	}
	
	public User convert(final String source) {
		return service.getUserByUsername(source);
	}

}
