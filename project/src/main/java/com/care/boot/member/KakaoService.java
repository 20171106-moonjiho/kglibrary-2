package com.care.boot.member;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@Service
public class KakaoService {
	
	private String accessToken;
	@Autowired 
	private HttpSession session;
	private String jgetiD;

	public void getAccessToken(String code) {
		String requrl = "https://kauth.kakao.com/oauth/token";	//요청 시작
		String reqParam = "grant_type=authorization_code";
		reqParam += "&client_id=034039e38d0144a17163014474d7bacd";
		reqParam += "&redirect_uri=http://localhost:80/kakaoLogin"; 
		reqParam += "&code=" + code;	//요청 끝
		
		//서버 대 서버 통신
		try {
			URL url = new URL(requrl);// Post요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST"); // POST 요청을 위해 기본값 false에서 setDoOutput을 true로 바꿈
			conn.setDoOutput(true); //POST 메소드를 이용해서 데이터를 전달하기 위한 설정
			
			// 기본 outputStream을 통해 문자열로 처리할 수 있는
			// OutputStreamWriter 변환 후 처리 속도를 빠르게 하기 위한
			// BufferedWriter로 변환해서 사용한다.
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bw.write(reqParam);//데이터 보내기
			bw.flush(); //저장소를 초기화 한다.
			
			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			InputStreamReader isr = new InputStreamReader(conn.getInputStream());
			ObjectMapper om = new ObjectMapper();
			
			Map<String, String> map = om.readValue(isr,new TypeReference<Map<String, String>>() {});
			accessToken = map.get("access_token");
			System.out.println("accessToken : " + map.get("access_token"));
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		
	}
	
	public void getUserInfo(Model model) {
		String requrl = "https://kapi.kakao.com/v2/user/me";
		
		//Authorization: Bearer ${ACCESS_TOKEN}
		
		try {
			
			URL url = new URL(requrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " +accessToken);
			
			int responseCode = conn.getResponseCode(); // 결과 코드가 200이면 성공 확인용
			System.out.println("code = " + responseCode);
			
			ObjectMapper om = new ObjectMapper();
			JsonNode jsonNode = om.readTree(conn.getInputStream());
			System.out.println();
			System.out.println(jsonNode.get("Kakao_account"));
			System.out.println(jsonNode);
			//System.out.println(jsonNode.get("id"));
			//System.out.println(jsonNode.get("kakao_account").get("profile").get("nickname"));
			jgetiD = om.writeValueAsString(jsonNode.get("id"));
			System.out.println("jgetiD = " +jgetiD);
			System.out.println("카카오 이름 = " + om.writeValueAsString(jsonNode.get("properties").get("nickname")));
			//session.setAttribute("id", jsonNode.get("kakao_account").get("email"));
			//session.setAttribute("id", jgetiD);
			
			session.setAttribute("kakaoid", om.writeValueAsString(jsonNode.get("id"))); //string 변환 해줘야 함./회원 탈퇴 시 사용 id
			session.setAttribute("kakaonickname", om.writeValueAsString(jsonNode.get("properties").get("nickname")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void unlink() {
		String requrl = "https://kapi.kakao.com/v1/user/unlink";	
		
	try {
			
			URL url = new URL(requrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();	
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " +accessToken);
			
			int responseCode = conn.getResponseCode(); // 결과 코드가 200이면 성공 확인용
			System.out.println("code = " + responseCode);
			
			ObjectMapper om = new ObjectMapper();
			JsonNode jsonNode = om.readTree(conn.getInputStream());			
			//session.setAttribute("id", jsonNode.get("kakao_account").get("email"));
			//System.out.println("id = "  + member.getId());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


/*
 * 네이버 디벨로퍼 로그인 참고 할 것.
 * 1.db table을 소셜 전용으로 하나 더 만들어서 진행 하는 것.
 * 2.기존 db + 필요한 정보는 추가적으로 입력 받게 만드는 것.
 * 
 */


