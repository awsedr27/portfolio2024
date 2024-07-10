package com.portfolio.user.dto;

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
}
