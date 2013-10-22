package ar.edu.itba.paw.web.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;

@Component
public class UserFormatter implements Formatter<TwattUser>{
	
	private UserRepo userRepo;
	
	@Autowired
	public UserFormatter(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public TwattUser parse(String arg0, Locale arg1) throws ParseException {
		return userRepo.getUserByUsername(arg0);
	}
	
	@Override
	public String print(TwattUser arg0, Locale arg1) {
		return arg0.getUsername();
	}
}
