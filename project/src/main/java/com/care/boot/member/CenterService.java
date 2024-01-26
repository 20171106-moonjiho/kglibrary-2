package com.care.boot.member;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import jakarta.servlet.http.HttpSession;

@Service
public class CenterService {
	
	private String accessToken;

	public void getCenterInfo(Model model) {
		String requrl = "https://openapi.gg.go.kr/OrganicAnimalProtectionFacilit?key=2c25d2c80d0f4770bc161cd84aabee64&Type=json&pIndex=1";

		// Authorization: Bearer ${ACCESS_TOKEN}

		try {

			URL url = new URL(requrl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");

			ObjectMapper om = new ObjectMapper();

			int responseCode = conn.getResponseCode(); // 결과 코드가 200이면 성공 확인용
			System.out.println("code = " + responseCode);

			JsonNode jsonNode = om.readTree(conn.getInputStream());

			JsonNode tmp = jsonNode.findValue("row");
			System.out.println(tmp.size());
			
			String rows = om.writeValueAsString(tmp);
			System.out.println("값 확인" + tmp.get(0).get("SUM_YY").toString());

			List<CenterInfoDTO> centerList = new ArrayList<CenterInfoDTO>();

			for (int i = 0; i < tmp.size(); i++) {	//replace 사용시 Error나는 것을 확인. for문으로 진행. //결국 ""까지 변수로 잡아버려서 문제가 생김.
				CenterInfoDTO centerDto = new CenterInfoDTO();
				centerDto.setSUM_YY(jsonNode.get("OrganicAnimalProtectionFacilit").get(1).get("row").get(i).get("SUM_YY").toString());
				centerDto.setSIGUN_NM(jsonNode.get("OrganicAnimalProtectionFacilit").get(1).get("row").get(i).get("SIGUN_NM").toString());
				centerDto.setSIGUN_CD(jsonNode.get("OrganicAnimalProtectionFacilit").get(1).get("row").get(i).get("SIGUN_CD").toString());
				centerDto.setRM_MATR(jsonNode.get("OrganicAnimalProtectionFacilit").get(1).get("row").get(i).get("RM_MATR").toString());
				centerDto.setREPRSNTV_NM(jsonNode.get("OrganicAnimalProtectionFacilit").get(1).get("row").get(i).get("REPRSNTV_NM").toString());
				centerDto.setREFINE_ZIP_CD(jsonNode.get("OrganicAnimalProtectionFacilit").get(1).get("row").get(i).get("REFINE_ZIP_CD").toString());
				centerDto.setREFINE_WGS84_LOGT(jsonNode.get("OrganicAnimalProtectionFacilit").get(1).get("row").get(i).get("REFINE_WGS84_LOGT").toString());
				centerDto.setREFINE_WGS84_LAT(jsonNode.get("OrganicAnimalProtectionFacilit").get(1).get("row").get(i).get("REFINE_WGS84_LAT").toString());
				centerDto.setREFINE_ROADNM_ADDR(jsonNode.get("OrganicAnimalProtectionFacilit").get(1).get("row").get(i).get("REFINE_ROADNM_ADDR").toString());
				centerDto.setREFINE_LOTNO_ADDR(jsonNode.get("OrganicAnimalProtectionFacilit").get(1).get("row").get(i).get("REFINE_LOTNO_ADDR").toString());
				centerDto.setENTRPS_TELNO(jsonNode.get("OrganicAnimalProtectionFacilit").get(1).get("row").get(i).get("ENTRPS_TELNO").toString());
				centerDto.setENTRPS_NM(jsonNode.get("OrganicAnimalProtectionFacilit").get(1).get("row").get(i).get("ENTRPS_NM").toString());
				centerDto.setCORPR_ANIMAL_HOSPTL_DTLS(jsonNode.get("OrganicAnimalProtectionFacilit").get(1).get("row").get(i).get("CORPR_ANIMAL_HOSPTL_DTLS").toString());
				centerDto.setCONTRACT_PERD(jsonNode.get("OrganicAnimalProtectionFacilit").get(1).get("row").get(i).get("CONTRACT_PERD").toString());
				centerDto.setACEPTNC_ABLTY_CNT(jsonNode.get("OrganicAnimalProtectionFacilit").get(1).get("row").get(i).get("ACEPTNC_ABLTY_CNT").toString());
				centerList.add(centerDto);
				
			}
			
			
			model.addAttribute("centers",centerList);


		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}


/*
 * 네이버 디벨로퍼 로그인 참고 할 것. 1.db table을 소셜 전용으로 하나 더 만들어서 진행 하는 것. 2.기존 db + 필요한 정보는
 * 추가적으로 입력 받게 만드는 것.
 * 
 */
