package ar.edu.itba.paw.web;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;

import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.HashtagService;
import ar.edu.itba.paw.service.MessageService;
import ar.edu.itba.paw.service.TwattService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.utils.HashtagBundle;
import ar.edu.itba.paw.utils.exceptions.DuplicatedUserException;
import ar.edu.itba.paw.web.command.UserForm;
import ar.edu.itba.paw.web.command.validator.UserFormValidator;

@Controller
@RequestMapping("/")
public class GeneralController {
	
	private HashtagService hashtagmanager;
	private UserService usermanager;
	private TwattService twattmanager;
	private MessageService messagemanager;
	private UserFormValidator validator;
	
	@Autowired
	public GeneralController(HashtagService hastagService, UserService userService, TwattService twattService, MessageService messagemanager, UserFormValidator userFormValidator){
			this.hashtagmanager = hastagService;
			this.usermanager = userService;
			this.twattmanager = twattService;
			this.validator = userFormValidator;
			this.messagemanager = messagemanager;
	}
	
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
        DateTime filterDate = DateTime.now().minusDays(days);
		List<Hashtag> hashtagList = hashtagmanager.getTrendingsHashtagsAfter(filterDate);
        List<HashtagBundle> hashtagBundles = new LinkedList<HashtagBundle>();
        for(Hashtag hashtag : hashtagList) {
            hashtagBundles.add(new HashtagBundle(hashtag, hashtagmanager.getMentions(hashtag, filterDate)));
        }
		mav.addObject("hashtags", hashtagBundles);
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(){
		return"redirect:user/login";
	}
	
}
