package com.portfolio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.portfolio.intercepter.Intercepter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private Intercepter Interceptor;
    
	@Value("${img.upload.dir}")
    private String imageDir;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(Interceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                		"/api/user/login/**",
                		"/api/product/list",
                		"/api/product/detail");
        		
        log.info("인터셉터 설정 완료");
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String resourcePath = "file:" + imageDir + "/"; 
        registry.addResourceHandler("/images/**")
                .addResourceLocations(resourcePath);
    }
}
