package ar.edu.itba.paw.web;

import ar.edu.itba.paw.repository.UrlRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/s")
public class UrlController {

	@Autowired
    UrlRepo urlRepo;
	
	@RequestMapping(value="{path}", method = RequestMethod.GET)
	public String user(@PathVariable String path){
		String redirect = urlRepo.resolve(path);
		return "redirect:"+redirect;
	}
}
