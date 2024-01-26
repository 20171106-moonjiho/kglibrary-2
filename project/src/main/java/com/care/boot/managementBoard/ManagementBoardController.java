package com.care.boot.managementBoard;

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
public class ManagementBoardController {

	@Autowired ManagementBoardService service;
	@Autowired HttpSession session;
	
	@RequestMapping("managementForm")
	public String managementForm(String search,Model model,
			@RequestParam(value="currentPage", required = false)String cp) {
			String select = "select";
			if(search == null || search.trim().isEmpty()) {
				search = ""; select = "all"; 
			}
			service.managementForm(cp, model, search, select);
		return "customer/managementForm";
	}
	
	@RequestMapping("managementBoardWrite")
	public String managementBoardWrite() {
		
		String sessionId = (String)session.getAttribute("id");	
		if(sessionId == null || sessionId.trim().isEmpty() || !sessionId.equals("admin"))
			return "customer/managementForm";	
		
		
		return "customer/managementBoardWrite";
	}
	
	@RequestMapping("managementWriteProc")
	public String managementWriteProc(MultipartHttpServletRequest multi, RedirectAttributes ra) {
		String path = service.managementWriteProc(multi);
		return path;		
	}
	
	@RequestMapping("managementBoardContent")
	public String managementBoardContent(String no,Model model) {
		
		ManagementBoardDTO board = service.managementBoardContent(no);
		if(board == null) {
			return "redirect:managementForm";
		}
		
		model.addAttribute("board", board);
		return "customer/managementBoardContent";
	}
	
	@RequestMapping("managementBoardDeleteProc")
	public String managementBoardDeleteProc(String no,RedirectAttributes ra) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		
		  String msg = service.managementBoardDeleteProc(no);
		if(msg.equals("관리자만 삭제 할 수 있습니다."))	
			return "redirect:managementBoardContent?no="+no;
		
		return "redirect:managementForm";
	}
	
	@RequestMapping("managementBoardModify")
	public String managementBoardModify(String no, Model model){
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null || !sessionId.equals("admin"))
			return "redirect:login";
		
		ManagementBoardDTO board = service.managementBoardContent(no);
		if(board == null) {
			return "redirect:managementForm";
		}
		
		model.addAttribute("board", board);
		return "customer/managementBoardModify";
	}
	
	@RequestMapping("managementBoardModifyProc")
	public String managementBoardModifyProc(MultipartHttpServletRequest multi,String no){
		System.out.println("컨트롤러 되나");
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null || !sessionId.equals("admin"))
			return "redirect:login";
		
		
		String path = service.managementBoardModifyProc(multi, no);
		return path;		
	}
	
	
}
