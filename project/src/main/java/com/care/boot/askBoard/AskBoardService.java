package com.care.boot.askBoard;

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
public class AskBoardService {

	@Autowired IAskBoardMapper mapper;
	@Autowired IAskReplyMapper remapper;
	@Autowired HttpSession session;
	
	public void askForm(String cp, Model model, String search, String select, String sessionId) {
		int currentPage = 1;
		try{
			currentPage = Integer.parseInt(cp);
		}catch(Exception e){
			currentPage = 1;
		}
		
		int pageBlock = 10; // 한 페이지에 보일 데이터의 수 
		int end = pageBlock * currentPage; // 테이블에서 가져올 마지막 행번호
		int begin = end - pageBlock + 1; // 테이블에서 가져올 시작 행번호
		
		List<AskBoardDTO> boards = mapper.askForm(begin, end, select, search,sessionId);
		int totalCount = mapper.totalCount(select, search);
		if(totalCount == 0) {
			return ;
		}
		
		String url = "askForm?currentPage=";
		String result = PageService.printPage(url, totalCount, pageBlock, currentPage);
		

//		//1.db에 값 추가 후,select,count 쿼리를 써서 값을 주고, db 값을 받아서 c:when으로 특정 값일 때 분류 (제일 쉬운듯, 귀찮..)
//		//2.model로 admin 관리자 댓이 있는지 없는지 확인 후 보낼 수 있나, 댓글 page_num이 있는지 확인, 그 후 그 안에 관리자 댓글이 있는지 확인. 후 그 값을 넘김. 
//		//근데 그 값이 게시글에 맞게 들어갈수가 있나? 한번에 보내는 것 밖에 생각이 안남. 	 
//		
		model.addAttribute("count", totalCount);
		model.addAttribute("boards", boards);
		model.addAttribute("result", result);
	}

	public String askWriteProc(MultipartHttpServletRequest multi) {
		String sessionId = (String) session.getAttribute("id");	
		
		String title = multi.getParameter("title");
		if(title == null || title.trim().isEmpty()) {
			return "redirect:askBoardWrite";
		}
		
		AskBoardDTO board = new AskBoardDTO();
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
				return "redirect:askBoardWrite";
			
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
		
		mapper.askWriteProc(board);
		return "redirect:askForm";
	}

	public AskBoardDTO askBoardContent(String no) {
		String sessionId = (String) session.getAttribute("id");	
		int n = 1;
		try{
			n = Integer.parseInt(no);
		}catch(Exception e){
			return null;
		}
		
		AskBoardDTO board = mapper.askBoardContent(n);
		   String content = board.getContent().replaceAll("\r\n","<br>");
		   session.setAttribute("viewGetContent", content);
		if(board != null) {
			//System.out.println("image name = " + board.getImage());
			
			if(board.getImage() != null) {
				String[] names = board.getImage().split("\\\\");
				
//				for(String name : names)	//파일 경로 확인용
//					System.out.println("BoardService-boardContent name : "+ name);
				
//				String[] fileNames = names[9].split("-", 2);
//				for(String fileName : fileNames)
//					System.out.println("BoardService-boardContent fileName : "+ fileName);
				
				board.setImage(names[9]);
			}
		}
		
		if(!sessionId.equals("admin")){ //1:1문의 게시판 정보를 볼 때 sessionid가 admin(관리자가 아니라면) / 한번 확인 했다면
			String recount = "noreply"; 
			mapper.askReCount(n,recount); // new 표시를 지운다.
		}
		
		System.out.println("replycount = " + board.getReplycount());
		return board;
		
	}

