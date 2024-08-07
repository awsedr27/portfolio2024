package com.portfolio.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.user.dao.UserDao;
import com.portfolio.user.dto.UserDto.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;

	@Override
	public User selectUser(String userId)throws Exception {
		return userDao.selectUser(userId);
	}

	

}
