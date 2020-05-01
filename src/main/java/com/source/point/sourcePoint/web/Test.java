package com.source.point.sourcePoint.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;

@Controller
@RequestMapping("/api")
@Api(description = "没有Api注解则不写入文档", tags = "Test")
public class Test {
	 
	
	@ResponseBody
	@GetMapping("/queryss")
	public String query() {
		return "好的--->"+System.currentTimeMillis();
	}

}
