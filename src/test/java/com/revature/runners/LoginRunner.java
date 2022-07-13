package com.revature.runners;

import java.io.File;

import org.junit.platform.suite.api.Suite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.pages.LoginPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;


@Suite
public class LoginRunner {

	public static WebDriver driver;
	public static LoginPage loginPage;

	@Before("@login")
	public static void setup() {

		File chrome = new File("src/test/resources/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", chrome.getAbsolutePath());
		driver = new ChromeDriver();

		loginPage = new LoginPage(driver);
	}

	@After("@login")
	public static void teardown() {
		driver.quit();
	}

}
