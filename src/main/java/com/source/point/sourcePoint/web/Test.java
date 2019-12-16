package com.source.point.sourcePoint.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class Test {
	
	@ResponseBody
	@GetMapping("/queryss")
	public String query() {
		return "好的--->"+System.currentTimeMillis();
	}

}
