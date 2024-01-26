package com.care.boot.answerBoard;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.boot.PageService;

import jakarta.servlet.http.HttpSession;

@Service
public class AnswerBoardService {

	@Autowired IAnswerBoardMapper mapper;
	@Autowired HttpSession session;
	
	public void answerForm(String cp, Model model, String search, String select) {
		int currentPage = 1;
		try{
			currentPage = Integer.parseInt(cp);
		}catch(Exception e){
			currentPage = 1;
		}
		
		int pageBlock = 10; // 한 페이지에 보일 데이터의 수 
		int end = pageBlock * currentPage; // 테이블에서 가져올 마지막 행번호
		int begin = end - pageBlock + 1; // 테이블에서 가져올 시작 행번호
		
		List<AnswerBoardDTO> boards = mapper.answerForm(begin, end, select, search);
		int totalCount = mapper.totalCount(select, search);
		if(totalCount == 0) {
			return ;
		}
		
		String url = "answerForm?currentPage=";
		String result = PageService.printPage(url, totalCount, pageBlock, currentPage);
		
		model.addAttribute("count", totalCount);
		model.addAttribute("boards", boards);
		model.addAttribute("result", result);
	}

	public String answerWriteProc(MultipartHttpServletRequest multi) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		
		String title = multi.getParameter("title");
		if(title == null || title.trim().isEmpty()) {
			return "redirect:answerBoardWrite";
		}
		
		AnswerBoardDTO board = new AnswerBoardDTO();
		board.setTitle(title);
		board.setContent(multi.getParameter("content"));
		board.setId(sessionId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		board.setTime(sdf.format(new Date()));
		board.setImage("");
		
		MultipartFile file = multi.getFile("image");
		if(file.getSize() != 0) { // 클라이언트가 파일을 업로드 했다면
			// 파일의 이름
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			String fileTime = sdf.format(new Date());
			String fileName = file.getOriginalFilename();
			
			String suffix = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
			if(suffix.equalsIgnoreCase("jpeg") == false)
				return "redirect:answerBoardWrite";
			
			// 파일의 저장 경로
			String fileSaveDirectory = "C:\\javas\\projectall\\src\\main\\resources\\static\\img\\" +sessionId;
			File f = new File(fileSaveDirectory);
			if(f.exists() == false) {
				f.mkdirs();
			}
			
			String fullPath = fileSaveDirectory + "\\" + fileTime + fileName;
			board.setImage(fullPath);
			f = new File(fullPath);
			try {
				file.transferTo(f);
			} catch (Exception e) {
				e.printStackTrace();
				board.setImage("");
			} 
			
			/*
			 file.transferTo(); //파일을 이동시키는 기능 
			 <input type="file" name="upfile"> 을 사용하여 서버에 파일 데이터가 전달되면
			 웹서버가 임시파일로 저장을 함. 
			 임시파일로 저장된 파일을 개발자가 원하는 경로로 이동시킬 때 file.transferTo()를 사용함.
			 */
		}
		
		mapper.answerWriteProc(board);
		return "redirect:answerForm";
	}

	public AnswerBoardDTO answerBoardContent(String no) {
		int n = 1;
		try{
			n = Integer.parseInt(no);
		}catch(Exception e){
			return null;
		}
		
		AnswerBoardDTO board = mapper.answerBoardContent(n);
		String content = board.getContent().replaceAll("\r\n","<br>");
		   session.setAttribute("viewGetContent", content);
		if(board != null) {
			System.out.println("image name = " + board.getImage());
			
			if(board.getImage() != null) {
				String[] names = board.getImage().split("\\\\");
				
				for(String name : names)
					System.out.println("BoardService-boardContent name : "+ name);
				/* 	/opt/tomcat/tomcat-10/webapps/upload/user1/20230925140126-pom.xml*/
				//  /D:\kdk\image\admin\20231023164230-냥이.jpeg
				/* 20230925140126-01-pom-v01.xml */
				//  C:\javas\projectall\src\main\resources\static\img\admin\20231024095431-냥이.jpeg
				String[] fileNames = names[9].split("-", 2);
				for(String fileName : fileNames)
					System.out.println("BoardService-boardContent fileName : "+ fileName);
				
				board.setImage(names[9]);
			}
		}
		return board;
		
	}

