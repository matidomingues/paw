package ar.edu.itba.paw.helper;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import ar.edu.itba.paw.manager.TwattDAO;
import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.User;

public class TwattHelper {

	private UserHelper usermanager = new UserHelper();
	private TwattDAO twattmanager = TwattDAO.getInstance();
	
	public boolean addTwatt(UUID uuid, String message){
		User user = usermanager.getUserBySession(uuid);
		if(user != null){
			return twattmanager.addTwatt(user, message);
		}
		return false;
	}
	
	public Set<Twatt> getTwattsByUUID(UUID uuid){
		User user = usermanager.getUserBySession(uuid);
		if(user != null){
			return prepareHrefLink(twattmanager.getTwattsByUser(user));
		}
		return new HashSet<Twatt>();
	}
	
	public Set<Twatt> getTwattsByUsername(String username){
		User user = usermanager.getUserByUsername(username);
		if(user != null){
			return prepareHrefLink(twattmanager.getTwattsByUser(user));
		}
		return new HashSet<Twatt>();
	}
	
	public Set<Twatt> prepareHrefLink(Set<Twatt> twatts){
		for(Twatt twatt: twatts){
			twatt.setMessage(MessageHelper.prepareMessage(twatt.getMessage()));
		}
		return twatts;
	}
	
}
