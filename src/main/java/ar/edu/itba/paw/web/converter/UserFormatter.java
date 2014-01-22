package ar.edu.itba.paw.web.converter;

import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class UserFormatter implements Formatter<TwattUser>{
	
	private UserRepo userRepo;
	
	@Autowired
	public UserFormatter(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public TwattUser parse(String arg0, Locale arg1) throws ParseException {
        TwattUser user =  userRepo.getUserByUsername(arg0);
        if (user == null) throw new IllegalArgumentException("Unknown user");
        return  user;
	}
	
	@Override
	public String print(TwattUser arg0, Locale arg1) {
		return arg0.getUsername();
	}
}
