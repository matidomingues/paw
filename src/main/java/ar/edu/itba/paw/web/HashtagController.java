package ar.edu.itba.paw.web;

import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.service.HashtagService;
import ar.edu.itba.paw.service.TwattService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/hashtag")
public class HashtagController {

    @Autowired
    private HashtagService hashtagService;

    @Autowired
    private TwattService twattService;

    @RequestMapping(value = "/{hashtagName}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String hashtagName) {
        ModelAndView mav = new ModelAndView("hashtag/view");
        Hashtag hashtag = hashtagService.getHashtag(hashtagName);
        if (hashtag == null) {
            mav.addObject("error", "No existe ese hashtag");
        } else {
            List<Twatt> twatts = twattService.getTwattsByHashtag(hashtag);
            mav.addObject("hashtag", hashtag);
            mav.addObject("twatts", twatts);
        }
        return mav;
    }

}
