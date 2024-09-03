package com.portfolio.user.dto;

import jakarta.validation.constraints.Email;
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
    @Getter
    @Setter
	public static class UserMyPageInfoUpdateRequest {
    	@NotBlank(message = "Code cannot be blank")
	    private String nickname;
    	
    	@Email(message = "이메일 주소 형식이 유효하지 않습니다.")
	    private String email;
	}
}