	public String answerBoardDeleteProc(String no) {
		
		int n = 0;
		try {
			n = Integer.parseInt(no);
		} catch (Exception e) {
			return "게시글 번호에 문제가 발생했습니다. 다시 시도하세요.";
		}
		
		AnswerBoardDTO board = mapper.answerBoardContent(n);
		if(board == null)
			return "게시글 번호에 문제가 발생했습니다. 다시 시도하세요.";
		
		// 로그인한 아이디와 관리자 아이디가 같은지 확인
		if(board.getId().equals("admin") == false)
			return "관리자만 삭제 할 수 있습니다.";
		
		String fullPath = board.getImage();
		if(fullPath != null) { // 테이블에 파일의 경로와 이름이 있다면
			File f = new File(fullPath);
			if(f.exists() == true) // 파일 저장소에 파일이 존재한다면
				f.delete();
		}
		
		// 테이블에서 게시글번호와 일치하는 행(row)삭제
		mapper.answerBoardDeleteProc(n);
		return "게시글 삭제 완료";
	}

	public String answerBoardModifyProc(MultipartHttpServletRequest multi, String no) {
		int n = 1;
		try{
			n = Integer.parseInt(no);
		}catch(Exception e){
			return null;
		}
		
		String sessionId = (String) session.getAttribute("id");
		String title = multi.getParameter("title");
		if(title == null || title.trim().isEmpty()) {
			return "redirect:answerBoardModify";
		}
		
		AnswerBoardDTO board = new AnswerBoardDTO();
		board.setNo(n);
		board.setTitle(title);
		board.setContent(multi.getParameter("content"));
		board.setId(sessionId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		board.setTime(sdf.format(new Date()));
		board.setImage("");
		
		MultipartFile file = multi.getFile("image");
		if(file.getSize() != 0) { // 클라이언트가 파일을 업로드 했다면
			// 파일의 이름
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			String fileTime = sdf.format(new Date());
			String fileName = file.getOriginalFilename();
			
			String suffix = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
			if(suffix.equalsIgnoreCase("jpeg") == false)
				return "redirect:answerBoardModify";
			
			// 파일의 저장 경로
			String fileSaveDirectory = "C:\\javas\\projectall\\src\\main\\resources\\static\\img\\" +sessionId;
			File f = new File(fileSaveDirectory);
			if(f.exists() == false) {
				f.mkdirs();
			}

			String fullPath = fileSaveDirectory + "\\" + fileTime + fileName;
			board.setImage(fullPath);
			f = new File(fullPath);
			try {
				file.transferTo(f);
			} catch (Exception e) {
				e.printStackTrace();
				board.setImage("");
			} 
			
			/*
			 file.transferTo(); //파일을 이동시키는 기능 
			 <input type="file" name="upfile"> 을 사용하여 서버에 파일 데이터가 전달되면
			 웹서버가 임시파일로 저장을 함. 
			 임시파일로 저장된 파일을 개발자가 원하는 경로로 이동시킬 때 file.transferTo()를 사용함.
			 */
		}
	
		mapper.answerBoardModify(board);
		return "redirect:answerForm";
		
	}

	public AnswerBoardDTO answerBoardModify(String no) {
		int n = 1;
		try{
			n = Integer.parseInt(no);
		}catch(Exception e){
			return null;
		}
		
		AnswerBoardDTO board = mapper.answerBoardContent(n);
		if(board != null) {
			System.out.println("image name = " + board.getImage());
			
			if(board.getImage() != null) {
				String[] names = board.getImage().split("\\\\");
				
				for(String name : names)
					System.out.println("BoardService-boardContent name : "+ name);
				/* 	/opt/tomcat/tomcat-10/webapps/upload/user1/20230925140126-pom.xml*/
				//  /D:\kdk\image\admin\20231023164230-냥이.jpeg
				/* 20230925140126-01-pom-v01.xml */
				//  C:\javas\projectall\src\main\resources\static\img\admin\20231024095431-냥이.jpeg
				String[] fileNames = names[9].split("-", 2);
				for(String fileName : fileNames)
					System.out.println("BoardService-boardContent fileName : "+ fileName);
				
				board.setImage(names[9]);
			}
		}
		return board;
		
	}
	
	
	
	
}
