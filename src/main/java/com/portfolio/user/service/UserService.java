package com.portfolio.user.service;

import com.portfolio.user.dto.UserDto.User;
import com.portfolio.user.dto.UserServiceDto.UserUpdateServiceDto;

public interface UserService {
	User selectUser(String userId) throws Exception;

	int updateUser(UserUpdateServiceDto userUpdateServiceDto);

	int deleteUser();
}
