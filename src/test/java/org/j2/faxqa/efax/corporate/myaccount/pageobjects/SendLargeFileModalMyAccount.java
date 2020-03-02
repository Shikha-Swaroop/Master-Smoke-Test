package org.j2.faxqa.efax.corporate.myaccount.pageobjects;

import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.j2.faxqa.efax.common.BasePage;
import org.j2.faxqa.efax.common.Config;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.api.Screen;

public class SendLargeFileModalMyAccount extends BasePage {

	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public SendLargeFileModalMyAccount() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 5);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}
	
	@FindBy(xpath = "//input[@id='from']")
	private WebElement from;

	@FindBy(id = "to")
	private WebElement to;

	@FindBy(id = "subject")
	private WebElement subject;

	@FindBy(id = "message")
	private WebElement message;
	
	@FindBy(id = "SWFUpload_0")
	private WebElement getFlash;

	@FindBy(id = "SWFUpload_0")
	private WebElement SWFUpload;

	@FindBy(xpath = "//*[@id='send_button_div']/a")
	private WebElement btnSend;

	@FindBy(id = "log")
	private WebElement log;

	@FindBy(id = "contentBody")
	private WebElement contentBody;
	
	public boolean sendLargeFile(String attachments, String email) {
		
		from.click();
		from.sendKeys(Config.corp_DID);
		to.sendKeys(email);
		subject.sendKeys("From : "+Config.corp_DID+" "+"To : "+email);
		message.sendKeys("From : "+Config.corp_DID+" "+"To : "+email);
		click(By.id("SWFUpload_0"));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.id("log"), "loading"));
		getFlash.sendKeys(attachments);
		btnSend.click();
		return contentBody.getText().contains("success");
	}

}