package com.revature.daotests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.revature.models.User;
import com.revature.repositories.UserDAO;
import com.revature.utils.ConnectionUtil;


@Suite
@SelectClasses(UserDAOTests.class)
public class UserDAOTests {
	
	
	private static ConnectionUtil cu;
	private static UserDAO userDao;
	
	@BeforeAll
	public static void setup() {
		ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
		try (Connection conn = cu.getConnection()){
			RunScript.execute(conn, new FileReader("src/test/resources/testdb.sql"));
		} catch (FileNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		userDao = new UserDAO();
	}
	
	@Test
	@DisplayName("Should assign id to new user")
	public void addUserTest() {
		User newUser = new User("h2", "two", "userTest", "userTestPass");
		User addedUser = userDao.createUser(newUser);
		
		assertNotEquals(0, addedUser);
	}
	
	@Test
	@DisplayName("Should return null if user id does not exist")
	public void getUserByIdInvalidTest() {

		assertEquals(null, userDao.getUserById(9999));
	}

}
