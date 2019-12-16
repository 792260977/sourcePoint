package com.source.point.sourcePoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SourcePointApplication {

	public static void main(String[] args) {
		SpringApplication.run(SourcePointApplication.class, args);
	}

}
