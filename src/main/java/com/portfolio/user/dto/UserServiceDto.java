package com.portfolio.user.dto;

import java.sql.Timestamp;

import com.portfolio.user.dto.UserRequest.UserMyPageInfoUpdateRequest;

import lombok.Getter;
import lombok.Setter;

public class UserServiceDto {
	 @Getter
	 @Setter
	public static class UserUpdateServiceDto {
	    private String nickname;
    	private String email;
		public UserUpdateServiceDto() {

		}
		public UserUpdateServiceDto(UserMyPageInfoUpdateRequest rq) {
			this.nickname=rq.getNickname();
			this.email=rq.getEmail();
		}
		
	}

}
