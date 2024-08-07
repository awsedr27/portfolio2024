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
import com.portfolio.user.dto.UserDto.User;
import com.portfolio.user.dto.UserRequest.NaverLoginRequest;
import com.portfolio.user.dto.UserResponse;
import com.portfolio.user.dto.UserResponse.LoginResponse;
import com.portfolio.user.service.NaverLoginService;
import com.portfolio.user.service.NaverLoginServiceImpl;
import com.portfolio.user.service.UserService;
import com.portfolio.utils.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private NaverLoginService naverLoginService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	UserService userService;
	
    @PostMapping("/login/naver")
    public ResponseEntity<String> loginNaver(@Valid @RequestBody NaverLoginRequest naverLoginRequest,HttpServletResponse httpServletResponse) {
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
    
    
    @PostMapping("/login/refresh")
    public ResponseEntity<String> refreshToken(HttpServletRequest request) {
    	try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("refreshToken".equals(cookie.getName())) {
                    	 Claims claims=jwtUtil.extractAllClaimsByRefreshToken(cookie.getValue());
                    	 String userId=claims.getSubject();
                         User userInfo=userService.selectUser(userId);
                         if("N".equals(userInfo.getUseYn())) {
                         	//회원탈퇴한 유저의 토큰일 시
                        	 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("통신성공");
                         }
                         HttpHeaders headers = new HttpHeaders();
                         String jwtAccessToken = jwtUtil.generateAccessToken(userInfo.getUserId());
                 	    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAccessToken);
                	    return ResponseEntity.status(HttpStatus.OK).headers(headers).body("통신성공!");
                    }
                }
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("통신성공");
    	}catch (Exception e) {
    		logger.error("리프레시토큰을 이용한 엑세스토큰 발급 실패 "+e.toString());
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("통신실패");
		}
    }
    


}
