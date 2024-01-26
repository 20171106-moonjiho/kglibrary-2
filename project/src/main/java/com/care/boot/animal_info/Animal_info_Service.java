package com.care.boot.animal_info;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class Animal_info_Service {
	
	@Autowired
	private Animal_info_Mapper mapper;
	@Autowired
	private HttpSession session;
	private String filePath = "C:\\Users\\user1\\Documents\\workspace-spring-tool-suite-4-4.20.0.RELEASE\\project\\src\\main\\resources\\static\\img\\";
	
	public void animal_info(String search_select ,String search, String cp, Model model) {
	
		int currentPage = 1;// 현재 페이지

		try {
			currentPage = Integer.parseInt(cp);
		} catch (Exception e) {
			currentPage = 1;
		}
		
		if (search_select == null) {
			search_select = "";
		}
		
		int pageBlock = 10;// 한 페이지에 보일 데이터의 수.
		int end = pageBlock * currentPage;// 테이블에서 가져올 마지막 행 번호.
		int begin = end - pageBlock + 1; // 테이블에서 가져올 시작 행 번호.
		
		ArrayList<Animal_info_DTO> boards = mapper.animal_info(begin,end,search_select,search);
		
		if(boards != null) {
			for(Animal_info_DTO b : boards) {
				if(b.getImage()!=null) {
					String[] names= b.getImage().split("/");
					for(String name : names) {
					System.out.println("name: " +name);
					}
					String[] fileNames = names[1].split("-",2);
					for(String fileName : fileNames) {
						System.out.println("fileName: " +fileName);
						}
					b.setImage(names[1]);
					}
			}
		}
		int totalCount = mapper.totalCount(search_select, search); // 테이블의 행의 갯수 를 구해 오기위함
		if (totalCount == 0) {
			return;
		}
		String url = "animal_info?search_select=" + search_select + "&search=" + search + "&currentPage=";
		String result = PageService.printPage(url, totalCount, pageBlock, currentPage);
		model.addAttribute("count", totalCount);
		model.addAttribute("boards", boards);
		model.addAttribute("result", result);
		model.addAttribute("search_select", search_select);
		model.addAttribute("search", search);
	}

	public String animal_info_writeProc(MultipartHttpServletRequest multi) {
		String sessionId = (String) session.getAttribute("id");
			if (sessionId == null)
				return "redirect:login";
		
		
		String title = multi.getParameter("title");
		String content = multi.getParameter("content");
		content = content.replace("\r\n","<br>");
		if (title == null || title.trim().isEmpty()) {
			return "redirect:animal_info";
		}

		Animal_info_DTO board = new Animal_info_DTO();
		board.setTitle(title);
		board.setContent(content);
		board.setId(sessionId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		board.setWriteDate(sdf.format(new Date()));
		board.setImage("");

		MultipartFile file = multi.getFile("upfile");

		if (file.getSize() != 0) {
			// 파일이름
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			String fileTime = sdf.format(new Date());
			String fileName = file.getOriginalFilename();

			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			System.out.println("BoardService-boardWriteProc-suffix : " + suffix);
			if (suffix.equalsIgnoreCase("jpg") == false && suffix.equalsIgnoreCase("png") == false
					&& suffix.equalsIgnoreCase("jpeg") == false) {
				System.out.print("파일명이 다릅니다.");
				return "redirect:animal_info";
			}

			// 파일의 저장 경로
			String fileSaveDirectory = filePath + sessionId;
			File f = new File(fileSaveDirectory);// 파일 객체 생성 으로 sessionId위치에 폴더가 있는지 없는지 확인 하기
			if (f.exists() == false) {
				// 저장경로가 없을 경우
				// f.mkdir();
				f.mkdirs();// 자동으로 만들어 줄 수 있게 한다.
			}
			// \\(역슬러시)를 /(슬러시로)
			String fullPath = fileSaveDirectory + "/" + fileTime + fileName;
			board.setImage(fullPath);
			f = new File(fullPath); // 파일 객체에 저장경로에 대한 정보 넣어주기.
			try {
				file.transferTo(f);// 파일 저장.
			} catch (IOException e) {
				e.printStackTrace();
				board.setImage("");// 예외가 발생하면 빈문자열을 넣어줄때.
			}
		}
		
		mapper.animal_info_writeProc(board);
		return "redirect:animal_info";
	}

	public Animal_info_DTO animal_info_Content(String no) {
		int n = 1;
		try {
			n = Integer.parseInt(no);
		}catch (Exception e) {
			return null;
		}
		
		Animal_info_DTO board = mapper.animal_info_Content(n);
		
		if(board != null) {
			mapper.incrementHits(n);
			board.setHits(board.getHits()+1);
			if(board.getImage()!=null) {
			String[] names=board.getImage().split("/");
			for(String name : names) {
			System.out.println("name: " +name);
			}
			String[] fileNames = names[1].split("-",2);
			for(String fileName : fileNames) {
				System.out.println("fileName: " +fileName);
				}
			board.setImage(names[1]);
			}
		}
		
		return board;
	}

	public String ContentDeleteProc3(String no) {
		int n = 0;
		try {
			n = Integer.parseInt(no);
		} catch (Exception e) {
			return "게시글 번호에 문제가 발생했습니다. 다시 시도하세요.";
		}
		
		Animal_info_DTO board = mapper.animal_info_Content(n);
		if(board == null)
			return "게시글 번호에 문제가 발생했습니다. 다시 시도하세요.";
		
		// 로그인한 아이디와 작성자 아이디가 같은지 확인
		String sessionId = (String)session.getAttribute("id");
		if(board.getId().equals(sessionId) == false)
			return "작성자만 삭제 할 수 있습니다.";
		
		String fullPath = board.getImage();
		if(fullPath != null) { // 테이블에 파일의 경로와 이름이 있다면
			File f = new File(fullPath);
			if(f.exists() == true) // 파일 저장소에 파일이 존재한다면
				f.delete();
		}
		
		// 테이블에서 게시글번호와 일치하는 행(row)삭제
		mapper.ContentDeleteProc3(n);
		return "게시글 삭제 완료";
	}


	public String animal_info_Modify(String no, Model model) {
		int n = 0;
		try {
			n = Integer.parseInt(no);
		} catch (Exception e) {
			return "redirect:animal_info";
		}
		Animal_info_DTO board = mapper.animal_info_Content(n);
		String content = board.getContent().replaceAll("<br>", "\r\n");
		board.setContent(content);
		System.out.println("컨텐츠 = " + board.getContent());
		if(board == null)
			return "redirect:animal_info";
		
		if(board.getImage() != null) {
			String[] names = board.getImage().split("\\\\");
			for(String name : names) {
				System.out.println("게시글 수정 name: " +name);
				}
			String[] fileNames = names[11].split("-", 2);
			for(String fileName : fileNames) {
				System.out.println("게시글 수정 fileName: " +fileName);
				}
			board.setImage(fileNames[1]);
		}

		model.addAttribute("board",board);
		return "animal_info/animal_info_Modify";
		
	}
	
	public String animal_info_ModifyProc(Animal_info_DTO board, MultipartHttpServletRequest multi) {
		Animal_info_DTO check = mapper.animal_info_Content(board.getNo());
		if(check == null)
			return "게시글 번호에 문제가 발생했습니다. 다시 시도하세요.";
		String sessionId = (String)session.getAttribute("id");	
		
		if(check.getId().equals(sessionId) == false)
			return "작성자만 수정 할 수 있습니다.";
		if(board.getTitle() == null || board.getTitle().trim().isEmpty()) {
			return "제목을 입력하세요.";
		}
		
		String content = multi.getParameter("content");
		content = content.replace("\r\n","<br>");
		board.setContent(content);
		board.setImage(check.getImage());
		
		System.out.println("컨텐츠2 = " + board.getContent());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		board.setWriteDate(sdf.format(new Date()));

		MultipartFile file = multi.getFile("upfile");

		if (file.getSize() != 0) {
			// 파일이름
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			String fileTime = sdf.format(new Date());
			String fileName = file.getOriginalFilename();

			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			System.out.println("BoardService-boardWriteProc-suffix : " + suffix);
			if (suffix.equalsIgnoreCase("jpg") == false && suffix.equalsIgnoreCase("png") == false
					&& suffix.equalsIgnoreCase("jpeg") == false) {
				System.out.print("파일명이 다릅니다.");
				return "redirect:animal_info";
			}

			// 파일의 저장 경로
			String fileSaveDirectory = filePath + sessionId;
			File f = new File(fileSaveDirectory);// 파일 객체 생성 으로 sessionId위치에 폴더가 있는지 없는지 확인 하기
			if (f.exists() == false) {
				// 저장경로가 없을 경우
				// f.mkdir();
				f.mkdirs();// 자동으로 만들어 줄 수 있게 한다.
			}
			// \\(역슬러시)를 /(슬러시로)
			String fullPath = fileSaveDirectory + "/" + fileTime + fileName;
			board.setImage(fullPath);
			f = new File(fullPath); // 파일 객체에 저장경로에 대한 정보 넣어주기.
			try {
				file.transferTo(f);// 파일 저장.
			} catch (IOException e) {
				e.printStackTrace();
				board.setImage("");// 예외가 발생하면 빈문자열을 넣어줄때.
			}
		}
		
		if(board.getImage()==null) {
			board.setImage("");
		}
		int result = mapper.animal_info_ModifyProc(board);
		if(result == 0)
			return "게시글 수정에 실패했습니다. 다시 시도하세요.";
		
		return "게시글 수정 성공";
	}

	public String askReplyWrite2(Animal_info_comment_DTO adto) {
		String sessionId = (String) session.getAttribute("id");	
		
		String content = adto.getContent();
		
		if(content == null || content.trim().isEmpty()) {
			return "redirect:/animal_info?no=" +adto.getPage_no();
		}
		content = content.replace("\r\n","<br>");
		adto.setContent(content);
		adto.setId(sessionId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		adto.setTime(sdf.format(new Date()));
		
		
		mapper.askReplyWrite2(adto);
		
		return "등록 완료";
	}

	public void askReplyView2(String no, Model model) {
		
		int n = 0;
		try {								
			n = Integer.parseInt(no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Animal_info_comment_DTO> askreplys = mapper.askReplyView2(n);
		if(askreplys == null) 
			return ;
		
		int replysize = askreplys.size();
		model.addAttribute("askreplys", askreplys);
		model.addAttribute("replysize",replysize);
		
	}

	public String askReplyBoardDeleteProc2(String no, String page_no, String sessionId) {
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
		Animal_info_comment_DTO askreplys = mapper.askReplyCunfirm2(n, pn);
		if(askreplys == null) 				//데이터가 넘어오지 않았거나, 비정상적인 접근 차단
			return "게시글 번호에 문제가 발생했습니다. 다시 시도하세요.";
		
		
		System.out.println("되나 안되나2");
		// 로그인한 아이디와 글 쓴이가 다르거나, 혹은 다른데 admin이 아닐 경우 return;
		if(askreplys.getId().equals(sessionId) == false && !sessionId.equals("admin")) {
		
			return "관리자 또는 글쓴이만 삭제 할 수 있습니다.";
		}
		
		System.out.println("되나 안되나3" + n);
		// 테이블에서 게시글번호와 일치하는 행(row)삭제
		mapper.askReplyBoardDeleteProc2(n, pn);
		return "게시글 삭제 완료";
	}

	
}
