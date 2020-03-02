package org.j2.faxqa.myfax.myaccount.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.*;
import org.j2.faxqa.efax.common.Config;
import org.j2.faxqa.efax.common.TLDriverFactory;

public class LoginPage {
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

	@FindBy(id = "submitButton")
	private WebElement loginSubmitBtn;

	@FindBy(id = "cookie-understand")
	private WebElement cookie_understand;

	public void login(String DID, String PIN) {
		wait.until(ExpectedConditions.elementToBeClickable(faxnumber));
		faxnumber.click();
		faxnumber.clear();
		faxnumber.sendKeys(DID);
		
		wait.until(ExpectedConditions.elementToBeClickable(passwordpin));
		passwordpin.click();
		passwordpin.clear();
		passwordpin.sendKeys(PIN);

		logger.info("DID = " + DID);
		logger.info("PIN = " + PIN);

		//cookie_understand.click();
		
		submit();
	}

	public void submit() {
		{
			loginSubmitBtn.click();
			logger.info("Log-in Submit.");
		}
	}


}