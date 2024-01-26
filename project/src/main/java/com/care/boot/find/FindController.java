package com.care.boot.find;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import jakarta.servlet.http.HttpSession;


/**
 * 커뮤니티 > 유기견 인증 게시판
 */
@Controller
public class FindController {
	
	@Autowired private FindService service;
	
	@Autowired private HttpSession session;
	
	/**
	 * 글 목록	화면
	 * @param model
	 * @param cp
	 * @return
	 */
	@RequestMapping("findForm")
	public String findForm(Model model,	@RequestParam(value="currentPage", required = false)String cp) {
		service.findFrom(cp, model);
		return "community/findForm";
	}
	
	
	/**
	 * 글 작성 화면
	 * @return
	 */
	@RequestMapping("findWrite")
	public String findWrite() {
		System.out.println("FindController - findWrite");
		return"community/findWrite";
	}
	
	/**
	 * 글 등록
	 * @param multi
	 * @return
	 */
	@RequestMapping("findWriteProc")
	public String findWriteProc(MultipartHttpServletRequest multi) {
		System.out.println("FindController - findWriteProc");
		String path = service.findWriteProc(multi);
		System.out.println(path);
		return path;
		
	}
	
	/**
	 * 글 상세 화면
	 * @param no
	 * @param model
	 * @return
	 */
	@RequestMapping("findContent")
	public String findContent(@RequestParam String no, Model model) {
		FindDTO find = service.findContent(no);
		model.addAttribute("find", find);
		return "community/findContent";
	}
	
	/**
	 * 글 삭제
	 * @param no
	 */
	@RequestMapping("deleteFind")
	public String deleteFind(@RequestParam String no) {
		service.deleteFind(no);
		return "redirect:findForm";
	}
	
	/**
	 * 글 수정 화면
	 * @param no
	 */
	@RequestMapping("editFind")
	public String editFind(@RequestParam String no, Model model) {
		FindDTO find = service.editFind(no);
		model.addAttribute("find", find);
		return "community/findWrite";
	}
	
	/**
	 * 글 수정
	 * @param multi
	 * @return
	 */
	@RequestMapping("findEditProc")
	public String findEditProc(MultipartHttpServletRequest multi) {
		service.findEditProc(multi);
		return "redirect:findForm";
	}
	
	/**
	 * 댓글 등록
	 * @param commentDTO
	 * @return
	 */
	@PostMapping("insertComment")
	@ResponseBody
	public int insertComment(@RequestBody CommentDTO commentDTO) {
		System.out.println(commentDTO);
		return service.insertComment(commentDTO);
	}
	/**
	 * 댓글삭제
	 * 
	 */
	@RequestMapping("deleteComment")
	@ResponseBody
	public int deleteComment(@RequestBody CommentDTO DTO) {
		return service.deleteComment(DTO);
	}
	
	
}	


