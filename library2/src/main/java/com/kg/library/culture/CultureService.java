package com.kg.library.culture;

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

import com.kg.library.PageService;

import jakarta.servlet.http.HttpSession;

@Service
public class CultureService {

	@Autowired
	private CultureMapper mapper;
		
	@Autowired
	private HttpSession session;
	private String filePath = "C:\\Users\\user1\\git\\kglibrary\\library\\src\\main\\resources\\static\\img\\";
	
	
	public void cultureboard(String search_select, String search, String cp, Model model) {
		String sessionId = (String) session.getAttribute("id");
//		System.out.println("1111");
		//페이지 번호
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
		
		ArrayList<CultureDTO> boards = mapper.cultureboard(begin,end,search_select,search);
//		
		if(boards != null) {
			List<String> apply_ckList = new ArrayList<>();
			for(CultureDTO b : boards) {
				if(b.getImage()!=null) {
					String[] names= b.getImage().split("/");
					String[] fileNames = names[1].split("-",2);
					b.setImage(names[1]);
					}
				if(sessionId!=null) {
					String apply_ck = mapper.apply_ck(b.getTitle(),sessionId);
					apply_ckList.add(apply_ck);
					
				}
				String applicants = mapper.applicants(b.getTitle());
				b.setApplicants(applicants);
				mapper.updateApplicantsCount(applicants,b.getNo());
			}
			model.addAttribute("apply_ckList", apply_ckList);
		}
		int totalCount = mapper.totalCount(search_select, search); // 테이블의 행의 갯수 를 구해 오기위함
		if (totalCount == 0) {
			return;
		}
		
		String url = "cultureboard?search_select=" + search_select + "&search=" + search + "&currentPage=";
		String result = PageService.printPage(url, totalCount, pageBlock, currentPage);

		model.addAttribute("count", totalCount);
		model.addAttribute("boards", boards);
		model.addAttribute("result", result);
		model.addAttribute("search_select", search_select);
		model.addAttribute("search", search);
		
	}


	public String culture_writeProc(MultipartHttpServletRequest multi) {
		String sessionId = (String) session.getAttribute("id");
		if (sessionId == null)
			return "redirect:login";
		
		String title = multi.getParameter("title");
		String class_period_start = multi.getParameter("class_period_start");
		String class_period_end = multi.getParameter("class_period_end");
		String reservation_period_start = multi.getParameter("reservation_period_start");
		String reservation_period_end = multi.getParameter("reservation_period_end");
		String target = multi.getParameter("target");
		String event_area = multi.getParameter("event_area");
		String reception_area = multi.getParameter("reception_area");
		String people = multi.getParameter("people");
		String teacher = multi.getParameter("teacher");
		String money = multi.getParameter("money");
		String contents = multi.getParameter("contents");
		String select_cate = multi.getParameter("select");
		
		contents = contents.replace("\r\n","<br>");
		if (title == null || title.trim().isEmpty()) {
			return "redirect:cultureboard";
		}
		
		CultureDTO board = new CultureDTO();
		board.setTitle(title);
		board.setClass_period_start(class_period_start);
		board.setClass_period_end(class_period_end);
		board.setReservation_period_start(reservation_period_start);
		board.setReservation_period_end(reservation_period_end);
		board.setTarget(target);
		board.setId(sessionId);
		board.setEvent_area(event_area);
		board.setReception_area(reception_area);
		board.setPeople(people);
		board.setMoney(money);
		board.setTeacher(teacher);
		board.setContents(contents);
		board.setImage("");
		board.setSelect_cate(select_cate);
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
				return "redirect:cultureboard";
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
		
		mapper.culture_writeProc(board);
		return "redirect:cultureboard";
	}


	public CultureDTO cultureContent(String no) {
		int n = 1;
		try {
			n = Integer.parseInt(no);
		}catch (Exception e) {
			return null;
		}
		CultureDTO board = mapper.cultureContent(n);
		
		if(board != null) {
			
			if(board.getImage()!=null) {
			String[] names=board.getImage().split("/");
//			for(String name : names) {
//			System.out.println("컨텐츠 name: " +name);
//			}
			String[] fileNames = names[1].split("-",2);
//			for(String fileName : fileNames) {
//				System.out.println("fileName: " +fileName);
//				}
			board.setImage(names[1]);
			}
		}
		
		return board;
	}


