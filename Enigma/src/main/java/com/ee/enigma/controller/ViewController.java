package com.ee.enigma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

	@RequestMapping({ "/index", "/" })
	public String redirect() {
		return "redirect:welcome";
	}

	@RequestMapping(value = "/welcome")
	public ModelAndView indexPage() {
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}

	@RequestMapping(value = "/profile")
	public ModelAndView profilePage() {
		ModelAndView mav = new ModelAndView("profile");
		return mav;
	}
	
	@RequestMapping(value = "/entities")
	public ModelAndView entityPage() {
		ModelAndView mav = new ModelAndView("entities");
		return mav;
	}
	
	@RequestMapping(value = "/report")
	public ModelAndView reportPage() {
		ModelAndView mav = new ModelAndView("reports");
		return mav;
	}

	@RequestMapping("/login")
	public ModelAndView loginPage(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username or password!");
		}
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");
		return model;
	}
}
