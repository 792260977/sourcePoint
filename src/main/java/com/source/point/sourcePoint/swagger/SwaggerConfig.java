package com.source.point.sourcePoint.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


	@Bean
	public Docket createRestApi(@Value("${api.swagger.title}") String title,
			@Value("${api.swagger.contact}") String contact, @Value("${api.swagger.version}") String version,
			@Value("${api.swagger.basePackage}") String basePackage) {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo(title, contact, version)).select()
				// 为当前包路径
				.apis(RequestHandlerSelectors.basePackage(basePackage)).paths(PathSelectors.any()).build();
	}

	// 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
	private ApiInfo apiInfo(String title, String contact, String version) {
		return new ApiInfoBuilder().title(title).contact(new Contact(contact, "", "")).version(version).build();
	}
}
