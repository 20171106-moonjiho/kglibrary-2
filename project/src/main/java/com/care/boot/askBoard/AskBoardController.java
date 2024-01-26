package com.care.boot.askBoard;

import java.util.List;

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
public class AskBoardController {

	@Autowired AskBoardService service;
	@Autowired HttpSession session;
	
	@RequestMapping("askForm")
	public String askForm(String search,Model model,
			@RequestParam(value="currentPage", required = false)String cp) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null || sessionId.trim().isEmpty())
			return "redirect:login";
		
			String select = "select";
			if(search == null || search.trim().isEmpty()) {
				search = ""; select = "all"; 
			}
			service.askForm(cp, model, search, select, sessionId);
			
			
		return "customer/askForm";
	}
	
	@RequestMapping("askBoardWrite")
	public String askBoardWrite() {
		
		String sessionId = (String)session.getAttribute("id");	
		if(sessionId == null || sessionId.trim().isEmpty())
			return "redirect:login";	
		
		return "customer/askBoardWrite";
	}
	
	@RequestMapping("askWriteProc")
	public String askWriteProc(MultipartHttpServletRequest multi, RedirectAttributes ra) {
		String path = service.askWriteProc(multi);
		return path;		
	}
	
	@RequestMapping("askBoardContent")
	public String askBoardContent(String no,Model model) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		
		AskBoardDTO board = service.askBoardContent(no);
		
		if(board == null) {
			System.out.println("댓글 달 때 no ="  + no);
			return "redirect:askForm";
		}
		
		service.askReplyView(no, model);

		model.addAttribute("board", board);
		return "customer/askBoardContent";
	}
	
	@RequestMapping("askBoardDeleteProc")
	public String askBoardDeleteProc(String no,RedirectAttributes ra) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		
		  String msg = service.askBoardDeleteProc(no, sessionId);
		if(msg.equals("관리자 또는 글쓴이만 삭제 할 수 있습니다."))	
			return "redirect:askBoardContent?no="+no;
		
		return "redirect:askForm";
	}
	
	@RequestMapping("askReplyWrite")
	public String askReplyWrite(AskReplyDTO adto,String re_no) {
		
		String sessionId = (String)session.getAttribute("id");	
		if(sessionId == null || sessionId.trim().isEmpty())
			return "redirect:login";
		
		if(re_no == null || re_no.trim().isEmpty())
			re_no = "0";
	
		int no = Integer.parseInt(re_no);
		adto.setNo(no);
		
		String msg =service.askReplyWrite(adto);
		
		return "redirect:/askBoardContent?no=" +adto.getPage_no();
	}
	
	@RequestMapping("askReplyBoardDeleteProc")
	public String askReplyBoardDeleteProc(String no,String page_no,RedirectAttributes ra) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		
		System.out.println("뭐고  no --- " + no);
		
		String msg = service.askReplyBoardDeleteProc(no,page_no, sessionId);
		if(msg.equals("관리자 또는 글쓴이만 삭제 할 수 있습니다.")) {
			ra.addFlashAttribute("msg",msg);
			return "redirect:askBoardContent?no="+page_no;
		}
		
		
		return "redirect:/askBoardContent?no=" +page_no;
	}
}
