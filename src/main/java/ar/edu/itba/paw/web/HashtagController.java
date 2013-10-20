package ar.edu.itba.paw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.entity.Hashtag;
import ar.edu.itba.paw.entity.Twatt;
import ar.edu.itba.paw.repository.HashtagRepo;
import ar.edu.itba.paw.repository.TwattRepo;

import java.util.List;

@Controller
@RequestMapping(value = "/hashtag")
public class HashtagController {

    @Autowired
    private HashtagRepo hashtagRepo;

    @Autowired
    private TwattRepo twattRepo;

    @RequestMapping(value = "/{hashtagName}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String hashtagName) {
        ModelAndView mav = new ModelAndView("hashtag/view");
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
