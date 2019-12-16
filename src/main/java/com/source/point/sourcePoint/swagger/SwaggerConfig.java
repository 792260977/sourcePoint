package com.source.point.sourcePoint.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
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
@ConditionalOnProperty(name = "api.swagger.switch", havingValue = "true")
public class SwaggerConfig {

	@Bean
	public Docket createRestApi(@Value("${api.swagger.title}") String title,
			@Value("${api.swagger.contact}") String contact, @Value("${api.swagger.version}") String version) {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo(title, contact, version)).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo(String title, String contact, String version) {
		return new ApiInfoBuilder().title(title).contact(new Contact(contact, "", "")).version(version).build();
	}
}
