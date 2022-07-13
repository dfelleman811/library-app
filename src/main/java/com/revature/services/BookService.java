package com.revature.services;

import com.revature.models.Book;
import com.revature.repositories.BookDAO;

public class BookService {

	private static BookDAO bd = new BookDAO();
	
	public void markAsRead(int userId, int bookId) {

		Book b = bd.getUserBookById(userId, bookId);
		if (b!=null) {
			bd.updateBookMarkAsRead(userId, bookId);
		}
		
	}
	

}
