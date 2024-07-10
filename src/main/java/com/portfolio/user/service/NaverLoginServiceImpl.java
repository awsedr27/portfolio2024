package com.portfolio.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.portfolio.user.dao.UserDao;
import com.portfolio.user.dto.UserResponse;
import com.portfolio.user.dto.UserDto.NaverUserProfile;
import com.portfolio.user.dto.UserDto.UserInfo;
import com.portfolio.user.dto.UserResponse.NaverTokenResponse;
import com.portfolio.utils.JwtUtil;

@Service
public class NaverLoginServiceImpl implements NaverLoginService {
    
	
	@Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private JwtUtil jwtUtil;

	public String getAccessToken(String code, String state) throws Exception {
		String tokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code" + "&client_id="
				+ clientId + "&client_secret=" + clientSecret + "&code=" + code
				+ "&state=" + state;

		RestTemplate restTemplate = new RestTemplate();
		NaverTokenResponse response = restTemplate.getForObject(tokenUrl, NaverTokenResponse.class);

		return response.getAccess_token();
	}


	public NaverUserProfile getUserProfile(String accessToken) throws Exception {
        String profileUrl = "https://openapi.naver.com/v1/nid/me";
        
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<NaverUserProfile> response = restTemplate.exchange(profileUrl, HttpMethod.GET, request, NaverUserProfile.class);
        return response.getBody();
	}

	@Override
	public UserResponse.LoginResponse processNaverLogin(String code, String state) throws Exception {
        String accessToken = getAccessToken(code, state);
        NaverUserProfile naverUserProfile = getUserProfile(accessToken);
        UserInfo userInfo=userDao.selectUserInfoByNaverSnsId(naverUserProfile.getResponse().getId());
        
        if (userInfo == null) {
        	//회원가입 
        	UserInfo userInfoRq=new UserInfo(naverUserProfile.getResponse());
        	userDao.insertUserInfo(userInfoRq);
        	
        }else {
        	if("N".equals(userInfo.getUseYn())) {
        		//활성화로 업데이트 
            	UserInfo userInfoRq=new UserInfo(naverUserProfile.getResponse());
            	userInfoRq.setUseYn("Y");
            	userInfoRq.setUserId(userInfo.getUserId());
        		userDao.updateUserInfo(userInfoRq);
        	}
        }
        
        String jwtAccessToken = jwtUtil.generateAccessToken(userInfo.getUserId());
        String jwtRefreshToken = jwtUtil.generateRefreshToken(userInfo.getUserId());
        UserResponse.LoginResponse loginResponse=new UserResponse.LoginResponse(jwtAccessToken,jwtRefreshToken);
        
        return loginResponse;
	}


	


}
