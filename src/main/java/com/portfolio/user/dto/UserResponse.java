package com.portfolio.user.dto;

import java.sql.Timestamp;

import com.portfolio.user.dto.UserDto.User;

import lombok.Getter;
import lombok.Setter;

public class UserResponse {

    @Getter
    @Setter
    public static class NaverTokenResponse{
    	String access_token;
    	String refresh_token;
    	String token_type;
    	Integer expires_in;
    	String error;
    	String error_description;
    }
    
    @Getter
    @Setter
    public static class LoginResponse{
    	String accessToken;
    	String refreshToken;
    	
    	public LoginResponse() {
    		
    	}
    	public LoginResponse(String accessToken,String refreshToken) {
    		this.accessToken=accessToken;
    		this.refreshToken=refreshToken;
    	}
    	
    }
    @Getter
    @Setter
    public static class UserMyPageInfoResponse{
        private String username;
        private String email;
        private String gender;
        private String birthday;
        private String birthyear;
        private String nickname;
    	public UserMyPageInfoResponse() {
    		
    	}
    	public UserMyPageInfoResponse(User user) {
    		this.username=user.getUsername();
    		this.email=user.getEmail();
    		this.gender=user.getGender();
    		this.birthday=user.getBirthday();
    		this.birthyear=user.getBirthyear();
    		this.nickname=user.getNickname();
    	}
    }
}
