package isa.projekat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	@RequestMapping(value = "/")
	public ModelAndView method() {
	    return new ModelAndView("redirect:" + "index.html");
	}
}
