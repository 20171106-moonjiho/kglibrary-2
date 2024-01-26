package com.care.boot.animal_find;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import jakarta.servlet.http.HttpSession;

@Controller
public class Animal_find_Controller {
	 
	@Autowired
	private Animal_find_Service service;
	@Autowired
	private HttpSession session;//로그인한 사용자만 사용할수 있게
	
	@GetMapping("home2")
	public String home2() {
		return "home/home2";
	}
	@RequestMapping("animal_find")
	public String animal_find(@RequestParam(value="currentPage", required = false)String cp, Model model,String animal_group, String search, String area) {
		service.animal_find(animal_group,search,area,cp,model); 
		return "home/animal_find";
	}
	@RequestMapping("animal_find_write")
	public String animal_find_write() {
		String sessionID=(String)session.getAttribute("id");
		if(sessionID==null) {
			return "redirect:login";
		}
		return "home/animal_find_write";
	}
	@PostMapping("animal_find_writeProc")
	public String animal_find_writeProc(MultipartHttpServletRequest multi) {
		String path = service.animal_find_writeProc(multi);
		return path;
	}
	
	@RequestMapping("animal_find_Content")
	public String animal_find_Content(String no, Model model) {
		Animal_find_DTO board = service.animal_find_Content(no);
		if(board == null) {
			return "redirect:animal_find";
		}
		model.addAttribute("board",board);
		return "home/animal_find_Content";
	}
	
	@RequestMapping("ContentDeleteProc")
	public String ContentDeleteProc(String no) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		
		String msg = service.ContentDeleteProc(no);
		if(msg.equals("작성자만 삭제 할 수 있습니다."))
			return "redirect:animal_find?no="+no;
		
		return "redirect:animal_find";
	}
	
	@RequestMapping("animal_find_Modify")
	public String animal_find_Modify(String no, Model model) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		String path = service.animal_find_Modify(no, model);
		return path;
	}
	
	@PostMapping("animal_find_ModifyProc")
	public String animal_find_ModifyProc(Animal_find_DTO board, RedirectAttributes ra, MultipartHttpServletRequest multi) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		String msg = service.animal_find_ModifyProc(board,multi);
		ra.addFlashAttribute("msg", msg);
		 
		if(msg.equals("게시글 수정 성공"))
			return "redirect:animal_find_Content?no="+board.getNo();
		
		return "redirect:animal_find_Modify?no="+board.getNo();
	}
}
