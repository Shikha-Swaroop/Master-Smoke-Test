package org.j2.faxqa.efax.efax_uk.myaccount.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.*;
import org.j2.faxqa.efax.common.BasePage;
import org.j2.faxqa.efax.common.Config;
import org.j2.faxqa.efax.common.TLDriverFactory;

public class LoginPage extends BasePage {
	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public LoginPage() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}

	@FindBy(id = "phoneNumber")
	private WebElement faxnumber;

	@FindBy(id = "pin")
	private WebElement passwordpin;

	final String loginbutton = "//*[@id='loginForm']//input[@name='Submit']";
	@FindBy(xpath = loginbutton)
	private WebElement loginSubmitBtn;

	@FindBy(id = "cookie-understand")
	private WebElement cookie_understand;

	public void login(String faxNumber, String password) {
		faxnumber.sendKeys(faxNumber);
		passwordpin.sendKeys(password);

		logger.info("DID = " + faxNumber);
		logger.info("PIN = " + password);

		click(By.id("cookie-understand"));
		
		submit();
	}

	public void submit() {
		{
			loginSubmitBtn.click();
			logger.info("Log-in Submit.");
		}
	}

	public boolean isLoggedIn() {
		return !(driver.findElements(By.xpath(loginbutton)).size() > 0); 
	}

}