package com.ssafy.d204;

import com.ssafy.d204.general.JwtInterceptor;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) // please do not remove.
@MapperScan(basePackages = "com.ssafy.d204.db.repository,com.ssafy.d204.chat.dao")
public class Project1Application implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    //JWTInterceptor를 설치한다.
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
//		        .addPathPatterns("/api/jwt/**") // 기본 적용 경로
            .addPathPatterns("/api/jwt/**"); // 기본 적용 경로
//		        .excludePathPatterns(Arrays.asList("/user/confirm/**", "/board/list"));// 적용 제외 경로
        // 만약 예외경로 적용하고자 한다면 참고하기
    }

    //Interceptor CORS bypass
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*")
            .exposedHeaders("auth-token");
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Project1Application.class, args);
    }
}
