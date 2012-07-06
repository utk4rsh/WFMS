package com.wfms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {

	@RequestMapping(value="/login.html", method = RequestMethod.GET)
	public ModelAndView printWelcome(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "Spring security allows you");
		modelAndView.setViewName("success");
		return modelAndView;
	}
}