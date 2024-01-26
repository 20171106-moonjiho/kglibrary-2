package com.kg.library.member;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

//import com.kg.library.book.BookDTO;
//import com.kg.library.book.IBookMapper;
//import com.kg.library.reservation.ReservationDTO;
//import com.kg.library.reservation.ReservationMapper;

import jakarta.servlet.http.HttpSession;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class MemberService {
	
	@Autowired private IMemberMapper mapper;
	@Autowired private HttpSession session;
//	@Autowired private ReservationMapper mapper2;
//	@Autowired private IBookMapper mapper3;
	
	@Value("${coolsms.apikey}")
    private String apiKey;

    @Value("${coolsms.apisecret}")
    private String apiSecret;

    @Value("${coolsms.fromnumber}")
    private String fromNumber;
	
	int count = 0;
	
	public String joinProc(MemberDTO member) {
		if(member.getId() == null || member.getId().trim().isEmpty()) {
			return "아이디를 입력하세요.";
		}
		if(member.getPw() == null || member.getPw().trim().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}
		if(member.getPw().equals(member.getConfirm()) == false) {
			return "두 비밀번호를 일치하여 입력하세요.";
		}
		if(member.getName() == null || member.getName().trim().isEmpty()) {
			return "이름을 입력하세요.";
		}
		
		MemberDTO check = mapper.login(member.getId());
		if(check != null) {
			return "이미 사용중인 아이디 입니다.";
		}
		
		/* 암호화 과정 */
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String secretPass = encoder.encode(member.getPw());
		member.setPw(secretPass);
		
		int result = mapper.joinProc(member);
		if(result == 1)
			return "회원 등록 완료";
		
		return "회원 등록을 다시 시도하세요.";
	}
	
	
	public String loginProc(String id, String pw) {
		if(id == null || id.trim().isEmpty()) {
			return "아이디를 입력하세요.";
		}
		if(pw == null || pw.trim().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}
		
		MemberDTO check = mapper.login(id);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(check != null && encoder.matches(pw, check.getPw()) == true) {
			session.setAttribute("id", check.getId());
			session.setAttribute("name", check.getName());
			session.setAttribute("email", check.getEmail());
			session.setAttribute("address", check.getAddress());
			session.setAttribute("tel", check.getTel());
			return "로그인 성공";
		}
		
		return "아이디 또는 비밀번호를 확인 후 다시 입력하세요.";
	}
	

	public void certifiedPhoneNumber(String tel, String numStr) {
		Message coolsms = new Message(apiKey, apiSecret);
 
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", tel);    
        params.put("from", fromNumber);   
        params.put("type", "SMS");
        params.put("text", "["+numStr+"]");
        params.put("app_version", "test app 1.2"); // application name and version

        System.out.println("CoolSMS API Request Parameters: " + params);
        
        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
	}

	
	public String userInfo(Model model) {
		String sessionId = (String)session.getAttribute("id");
		if(sessionId == null)
			return "로그인 후 이용하세요.";
		
		MemberDTO member = mapper.login(sessionId);
		if(member.getAddress() != null && member.getAddress().isEmpty() == false) {
			String[] address = member.getAddress().split(",");
			model.addAttribute("postcode", address[0]);
			member.setAddress(address[1]);
			model.addAttribute("detailAddress", address[2]);
		}
		model.addAttribute("member", member);
		return "회원 검색 완료";
	}
	
	public String updateProc(MemberDTO member) {
		if(member.getPw() == null || member.getPw().trim().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}
		if(member.getPw().equals(member.getConfirm()) == false) {
			return "두 비밀번호를 일치하여 입력하세요.";
		}
		if(member.getName() == null || member.getName().trim().isEmpty()) {
			return "이름을 입력하세요.";
		}
		/* 암호화 과정 */
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String secretPass = encoder.encode(member.getPw());
		member.setPw(secretPass);
		
		session.setAttribute("id", member.getId());
		session.setAttribute("name", member.getName());
		session.setAttribute("email", member.getEmail());
		session.setAttribute("address", member.getAddress());
		session.setAttribute("tel", member.getTel());
		
		int result = mapper.updateProc(member);
		if(result == 1)
			return "회원 수정 완료";
		
		return "회원 수정을 다시 시도하세요.";
	}


	public String deleteProc(MemberDTO member) {
		if(member.getPw() == null || member.getPw().trim().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}
		if(member.getPw().equals(member.getConfirm()) == false) {
			return "두 비밀번호를 일치하여 입력하세요.";
		}
		
		MemberDTO check = mapper.login(member.getId());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(check != null && encoder.matches(member.getPw(), check.getPw()) == true) {
			int result = mapper.deleteProc(member.getId());
			if(result == 1)
				return "회원 삭제 완료";
			return "회원 삭제를 다시 시도하세요.";
		}
		
		return "아이디 또는 비밀번호를 확인 후 입력하세요";
	}

//	public List<ReservationDTO> myReservation(String sessionId) {
//		LocalDate date = LocalDate.now();
//		List<ReservationDTO> now = new ArrayList<>();
//		List<ReservationDTO> list = mapper2.getReservations4(sessionId);
//		for(ReservationDTO reservation: list) {
//			if(!date.isAfter(LocalDate.parse(reservation.getReservation_date()))) now.add(reservation);
//		}
//		return now;
//	}
//
//
//	public void cancel(ReservationDTO dto) {
//		mapper2.cancel(dto);
//	}
//
//
//	public List<ReservationDTO> preReservation(String sessionId) {
//		LocalDate date = LocalDate.now();
//		List<ReservationDTO> pre = new ArrayList<>();
//		List<ReservationDTO> list = mapper2.getReservations4(sessionId);
//		for(ReservationDTO reservation: list) {
//			if(date.isAfter(LocalDate.parse(reservation.getReservation_date()))) pre.add(reservation);
//		}
//		return pre;
//	}


//	public void myBook(Model model, String id) {
//		List<BookDTO> boards = mapper3.myBook(id); //도서 리스트	
//
//		model.addAttribute("boards", boards);
//	}


//	public void borrowDateExtend(Model model, String no, String sessionId) {
//	    int n = 1;
//	  
//	    try {
//	        n = Integer.parseInt(no);
//	    } catch (Exception e) {
//	        
//	    }
//	   
//	    BookDTO board = mapper3.bookContent(n);
//	    //n = board.getNo();
//	    
////	    if (board == null) {
////	        // 책이 존재하지 않는 경우 또는 오류가 발생한 경우
////	        
////	    }
//
//	    Timestamp storedTime = board.getBorrowdate();
//	    LocalDateTime storedLocalDateTime = storedTime.toLocalDateTime();
//	    LocalDateTime rentalday = storedLocalDateTime.plus(Duration.ofDays(14));
//	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//	    String rentaldate = rentalday.format(formatter);
//
//	    //model.addAttribute("rentaldate", rentaldate);
//	    mapper3.borrowDateExtend(n, sessionId, rentaldate);
//	    	    
//	}
	
//	public void returnProc2(String no) { // 반납
//		
//		int n = 1;
//		try{
//			n = Integer.parseInt(no);
//		}catch(Exception e){
//		}
//		
//		String borrowperson = "대여 가능";
//		
//		mapper3.returnProc2(n, borrowperson);
//		
//	}


	public String nameTelCheck(MemberDTO member) {
		MemberDTO result = mapper.findId(member.getName(), member.getTel());
		if(result == null) {
			return "error";
		} else {
			return "success";
		}
	}
	
	public void findIdResult(Model model, MemberDTO member) {
		String name = member.getName();
		String tel= member.getTel();
	    String id = mapper.findIdResult(name, tel);
	    System.out.println(name+tel+id);
	    model.addAttribute("id", id);
	}


	public String idTelCheck(MemberDTO member) {
		MemberDTO result = mapper.findPw(member.getId(), member.getTel());
		if(result == null) {
			return "error";
		} else {
			return "success";
		}
	}


	public void findPwResult(Model model, MemberDTO member, String numStr) {
		String id = member.getId();
		String tel = member.getTel();
		
	    System.out.println(id+tel+numStr);
	    model.addAttribute("pw", numStr);
	    /* 암호화 과정 */
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    String secretPass = encoder.encode(numStr);
	    member.setPw(secretPass);
	    
	    mapper.findPwResult(id, tel, secretPass);
	}
}
