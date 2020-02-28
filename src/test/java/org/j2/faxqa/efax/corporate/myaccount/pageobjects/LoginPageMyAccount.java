package org.j2.faxqa.efax.corporate.myaccount.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.j2.faxqa.efax.common.BasePage;
import org.j2.faxqa.efax.common.Config;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPageMyAccount extends BasePage {

	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public LoginPageMyAccount() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}

	static final String faxNumberField = "phoneNumber";
	static final String passwordField = "pin";
	static final String loginButton = "userLoginButton";
	static final String forgotPasswordLink = "forgotPassword";
	static final String errorMessage = "//div[@class='loginError']";
	static final String rememberMeCheckbox = "rememberUsernameAndPasswordChk";
	static final String cookie_understand = "cookie-understand";

	
	@FindBy(id = faxNumberField)
	private WebElement faxNumberFieldWebElement;

	@FindBy(id = passwordField)
	private WebElement passwordFieldWebElement;

	@FindBy(id = loginButton)
	private WebElement loginButtonWebElement;

	@FindBy(name = forgotPasswordLink)
	private WebElement forgotPasswordLinkWebElement;

	@FindBy(xpath = errorMessage)
	private WebElement errorMessageWebElement;

	@FindBy(id = rememberMeCheckbox)
	private WebElement rememberMeCheckboxWebElement;

	@FindBy(id = cookie_understand)
	private WebElement cookie_understandWebElement;
	
	public String errorMessage() {
		String errorMessage = errorMessageWebElement.getText();
		return errorMessage;
	}

	public void enterFaxNumber(String AccountNumber) {
		faxNumberFieldWebElement.clear();
		faxNumberFieldWebElement.sendKeys(AccountNumber);
	}

	public void enterPassword(String password) {
		passwordFieldWebElement.clear();
		passwordFieldWebElement.sendKeys(password);
	}

	public void clickLoginButton() {
		loginButtonWebElement.click();
	}

	public void login(String AccountNumber, String password) {
		this.enterFaxNumber(AccountNumber);
		this.enterPassword(password);
		if (cookie_understandWebElement.isDisplayed()) this.cookie_understandWebElement.click();
		this.clickLoginButton();
	}

	public void clickForgotPasswordLink() {
		forgotPasswordLinkWebElement.click();
	}
}