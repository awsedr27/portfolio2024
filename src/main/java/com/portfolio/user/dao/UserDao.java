package com.portfolio.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.user.dto.UserDto.User;

@Mapper
public interface UserDao {
	User selectUserInfoByNaverSnsId(String naverSnsId);
	int insertUser(User userInfo);
	int updateUser(User userInfo);
	User selectUser(String id);
	int deleteUser(String userId);
}
