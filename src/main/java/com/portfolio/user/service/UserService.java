package com.portfolio.user.service;

import com.portfolio.user.dto.UserDto.User;

public interface UserService {
	User selectUser(String userId) throws Exception;
}
