package com.revature.services;

import java.util.List;

import com.revature.models.User;
import com.revature.repositories.UserDAO;

public class UserService {
	
	//private static UserDAO userDao = new UserDAO();
	
	
	private static UserDAO userDao;
	
	public UserService(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	
	//login
	public User login(String username, String password) {
		
		User u = userDao.getUserByUsername(username); // Should use an Optional
		if (u != null) {
			if (u.getPassword().equals(password)) {
				return u;
			}
		}
		
		return null;
	}
	
	public User updatePassword(int id, String password) {
		// check if that user exists
		return userDao.updateUserPassword(id, password);
	}
	
	// register / create user
	public User createUser(User u) {
		User createdUser = userDao.createUser(u);
		return createdUser;
	}

	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	public User getUserById(int id) throws Exception {
		// this is where you could put some business logic 
		// for example checking if the User returned by userDao.getUserById(id) is null 
		User u = userDao.getUserById(id);
		
		if (u == null) {
			throw new Exception("User not found");
		}
		
		return u;
	}

	public void deleteUser(int id) {
		userDao.deleteUser(id);
	}
	
	public void updateUser(User uChanged) {
		userDao.updateUser(uChanged);
	}


	

}
