package com.revature.integrationtests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses(ITTests.class)
public class ITTests {

	@Test
	public void fakeTest() {
		int i = 0;
		assertEquals(0, i);
	}
	
}
