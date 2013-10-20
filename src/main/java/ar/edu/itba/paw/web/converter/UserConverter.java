package ar.edu.itba.paw.web.converter;

import ar.edu.itba.paw.entity.User;
import ar.edu.itba.paw.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<String, User>{
	
	@Autowired
	private UserRepo userRepo;
	
	public User convert(final String source) {
		return userRepo.getUserByUsername(source);
	}

}
