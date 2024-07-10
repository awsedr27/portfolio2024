package com.portfolio.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.user.dao.UserDao;
import com.portfolio.user.dto.UserDto.UserInfo;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;

	@Override
	public UserInfo selectUserInfo(String userId) {
		return userDao.selectUserInfo(userId);
	}

	

}
