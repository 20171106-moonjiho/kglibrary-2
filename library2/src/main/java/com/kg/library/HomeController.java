
package com.kg.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kg.library.Introduction.BookService;
import com.kg.library.culture.CultureService;
import com.kg.library.notice.NoticeBoardService;

@Controller
public class HomeController {
	@Autowired
	private NoticeBoardService notice_service;
	@Autowired
	private BookService Book_Service;
	@Autowired
	private CultureService clture_Service;
	
	@RequestMapping("index")
	public void index() {}
	
	@RequestMapping("header")
	public String header() {
		return "default/header";
	}	
	@RequestMapping("main")
	public String main(Model model) {
		notice_service.main_board(model);
		clture_Service.main_board(model);
		Book_Service.hit_book(model);
		Book_Service.new_book(model);
		return "default/main";
	}
	@RequestMapping("footer")
	public String footer() {
		return "default/footer";
	}

}
