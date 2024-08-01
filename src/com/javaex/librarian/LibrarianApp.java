package com.javaex.librarian;

import com.javaex.user.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class LibrarianApp {
	public static void main(String[] args) throws IOException{
		
		Scanner sc = new Scanner(System.in);
		boolean run = true;

		
		System.out.println("관리자 페이지에 온걸 환영 합니다");
		System.out.println("행등을 선택 하십시오");
		
		while (run) {
			BookDao bookDao = new BookDao();
			UserDao userDao = new UserDao();
			
			
			List<BookVo> BookList = bookDao.selectBookAll(); //new리스트만들어서 주소만 전달한다
			List<UserVo> UserList = userDao.selectUserAll();
			
			// 메뉴 출력
			System.out.println("");
			System.out.println("1.대여등록		2.반납등록		3.대여 현황	4.회원관리		5.책관리		6.종료");
			System.out.println("--------------------------------------------------------------------------");
			System.out.print(">메뉴번호: ");

			// 메뉴 입력
			int menuNum = sc.nextInt();
			sc.nextLine(); // 숫자 뒤에 문자열 받을때 생기는 오류 수정용

			int bookId;
			int memberId;
			
			switch (menuNum) {
			
				case 1: //대여등록
					System.out.print("회원id:");
					String user_id = sc.nextLine();
					int userNum = bookDao.idToNum(user_id);
					//애러 확인
					if (userNum == -1) {
						System.out.println("해당 아이디로 등록된 사용자가 없습니다");
						break;
					}			
					System.out.print("책id:");
					int bid = sc.nextInt();
					bookDao.insertRent(bid, userNum);

					System.out.println("책 " +BookList.get(bid).getTitle() + "가 대여 되었습니다");
					break;
			
				case 2: //반납등록
					System.out.println("<2.반납등록>");
					System.out.print(">책 번호: ");
					bookId = sc.nextInt();
					//애러 확인
					if(BookList.get(bookId - 1).getB_status() == -1 || BookList.get(bookId - 1).getB_status() == 0) {
						System.out.println("해당 아이디로 등록된 책이 없습니다");
						break;
					}
					
					bookDao.updateReturn(bookId); 
					System.out.println("책 " +BookList.get(bookId).getTitle() + "이 반납 되었습니다");
					break;	
				
				case 3: //대여 현황
					System.out.println("<3.대여 현황>");
					for(int i = 0; i < BookList.size(); i++) {
						if (BookList.get(i).getB_status() != 0) {
							System.out.println(BookList.get(i).toString());
						}
					}
					break;
				
			// 4(회원관리)
			//MemberList
				case 4:
					System.out.println("<4.회원관리>");
					
					for(int i = 0; i < UserList.size(); i++) {
						System.out.println(UserList.get(i).toString());

					}

					System.out.println("회원 아이디 찾기 ");
					System.out.print(">전화번호: ");
					String keyword = sc.nextLine();
					
					if (keyword.equals("")) {
						break;
					}
					else {
						showUserList(UserList, keyword) ;//키워드를 포함한 리스트 출력
					}
					break;	
			
				// 5(책관리)
				case 5:
					System.out.println("<5.책관리>");
					boolean temp = true;
					while(temp) {
						temp = bookManagement();
					}
					
					
					break;					
			
			// 6(종료)
				case 6:
					run = false;
					break;

			// 없는 번호일때
				default:
					System.out.println("[다시 입력해 주세요.]");
					break;
				
			}// switch() 종료
		
		}
		System.out.println("");
		System.out.println("******************************************");
		System.out.println("*                   감사합니다           *");
		System.out.println("******************************************");

	}
		
		
	public static boolean bookManagement() {
		boolean loop = true;
		BookDao bookDao = new BookDao();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("행등을 선택 하십시오");
			// 메뉴 출력
		System.out.println("");
		System.out.println("1.책등록		2.책수정		3.책 삭제		4.종료");
		System.out.println("--------------------------------------------------------------------------");
		System.out.print(">메뉴번호: ");
		
		int menuNum = sc.nextInt();
		sc.nextLine(); // 숫자 뒤에 문자열 받을때 생기는 오류 수정용
		
		switch (menuNum) {

		case 1: //책등록
			System.out.println("<1.책등록>");
			System.out.print(">제목: ");
			String title = sc.nextLine();
			System.out.print(">작가: ");
			String author = sc.nextLine();
			System.out.print(">출판사: ");
			String pubs = sc.nextLine();
			System.out.print(">출판일: ");
			String pubs_date = sc.nextLine();
			int count = bookDao.insertBook(title, author, pubs, pubs_date);
			break;
		
		case 2: //책수정
			System.out.println("<2.책수정>");
			//인스트럭션 쓰기
			System.out.print(">수정할 책 번호: ");
			int bookid = sc.nextInt();
			sc.nextLine();
			System.out.println(">제목: ");
			String alterTitle = sc.nextLine();
			System.out.print(">작가: ");
			String alterAuthor = sc.nextLine();
			System.out.print(">출판사: ");
			String alterPubs = sc.nextLine();
			System.out.print(">출판일: ");
			String alterPubs_date = sc.nextLine();
			//updateBook(bookid, alterTitle, alterAuthor, alterPubs, alterPubs_date);
			break;	
			
		case 3: //삭제
			System.out.println("<3.책 삭제>");
			System.out.print(">삭제할 책 번호: ");
			int delBookid = sc.nextInt();
			bookDao.delBook(delBookid);
			break;

		// 5(종료)
		case 4:
			loop = false;
			break;

		// 없는 번호일때
		default:
			System.out.println("[다시 입력해 주세요.]");
			break;
			
		}// switch() 종료

		return loop;
		
		
	}
		
		
		
		
/*	public static void showBookList(List<BookVo> bookList) {
		showBookList(bookList, ""); // 아래함수에 키워드를 아무것도 없는 값으로 전달
		}
	public static void showBookList(List<BookVo> bookList, String keyword) {
		for (int i = 0; i < bookList.size(); i++) {
			String serchPhone = bookList.get(i).get();
			if (serchPhone.contains(keyword)) {
				System.out.print(i + 1 + ".   ");
				System.out.print(bookList.get(i).getName() + "\t");
				System.out.print(bookList.get(i).getHp() + "\t");
				System.out.print(bookList.get(i).getCompany() + "\t");
				System.out.println("");
			}
			else {
				System.out.println("해당 책이 없습니다")
			}
		}*/
		
	
	
	
	// 나중에 전화번호로 등록된거 없음 없다라고 표시하게 하기
	public static void showUserList(List<UserVo> userList) {
		showUserList(userList, ""); // 아래함수에 키워드를 아무것도 없는 값으로 전달
		}
	
	public static void showUserList(List<UserVo> userList, String keyword) {
		for (int i = 0; i < userList.size(); i++) {
			String serchName = userList.get(i).getPhoneNum();
			if (serchName.contains(keyword)) {
				System.out.print(userList.get(i).toString());
			}
			
		}
		
	}
	
	
}
