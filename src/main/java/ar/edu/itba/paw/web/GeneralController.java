package ar.edu.itba.paw.web;

import ar.edu.itba.paw.helper.MessageHelper;
import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.hashtag.HashtagRepo;
import ar.edu.itba.paw.domain.twatt.TwattRepo;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.utils.HashtagBundle;
import ar.edu.itba.paw.web.command.validator.UserFormValidator;

import com.google.common.base.Strings;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView	home(@RequestParam(value="dayfilter", required=false) String sDays) {
		ModelAndView mav = new ModelAndView();
		int days = 1;
        try {
            if (!Strings.isNullOrEmpty(sDays)) {
                days = Integer.parseInt(sDays);
            }
        } catch (Exception e) {

        }
        DateTime filterDate = new DateTime().minusDays(days);
		List<Hashtag> hashtagList = hashtagRepo.getTrendingsHashtagsAfter(filterDate);
        List<HashtagBundle> hashtagBundles = new LinkedList<HashtagBundle>();
        for(Hashtag hashtag : hashtagList) {
            hashtagBundles.add(new HashtagBundle(hashtag, hashtagRepo.getMentions(hashtag, filterDate)));
        }
		mav.addObject("hashtags", hashtagBundles);
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(){
		return"redirect:user/login";
	}
	
}
