package com.care.boot.find;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.boot.PageService;
import com.care.boot.utils.FileUploadUtils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;



@Service
public class FindService {
	
	@Autowired private FindMapper mapper;
	

	@Autowired
	ServletContext servletContext;
	
	
	/**
	 * 글 목록
	 * @param cp
	 * @param model
	 */
	public void findFrom(String cp, Model model) {
		//System.out.println("FindService - findForm");
		
		int currentPage = 1;
		try{
			currentPage = Integer.parseInt(cp);
		}catch(Exception e){
			currentPage = 1;
		}
		
		int pageBlock = 10; // 한 페이지에 보일 데이터의 수 
		int end = pageBlock * currentPage; // 테이블에서 가져올 마지막 행번호
		int begin = end - pageBlock + 1; // 테이블에서 가져올 시작 행번호
		
		List<FindDTO> finds = mapper.findForm(begin, end);
		int totalCount = mapper.totalCount();
		if(totalCount == 0) {
			return ;
		}
		
		
		String url = "findForm?currentPage=";
		String result = PageService.printPage(url, totalCount, pageBlock, currentPage);
		
		model.addAttribute("finds", finds);
		model.addAttribute("result", result);
		
		
	}
	
	@Autowired private HttpSession session;
	
	/**
	 * 글 등록
	 * @param mpRequest
	 * @return
	 */
	public String findWriteProc(MultipartHttpServletRequest mpRequest) {
		
		
		//첨부파일 - 메인 사진
		MultipartFile file1 = mpRequest.getFile("image");
		String image = null;
		if (file1 == null) {
			System.out.println(" >>>>>>>>>>>>>>>>> file1 == null");
		} else if (file1.getSize() == 0) {
			System.out.println(" >>>>>>>>>>>>>>>>> file1.getSize() == 0");
		} else {
			//파일업로드
			try {
				image = FileUploadUtils.fileUpload(file1, servletContext, "findImg");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//첨부파일 
		MultipartFile file2 = mpRequest.getFile("image2");
		String image2 = null;
		if (file2 == null) {
			System.out.println(" >>>>>>>>>>>>>>>>> file2 == null");
		} else if (file2.getSize() == 0) {
			System.out.println(" >>>>>>>>>>>>>>>>> file2.getSize() == 0");
		} else {
			//파일업로드
			try {
				image2 = FileUploadUtils.fileUpload(file2, servletContext, "findImg");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		FindDTO board = new FindDTO();
		board.setId(mpRequest.getParameter("id"));
		//board.setId(sessionId);
		board.setTitle(mpRequest.getParameter("title"));
		board.setContent(mpRequest.getParameter("content"));
		board.setSpecies(mpRequest.getParameter("species"));
		board.setImage(image);
		board.setImage2(image2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		board.setTime(sdf.format(new Date()));

		System.out.println("board >> " + board);
		
		mapper.findWriteProc(board);
		
		return "redirect:findForm";
	}

	/**
	 * 글 상세
	 * @param no
	 * @return
	 */
	public FindDTO findContent(String no) {
		System.out.println("FindService - findContent parameter : " + no);
		int n = 1;
		try {
			n = Integer.parseInt(no);
		}catch(Exception e) {
			return null;
		}
		FindDTO board = mapper.findContent(n);
		List<CommentDTO> comments = mapper.selectComments(n);
		board.setComments(comments);
		
		return board;
	}

	/**
	 * 글 삭제
	 * @param no
	 */
	public void deleteFind(String no) {
		System.out.println("FindService - deleteFind parameter : " + no);
		int n = Integer.parseInt(no);
		mapper.deleteFind(n);
	}

	/**
	 * 글 수정 화면 - 기존 데이터 조회
	 * @param no
	 */
	public FindDTO editFind(String no) {
		System.out.println("FindService - editFind parameter : " + no);
		FindDTO board = mapper.findContent(Integer.parseInt(no));
		System.out.println("FindService - editFind result : " + board);
		return board;
	}

	/**
	 * 글 수정 
	 * @param multi
	 * @return
	 */
	public void findEditProc(MultipartHttpServletRequest multi) {
		
		FindDTO board = new FindDTO();
		board.setNo(Integer.parseInt(multi.getParameter("no")));
		board.setId(multi.getParameter("id"));
		//board.setId(sessionId);
		board.setTitle(multi.getParameter("title"));
		board.setContent(multi.getParameter("content"));
		board.setSpecies(multi.getParameter("species"));
		
		String image = null;
		String image2 = null;
		boolean deleteImage = false;
		boolean deleteImage2 = false;
		
		Enumeration<String> e= multi.getParameterNames();
		while(e.hasMoreElements()){
			String name = e.nextElement();
			System.out.println(name + " : " + multi.getParameter(name));
			
			//이미지 삭제 여부 설정
			if(name.equals("deleteImage")) {
				//board.setImage(null);
				deleteImage = true;
			}
			
			//이미지2 삭제 여부 설정
			if(name.equals("deleteImage2")) {
				//board.setImage2(null);
				deleteImage2 = true;
			}
		}

		//첨부파일 - 메인 사진
		MultipartFile file1 = multi.getFile("image");
		if (file1 == null) {
			System.out.println(" >>>>>>>>>>>>>>>>> file1 == null");
		} else if (file1.getSize() == 0) {
			if(!deleteImage) {
				System.out.println(" >>>>>>>>>>>>>>>>> image will be same");
				board.setSameImage(true);
			}
		} else {
			//파일업로드
			try {
				image = FileUploadUtils.fileUpload(file1, servletContext, "findImg");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		//첨부파일 
		MultipartFile file2 = multi.getFile("image2");
		if (file2 == null) {
			System.out.println(" >>>>>>>>>>>>>>>>> file2 == null");
		} else if (file2.getSize() == 0) {
			if(!deleteImage2) {
				System.out.println(" >>>>>>>>>>>>>>>>> image2 will be same");
				board.setSameImage2(true);
			}
		} else {
			//파일업로드
			try {
				image2 = FileUploadUtils.fileUpload(file2, servletContext, "findImg");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		board.setImage(image);
		board.setImage2(image2);
		
		System.out.println("board >> " + board);
		
		int result = mapper.findEditProc(board);
		System.out.println("updated row : " + result);
	}

	/**
	 * 댓글 등록
	 * @param commentDTO
	 */
	public int insertComment(CommentDTO commentDTO) {
		System.out.println("FindService - insertComment parameter : " + commentDTO);
		int result = mapper.insertComment(commentDTO);
		System.out.println("inserted row : " + result);
		return result;
	}

	

	public int deleteComment(CommentDTO DTO) {		
		int result = mapper.deleteComment(DTO);
		return result;
		
	}


	

}
