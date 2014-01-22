/*
package ar.edu.itba.paw.web.old;

import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.hashtag.HashtagRepo;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twatt.TwattRepo;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.helper.MessageHelper;
import ar.edu.itba.paw.utils.HashtagBundle;
import ar.edu.itba.paw.utils.exceptions.DuplicatedUserException;
import ar.edu.itba.paw.web.command.validator.UserFormValidator;

import com.google.common.base.Strings;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.util.LinkedList;
import java.util.List;

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
	public ModelAndView	home(@RequestParam(value="dayfilter", required=false) String sDays,HttpSession seq) throws NumberFormatException {
		ModelAndView mav = new ModelAndView();
		int days = 1;
        try {
            if (!Strings.isNullOrEmpty(sDays)) {
                days = Integer.parseInt(sDays);
            }
        } catch (Exception e) {

        }
        TwattUser localUser = userRepo.getUserByUsername((String)seq.getAttribute("user_username"));
        mav.addObject(UserController.LOCAL_USER_REFERENCER, localUser);
        DateTime filterDate = new DateTime().minusDays(days);
		List<Hashtag> hashtagList = hashtagRepo.getTrendingsHashtagsAfter(filterDate);
        List<HashtagBundle> hashtagBundles = new LinkedList<HashtagBundle>();
        for(Hashtag hashtag : hashtagList) {
            hashtagBundles.add(new HashtagBundle(hashtag, hashtagRepo.getMentions(hashtag, filterDate)));
        }
		mav.addObject("hashtags", hashtagBundles);

		mav.addObject("candidates", userRepo.getRecomendations(localUser));
		if(localUser.getFollowings().size() > 0){
            mav.addObject("followingsTwatts", twattRepo.getTwattsByFollowings(localUser));
        }
        mav.addObject("userTwatts", twattRepo.getTwattsByUsername(localUser.getUsername()));

		return mav;
	}
	
	 public void addUsers() throws DuplicatedUserException{
				 TwattUser test1 = new TwattUser("test1", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test2 = new TwattUser("test2", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test3 = new TwattUser("test3", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test4 = new TwattUser("test4", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test5 = new TwattUser("test5", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test6 = new TwattUser("test6", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test7 = new TwattUser("test7", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test8 = new TwattUser("test8", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test9 = new TwattUser("test9", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test10 = new TwattUser("test10", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test11 = new TwattUser("test11", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test12 = new TwattUser("test12", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test13 = new TwattUser("test13", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test14 = new TwattUser("test14", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test15 = new TwattUser("test15", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test16 = new TwattUser("test16", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test17 = new TwattUser("test17", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test18 = new TwattUser("test18", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test19 = new TwattUser("test19", "test", "test", "test", "test", "test", "test", null);
				 TwattUser test20 = new TwattUser("test20", "test", "test", "test", "test", "test", "test", null);
				 test1.addFollowing(test2);
				 test1.addFollowing(test3);
				 test1.addFollowing(test4);
				 test1.addFollowing(test5);
				 test1.addFollowing(test6);
				 test1.addFollowing(test7);
				 test2.addFollowing(test5);
				 test2.addFollowing(test3);
				 test2.addFollowing(test4);
				 test2.addFollowing(test1);
				 test2.addFollowing(test5);
				 test3.addFollowing(test4);
				 test3.addFollowing(test5);
				 test3.addFollowing(test6);
				 test3.addFollowing(test7);
				 test3.addFollowing(test8);
				 test4.addFollowing(test5);
				 test4.addFollowing(test6);
				 test4.addFollowing(test7);
				 test4.addFollowing(test8);
				 test4.addFollowing(test2);
				 test5.addFollowing(test6);
				 test5.addFollowing(test7);
				 test5.addFollowing(test8);
				 test5.addFollowing(test9);
				 test5.addFollowing(test10);
				 test5.addFollowing(test11);
				 test5.addFollowing(test12);
				 userRepo.registerUser(test1);
				 userRepo.registerUser(test2);
				 userRepo.registerUser(test3);
				 userRepo.registerUser(test4);
				 userRepo.registerUser(test5);
				 userRepo.registerUser(test6);
				 userRepo.registerUser(test7);
				 userRepo.registerUser(test8);
				 userRepo.registerUser(test9);
				 userRepo.registerUser(test10);
				 userRepo.registerUser(test11);
				 userRepo.registerUser(test12);
				 userRepo.registerUser(test13);
				 userRepo.registerUser(test14);
				 userRepo.registerUser(test15);
				 userRepo.registerUser(test16);
				 userRepo.registerUser(test17);
				 userRepo.registerUser(test18);
				 userRepo.registerUser(test19);
				 userRepo.registerUser(test20);
				 
		     
		     
		     
		   }
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(){
		return"redirect:user/login";
	}
	
}
*/
