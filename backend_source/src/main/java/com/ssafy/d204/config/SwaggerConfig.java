package com.ssafy.d204.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	//3.0.0 http://localhost:8088/temp/swagger-ui/index.html

	RequestParameterBuilder tokenBuilder = new RequestParameterBuilder();

	List<RequestParameter> aParameters = new ArrayList<>();

	public SwaggerConfig(){
		tokenBuilder
				.name("auth-token")
				.description("auth-t2oken")
	            .required(false)
	            .in("header")
	            .accepts(Collections.singleton(MediaType.APPLICATION_JSON));
		aParameters.add(tokenBuilder.build());
	}

	@Bean
	public Docket authApi() {

	    //swagger 내에서 글로벌하게 적용되어야 하는 헤더가 필요하다면 RequestParameterBuilder 하나 더 만들어서
	    //aParameters에 추가하기.
		final ApiInfo apiInfo=new ApiInfoBuilder()
				.title("")
				.description("구미 2반 4팀 서브프젝1")
				.contact(new Contact("SSAFY_5th_D204", "https://ssafy.com", "no@email.com"))
				.license("MIT License")
				.version("5.0")
				.build();
		return new Docket(DocumentationType.SWAGGER_2)
                .globalRequestParameters(aParameters) // jwt 테스트를 위한 토큰 추가
				.apiInfo(apiInfo)
				.groupName("All controller")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ssafy.d204.api.controller"))
				.paths(PathSelectors.ant("/**/api/**")
//						.or(PathSelectors.ant("/**/**"))
						) 
				.build();
	}
	@Bean
	public Docket chatApi() {
	    RequestParameterBuilder tokenBuilder = new RequestParameterBuilder();
	    tokenBuilder
	            .name("auth-token")
	            .description("auth-t2oken")
	            .required(false)
	            .in("header")
	            .accepts(Collections.singleton(MediaType.APPLICATION_JSON));
	    List<RequestParameter> aParameters = new ArrayList<>();
	    aParameters.add(tokenBuilder.build());
	    //swagger 내에서 글로벌하게 적용되어야 하는 헤더가 필요하다면 RequestParameterBuilder 하나 더 만들어서
	    //aParameters에 추가하기.


		final ApiInfo apiInfo=new ApiInfoBuilder()
				.title("")
				.description("채팅API")
				.contact(new Contact("SSAFY_5th_D204", "https://ssafy.com", "no@email.com"))
				.license("MIT License")
				.version("5.0")
				.build();
		return new Docket(DocumentationType.SWAGGER_2)
                .globalRequestParameters(aParameters) // jwt 테스트를 위한 토큰 추가
				.apiInfo(apiInfo)
				.groupName("Chat")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ssafy.d204.chat.controller"))
				.paths(PathSelectors.ant("/**/api/chat/**")
//						.or(PathSelectors.ant("/**/chat/**"))
						)
				.build();
	}


}
