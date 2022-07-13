package com.revature;
import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.patch;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.controllers.BookController;
import com.revature.controllers.UserController;
import com.revature.models.Book;
import com.revature.models.User;
import com.revature.repositories.BookDAO;
import com.revature.repositories.UserDAO;
import com.revature.services.UserService;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class AppDriver {
	
	//private static Logger log = LogManager.getLogger(AppDriver.class);
	
	public static void main(String[] args) {
		BookDAO bd = new BookDAO();
		UserController uc = new UserController(new UserService(new UserDAO()));
		Javalin app = Javalin.create(config -> {
			config.enableCorsForAllOrigins();
			//config.enableCorsForOrigin("http://demo-s3-static-hosting.s3-website-us-east-1.amazonaws.com");
			// commenting out in order to access from S3 Bucket instead of local server
			config.addStaticFiles("/public/html", Location.CLASSPATH);
		});
		
		app.start(8080);
		
		// Javalin provides us with a Context Class (ctx) that represents information 
		// about BOTH the Http Request AND Http Response Objects
		// we'll be using methods from the context class to handle our incoming http requests
		// and to send our http resonses
		
		// lambdas - introduced functional programming to Java
		// (parameter) -> {// implementation}
		app.before(ctx -> {
			ctx.header("Access-Control-Allow-Private-Network", "true");
		});
		app.routes(() -> {
			path("/login", () -> {
				post(uc::loginUser);
			});
			path("/getSession", () -> {
				get(ctx -> {
					// once set, we can access that session attribute
					User loggedInUser = ctx.sessionAttribute("loggedInUser");
					System.out.println(loggedInUser);
				});
			});
			path("/logout", () -> {
				delete(ctx -> {
					// invalidating session so loggedInUser is null
					ctx.req.getSession(false).invalidate();
				});
			});
			path("/users", () -> { // http://localhost:8080/users
				
				get(uc::getAllUsers);
				post(uc::createNewUser);
				path("/{id}", () -> { // http://localhost:8080/users/10
				
					get(uc::getUserById);
					delete(uc::deleteUser);
					put(uc::updateUser); 
					patch(uc::updatePassword);
					path("/books", () -> { //http://localhost:8080/users/10/books
						get(ctx -> {
							int id = Integer.parseInt(ctx.pathParam("id"));
							List<Book> books = bd.getBooksByUserId(id);
							ctx.status(200);
							ctx.json(books);
						});
						path("/{bookId}", () -> { //http://localhost:8080/users/10/books/5?someQueryParamKey=someValue?anotherQParam=helloagain
							patch(BookController::markBookAsRead);
						});
						
					});
				});
			});
		});
		
		
	
		
		// Exception Mapping - best practice would be to use a more specific (or even custom) exception like a UserNotFoundException
		app.exception(Exception.class, (e, ctx) -> {
		    ctx.status(404);
		    ctx.result("<h1>User not found</h1>");
		});
		// Error Mapping
		//app.error(404, ctx -> { ctx.result("Hahahah you typed in the url wrong");});
		
		// Test Endpoints that won't be in the final application 
		app.get("/test", ctx -> {
			ctx.status(200);
			String name = ctx.queryParam("name");
			ctx.result("Test successful! Hello " + name);
		});
		
//		app.get("/{name}", ctx -> {
//			ctx.status(200);
//			String name = ctx.pathParam("name");
//			ctx.result("Hello, " + name);
//		});
		
	
		app.get("/bodystring",ctx -> {
			
			String body = ctx.body();
			System.out.println("Body: " + body);
			String[] split = body.split(":");
			 
			 for (String s : split) {
				 System.out.println(s);
			 }
			
		});
		
	}


}
