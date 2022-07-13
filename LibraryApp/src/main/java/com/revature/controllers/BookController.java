package com.revature.controllers;

import com.revature.services.BookService;

import io.javalin.http.Context;

public class BookController {
	
	private static BookService bookService = new BookService();
	
	public static void markBookAsRead(Context ctx) {
		int userId = Integer.parseInt(ctx.pathParam("id"));
		int bookId = Integer.parseInt(ctx.pathParam("bookId"));
		// query params? 
//		String queryParam = ctx.queryParam("someQueryParamKey");
//		String qp2 = ctx.queryParam("anotherQParam");
//		System.out.println("queryParam is : " + queryParam);
//		System.out.println("second qp = " + qp2);
		bookService.markAsRead(userId, bookId);
		
		ctx.status(204);
	}

}
