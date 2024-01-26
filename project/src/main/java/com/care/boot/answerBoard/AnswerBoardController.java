package com.care.boot.answerBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class AnswerBoardController {

	@Autowired AnswerBoardService service;
	@Autowired HttpSession session;
	
	@RequestMapping("answerForm")
	public String answerForm(String search,Model model,
			@RequestParam(value="currentPage", required = false)String cp) {
			String select = "select";
			if(search == null || search.trim().isEmpty()) {
				search = ""; select = "all"; 
			}
			service.answerForm(cp, model, search, select);
		return "customer/answerForm";
	}
	
	@RequestMapping("answerBoardWrite")
	public String answerBoardWrite() {
		
		String sessionId = (String)session.getAttribute("id");	
		if(sessionId == null || sessionId.trim().isEmpty() || !sessionId.equals("admin"))
			return "customer/answerForm";	
		
		
		return "customer/answerBoardWrite";
	}
	
	@RequestMapping("answerWriteProc")
	public String answerWriteProc(MultipartHttpServletRequest multi, RedirectAttributes ra) {
		String path = service.answerWriteProc(multi);
		return path;		
	}
	
	@RequestMapping("answerBoardContent")
	public String answerBoardContent(String no,Model model) {
		
		AnswerBoardDTO board = service.answerBoardContent(no);
		if(board == null) {
			return "redirect:answerForm";
		}
		
		model.addAttribute("board", board);
		return "customer/answerBoardContent";
	}
	
	@RequestMapping("answerBoardDeleteProc")
	public String answerBoardDeleteProc(String no,RedirectAttributes ra) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		
		  String msg = service.answerBoardDeleteProc(no);
		if(msg.equals("관리자만 삭제 할 수 있습니다."))	
			return "redirect:answerBoardContent?no="+no;
		
		return "redirect:answerForm";
	}
	
	@RequestMapping("answerBoardModify")
	public String answerBoardModify(String no, Model model){
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null || !sessionId.equals("admin"))
			return "redirect:login";
		
		AnswerBoardDTO board = service.answerBoardContent(no);
		if(board == null) {
			return "redirect:answerForm";
		}
		
		model.addAttribute("board", board);
		return "customer/answerBoardModify";
	}
	
	@RequestMapping("answerBoardModifyProc")
	public String answerBoardModifyProc(MultipartHttpServletRequest multi,String no){
		System.out.println("컨트롤러 되나");
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null || !sessionId.equals("admin"))
			return "redirect:login";
		
		
		String path = service.answerBoardModifyProc(multi, no);
		return path;		
	}
	
	
}
