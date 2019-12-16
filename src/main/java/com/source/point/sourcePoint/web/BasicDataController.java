package com.source.point.sourcePoint.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@Api(description = "基础数据相关", tags = "BASIC-DATA")
public class BasicDataController {

	@GetMapping("/query")
	public String query() {
		return "好的--->"+System.currentTimeMillis();
	}

}