	public String askBoardDeleteProc(String no, String sessionId) {
		
		int n = 0;
		try {								//게시글에 숫자가 아닌 다른 접근 차단
			n = Integer.parseInt(no);
		} catch (Exception e) {
			return "게시글 번호에 문제가 발생했습니다. 다시 시도하세요.";
		}
		
		AskBoardDTO board = mapper.askBoardContent(n);
		if(board == null)					//데이터가 넘어오지 않았거나, 비정상적인 접근 차단
			return "게시글 번호에 문제가 발생했습니다. 다시 시도하세요.";
		
		// 로그인한 아이디와 글 쓴이가 다르거나, 혹은 다른데 admin이 아닐 경우 return;
		if(board.getId().equals(sessionId) == false && !sessionId.equals("admin")) {
			return "관리자 또는 글쓴이만 삭제 할 수 있습니다.";
		}
		
		String fullPath = board.getImage();// 이미지 경로
		if(fullPath != null) { // 테이블에 파일의 경로와 이름이 있다면
			File f = new File(fullPath); //새로 만듦
			if(f.exists() == true) // 파일 저장소에 파일이 존재한다면
				f.delete();
		}
		
		// 테이블에서 게시글번호와 일치하는 행(row)삭제
		mapper.askBoardDeleteProc(n);
		return "게시글 삭제 완료";
	}

	public String askReplyWrite(AskReplyDTO adto) {
		String sessionId = (String) session.getAttribute("id");	
		
		String content = adto.getContent();
		if(content == null || content.trim().isEmpty()) {
			return "redirect:/askBoardContent?no=" +adto.getPage_no();
		}
		
		adto.setId(sessionId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		adto.setTime(sdf.format(new Date()));
		
		if(sessionId.equals("admin")){ //댓글 등록 할 때 sessionid가 admin(관리자라면)
			String recount = "exgistreply"; 
			mapper.askReCount(adto.getPage_no(),recount); // 1:1문의 게시판 db에  업데이트 한다.
		}
		
		remapper.askReplyWrite(adto);
		
		return "등록 완료";
		
	}

	public void askReplyView(String no, Model model) {
		
		int n = 0;
		try {								
			n = Integer.parseInt(no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<AskReplyDTO> askreplys = remapper.askReplyView(n);
		if(askreplys == null) 
			return ;
		
		int replysize = askreplys.size();
		model.addAttribute("askreplys", askreplys);
		model.addAttribute("replysize",replysize);
		
	}

	public String askReplyBoardDeleteProc(String no,String page_no, String sessionId) {
		
		System.out.println(" no --- " + no);
		System.out.println(" page_no --- " + page_no);
		int n = 0;
		int pn = 0;
		try {								//게시글에 숫자가 아닌 다른 접근 차단
			n = Integer.parseInt(no);
			pn = Integer.parseInt(page_no);
		} catch (Exception e) {
			return "게시글 번호에 문제가 발생했습니다. 다시 시도하세요.";
			
		}
		
		System.out.println("되나 안되나1");
		AskReplyDTO askreplys = remapper.askReplyCunfirm(n, pn);
		if(askreplys == null) 				//데이터가 넘어오지 않았거나, 비정상적인 접근 차단
			return "게시글 번호에 문제가 발생했습니다. 다시 시도하세요.";
		
		
		System.out.println("되나 안되나2");
		// 로그인한 아이디와 글 쓴이가 다르거나, 혹은 다른데 admin이 아닐 경우 return;
		if(askreplys.getId().equals(sessionId) == false && !sessionId.equals("admin")) {
		
			return "관리자 또는 글쓴이만 삭제 할 수 있습니다.";
		}
		
		System.out.println("되나 안되나3" + n);
		// 테이블에서 게시글번호와 일치하는 행(row)삭제
		remapper.askReplyBoardDeleteProc(n, pn);
		return "게시글 삭제 완료";
	}

	public void allBoardDeleteProc(String id) {
		List<AskBoardDTO> boards = mapper.findDeleteNo(id);
		
		
		for(AskBoardDTO findboards : boards) { //id로 찾은 1:1문의 수를 대입해서 삭제.
			System.out.println("삭제한 게시글 번호 : " + findboards.getNo());
			mapper.askBoardDeleteProc(findboards.getNo()); // 1:1 문의 게시판 글 삭제
			remapper.findDeleteRplyBoard(findboards.getNo()); //그 게시판 글의 댓글 삭제
		}
		
	}	
	
}
