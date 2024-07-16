package com.portfolio.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class UserRequest {
	
    @Getter
    @Setter
	public static class NaverLoginRequest {
    	@NotBlank(message = "Code cannot be blank")
	    private String code;
    	
        @NotBlank(message = "State cannot be blank")
	    private String state;
	}
}
