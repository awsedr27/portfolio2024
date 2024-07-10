package com.portfolio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@Profile("local")
public class CorsConfig implements WebMvcConfigurer {
	
    private static final Logger logger = LoggerFactory.getLogger(CorsConfig.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 엔드포인트에 대해 CORS 설정을 적용
                .allowedOrigins("http://39.120.192.178:3000") // 클라이언트의 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 허용할 HTTP 헤더
                .allowCredentials(true) // 클라이언트의 credentials(cookie, HTTP 인증 등) 허용 여부
                .maxAge(0) // preflight 요청 결과를 캐시할 시간
        		.exposedHeaders("Authorization");
        logger.info("CORS 설정이 적용되었습니다.");
    }
    
}
