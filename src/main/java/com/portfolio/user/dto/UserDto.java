package com.portfolio.user.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

public class UserDto {
    @Getter
    @Setter
    public static class NaverUserProfile{
        private String resultCode;
        private String message;
        private Response response;
    }
    
    @Getter
    @Setter
    public static class Response {
        private String id;
        private String nickname;
        private String name;
        private String email;
        private String gender;
        private String age;
        private String birthday;
        private String profile_image;
        private String birthyear;
        private String mobile;
    }
    
    @Getter
    @Setter
    public static class UserInfo {
        private String userId;
        private String username;
        private String email;
        private String nickname;
        private String useYn;
        private String naverSnsId;
        private Timestamp  createDate;
        private Timestamp  modifyDate;
        
        public UserInfo() {
        	
        }
        // Response 객체를 이용한 생성자
        public UserInfo(Response response) {
            this.username = response.getName();
            this.email = response.getEmail();
            this.nickname = response.getNickname();
            this.naverSnsId = response.getId(); 
        }

    }
}
