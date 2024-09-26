package com.portfolio.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.common.UserContext;
import com.portfolio.exception.CustomException;
import com.portfolio.user.dto.UserDto.User;
import com.portfolio.user.dto.UserRequest.NaverLoginRequest;
import com.portfolio.user.dto.UserRequest.UserMyPageInfoUpdateRequest;
import com.portfolio.user.dto.UserResponse;
import com.portfolio.user.dto.UserResponse.UserMyPageInfoResponse;
import com.portfolio.user.dto.UserServiceDto.UserUpdateServiceDto;
import com.portfolio.user.service.NaverLoginService;
import com.portfolio.user.service.UserService;
import com.portfolio.utils.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
	
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private NaverLoginService naverLoginService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserContext userContext;
	
    @PostMapping("/login/naver")
    public ResponseEntity<String> loginNaver(@Valid @RequestBody NaverLoginRequest naverLoginRequest,HttpServletResponse httpServletResponse) {
    	try {
    		UserResponse.LoginResponse result=naverLoginService.processNaverLogin(naverLoginRequest.getCode(), naverLoginRequest.getState());
    		HttpHeaders headers = new HttpHeaders();
    	    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + result.getAccessToken());
            headers.add(HttpHeaders.SET_COOKIE, "refreshToken=" + result.getRefreshToken() + "; Path=/; HttpOnly; Max-Age=2592000;");
    	    return ResponseEntity.status(HttpStatus.OK).headers(headers).body("로그인 완료");	
    	}catch (CustomException e) {
    		logger.error("네이버 로그인 실패 "+e.toString());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}catch (Exception e) {
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
                         if(userInfo==null||"N".equals(userInfo.getUseYn())) {
                         	//회원탈퇴한 유저의 토큰일 시
                        	throw new CustomException("회원탈퇴한 유저입니다");
                         }
                         HttpHeaders headers = new HttpHeaders();
                         String jwtAccessToken = jwtUtil.generateAccessToken(userInfo.getUserId());
                 	    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAccessToken);
                	    return ResponseEntity.status(HttpStatus.OK).headers(headers).body("통신성공!");
                    }
                }
            }
            throw new CustomException("리프레시 토큰 읽기 실패");
    	}catch (Exception e) {
    		logger.error("리프레시토큰을 이용한 엑세스토큰 발급 실패 "+e.toString());
    		HttpHeaders headers = new HttpHeaders();
    		headers.add(HttpHeaders.SET_COOKIE, "refreshToken=; Path=/; HttpOnly; Max-Age=0;");
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body("통신실패");
		}
    }

    @PostMapping("/myPage/info")
    public ResponseEntity<?> userMyPageInfo() {
    	try {
    		User user=userContext.getUserInfo();
    		UserMyPageInfoResponse rs=new UserMyPageInfoResponse(user);
    	    return ResponseEntity.status(HttpStatus.OK).body(rs);
    	}catch (Exception e) {
    		log.error("마이페이지 유저정보 불러오기 실패 "+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("마이페이지 유저정보 불러오기 실패했습니다");
		}
    
    }
    @PostMapping("/myPage/info/update")
    public ResponseEntity<?> userMyPageInfoUpdate(@Valid @RequestBody UserMyPageInfoUpdateRequest userMyPageInfoUpdateRequest) {
    	try {
    		UserUpdateServiceDto UserUpdateServiceDto=new UserUpdateServiceDto(userMyPageInfoUpdateRequest);
    		userService.updateUser(UserUpdateServiceDto);
    	    return ResponseEntity.status(HttpStatus.OK).body("업데이트 성공");
    	}catch (Exception e) {
    		log.error("마이페이지 유저정보 수정 실패 "+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("마이페이지 유저정보 수정 실패했습니다");
		}
    
    }
    @PostMapping("/delete")
    public ResponseEntity<?> userDelete() {
    	try {
    		userService.deleteUser();
    		HttpHeaders headers = new HttpHeaders();
    		headers.add(HttpHeaders.SET_COOKIE, "refreshToken=; Path=/; HttpOnly; Max-Age=0;");
    	    return ResponseEntity.status(HttpStatus.OK).headers(headers).body("회원 탈퇴 성공");
    	}catch (Exception e) {
    		log.error("회원 탈퇴 실패 "+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 탈퇴 실패");
		}
    
    }
    @PostMapping("/logout")
    public ResponseEntity<?> userLogout() {
    	try {
    		HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, "refreshToken=" + "; Path=/; HttpOnly; Max-Age=0;");
    	    return ResponseEntity.status(HttpStatus.OK).headers(headers).body("로그아웃 성공");
    	}catch (Exception e) {
    		log.error("로그아웃 실패 "+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그아웃 실패");
		}
    
    }
}
