package com.revature.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	private WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id = "uname")
	public WebElement usernameInput;
	
	@FindBy(id = "pass")
	public WebElement passwordInput;
	
	@FindBy(xpath = "/html/body/div/button")
	public WebElement loginButton;

}
