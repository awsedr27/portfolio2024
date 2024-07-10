package com.portfolio.user.service;

import com.portfolio.user.dto.UserResponse;

public interface NaverLoginService {
	UserResponse.LoginResponse processNaverLogin(String code,String state) throws Exception;
}
