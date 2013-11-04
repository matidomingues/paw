package ar.edu.itba.paw.web;

import ar.edu.itba.paw.domain.twatt.Retwatt;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twatt.TwattRepo;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
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
       
        int localUser_id = (Integer) seq.getAttribute("user_id");
        TwattUser localUser = this.userRepo.find(localUser_id);
        
        ModelAndView mav = new ModelAndView("redirect:/bin/profile/" + localUser.getUsername());
        mav.addObject(UserController.LOCAL_USER_REFERENCER, localUser);
        twattRepo.create(new Twatt(message, localUser));
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
        TwattUser localUser = this.userRepo.find((Integer)seq.getAttribute("user_id"));

        if (!twatt.getCreator().equals(localUser) && !localUser.isEnabled() && !twatt.isDeleted()) {
            throw new InvalidOperationExcetion();
        }

        twatt.setDeleted();
        
        ModelAndView mav = new ModelAndView("redirect:/bin/user/favourites");
        mav.addObject(UserController.LOCAL_USER_REFERENCER, localUser);
        mav.addObject("success", "Has elminiado un twatt!");

        return mav;
	}

    @RequestMapping(value = "/favourite", method = RequestMethod.POST)
    public ModelAndView favourite(@RequestParam("twattId") Integer twatt_id, HttpSession seq) {
        Twatt twatt = this.twattRepo.getTwatt(twatt_id);
        TwattUser localUser = this.userRepo.find((Integer)seq.getAttribute("user_id"));

        if (localUser.isFavourite(twatt)) {
            throw new InvalidOperationExcetion();
        }

        localUser.addFavourite(twatt);

        ModelAndView mav = new ModelAndView("redirect:/bin/user/favourites");
        mav.addObject(UserController.LOCAL_USER_REFERENCER, localUser);
        mav.addObject("success", "Has agregado un twatt como favorito!");

        return mav;
    }

    @RequestMapping(value = "/unfavourite", method = RequestMethod.POST)
    public ModelAndView unfavourite(@RequestParam("twattId") Integer twatt_id, HttpSession seq) {
        Twatt twatt = this.twattRepo.getTwatt(twatt_id);
        TwattUser localUser = this.userRepo.find((Integer)seq.getAttribute("user_id"));

        if (!localUser.isFavourite(twatt)) {
            throw new InvalidOperationExcetion();
        }

        localUser.removeFavourite(twatt);

        ModelAndView mav = new ModelAndView("redirect:/bin/profile/" + localUser.getUsername());
        mav.addObject(UserController.LOCAL_USER_REFERENCER, localUser);
        mav.addObject("success", "Has removido un twatt de tus favoritos!");

        return mav;
    }

    @RequestMapping(value = "/retwatt", method = RequestMethod.POST)
    public ModelAndView retwatt(@RequestParam("twattId") Integer twatt_id, HttpSession seq) {
        Twatt twatt = this.twattRepo.getTwatt(twatt_id);
        TwattUser localUser = this.userRepo.find((Integer)seq.getAttribute("user_id"));

        Retwatt retwatt = new Retwatt(twatt, localUser);

        this.twattRepo.create(retwatt);

        ModelAndView mav = new ModelAndView("redirect:/bin/profile/" + localUser.getUsername());
        mav.addObject(UserController.LOCAL_USER_REFERENCER, localUser);
        mav.addObject("success", "Has hecho un retwatt!");

        return mav;
    }

}
