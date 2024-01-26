package com.care.boot.member;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.care.boot.PageService;

import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.http.HttpSession;

@Service
public class MemberService {

	@Autowired private IMemberMapper mapper;
	@Autowired private HttpSession session;
	public String registProc(MemberDTO member) {
		
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
		if(member.getAddress() == null || member.getAddress().trim().isEmpty()) {
			return "주소를 입력하세요.";
		}
		if(member.getEmail() == null || member.getEmail().trim().isEmpty()) {
			return "E-mail을 입력하세요.";
		}
		if(member.getTel() == null || member.getTel().trim().isEmpty()) {
			return "전화번호를 입력하세요.";
		}
		
		
		
		MemberDTO check = mapper.login(member.getId()); //
		if(check != null) {
			return "이미 사용중인 아이디 입니다.";
		}

		/* 암호화 과정 */
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); //DB에 데이터 저장하기 전에 암호화 진행.
		String secretPass = encoder.encode(member.getPw());
		member.setPw(secretPass);

		
		int result = mapper.registProc(member);
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
			//id로 찾은 db자료가 null이 아니고, pw가 입력한 pw와 동일하다면, no,pw 제외하고 session에 담는다. 
			session.setAttribute("id", check.getId());
			session.setAttribute("name", check.getName());
			session.setAttribute("address", check.getAddress());
			session.setAttribute("tel", check.getTel());
			session.setAttribute("email", check.getEmail());
		
