package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.Suite;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.models.User;
import com.revature.repositories.UserDAO;



@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
	
	
	@InjectMocks
	UserService userService;
	
	@Mock
	UserDAO userDaoMock;
	
	
	@BeforeEach
	public void setupEach() {
		userService = new UserService(userDaoMock);
	}
	
	
	@Test
	public void loginWithValidInput() {
		
		User mockUser = new User(1, "name", "lname", "username", "pass");
		
		when(userDaoMock.getUserByUsername(anyString()))
			.thenReturn(mockUser);
		
		User loggedInUser = userService.login("username", "pass");
		
		assertEquals(mockUser, loggedInUser);
		
		
	}
	
	
	
	@Test
	public void loginWithInvalidUsernameShouldReturnNull() {
		
		when(userDaoMock.getUserByUsername(anyString()))
		.thenReturn(null);
	
		User loggedInUser = userService.login("username", "pa$$word");
		
		assertEquals(null, loggedInUser);
	}
	
	
	@Test
	public void loginWithValidUsernameInvalidShouldReturnNull() {
		
		User mockUser = new User(1, "name", "lname", "username", "pass");
		
		when(userDaoMock.getUserByUsername(anyString()))
		.thenReturn(mockUser);
	
		User loggedInUser = userService.login("username", "pa$$word");
		
		assertEquals(null, loggedInUser);
	}
}
