package com.javaex.librarian;

import com.javaex.user.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LibrarianApp {
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		boolean run = true;

		System.out.println("┌────────────── ♡ 관 리 자 ♡ ──────────────┐");
		System.out.println("│     [ 관리자 페이지에 오신것을 환영합니다 ]   	 │");
		System.out.println("└────────────────────────────────────────┘");

		while (run) {
			BookDao bookDao = new BookDao();
			UserDao userDao = new UserDao();

			List<BookVo> bookList = bookDao.selectBookAll();
			List<UserVo> userList = userDao.selectUserAll();

			// 메뉴 출력
			System.out.println("");
			System.out.println("     ▶ 원하시는 메뉴를 선택해주세요 ◀");
	         System.out.println("┌─────────────────────────────────┐");
	         System.out.println("│ 1. 대여 등록       2. 반납 등록 	  │");
	         System.out.println("│ 3. 대여 현황       4. 회원 관리      │");
	         System.out.println("│ 5. 책 관리        6. 종료          │");
	         System.out.println("└─────────────────────────────────┘");
			
			System.out.print(" ▶ 메뉴 번호: ");

			// 메뉴 입력
			int menuNum = sc.nextInt();
			sc.nextLine(); // 숫자 뒤에 문자열 받을 때 생기는 오류 수정용

			int bookId;
			int memberId;

			switch (menuNum) {
			case 1: // 대여 등록
				System.out.print("▶ 회원 ID를 입력하세요: ");
				String userId = sc.nextLine();
				int userNum = bookDao.idToNum(userId);

				if (userNum == -1) {
					System.out.println("※ 해당 ID로 등록된 사용자가 없습니다. ※");
					break;
				}

				System.out.print("▶ 책 ID를 입력하세요: ");
				int bookIdForRent = sc.nextInt();
				bookDao.insertRent(bookIdForRent, userNum);

				System.out.println("♥ 책 \"" + bookList.get(bookIdForRent).getTitle() + "\"가 대여되었습니다. ♥");
				break;

			case 2: // 반납 등록
				System.out.println("<반납 등록>");
				System.out.print("▶ 책 번호를 입력하세요: ");
				bookId = sc.nextInt();

				if (bookId >= bookList.size() || bookId < 0) {
					System.out.println("※ 유효하지 않은 책 번호입니다. ※");
					break;
				}

				if (bookList.get(bookId).getB_status() == -1) {
					System.out.println("※ 이미 반납된 책입니다. ※");
					break;
				}

				bookDao.updateReturn(bookId);
				System.out.println("♥ 책 \"" + bookList.get(bookId).getTitle() + "\"가 반납되었습니다. ♥");
				break;

			case 3: // 대여 현황
				System.out.println("<대여 현황>");
				for (BookVo book : bookList) {
					if (book.getB_status() != 0) {
						System.out.println(book);
					}
				}
				break;

			case 4: // 회원 관리
				System.out.println("<회원 관리>");
				for (UserVo user : userList) {
					System.out.println(user);
				}

				System.out.print("▶ 전화번호로 회원 검색: ");
				String phoneKeyword = sc.nextLine();

				if (!phoneKeyword.isEmpty()) {
					showUserList(userList, phoneKeyword);
				}
				break;

			case 5: // 책 관리
				System.out.println("<책 관리>");
				boolean temp = true;
				while (temp) {
					temp = bookManagement();
				}
				break;

			case 6: // 종료
				run = false;
				break;

			default:
				System.out.println("※ 유효하지 않은 옵션입니다. 다시 시도해주세요. ※");
				break;
			}
		}

		System.out.println("");
		System.out.println("♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡");
		System.out.println("♡        사용해 주셔서 감사합니다!          ♡");
		System.out.println("♡          좋은 하루 보내세요!            ♡");
		System.out.println("♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡");
	}

	public static boolean bookManagement() {
		boolean loop = true;
		BookDao bookDao = new BookDao();
		Scanner sc = new Scanner(System.in);

		System.out.println("");
		System.out.println("     ▶ 책 관리 메뉴를 선택하세요 ◀");
		System.out.println("┌──────────────────────────────────┐");
		System.out.println("│ 1. 책 등록     2. 책 수정            │");
		System.out.println("│ 3. 책 삭제     4. 메인 메뉴로 돌아가기  │");
		System.out.println("└──────────────────────────────────┘");
		System.out.print(" ▶ 메뉴 번호: ");

		int menuNum = sc.nextInt();
		sc.nextLine(); // 숫자 뒤에 문자열 받을 때 생기는 오류 수정용

		switch (menuNum) {
		case 1: // 책 등록
			System.out.println("<책 등록>");
			System.out.print("▶ 제목: ");
			String title = sc.nextLine();
			System.out.print("▶ 작가: ");
			String author = sc.nextLine();
			System.out.print("▶ 출판사: ");
			String publisher = sc.nextLine();
			System.out.print("▶ 출판일: ");
			String pubDate = sc.nextLine();
			bookDao.insertBook(title, author, publisher, pubDate);
			System.out.println("♥ 책이 등록되었습니다. ♥");
			break;

		case 2: // 책 수정
			System.out.println("<책 수정>");
			System.out.print("▶ 수정할 책 번호를 입력하세요: ");
			int bookId = sc.nextInt();
			sc.nextLine();
			System.out.print("▶ 새 제목: ");
			String newTitle = sc.nextLine();
			System.out.print("▶ 새 작가: ");
			String newAuthor = sc.nextLine();
			System.out.print("▶ 새 출판사: ");
			String newPublisher = sc.nextLine();
			System.out.print("▶ 새 출판일: ");
			String newPubDate = sc.nextLine();
			bookDao.updateBook(bookId, newTitle, newAuthor, newPublisher, newPubDate); // Uncomment when method is
																						// implemented
			System.out.println("♥ 책이 수정되었습니다. ♥");
			break;

		case 3: // 책 삭제
			System.out.println("<책 삭제>");
			System.out.print("▶ 삭제할 책 번호를 입력하세요: ");
			int delBookId = sc.nextInt();
			bookDao.delBook(delBookId);
			System.out.println("♥ 책이 삭제되었습니다. ♥");
			break;

		case 4: // 메인 메뉴로 돌아가기
			loop = false;
			break;

		default:
			System.out.println("※ 유효하지 않은 옵션입니다. 다시 시도해주세요. ※");
			break;
		}

		return loop;
	}

	public static void showUserList(List<UserVo> userList, String keyword) {
		boolean found = false;
		for (UserVo user : userList) {
			if (user.getPhoneNum().contains(keyword)) {
				System.out.println(user);
				found = true;
			}
		}
		if (!found) {
			System.out.println("※ 제공된 전화번호로 등록된 회원이 없습니다. ※");
		}
	}
}
