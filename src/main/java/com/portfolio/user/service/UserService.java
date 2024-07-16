package com.portfolio.user.service;

import com.portfolio.user.dto.UserDto.UserInfo;

public interface UserService {
	UserInfo selectUserInfo(String userId) throws Exception;
}
