package com.javaex.user;

import java.util.Scanner;

public class UserApp {

	   public static void main(String[] args) {

	      // 회원가입 로그인
	      // 로그인 후 히스토리 회원수정

	      UserDao userDao = new UserDao();

	      Scanner sc = new Scanner(System.in);
	      boolean run = true;

	      int loginUserNo;

	      System.out.println("┌──────────── ♡ 도  서  대  여 ♡ ────────────┐");
	      System.out.println("│     [ 도서대여 프로그램에 오신것을 환영합니다 ]     │");
	      System.out.println("└─────────────────────────────────────────┘");
	      System.out.println("");
	      System.out.println("     ▶ 원하시는 옵션을 선택해주세요 ◀");

	      while (run) {
	         System.out.println("┌────────────────────────────────┐");
	         System.out.println("│1.로그인      2.회원가입       3.종료 │");
	         System.out.println("└────────────────────────────────┘");
	         System.out.print(" ▶ 메뉴번호: ");
	         int menuNum = sc.nextInt();
	         sc.nextLine(); // 숫자 뒤에 문자열 받을때 생기는 오류 수정용

	         switch (menuNum) {

	         case 1: // 로그인

	            loginUserNo = 0;

	            System.out.println("");
	            System.out.println("[로그인]");
	            System.out.println("♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥");
	            System.out.println("        ♡ 회원정보를 입력해주세요 ♡       ");
	            System.out.println("♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥");
	            System.out.println("");
	            System.out.print(" ▶ 아이디: ");
	            String lid = sc.nextLine();
	            System.out.print(" ▶ 비밀번호: ");
	            String lpw = sc.nextLine();

	            loginUserNo = userDao.login(lid, lpw);

	            if (loginUserNo <= 0) {
	               System.out.println(" ※ 회원정보가 없습니다 ※ ");
	               System.out.println(" ※ 회원가입을 해주세요 ※ ");

	            } else {

	               System.out.println("");
	               System.out.println("     ▶ 원하시는 옵션을 선택해주세요 ◀");

	               while (run) {
	                  System.out.println("┌──────────────────────────────────┐");
	                  System.out.println("│ 1.history   2.정보수정       3.종료  │");
	                  System.out.println("└──────────────────────────────────┘");
	                  System.out.println("");
	                  System.out.print(" ▶ 메뉴번호: ");
	                  int menuNo = sc.nextInt();
	                  sc.nextLine(); // 숫자 뒤에 문자열 받을때 생기는 오류 수정용

	                  switch (menuNo) {

	                  case 1: // 히스토리

	                     userDao.displayUserRentals(loginUserNo);
	                     // System.out.println(userList);

	                     // System.out.println("히스토리");
	                     break;

	                  case 2: // 정보수정

	                     int result;

	                     int userNum = loginUserNo;
	                     System.out.print(" ▶ 새 비밀번호: ");
	                     String password = sc.next();
	                     System.out.print(" ▶ 새 전화번호: ");
	                     String phoneNum = sc.next();
	                     System.out.print(" ▶ 새 이메일: ");
	                     String email = sc.next();

	                     result = userDao.updateUser(userNum, password, phoneNum, email);
	                     if (result > 0) {
	                        System.out.println(" ♥ 회원 정보 수정 성공 ♥ ");
	                     } else {
	                        System.out.println(" ※ 회원 정보 수정 실패 ※");
	                     }

	                     break;

	                  case 3: // 종료

	                     run = false;
	                     break;

	                  default:
	                     System.out.println("[다시 입력해 주세요.]");
	                     System.out.println("");
	                     break;

	                  } // switch() 종료

	               } // while() 종료

	            } // if{} 종료 (로그인)

	            break;

	         case 2: // 회원가입

	            System.out.println("[회원가입]");
	            System.out.println("♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥");
	            System.out.println("        ♡ 회원정보를 입력해주세요 ♡       ");
	            System.out.println("♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥♡♥");
	            System.out.print(" ▶ 아이디: ");
	            String id = sc.nextLine();
	            System.out.print(" ▶ 비밀번호: ");
	            String pw = sc.nextLine();
	            System.out.print(" ▶ 이름: ");
	            String name = sc.nextLine();
	            System.out.print(" ▶ 전화번호: ");
	            String pnum = sc.nextLine();
	            System.out.print(" ▶ 이메일: ");
	            String email = sc.nextLine();
	            userDao.insertUser(id, pw, name, pnum, email);

	            break;

	         case 3: // 종료

	            run = false;
	            break;

	         // 없는 번호일때
	         default:
	            System.out.println("[다시 입력해 주세요.]");
	            System.out.println("");
	            break;

	         } // switch() 종료

	      } // while() 종료
	      System.out.println("");
	      System.out.println("♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡");
	      System.out.println("♡        감사합니다! 좋은하루 보내세용!!       ♡");
	      System.out.println("♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡");

	      sc.close();
	   }

	}
