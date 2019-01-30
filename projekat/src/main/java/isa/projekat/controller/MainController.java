package isa.projekat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import isa.projekat.model.User;
import isa.projekat.security.TokenUtils;
import isa.projekat.service.CustomUserDetailsService;

@Controller
public class MainController {
	

	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
	TokenUtils tokenUtils;
	@RequestMapping(value = "/")
	public ModelAndView method(HttpServletRequest request) {
	
	    	return new ModelAndView("redirect:" + "index.html");
		

	}
}
