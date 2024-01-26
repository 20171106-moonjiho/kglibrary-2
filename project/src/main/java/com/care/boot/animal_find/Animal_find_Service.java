package com.care.boot.animal_find;

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

import com.care.boot.PageService;

import jakarta.servlet.http.HttpSession;

@Service
public class Animal_find_Service {

	@Autowired
	private Animal_find_Mapper mapper;
	@Autowired
	private HttpSession session;
	private String filePath = "C:\\Users\\user1\\Documents\\workspace-spring-tool-suite-4-4.20.0.RELEASE\\project\\src\\main\\resources\\static\\img\\";
	// "/opt/tomcat/tomcat-10/webapps/upload/" (서버용)

	public void animal_find(String animal_group, String search, String area, String cp, Model model) {
		
		int currentPage = 1;// 현재 페이지

		try {
			currentPage = Integer.parseInt(cp);
		} catch (Exception e) {
			currentPage = 1;
		}

		if (animal_group == null) {
			animal_group = "";
		}
		if(animal_group.equals("전체")) {
			animal_group = "";
		}
		
		int pageBlock = 12;// 한 페이지에 보일 데이터의 수.
		int end = pageBlock * currentPage;// 테이블에서 가져올 마지막 행 번호.
		int begin = end - pageBlock + 1; // 테이블에서 가져올 시작 행 번호.
		

		// ArrayList<Animal_find_DTO> boards = mapper.animal_find(begin,end);
		ArrayList<Animal_find_DTO> boards = mapper.animal_find(begin, end, animal_group, search, area);
		
		if(boards != null) {
		for (Animal_find_DTO b : boards) {
			if (b.getImage() !=null){
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
		int totalCount = mapper.totalCount(animal_group, search,area); // 테이블의 행의 갯수 를 구해 오기위함

		if (totalCount == 0) {
			return;
		}
	
		String url = "animal_find?animal_group=" + animal_group + "&search=" + search + "&area=" + area + "&currentPage=";
		String result = PageService.printPage(url, totalCount, pageBlock, currentPage);
		model.addAttribute("boards", boards);
		model.addAttribute("result", result);
		model.addAttribute("animal_group", animal_group);
		model.addAttribute("search", search);
		model.addAttribute("area", area);
	}
//넘어온 값 받기 그룹,지역
	public String animal_find_writeProc(MultipartHttpServletRequest multi) {
		String sessionId = (String) session.getAttribute("id");

		if (sessionId == null)
			return "redirect:login";
		

		String title = multi.getParameter("title");
		String animal_group = multi.getParameter("animal_group");
		String area = multi.getParameter("area");
		String tel = multi.getParameter("tel");
		String cate = multi.getParameter("cate");
		String money = multi.getParameter("money");
		String name = multi.getParameter("name");
		String age = multi.getParameter("age");
		String sex = multi.getParameter("sex");
		String day = multi.getParameter("day");
		String areatext = multi.getParameter("areatext");
		String content = multi.getParameter("content");
		content = content.replace("\r\n","<br>");
		if (area == null || area.trim().isEmpty()) {
			return "redirect:animal_find";
		}
		if (title == null || title.trim().isEmpty()) {
			return "redirect:animal_find";
		}

		Animal_find_DTO board = new Animal_find_DTO();
		board.setTitle(title);
		board.setContent(content);
		board.setId(sessionId);
		board.setAnimal_group(animal_group);
		board.setArea(area);
		board.setTel(tel);
		board.setCate(cate);
		board.setMoney(money);
		board.setName(name);
		board.setAge(age);
		board.setSex(sex);
		board.setDay(day);
		board.setAreatext(areatext);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		board.setTime(sdf.format(new Date()));
		board.setImage("");
		board.setImage2("");
		board.setImage3("");

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
				return "redirect:animal_find";
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
		
		MultipartFile file2 = multi.getFile("upfile2");

		if (file2.getSize() != 0) {
			// 파일이름
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			String fileTime = sdf.format(new Date());
			String fileName = file2.getOriginalFilename();

			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			System.out.println("BoardService-boardWriteProc-suffix : " + suffix);
			if (suffix.equalsIgnoreCase("jpg") == false && suffix.equalsIgnoreCase("png") == false
					&& suffix.equalsIgnoreCase("jpeg") == false) {
				System.out.print("파일명이 다릅니다.");
				return "redirect:animal_find";
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
			board.setImage2(fullPath);
			f = new File(fullPath); // 파일 객체에 저장경로에 대한 정보 넣어주기.
			try {
				file2.transferTo(f);// 파일 저장.
			} catch (IOException e) {
				e.printStackTrace();
				board.setImage2("");// 예외가 발생하면 빈문자열을 넣어줄때.
			}
		}
		MultipartFile file3 = multi.getFile("upfile3");

		if (file3.getSize() != 0) {
			// 파일이름
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			String fileTime = sdf.format(new Date());
			String fileName = file3.getOriginalFilename();

			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			System.out.println("BoardService-boardWriteProc-suffix : " + suffix);
			if (suffix.equalsIgnoreCase("jpg") == false && suffix.equalsIgnoreCase("png") == false
					&& suffix.equalsIgnoreCase("jpeg") == false) {
				System.out.print("파일명이 다릅니다.");
				return "redirect:animal_find";
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
			board.setImage3(fullPath);
			f = new File(fullPath); // 파일 객체에 저장경로에 대한 정보 넣어주기.
			try {
				file3.transferTo(f);// 파일 저장.
			} catch (IOException e) {
				e.printStackTrace();
				board.setImage3("");// 예외가 발생하면 빈문자열을 넣어줄때.
			}
		}
		mapper.animal_find_writeProc(board);
		return "redirect:animal_find";
	}
	
	public Animal_find_DTO animal_find_Content(String no) {
		int n = 1;
		try {
			n = Integer.parseInt(no);
		}catch (Exception e) {
			return null;
		}
		
		Animal_find_DTO board = mapper.animal_find_Content(n);
		if(board != null) {
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
		if(board != null) {
			if(board.getImage2()!=null) {
			String[] names=board.getImage2().split("/");
			for(String name : names) {
			System.out.println("name: " +name);
			}
			String[] fileNames = names[1].split("-",2);
			for(String fileName : fileNames) {
				System.out.println("fileName: " +fileName);
				}
			board.setImage2(names[1]);
			}
		}
		if(board != null) {
			if(board.getImage3()!=null) {
			String[] names=board.getImage3().split("/");
			for(String name : names) {
			System.out.println("name: " +name);
			}
			String[] fileNames = names[1].split("-",2);
			for(String fileName : fileNames) {
				System.out.println("fileName: " +fileName);
				}
			board.setImage3(names[1]);
			}
		}
		
		return board;
	}
	public String ContentDeleteProc(String no) {
		int n = 0;
		try {
			n = Integer.parseInt(no);
		} catch (Exception e) {
			return "게시글 번호에 문제가 발생했습니다. 다시 시도하세요.";
		}
		
		Animal_find_DTO board = mapper.animal_find_Content(n);
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
		mapper.ContentDeleteProc(n);
		return "게시글 삭제 완료";
	}
	public String animal_find_Modify(String no, Model model) {
		int n = 0;
		try {
			n = Integer.parseInt(no);
		} catch (Exception e) {
			return "redirect:animal_find";
		}
		Animal_find_DTO board = mapper.animal_find_Content(n);
		String content = board.getContent().replaceAll("<br>", "\r\n");
		board.setContent(content);
		
		if(board == null)
			return "redirect:animal_find";
		
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
		if(board.getImage2() != null) {
			String[] names = board.getImage2().split("\\\\");
			for(String name : names) {
				System.out.println("게시글 수정 name: " +name);
				}
			String[] fileNames = names[11].split("-", 2);
			for(String fileName : fileNames) {
				System.out.println("게시글 수정 fileName: " +fileName);
				}
			board.setImage2(fileNames[1]);
		}
		if(board.getImage3() != null) {
			String[] names = board.getImage3().split("\\\\");
			for(String name : names) {
				System.out.println("게시글 수정 name: " +name);
				}
			String[] fileNames = names[11].split("-", 2);
			for(String fileName : fileNames) {
				System.out.println("게시글 수정 fileName: " +fileName);
				}
			board.setImage3(fileNames[1]);
		}
		model.addAttribute("board",board);
		return "home/animal_find_Modify";
		
	}
	public String animal_find_ModifyProc(Animal_find_DTO board,MultipartHttpServletRequest multi) {
		Animal_find_DTO check = mapper.animal_find_Content(board.getNo());
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
		
		System.out.println("check_image(1) = " +check.getImage());
		board.setImage(check.getImage());
		System.out.println("check_image(2) = " +check.getImage2());
		board.setImage2(check.getImage2());
		System.out.println("check_image(3) = " +check.getImage3());
		board.setImage3(check.getImage3());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		board.setTime(sdf.format(new Date()));

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
				return "redirect:animal_find";
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
		
		MultipartFile file2 = multi.getFile("upfile2");

		if (file2.getSize() != 0) {
			// 파일이름
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			String fileTime = sdf.format(new Date());
			String fileName = file2.getOriginalFilename();

			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			System.out.println("BoardService-boardWriteProc-suffix : " + suffix);
			if (suffix.equalsIgnoreCase("jpg") == false && suffix.equalsIgnoreCase("png") == false
					&& suffix.equalsIgnoreCase("jpeg") == false) {
				System.out.print("파일명이 다릅니다.");
				return "redirect:animal_find";
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
			board.setImage2(fullPath);
			f = new File(fullPath); // 파일 객체에 저장경로에 대한 정보 넣어주기.
			try {
				file2.transferTo(f);// 파일 저장.
			} catch (IOException e) {
				e.printStackTrace();
				board.setImage2("");// 예외가 발생하면 빈문자열을 넣어줄때.
			}
		}
		
		MultipartFile file3 = multi.getFile("upfile3");

		if (file3.getSize() != 0) {
			// 파일이름
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			String fileTime = sdf.format(new Date());
			String fileName = file3.getOriginalFilename();

			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			System.out.println("BoardService-boardWriteProc-suffix : " + suffix);
			if (suffix.equalsIgnoreCase("jpg") == false && suffix.equalsIgnoreCase("png") == false
					&& suffix.equalsIgnoreCase("jpeg") == false) {
				System.out.print("파일명이 다릅니다.");
				return "redirect:animal_find";
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
			board.setImage3(fullPath);
			f = new File(fullPath); // 파일 객체에 저장경로에 대한 정보 넣어주기.
			try {
				file3.transferTo(f);// 파일 저장.
			} catch (IOException e) {
				e.printStackTrace();
				board.setImage3("");// 예외가 발생하면 빈문자열을 넣어줄때.
			}
		}
		System.out.println(board.getImage());
		System.out.println(board.getImage2());
		System.out.println(board.getImage3());
		
		if(board.getImage()==null) {
			board.setImage("");
		}
		if(board.getImage2()==null) {
			board.setImage2("");
		}
		if(board.getImage3()==null) {
			board.setImage3("");
		}
		int result = mapper.animal_find_ModifyProc(board);
		if(result == 0)
			return "게시글 수정에 실패했습니다. 다시 시도하세요.";
		
		return "게시글 수정 성공";
	}
	public void animal_find_main(Model model) {
		
		ArrayList<Animal_find_DTO> boards = mapper.animal_find_main();
		
		if(boards != null) {
		for (Animal_find_DTO b : boards) {
			if (b.getImage() !=null){
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
		model.addAttribute("boards", boards);
	}

}
