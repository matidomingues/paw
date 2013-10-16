package ar.edu.itba.paw.web;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;

import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.TwattService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.utils.exceptions.InvalidOperationExcetion;
import ar.edu.itba.paw.utils.exceptions.MessageEmptyException;

@Controller
public class TwattController {

    @Autowired
	private UserService usermanager;

    @Autowired
	private TwattService twattmanager;

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView add(@RequestParam("message") String message, HttpSession seq){
		
        if (Strings.isNullOrEmpty(message)) {
            throw new MessageEmptyException();
        }
       
        int user_id = (Integer) seq.getAttribute("user_id");
        User user = this.usermanager.find(user_id);
        
        ModelAndView mav = new ModelAndView("redirect:/bin/profile/"+user.getUsername());
        twattmanager.addTwatt(new Twatt(user, message, false, DateTime.now()));
        boolean result = true;
        if (!result) {
			mav.addObject("error", "No es posible Twattear en este momento");
		} else {
			mav.addObject("success", "Se Twatteo correctamente");
		}
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam("twattId") Integer twatt_id, HttpSession seq){
		
        Twatt twatt = this.twattmanager.getTwatt(twatt_id);
        User user = this.usermanager.find((Integer)seq.getAttribute("user_if"));

        if (!twatt.getCreator().equals(user) && !user.isEnabled() && !twatt.isDeleted()) {
            throw new InvalidOperationExcetion();
        }

        this.twattmanager.delete(twatt);
        
        ModelAndView mav = new ModelAndView("forward:/bin/profile/"+user.getUsername());
        mav.addObject("success", "Has elminiado un twatt!");

        return mav;
	}
	
}
