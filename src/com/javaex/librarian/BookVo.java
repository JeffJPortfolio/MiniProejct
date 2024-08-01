package com.javaex.librarian;

public class BookVo {
	private int book_id;
	private String title;
	private String author;
	private String pubs;
	private String pubs_date;
	private int b_status;

	//생성자
	public BookVo() {
	}

	//메소드-gs
	public BookVo(String title, String author, String pubs, String pubs_date, int b_status ) {
		this.title = title;
		this.author = author;
		this.title = pubs;
		this.author = pubs_date;
		this.b_status = b_status;
	}

	
	//메소드-gs
	public BookVo(int book_id ,String title, String author, String pubs, String pubs_date, int b_status ) {
		this.book_id = book_id;
		this.title = title;
		this.author = author;
		this.pubs = pubs;
		this.pubs_date = pubs_date;
		this.b_status = b_status;
	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPubs() {
		return pubs;
	}

	public void setPubs(String pubs) {
		this.pubs = pubs;
	}

	public String getPubs_date() {
		return pubs_date;
	}

	public void setPubs_date(String pubs_date) {
		this.pubs_date = pubs_date;
	}

	public int getB_status() {
		return b_status;
	}

	public void setB_status(int b_status) {
		this.b_status = b_status;
	}

	@Override
	public String toString() {
		return String.format(
		        "책ID: %-5d 제목: %-20s 작가: %-15s 출판사: %-20s 출판일: %-10s 대여현황: %d",
		        book_id, title, author, pubs, pubs_date, b_status
		    );
	}


	
	
}
