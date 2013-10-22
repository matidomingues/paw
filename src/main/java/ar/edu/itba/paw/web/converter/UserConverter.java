package ar.edu.itba.paw.web.converter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;

@Component
public class UserConverter implements Converter<String, TwattUser>{
	
	private UserRepo userRepo;
	
	@Autowired
	public UserConverter(UserRepo service) {
		this.userRepo = service;
	}
	
	public TwattUser convert(final String source) {
		return userRepo.getUserByUsername(source);
	}

}
