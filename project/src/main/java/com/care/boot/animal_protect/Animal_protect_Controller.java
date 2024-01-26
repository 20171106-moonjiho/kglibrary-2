package com.care.boot.animal_protect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import jakarta.servlet.http.HttpSession;

@Controller
public class Animal_protect_Controller {
	@Autowired
	private Animal_protect_service service;
	@Autowired
	private HttpSession session;//로그인한 사용자만 사용할수 있게
	
	@RequestMapping("animal_protect")
	public String animal_protect(@RequestParam(value="currentPage", required = false)String cp, Model model,String animal_group, String search, String area) {
		service.animal_protect(animal_group,search,area,cp,model); 
		return "animal_protect/animal_protect";
	}
	
	@RequestMapping("animal_protect_write")
	public String animal_protect_write() {
		String sessionID=(String)session.getAttribute("id");
		if(sessionID==null) {
			return "redirect:login";
		}
		return "animal_protect/animal_protect_write";
	}
	
	@PostMapping("animal_protect_writeProc")
	public String animal_protect_writeProc(MultipartHttpServletRequest multi) {
		String path = service.animal_protect_writeProc(multi);
		return path;
	}
	
	@RequestMapping("animal_protect_Content2")
	public String animal_protect_Content(String no, Model model) {
		Animal_protect_DTO board = service.animal_protect_Content(no);
		if(board == null) {
			return "redirect:animal_protect"; 
		}
		model.addAttribute("board",board);
		return "animal_protect/animal_protect_Content";
	}
	
	@RequestMapping("ContentDeleteProc2")
	public String ContentDeleteProc2(String no) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		
		String msg = service.ContentDeleteProc2(no);
		if(msg.equals("작성자만 삭제 할 수 있습니다."))
			return "redirect:animal_protect?no="+no;
		
		return "redirect:animal_protect";
	}
	
	@RequestMapping("animal_protect_Modify")
	public String animal_protect_Modify(String no, Model model) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		String path = service.animal_protect_Modify(no, model);
		return path;
	}
	
	@PostMapping("animal_protect_ModifyProc")
	public String animal_protect_ModifyProc(Animal_protect_DTO board, RedirectAttributes ra, MultipartHttpServletRequest multi) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		String msg = service.animal_protect_ModifyProc(board,multi);
		ra.addFlashAttribute("msg", msg);
		 
		if(msg.equals("게시글 수정 성공"))
			return "redirect:animal_protect_Content2?no="+board.getNo();
		
		return "redirect:animal_protect_Modify?no="+board.getNo();
	}
}
