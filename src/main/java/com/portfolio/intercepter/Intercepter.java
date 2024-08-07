package com.portfolio.intercepter;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.portfolio.common.UserContext;
import com.portfolio.user.dto.UserDto.User;
import com.portfolio.user.service.UserService;
import com.portfolio.utils.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Intercepter implements HandlerInterceptor {

    @Autowired
    private UserService userService; 

    @Autowired
    private UserContext userContext; 
    
    @Autowired
    private JwtUtil jwtUtil; 

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	if(CorsUtils.isPreFlightRequest(request)) {
    		return true;
    	}
    	
    	String token = extractJwtToken(request);
        
        if (token != null) {
            try {
            	
                Claims claims = jwtUtil.extractAllClaimsByAccessToken(token);
                String userId=claims.getSubject();
                User userInfo=userService.selectUser(userId);
                if("N".equals(userInfo.getUseYn())) {
                	//회원탈퇴한 유저의 토큰일 시
                	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                	return false;
                }
                userContext.setUserInfo(userInfo);

                return true;
            }catch (ExpiredJwtException ex) {
                // 만료된 토큰 처리
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }catch (JwtException ex) {
                // 그 외의 JWT 예외 처리
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
    
    

    @Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}



	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
	
    private String extractJwtToken(HttpServletRequest request) {
        // HTTP 요청의 Authorization 헤더에서 JWT 토큰을 추출합니다.
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후의 실제 토큰 값을 반환합니다.
        }

        return null; // 유효한 JWT 토큰이 없는 경우 null을 반환합니다.
    }

}

