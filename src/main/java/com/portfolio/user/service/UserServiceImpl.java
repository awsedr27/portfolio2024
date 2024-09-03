package com.portfolio.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.common.UserContext;
import com.portfolio.user.dao.UserDao;
import com.portfolio.user.dto.UserDto.User;
import com.portfolio.user.dto.UserServiceDto.UserUpdateServiceDto;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	UserContext userContext;

	@Override
	public User selectUser(String userId)throws Exception {
		return userDao.selectUser(userId);
	}

	@Override
	public int updateUser(UserUpdateServiceDto userUpdateServiceDto) {
		User user=new User(userUpdateServiceDto);
		user.setUserId(userContext.getUserInfo().getUserId());
		return userDao.updateUser(user);
	}

	

}
