package com.portfolio.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.user.dto.UserDto.UserInfo;

@Mapper
public interface UserDao {
	UserInfo selectUserInfoByNaverSnsId(String naverSnsId);
	int insertUserInfo(UserInfo userInfo);
	int updateUserInfo(UserInfo userInfo);
	UserInfo selectUserInfo(String id);
}
