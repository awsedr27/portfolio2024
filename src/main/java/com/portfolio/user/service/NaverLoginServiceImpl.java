package com.portfolio.user.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.portfolio.exception.CustomException;
import com.portfolio.user.dao.UserDao;
import com.portfolio.user.dto.UserDto.NaverUserProfile;
import com.portfolio.user.dto.UserDto.User;
import com.portfolio.user.dto.UserResponse;
import com.portfolio.user.dto.UserResponse.NaverTokenResponse;
import com.portfolio.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NaverLoginServiceImpl implements NaverLoginService {
    
	@Value("${naver.client.token.url}")
    private String tokenRequestUrl;
	
	@Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;
    
    @Value("${naver.client.profile.url}")
    private String naverProfileUrl;

    private final UserDao userDao;
    private final JwtUtil jwtUtil;

	public String getAccessToken(String code, String state) throws Exception {
		String tokenUrl = tokenRequestUrl + "&client_id="
				+ clientId + "&client_secret=" + clientSecret + "&code=" + code
				+ "&state=" + state;

		RestTemplate restTemplate = new RestTemplate();
		NaverTokenResponse response = restTemplate.getForObject(tokenUrl, NaverTokenResponse.class);

		return response.getAccess_token();
	}


	public NaverUserProfile getUserProfile(String accessToken) throws Exception {
        String profileUrl = naverProfileUrl;
        
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<NaverUserProfile> response = restTemplate.exchange(profileUrl, HttpMethod.GET, request, NaverUserProfile.class);
        return response.getBody();
	}

	@Override
	public UserResponse.LoginResponse processNaverLogin(String code, String state) throws Exception  {
        String accessToken = getAccessToken(code, state);
        NaverUserProfile naverUserProfile = getUserProfile(accessToken);
        if(naverUserProfile==null||naverUserProfile.getResponse()==null||StringUtils.isBlank(naverUserProfile.getResponse().getId())) {
        	 throw new CustomException("네이버 프로필이 없습니다");
        }
        User userInfo=userDao.selectUserInfoByNaverSnsId(naverUserProfile.getResponse().getId());
        User userInfoRq=new User(naverUserProfile.getResponse());
        if (userInfo == null) {
        	//회원가입 
        	userDao.insertUser(userInfoRq);
        	
        }else {
        	userInfoRq.setUserId(userInfo.getUserId());
        	if("N".equals(userInfo.getUseYn())) {
        		throw new CustomException("사용이 중지된 아이디입니다");
        	}
        }
        
        String jwtAccessToken = jwtUtil.generateAccessToken(userInfoRq.getUserId());
        String jwtRefreshToken = jwtUtil.generateRefreshToken(userInfoRq.getUserId());
        UserResponse.LoginResponse loginResponse=new UserResponse.LoginResponse(jwtAccessToken,jwtRefreshToken);
        
        return loginResponse;
	}


	


}
