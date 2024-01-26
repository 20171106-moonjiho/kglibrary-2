package com.care.boot.community;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommunityController {
	@GetMapping("find")
	public String come() {
		return "community/find";
	}
	@GetMapping("find2")
	public String find2() {
		return "community/find2";
	}
	@GetMapping("find3")
	public String find3() {
		return "community/find3";
	}
}
