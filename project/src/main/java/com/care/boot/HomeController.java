package com.care.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.care.boot.animal_find.Animal_find_Service;
import com.care.boot.animal_protect.Animal_protect_service;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	private Animal_find_Service service;
	@Autowired
	private Animal_protect_service service2;
	
	@RequestMapping("index")
	public String index() {
		return "index";
	}
	@RequestMapping("header")
	public String header() {
		return "default/header";
	}
	@RequestMapping("main")
	public String top(Model model) {
		service.animal_find_main(model);
		service2.animal_protect_main(model);
		return "default/main";
	}
	@RequestMapping("footer")
	public String bottom() {
		return "default/footer";
	}
	
}
