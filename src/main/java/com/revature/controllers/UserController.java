package com.revature.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.User;
import com.revature.repositories.UserDAO;
import com.revature.services.UserService;

import io.javalin.http.Context;

public class UserController {
	
	private static Logger log = LogManager.getLogger(UserController.class);
	
	private UserService us;
	
	public UserController(UserService us) {
		this.us = us;
	}
	
	public void loginUser(Context ctx) {
		User u = ctx.bodyAsClass(User.class);
		User loggedInUser = us.login(u.getUsername(), u.getPassword());
		if (u != null) {
			// Setting Session Attribute
			ctx.sessionAttribute("loggedInUser", loggedInUser);
			log.info("Session Attribute Set for User " + loggedInUser);
		}
		ctx.json(loggedInUser);
	}

	public void getAllUsers(Context ctx) {
		log.info("GET Request received at endpoint /users");
		ctx.status(200);
		List<User> users = us.getAllUsers();
		ctx.json(users);
	}
	
	public void createNewUser(Context ctx) {
		log.info("Creating a new user");
		ctx.status(201);
		User userFromRequestBody = ctx.bodyAsClass(User.class);
		User u = us.createUser(userFromRequestBody); // unmarshalling
		ctx.json(u);
	}
	
	public void getUserById(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		User u = null;
		try {
			u = us.getUserById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (u != null) {
			ctx.status(200);
			ctx.json(u);
		} else {
			log.error("User with id of " + id + " attempted to login, but does not exist.");
			ctx.status(404);
		}
		
	}
	
	public void deleteUser(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		us.deleteUser(id);
	}
	
	public void updateUser(Context ctx) {
		User uChanged = ctx.bodyAsClass(User.class); //unmarshalling
		System.out.println("updateUser -= " + uChanged);
		us.updateUser(uChanged);
	}
	public void updatePassword(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		User u = ctx.bodyAsClass(User.class); // {"password": "newPassword"}
		System.out.println(u.getPassword());
		us.updatePassword(id, u.getPassword());
	}
}
