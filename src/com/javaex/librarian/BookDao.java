package com.javaex.librarian;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/bookrent_db";
	private String id = "bookrentdb";
	private String pw = "1234";

	// 생성자

	// 메소드 gs

	// 메소드 일반

	// DB연결 메소드
	private void getConnection() {
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

	// 자원정리 메소드
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

	
	// 작가전체리스트
	public List<BookVo> selectBookAll() {
		List<BookVo> bookList = new ArrayList<BookVo>();

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " select 	* ";
			query += " from book ";
			query += " order by book_id asc ";

			// *바인딩
			pstmt = conn.prepareStatement(query);

			// *실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			// 리스트로 만들기
			while (rs.next()) {
				int book_id = rs.getInt("book_id");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String pubs = rs.getString("pubs");
				String pubs_date = rs.getString("pubs_date");
				int b_status = rs.getInt("b_status");

				BookVo bookVo = new BookVo(book_id, title, author, pubs, pubs_date, b_status);
				bookList.add(bookVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return bookList; // 리스트의 *주소를 리턴해준다

	}// 작가전체리스트

	public int insertBook(String title, String author, String pubs, String pubs_date) {
		int count = -1;

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " insert into book ";
			query += " values( null, ? , ? , ? , ? , -1 ) ";


			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, author);
			pstmt.setString(3, pubs);
			pstmt.setString(4, pubs_date);

			// *실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 추가되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}// 작가수정
		
	public int updateBook(int bookId, String title, String author, String pubs, String pubs_date) {
		int count = -1;

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " update book ";
			query += " set title = ?, ";
			query += "     author = ? ";
			query += "     pubs = ? ";
			query += "     pubs_date = ? ";
			query += " where book_id = ? ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, author);
			pstmt.setString(3, pubs);
			pstmt.setString(4, pubs_date);
			pstmt.setInt(5, bookId);

			// *실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 수정되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}// 작가수정
		
	//dellBook 진짜 삭제가 아닌 b_status가 0인 상태로 만들기
	public int delBook(int bookId) {
		int count = -1;

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " update book ";
			query += " set b_status = 0 ";
			query += " where book_id = ? ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookId);

			// *실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}// 작가수정

	int userNum = -1;

	// 대여등록
	public int idToNum(String user_id) {
		
	    this.getConnection();
	    
		
		
		try {
			pstmt = conn.prepareStatement(" SELECT user_num FROM user WHERE user_id = ?; ");
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();
			
			rs.next();
			userNum = rs.getInt("user_num");
			System.out.println(userNum);
			

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		this.close();
		return userNum;
		
	}
	
	public void insertRent(int book_id, int userNum) {
		
		  this.getConnection();
		try {
		pstmt = conn.prepareStatement(" insert into history ( history_num, book_id, user_num,rent_date ) values ( null, ?,?,now() ); ");
		pstmt.setInt(1, book_id);
		pstmt.setInt(2, userNum);

		pstmt.executeUpdate();
		}catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		try {
			pstmt = conn.prepareStatement(" update book set b_status = ? where book_id = ?; ");
			pstmt.setInt(1, userNum);
			pstmt.setInt(2, book_id);
			

			pstmt.executeUpdate();

			System.out.println(book_id + "번 책을 대여하였습니다. ");
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		this.close();
	}
	


//	//반납 등록
	public void updateReturn(int book_id) {
		this.getConnection();

		try {
			String query = "";
			query += " update history ";
			query += " set return_date = now() ";
			query += " where book_id = ?; ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, book_id);

			pstmt.executeUpdate();

			

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		try {
			pstmt = conn.prepareStatement(" update book set b_status = -1 where book_id = ?; ");
			pstmt.setInt(1, book_id);

			pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(book_id + "가 반납 되었습니다.");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
	}// 반납등록 끝
	//반납 등록
		public void updateBookStatus(int book_id) {
			this.getConnection();

			try {
				String query = "";
				query += " update book ";
				query += " set b_status = -1 ";
				query += " where book_id = ?; ";

				// *바인딩
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, book_id);

				pstmt.executeUpdate();

				

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			try {
				pstmt = conn.prepareStatement(" update book set b_status = -1 where book_id = ?; ");
				pstmt.setInt(1, book_id);

				pstmt.executeUpdate();

				// 4.결과처리
				System.out.println(book_id + "가 반납 되었습니다.");
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			this.close();
		}// 반납등록 끝

	



}