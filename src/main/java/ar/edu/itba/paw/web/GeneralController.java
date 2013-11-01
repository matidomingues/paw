package ar.edu.itba.paw.web;

import ar.edu.itba.paw.helper.MessageHelper;
import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.hashtag.HashtagRepo;
import ar.edu.itba.paw.domain.twatt.TwattRepo;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.utils.HashtagBundle;
import ar.edu.itba.paw.utils.exceptions.DuplicatedUserException;
import ar.edu.itba.paw.web.command.validator.UserFormValidator;

import com.google.common.base.Strings;

import org.apache.commons.configuration.ConfigurationException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class GeneralController {
	
	@Autowired
    private HashtagRepo hashtagRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TwattRepo twattRepo;

    @Autowired
    private MessageHelper messagemanager;

    @Autowired
    private UserFormValidator validator;

	@RequestMapping(value="home", method = RequestMethod.GET)
	public ModelAndView	home(@RequestParam(value="dayfilter", required=false) String sDays,HttpSession seq) throws NumberFormatException, ConfigurationException {
		ModelAndView mav = new ModelAndView();
		int days = 1;
        try {
            if (!Strings.isNullOrEmpty(sDays)) {
                days = Integer.parseInt(sDays);
            }
        } catch (Exception e) {

        }
        TwattUser user = userRepo.getUserByUsername((String)seq.getAttribute("user_username"));
        DateTime filterDate = new DateTime().minusDays(days);
		List<Hashtag> hashtagList = hashtagRepo.getTrendingsHashtagsAfter(filterDate);
        List<HashtagBundle> hashtagBundles = new LinkedList<HashtagBundle>();
        for(Hashtag hashtag : hashtagList) {
            hashtagBundles.add(new HashtagBundle(hashtag, hashtagRepo.getMentions(hashtag, filterDate)));
        }
		mav.addObject("hashtags", hashtagBundles);
		//List<TwattUser> list = userRepo.getRecomendationsByUser(user);
		//System.out.println(list);
		if(user.getFollowings().size() != 0){
			mav.addObject("followingsTwatts", twattRepo.getTwattsByFollowings(user));
		}
		
		return mav;
	}
	
	public void addUsers() throws DuplicatedUserException{
		userRepo.registerUser(new TwattUser("test1", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test2", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test3", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test4", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test5", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test6", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test7", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test8", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test9", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test10", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test11", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test12", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test13", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test14", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test15", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test16", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test17", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test18", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test19", "test", "test", "test", "test", "test", "test", null));
		userRepo.registerUser(new TwattUser("test20", "test", "test", "test", "test", "test", "test", null));
		userRepo.getUserByUsername("test").addFollowing(userRepo.getUserByUsername("test1"));
		
		
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(){
		return"redirect:user/login";
	}
	
}
