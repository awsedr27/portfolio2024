package com.portfolio.user.dto;

import lombok.Getter;
import lombok.Setter;

public class UserRequest {
	
    @Getter
    @Setter
	public static class NaverLoginRequest {
	    private String code;
	    private String state;
	}
}
