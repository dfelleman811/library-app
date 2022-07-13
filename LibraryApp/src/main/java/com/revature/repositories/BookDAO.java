package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Book;
import com.revature.utils.ConnectionUtil;

public class BookDAO {
	
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	// CRUD
	
	public List<Book> getBooksByUserId(int id) {
		List<Book> books = new ArrayList<>();
		
		String sql = "select * from users_books ub"
				+ " left join users u on ub.user_id = u.id"
				+ " left join books b on ub.book_id = b.id"
				+ " where user_id = ?";
		
		try (Connection conn = cu.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				books.add(new Book(
						rs.getInt("book_id"),
						rs.getString("title"),
						rs.getString("author")
						));
				
			}
			return books;
			
			
			
		} catch (SQLException e) {
		}
		return null;
	}

	public Book getUserBookById(int userId, int bookId) {
		String sql = "select * from users_books ub"
				+ " left join users u on ub.user_id = u.id"
				+ " left join books b on ub.book_id = b.id"
				+ " where user_id = ?"
				+ " and book_id = ?";
		
		try (Connection conn = cu.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, bookId);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return new Book(rs.getInt("book_id"), rs.getString("title"), rs.getString("author"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateBookMarkAsRead(int userId, int bookId) {
		
		String sql = "update users_books set is_read = true where user_id = ? and book_id = ?";
		
		try (Connection conn = cu.getConnection()) {

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, bookId);
			
			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