	public String CultureDeleteProc(String no) {
		int n = 0;
		try {
			n = Integer.parseInt(no);
		}catch (Exception e) {
			return "게시글 번호에 문제가 발생하였습니다.";
		}	
		
		CultureDTO board = mapper.cultureContent(n);
		
		String sessionId = (String) session.getAttribute("id");
		if(board.getId().equals(sessionId) == false)
			return "작성자만 삭제 가능합니다.";
		String fullPath = board.getImage();
		if(fullPath != null) { // 테이블에 파일의 경로와 이름이 있다면
			File f = new File(fullPath);
			if(f.exists() == true) // 파일 저장소에 파일이 존재한다면
				f.delete();
		}
		mapper.CultureDeleteProc(n);
		return "게시글 삭제 완료";
	}


	public String cultureModify(String no, Model model) {
		int n = 0;
		try {
			n = Integer.parseInt(no);
		} catch (Exception e) {
			return "redirect:cultureboard";
		}
		CultureDTO board = mapper.cultureContent(n);
		String content = board.getContents().replaceAll("<br>", "\r\n");
		board.setContents(content);
		if(board==null) {
			return "redirect:cultureboard";
		}
		if(board.getImage() != null) {
			String[] names= board.getImage().split("\\\\");
			String[] fileNames = names[11].split("-", 2);
			board.setImage(fileNames[1]);
		}
		model.addAttribute("board",board);
		return "culture/cultureModify";
	}


	public String cultureModifyPro(CultureDTO board, MultipartHttpServletRequest multi) {
		CultureDTO check = mapper.cultureContent(board.getNo());
		String sessionId = (String)session.getAttribute("id");
		
		if(check.getId().equals(sessionId) == false)
			return "작성자만 수정 할 수 있습니다.";
		if(board.getTitle() == null || board.getTitle().trim().isEmpty()) {
			return "제목을 입력하세요.";
		}
		
		String title = multi.getParameter("title");
		String class_period_start = multi.getParameter("class_period_start");
		String class_period_end = multi.getParameter("class_period_end");
		String reservation_period_start = multi.getParameter("reservation_period_start");
		String reservation_period_end = multi.getParameter("reservation_period_end");
		String target = multi.getParameter("target");
		String select_cate = multi.getParameter("select");
		String event_area = multi.getParameter("event_area");
		String reception_area = multi.getParameter("reception_area");
		String people = multi.getParameter("people");
		String teacher = multi.getParameter("teacher");
		String money = multi.getParameter("money");
		String contents = multi.getParameter("contents");
		contents = contents.replace("\r\n","<br>");
		
		board.setTitle(title);
		board.setClass_period_start(class_period_start);
		board.setClass_period_end(class_period_end);
		board.setReservation_period_start(reservation_period_start);
		board.setReservation_period_end(reservation_period_end);
		board.setTarget(target);
		board.setSelect_cate(select_cate);
		board.setId(sessionId);
		board.setEvent_area(event_area);
		board.setReception_area(reception_area);
		board.setPeople(people);
		board.setMoney(money);
		board.setTeacher(teacher);
		board.setContents(contents);
		board.setImage(check.getImage());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		board.setWriteDate(sdf.format(new Date()));
		
		MultipartFile file = multi.getFile("upfile");
		
		if (file.getSize() != 0) {
			// 파일이름
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			String fileTime = sdf.format(new Date());
			String fileName = file.getOriginalFilename();

			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			if (suffix.equalsIgnoreCase("jpg") == false && suffix.equalsIgnoreCase("png") == false
					&& suffix.equalsIgnoreCase("jpeg") == false) {
				System.out.print("파일명이 다릅니다.");
				return "redirect:cultureboard";
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
		mapper.cultureModifyPro(board);
		return "게시글 수정 성공";
	}
	
	public void main_board(Model model) {
		ArrayList<CultureDTO> cultures = mapper.main_board();
		
		for(CultureDTO b : cultures) {
			System.out.println("여기--------------------------"+b.getSelect_cate());
			System.out.println("여기--------------------------"+b.getTitle());
			System.out.println("여기--------------------------"+b.getWriteDate());
		}
		model.addAttribute("cultures",cultures);
	}

}
