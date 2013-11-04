package ar.edu.itba.paw.web;

import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.hashtag.HashtagRepo;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twatt.TwattRepo;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/hashtag")
public class HashtagController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private HashtagRepo hashtagRepo;

    @Autowired
    private TwattRepo twattRepo;

    @RequestMapping(value = "/{hashtagName}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String hashtagName, HttpSession seq) {
        ModelAndView mav = new ModelAndView("hashtag/view");
        TwattUser localUser = userRepo.getUserByUsername((String)seq.getAttribute(UserController.SESSION_USERNAME));
        mav.addObject(UserController.LOCAL_USER_REFERENCER, localUser);
        Hashtag hashtag = hashtagRepo.getHashtag(hashtagName);
        if (hashtag == null) {
            mav.addObject("error", "No existe ese hashtag");
        } else {
            List<Twatt> twatts = twattRepo.getTwattsByHashtag(hashtag);
            mav.addObject("hashtag", hashtag);
            mav.addObject("twatts", twatts);
        }
        return mav;
    }

}
