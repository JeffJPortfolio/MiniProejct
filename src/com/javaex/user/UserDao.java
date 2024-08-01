package com.javaex.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDao {

   // 필드
   private Connection conn = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;

   private String driver = "com.mysql.cj.jdbc.Driver";
   private String url = "jdbc:mysql://192.168.0.73:3306/bookrent_db";
   private String id = "dahyun";
   private String pw = "1234";

   // 생성자

   // 메소드 gs

   // 메소드 일반

   private void getConnection() {
      // DB연결메소드
      try {

         // 1. JDBC 드라이버 (Oracle) 로딩
         Class.forName(driver);

         // 2. Connection 얻어오기
         conn = DriverManager.getConnection(url, id, pw);

      } catch (ClassNotFoundException e) {
         System.out.println("error: 드라이버 로딩 실패 - " + e);
      } catch (SQLException e) {
         System.out.println("error:" + e);
      }
   }

   private void close() {
      // 5. 자원정리
      try {
         if (rs != null) {
            rs.close();
         }
         if (pstmt != null) {
            pstmt.close();
         }
         if (conn != null) {
            conn.close();
         }
      } catch (SQLException e) {
         System.out.println("error:" + e);
      }

   }

   // -------------------------------------------------------------------------
   // 회원가입
   public int insertUser(String userId, String password, String userName, String phoneNum, String email) {

      this.getConnection();

      int count = -1;

      try {
         // 아이디 중복 체크
            String checkIdQuery = "SELECT user_id FROM user WHERE user_id = ?";
            pstmt = conn.prepareStatement(checkIdQuery);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("이미 사용 중인 ID입니다.");
                return count;
            }

            // 전화번호 중복 체크
            String checkPhoneQuery = "SELECT phone_num FROM user WHERE phone_num = ?";
            pstmt = conn.prepareStatement(checkPhoneQuery);
            pstmt.setString(1, phoneNum);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("이미 사용 중인 전화번호입니다.");
                return count;
            }

         // 3. SQL문 준비 / 바인딩 / 실행
         // sql문 준비
         String query = "";
         query += " insert into user ";
         query += " values (null, ?, ?, ?,?,?) ";

         // 바인딩 (말랑말랑하게 해주기)
         pstmt = conn.prepareStatement(query);
         pstmt.setString(1, userId);
         pstmt.setString(2, password);
         pstmt.setString(3, userName);
         pstmt.setString(4, phoneNum);
         pstmt.setString(5, email);

         // 실행
         count = pstmt.executeUpdate();

         // 4.결과처리

      } catch (SQLException e) {
         System.out.println("error:" + e);
      }
      this.close();
      System.out.println("회원가입 성공.");
      return count;

   }

   // -------------------------------------------------------------------------
   // 로그인
   public int login(String userId, String password) {

      this.getConnection();

      try {

         String query = "";
         query += " select user_num, user_name, user_id from user ";
         query += " where user_id = ?  ";
         query += " and password = ? ";

         pstmt = conn.prepareStatement(query);
         pstmt.setString(1, userId);
         pstmt.setString(2, password);

         rs = pstmt.executeQuery(); 

         if (rs.next() == true) {
            // System.out.println("로그인 성공");
            int userNo = rs.getInt("user_num");
            return userNo;
            // System.out.println(userNo);
         } else {
            // System.out.println("로그인 실패");
            return 0;
         }

      } catch (SQLException e) {
         e.printStackTrace();
         return -1;

      }

   }

   // -------------------------------------------------------------------------
   // 히스토리
   public void displayUserRentals(int userNum) {
      this.getConnection();
      try {
         String query = "";
         query += " SELECT b.title ";
         query += "        ,b.author ";
         query += "       ,h.rent_date ";
         query += "       ,h.return_date ";
         query += " FROM history h ";
         query += " JOIN book b ON h.book_id = b.book_id ";
         query += " WHERE h.user_num = ? ";
         query += " ORDER BY h.rent_date DESC ";

         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1, userNum);
         rs = pstmt.executeQuery();
         System.out.println("===== 대여 및 반납 현황 =====");

         // Check if there are any records
         if (!rs.isBeforeFirst()) { // rs.isBeforeFirst() returns false if the ResultSet is empty
            System.out.println("대여 기록이 없습니다.");
         } else {
            while (rs.next()) {
               String title = rs.getString("title");
               String author = rs.getString("author");
               String rentDate = rs.getString("rent_date");
               String returnDate = rs.getString("return_date");
               System.out.println("책 제목: " + title);
               System.out.println("작가: " + author);
               System.out.println("대여 날짜: " + rentDate);
               System.out.println("반납 날짜: " + (returnDate != null ? returnDate : "반납 안됨"));
               System.out.println("------------------");
            }
         }
      } catch (SQLException e) {
         System.out.println("error:" + e);
      }
      this.close();
   }

   // -------------------------------------------------------------------------
   // 회원수정
   public int updateUser(int userNum, String password, String phoneNum, String email) {
      this.getConnection();
      int count = -1;
      try {
         
         String query = "UPDATE user SET password = ?, phone_num = ?, email = ? WHERE user_num = ?";
         pstmt = conn.prepareStatement(query);
         pstmt.setString(1, password);
         pstmt.setString(2, phoneNum);
         pstmt.setString(3, email);
         pstmt.setInt(4, userNum);
         count = pstmt.executeUpdate(); 
      } catch (SQLException e) {
         System.out.println("error:" + e);
      } finally {
         this.close();
      }
      return count;

   }

   public List<UserVo> selectUserAll() {
		List<UserVo> userList = new ArrayList<UserVo>();

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " select 	* ";
			query += " from user ";
			query += " order by user_id asc ";

			// *바인딩
			pstmt = conn.prepareStatement(query);

			// *실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			// 리스트로 만들기
			while (rs.next()) {
				int book_num = rs.getInt("user_num");
				String user_id = rs.getString("user_id");
				String password = rs.getString("password");
				String user_name = rs.getString("user_name");
				String phone_num = rs.getString("phone_num");
				String email = rs.getString("email");

				UserVo userVo = new UserVo(book_num, user_id, password, user_name, phone_num, email);
				userList.add(userVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return userList; // 리스트의 *주소를 리턴해준다

	}// 작가전체리스트
}

