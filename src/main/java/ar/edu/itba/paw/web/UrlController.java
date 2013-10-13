package ar.edu.itba.paw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ar.edu.itba.paw.service.UrlService;

@Controller
@RequestMapping("/s")
public class UrlController {

	UrlService urlService;
	
	@Autowired
	public UrlController(UrlService urlService){
		this.urlService = urlService;
	}
	
	@RequestMapping(value="{path}", method = RequestMethod.GET)
	public String user(@PathVariable String path){
		String redirect = urlService.resolve(path);
		return "redirect:"+redirect;
	}
}
