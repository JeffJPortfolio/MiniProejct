package com.javaex.user;

public class UserVo {
	   
	   // 필드
	   private int userNum;
	   private String userId;
	   private String password;
	   private String userName;
	   private String phoneNum;
	   private String email;
	   
	   
	   //생성자
	   public UserVo() {
	      super();
	   }



	   public UserVo(String userId, String password) {
	      super();
	      this.userId = userId;
	      this.password = password;
	   }


	   //회원가입
	   public UserVo(String userId, String password, String userName, String phoneNum, String email) {
	      super();
	      this.userId = userId;
	      this.password = password;
	      this.userName = userName;
	      this.phoneNum = phoneNum;
	      this.email = email;
	   }


	   public UserVo(int userNum, String userId, String password, String userName, String phoneNum, String email) {
	      super();
	      this.userNum = userNum;
	      this.userId = userId;
	      this.password = password;
	      this.userName = userName;
	      this.phoneNum = phoneNum;
	      this.email = email;
	   }


	   //메소드 gs
	   public int getUserNum() {
	      return userNum;
	   }

	   public void setUserNum(int userNum) {
	      this.userNum = userNum;
	   }

	   public String getUserId() {
	      return userId;
	   }

	   public void setUserId(String userId) {
	      this.userId = userId;
	   }

	   public String getPassword() {
	      return password;
	   }

	   public void setPassword(String password) {
	      this.password = password;
	   }

	   public String getUserName() {
	      return userName;
	   }

	   public void setUserName(String userName) {
	      this.userName = userName;
	   }

	   public String getPhoneNum() {
	      return phoneNum;
	   }

	   public void setPhoneNum(String phoneNum) {
	      this.phoneNum = phoneNum;
	   }

	   public String getEmail() {
	      return email;
	   }

	   public void setEmail(String email) {
	      this.email = email;
	   }

	   
	   //메소드 일반
	   @Override
	   public String toString() {
			return String.format(
			        "유저번호: %-5d 아이디: %-20s 비밀번호: %-15s 이름: %-20s 전화번호: %-10s 이메일: %s",
			        userNum, userId, password, userName, phoneNum, email
			    );

	   }

	   
	}