			return "로그인 성공";
		}
		
		return "아이디 또는 비밀번호를 확인 후 다시 입력하세요.";
		
	}

	public String userInfo(Model model, String id) {
		
		String sessionId = (String)session.getAttribute("id");
		if(sessionId == null)
			return "로그인 후 이용하세요.";
		MemberDTO member; 
		
		if(sessionId.equals("admin")&& id != null)
			member = mapper.login(id);
		else {
			member = mapper.login(sessionId);
		}
		
		if(member.getAddress() != null && member.getAddress().isEmpty() == false) {
			String[] address = member.getAddress().split(",");
			if(address.length >= 2) {
				model.addAttribute("postcode", address[0]);
				member.setAddress(address[1]);
				if(address.length == 3) {
					model.addAttribute("detailAddress", address[2]);
				}
			}
		}
		model.addAttribute("member", member);
		return "회원 검색 완료";
		
	}

	public String updateProc(MemberDTO member, Model model) {
		
		if(member.getPw() == null || member.getPw().trim().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}
		if(member.getPw().equals(member.getConfirm()) == false) {
			return "두 비밀번호를 일치하여 입력하세요.";
		}
		if(member.getName() == null || member.getName().trim().isEmpty()) {
			return "이름을 입력하세요.";
		}
		if(member.getAddress() == null || member.getAddress().trim().isEmpty()) {
			return "주소를 입력하세요.";
		}
		if(member.getEmail() == null || member.getEmail().trim().isEmpty()) {
			return "E-mail을 입력하세요.";
		}
		if(member.getTel() == null || member.getTel().trim().isEmpty()) {
			return "전화번호를 입력하세요.";
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String secretPass = encoder.encode(member.getPw());
		member.setPw(secretPass);
		
		System.out.println(member.getId());
		System.out.println(member.getPw());
		System.out.println(member.getName());
		System.out.println(member.getAddress());
		System.out.println(member.getEmail());
		System.out.println(member.getTel());

		
		int result = mapper.updateProc(member);
		if(result == 1) {
			String id = member.getId();
			session.setAttribute("targetid", id); //관리자가 수정 하고 난 후, 수정된 유저 페이지를 보여줄때 id
			return "회원 수정 완료";
			
		}
		
		return "회원 수정을 다시 시도하세요.";
	}

	public String deleteProc(String id, String pw, String confirm) {
		if(pw == null || pw.trim().isEmpty()) {
			return "비밀번호를 입력하세요";
		}
		if(pw.equals(confirm) == false) {
			return "두 비밀번호를 일치하세요.";
		}
		String sessionId = (String)session.getAttribute("id");
		MemberDTO admincheck = mapper.login(sessionId);
		MemberDTO check = mapper.login(id);
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if(id.equals(sessionId)) { //아이디가 본 주인이라면,
			if(check != null && encoder.matches(pw, check.getPw()) == true) {
				int result = mapper.deleteProc(id);
					if(result == 1)
						return "회원 삭제 완료";
					return "회원 삭제를 다시 시도하세요.";
			}
		}else { //아이디가 관리자라면
			if(admincheck != null && encoder.matches(pw, admincheck.getPw()) == true) {
				int result = mapper.deleteProc(id);		
					if(result == 1)
						return "회원 삭제 완료";
					return "회원 삭제를 다시 시도하세요.";
			}
		}
		
		
		return "아이디 또는 비밀번호를 확인 후 입력하세요";
		
	}

	public void memberInfo(String select, String search, String cp, Model model) {
		
		int currentPage = 1;
		try{
			currentPage = Integer.parseInt(cp);
		}catch(Exception e){
			currentPage = 1;
		}
		
		if(select == null)
			select = "";
		
		int pageBlock = 3; // 한 페이지에 보일 데이터의 수 
		int end = pageBlock * currentPage; // 테이블에서 가져올 마지막 행번호
		int begin = end - pageBlock + 1; // 테이블에서 가져올 시작 행번호
		
		ArrayList<MemberDTO> members = mapper.memberInfo(begin, end, select, search);
		int totalCount = mapper.totalCount(select, search);
		if(totalCount == 0) {
			return ;
		}
		
		String url = "memberInfo?select="+select+"&search="+search+"&currentPage=";
		String result = PageService.printPage(url, totalCount, pageBlock, currentPage);
		
		model.addAttribute("select", select);
		model.addAttribute("search", search);
		model.addAttribute("result", result);
		model.addAttribute("members", members);
		
	}

	public String idConfirm() {
		String kakaoid =(String)session.getAttribute("kakaoid"); //kakao id 받아옴
		System.out.println("kakaoid = " + kakaoid);
		
		MemberDTO kakao = mapper.login(kakaoid);	//db에 값이 있는지 확인.
		if(kakao == null) {				
			return "추가 사항을 기입해 주세요.";
		}
		
		
		session.setAttribute("id", kakao.getId());
		session.setAttribute("name", kakao.getName());
		session.setAttribute("address", kakao.getAddress());
		session.setAttribute("tel", kakao.getTel());
		session.setAttribute("email", kakao.getEmail());
		return "login 되었습니다.";
	}

	public String kakaoregistProc(MemberDTO member) {
		
		if(member.getAddress() == null || member.getAddress().trim().isEmpty()) {
			return "주소를 입력하세요.";
		}
		if(member.getEmail() == null || member.getEmail().trim().isEmpty()) {
			return "E-mail을 입력하세요.";
		}
		if(member.getTel() == null || member.getTel().trim().isEmpty()) {
			return "전화번호를 입력하세요.";
		}
		
		String sessionId = (String)session.getAttribute("kakaoid");
		String sessionname = (String)session.getAttribute("kakaonickname");
		
		member.setId(sessionId);
		member.setPw("1"); //임시로 넣어준다.
		member.setName(sessionname);
		
		MemberDTO check = mapper.login(member.getId()); 
		if(check != null) {
			return "이미 사용중인 아이디 입니다. 관리자에게 문의 하세요.";
		}

		/* 암호화 과정 */
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); //DB에 데이터 저장하기 전에 암호화 진행.
		String secretPass = encoder.encode(member.getPw());
		member.setPw(secretPass);

		
		int result = mapper.registProc(member);
		if(result == 1) {
			session.setAttribute("id", member.getId());
			session.setAttribute("name", member.getName());
			session.setAttribute("address", member.getAddress());
			session.setAttribute("tel", member.getTel());
			session.setAttribute("email", member.getEmail());
			return "회원 등록 완료";
		}
		return "회원 등록을 다시 시도하세요.";
	}

	public String kakaoDeleteProc(String sessionId) {
		
		MemberDTO check = mapper.login(sessionId);
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if(check != null) {
			int result = mapper.deleteProc(sessionId);
				if(result == 1)
					return "회원 삭제 완료";
				return "회원 삭제를 다시 시도하세요.";
		}
		
		return "삭제 실패. 관리자에게 문의해 주세요.";
		
	}
	
}