package com.source.point.sourcePoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableEurekaServer 暂时注释掉Eureka服务
public class SourcePointApplication {

	public static void main(String[] args) {
		SpringApplication.run(SourcePointApplication.class, args);
	}

}
