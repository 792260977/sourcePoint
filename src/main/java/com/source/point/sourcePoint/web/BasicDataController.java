package com.source.point.sourcePoint.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@Api(description = "基础数据相关", tags = "BASIC-DATA")
public class BasicDataController {
	private static final Logger log = LoggerFactory.getLogger(BasicDataController.class);
	@GetMapping("/query")
	public String query() {
		String str = "好的--->"+System.currentTimeMillis();
		log.info(str);
		return str;
	}

}
