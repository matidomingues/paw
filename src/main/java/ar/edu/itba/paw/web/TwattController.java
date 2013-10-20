package ar.edu.itba.paw.web;

import ar.edu.itba.paw.hibernate.entity.Twatt;
import ar.edu.itba.paw.hibernate.entity.TwattUser;
import ar.edu.itba.paw.hibernate.repository.TwattRepo;
import ar.edu.itba.paw.hibernate.repository.UserRepo;
import ar.edu.itba.paw.utils.exceptions.InvalidOperationExcetion;
import ar.edu.itba.paw.utils.exceptions.MessageEmptyException;

import com.google.common.base.Strings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class TwattController {

    @Autowired
	private UserRepo userRepo;

    @Autowired
	private TwattRepo twattRepo;

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView add(@RequestParam("message") String message, HttpSession seq){
		
        if (Strings.isNullOrEmpty(message)) {
            throw new MessageEmptyException();
        }
       
        int user_id = (Integer) seq.getAttribute("user_id");
        TwattUser user = this.userRepo.find(user_id);
        
        ModelAndView mav = new ModelAndView("redirect:/bin/profile/"+user.getUsername());
        twattRepo.addTwatt(new Twatt(message, user));
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
		
        Twatt twatt = this.twattRepo.getTwatt(twatt_id);
        TwattUser user = this.userRepo.find((Integer)seq.getAttribute("user_if"));

        if (!twatt.getCreator().equals(user) && !user.isEnabled() && !twatt.isDeleted()) {
            throw new InvalidOperationExcetion();
        }

        this.twattRepo.delete(twatt);
        
        ModelAndView mav = new ModelAndView("forward:/bin/profile/"+user.getUsername());
        mav.addObject("success", "Has elminiado un twatt!");

        return mav;
	}
	
}
