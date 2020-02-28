package org.j2.faxqa.efax.efax_jp.myaccount.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
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

	@FindBy(xpath = "//*[@id='loginForm']//input[@name='Submit']")
	private WebElement loginSubmitBtn;

	@FindBy(id = "cookie-understand")
	private WebElement cookie_understand;

	public void login() {
		faxnumber.sendKeys(Config.DID_US);
		passwordpin.sendKeys(Config.PIN_US);

		logger.info("DID = " + Config.DID_US);
		logger.info("PIN = " + Config.PIN_US);
		
		submit();
	}

	public void submit() {
		{
			scrollToTheSpecificWebelement(cookie_understand);
			click(By.id("cookie-understand"));
			loginSubmitBtn.click();
			logger.info("Log-in Submit.");
		}
	}


}