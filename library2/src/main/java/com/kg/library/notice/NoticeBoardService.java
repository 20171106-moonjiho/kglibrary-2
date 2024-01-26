package com.kg.library.notice;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kg.library.PageService;

import jakarta.servlet.http.HttpSession;

@Service
public class NoticeBoardService {
	@Autowired
	private NoticeBoardMapper mapper;
	@Autowired
	private HttpSession session;
	private String filePath = "C:\\Users\\user1\\git\\kglibrary\\library\\src\\main\\resources\\static\\img\\";
	

	public void noticeBoard(String search_select, String search, String cp, Model model) {
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
		
		ArrayList<NoticeBoardDTO> boards = mapper.noticeBoard(begin,end,search_select,search);
		
		if(boards != null) {
			for(NoticeBoardDTO b : boards) {
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
		String url = "noticeBoard?search_select=" + search_select + "&search=" + search + "&currentPage=";
		String result = PageService.printPage(url, totalCount, pageBlock, currentPage);
		model.addAttribute("count", totalCount);
		model.addAttribute("boards", boards);
		model.addAttribute("result", result);
		model.addAttribute("search_select", search_select);
		model.addAttribute("search", search);
		
	}


	public String noticeboard_writeProc(MultipartHttpServletRequest multi) {
		String sessionId = (String) session.getAttribute("id");
		if (sessionId == null)
			return "redirect:login";
	
	
	String title = multi.getParameter("title");
	String content = multi.getParameter("content");
	content = content.replace("\r\n","<br>");
	if (title == null || title.trim().isEmpty()) {
		return "redirect:noticeBoard";
	}

	NoticeBoardDTO board = new NoticeBoardDTO();
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
			return "redirect:noticeBoard";
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
	
	mapper.noticeboard_writeProc(board);
	return "redirect:noticeBoard";
	}


	public NoticeBoardDTO noticeboard_Content(String no) {
		int n = 1;
		try {
			n = Integer.parseInt(no);
		}catch (Exception e) {
			return null;
		}
		
		NoticeBoardDTO board = mapper.noticeboard_Content(n);
		
		if(board != null) {
			mapper.incrementHits(n);
			board.setHits(board.getHits()+1);
			if(board.getImage()!=null) {
			String[] names=board.getImage().split("/");
			for(String name : names) {
			System.out.println("컨텐츠 name: " +name);
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
		NoticeBoardDTO board = mapper.noticeboard_Content(n);
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


	public String noticeboard_Modify(String no, Model model) {
		int n = 0;
		try {
			n = Integer.parseInt(no);
		} catch (Exception e) {
			return "redirect:noticeBoard";
		}
		NoticeBoardDTO board = mapper.noticeboard_Content(n);
		String content = board.getContent().replaceAll("<br>", "\r\n");
		board.setContent(content);
		System.out.println("컨텐츠 = " + board.getContent());
		if(board == null)
			return "redirect:noticeBoard";
		
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
		return "notice/noticeboard_Modify";
		
	}


	public String noticeboard_ModifyProc(NoticeBoardDTO board, MultipartHttpServletRequest multi) {
		NoticeBoardDTO check = mapper.noticeboard_Content(board.getNo());
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
		int result = mapper.noticeboard_ModifyProc(board);
		if(result == 0)
			return "게시글 수정에 실패했습니다. 다시 시도하세요.";
		
		return "게시글 수정 성공";
	}


	public void main_board(Model model) {
	
	ArrayList<NoticeBoardDTO> boards = mapper.main_board();
	for (NoticeBoardDTO board : boards) {
	    System.out.println(board.getTitle());
	    System.out.println(board.getNo());
	    System.out.println(board.getWriteDate());
		}
	model.addAttribute("boards", boards);
	}	

	}
	
