package ar.edu.itba.paw.web.converter;


import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;

@Component
public class UserConverter implements IConverter<TwattUser>{
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public TwattUser convertToObject(String value, Locale locale) {
		TwattUser user =  userRepo.getUserByUsername(value);
        if (user == null) throw new IllegalArgumentException("Unknown user");
        return user;
	}

	@Override
	public String convertToString(TwattUser value, Locale locale) {
		return value.toString();
	}

}
