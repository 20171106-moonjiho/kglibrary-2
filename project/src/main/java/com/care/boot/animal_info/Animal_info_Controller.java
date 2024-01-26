package com.care.boot.animal_info;

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
public class Animal_info_Controller {
	@Autowired
	private Animal_info_Service service;
	@Autowired
	private HttpSession session;//로그인한 사용자만 사용할수 있게
	
	@RequestMapping("animal_info")
	public String animal_info(@RequestParam(value="currentPage", required = false)String cp, Model model,String search_select,String search) {
		service.animal_info(search_select,search,cp,model);
		return "animal_info/animal_info";
	}
	@RequestMapping("animal_info_write")
	public String animal_info_write() {
		String sessionID=(String)session.getAttribute("id");
		if(sessionID==null) {
			return "redirect:login";
		}
		return "animal_info/animal_info_write";
	}
	@PostMapping("animal_info_writeProc")
	public String animal_info_writeProc(MultipartHttpServletRequest multi) {
		String path = service.animal_info_writeProc(multi);
		return path;
	}
	
	@RequestMapping("animal_info_Content")
	public String animal_info_Content(String no, Model model) {
		Animal_info_DTO board = service.animal_info_Content(no);
		service.askReplyView2(no, model);
		if(board == null) {
			return "redirect:animal_info";
		}
		model.addAttribute("board",board);
		return "animal_info/animal_info_Content";
	}
	
	@RequestMapping("ContentDeleteProc3")
	public String ContentDeleteProc3(String no) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		
		String msg = service.ContentDeleteProc3(no);
		if(msg.equals("작성자만 삭제 할 수 있습니다."))
			return "redirect:animal_info?no="+no;
		
		return "redirect:animal_info";
	}
	
	@RequestMapping("animal_info_Modify")
	public String animal_info_Modify(String no, Model model) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		String path = service.animal_info_Modify(no, model);
		return path;
	}
	
	@PostMapping("animal_info_ModifyProc")
	public String animal_info_ModifyProc(Animal_info_DTO board, RedirectAttributes ra, MultipartHttpServletRequest multi) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		String msg = service.animal_info_ModifyProc(board,multi);
		ra.addFlashAttribute("msg", msg);
		 
		if(msg.equals("게시글 수정 성공"))
			return "redirect:animal_info_Content?no="+board.getNo();
		
		return "redirect:animal_info_Modify?no="+board.getNo();
	}
	
	@RequestMapping("askReplyWrite2")
	public String askReplyWrite2(Animal_info_comment_DTO adto,String re_no) {
		
		String sessionId = (String)session.getAttribute("id");	
		if(sessionId == null || sessionId.trim().isEmpty())
			return "redirect:login";
		
		if(re_no == null || re_no.trim().isEmpty())
			re_no = "0";
	
		int no = Integer.parseInt(re_no);
		adto.setNo(no);
		
		String msg =service.askReplyWrite2(adto);
		
		return "redirect:/animal_info_Content?no=" +adto.getPage_no();
	}
	
	@RequestMapping("askReplyBoardDeleteProc2")
	public String askReplyBoardDeleteProc2(String no,String page_no,RedirectAttributes ra) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		
		System.out.println("뭐고  no --- " + no);
		
		String msg = service.askReplyBoardDeleteProc2(no,page_no, sessionId);
		if(msg.equals("관리자 또는 글쓴이만 삭제 할 수 있습니다.")) {
			ra.addFlashAttribute("msg",msg);
			return "redirect:animal_info?no="+page_no;
		}
		
		
		return "redirect:/animal_info_Content?no=" +page_no;
	}
	
}
