package com.portfolio.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.user.dto.UserDto.NaverUserProfile;
import com.portfolio.user.dto.UserRequest.NaverLoginRequest;
import com.portfolio.user.dto.UserResponse;
import com.portfolio.user.dto.UserResponse.LoginResponse;
import com.portfolio.user.service.NaverLoginService;
import com.portfolio.user.service.NaverLoginServiceImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private NaverLoginService naverLoginService;
	
    @PostMapping("/login/naver")
    public ResponseEntity<String> createTodo(@RequestBody NaverLoginRequest naverLoginRequest,HttpServletResponse httpServletResponse) {
    	try {
    		UserResponse.LoginResponse result=naverLoginService.processNaverLogin(naverLoginRequest.getCode(), naverLoginRequest.getState());
    		HttpHeaders headers = new HttpHeaders();
    	    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + result.getAccessToken());
            headers.add(HttpHeaders.SET_COOKIE, "refreshToken=" + result.getRefreshToken() + "; Path=/; HttpOnly; Max-Age=2592000;");

            
    	    
    	    return ResponseEntity.status(HttpStatus.OK).headers(headers).body("로그인 완료");	
    	}catch (Exception e) {
			// TODO: handle exception
    		logger.error("네이버 로그인 실패 "+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 실패");
		}
    }
    
    
    @PostMapping("/test")
    public ResponseEntity<String> test(HttpServletRequest request) {
    	try {
    		Cookie[] cookies = request.getCookies();
            return ResponseEntity.status(HttpStatus.OK).body("통신성공");	
    	}catch (Exception e) {
			// TODO: handle exception
    		logger.error("네이버 로그인 실패 "+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("통신실패");
		}
    }

}
