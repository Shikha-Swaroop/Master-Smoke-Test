package org.j2.faxqa.myfax.funnel.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;

import com.github.javafaker.Faker;

import net.bytebuddy.dynamic.DynamicType.Builder.MethodDefinition.ExceptionDefinition;

//import com.google.common.*;
//import com.google.common.io.Files;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.logging.log4j.*;
import org.j2.faxqa.efax.common.BasePage;
import org.j2.faxqa.efax.common.Config;
import org.j2.faxqa.efax.common.TLDriverFactory;

public class SignUpPage extends BasePage {
	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public SignUpPage() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 60);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}

	@FindBy(id = "spanMyFaxChooseNumberOldUSMap")
	private WebElement spanMyFaxChooseNumberOldUSMap;

	@FindBy(id = "ddlMyFaxChooseNumberOldState")
	private WebElement ddlMyFaxChooseNumberOldState;

	@FindBy(id = "ddlMyFaxChooseNumberOldCity")
	private WebElement ddlMyFaxChooseNumberOldCity;

	@FindBy(id = "btnMyFaxChooseNumberOldNextStep")
	private WebElement btnMyFaxChooseNumberOldNextStep;

	@FindBy(id = "txtFirstName")
	private WebElement txtFirstName;

	@FindBy(id = "txtEmailAddress")
	private WebElement txtEmailAddress;

	@FindBy(id = "btnAbandonedUser")
	private WebElement btnAbandonedUser;

	@FindBy(id = "txtCreditCardNameBillingdomestic")
	private WebElement txtCreditCardNameBillingdomestic;

	@FindBy(id = "txtBillingInfoCompany")
	private WebElement txtBillingInfoCompany;

	@FindBy(id = "ddlBillingInfoCardTypeDDLCardType")
	private WebElement ddlBillingInfoCardTypeDDLCardType;

	@FindBy(id = "ddlBillingInfoCountry")
	private WebElement ddlBillingInfoCountry;

	@FindBy(id = "txtBillingInfoCreditCardNumber")
	private WebElement txtBillingInfoCreditCardNumber;

	@FindBy(id = "txtBillingInfoAddress")
	private WebElement txtBillingInfoAddress;

	@FindBy(id = "ddlBillingExpMonthFull")
	private WebElement ddlBillingExpMonthFull;

	@FindBy(id = "ddlBillingExpYearFull")
	private WebElement ddlBillingExpYearFull;

	@FindBy(id = "txtBillingInfoCVV")
	private WebElement txtBillingInfoCVV;

	@FindBy(id = "txtBillingInfoCity")
	private WebElement txtBillingInfoCity;

	@FindBy(id = "txtPhoneNumber")
	private WebElement txtPhoneNumber;

	@FindBy(id = "ddlBillingInfoState")
	private WebElement ddlBillingInfoState;

	@FindBy(id = "txtBillingInfoPostalCode")
	private WebElement txtBillingInfoPostalCode;

	@FindBy(id = "chkAgreementBillingdomestic")
	private WebElement chkAgreementBillingdomestic;

	@FindBy(id = "btnBillingInfoSubmitEnable")
	private WebElement btnBillingInfoSubmitEnable;

	@FindBy(id = "lblMyFaxNumberValue")
	private WebElement lblMyFaxNumberValue;

	@FindBy(id = "lblMyFaxPasswordValue")
	private WebElement lblMyFaxPasswordValue;
	
	@FindBy(id = "lblMyFaxLoginText")
	private WebElement lblMyFaxLoginText;

	public void setCountryUnitedStates() throws InterruptedException {
		Thread.sleep(3000);
		wait.until(ExpectedConditions.elementToBeClickable(spanMyFaxChooseNumberOldUSMap));
		doubleClickAction(spanMyFaxChooseNumberOldUSMap);
		logger.info("Selecting country as United States");
	}

	public String setState() {
		wait.until(ExpectedConditions.textToBePresentInElement(ddlMyFaxChooseNumberOldState, "State"));
		Select selection = new Select(ddlMyFaxChooseNumberOldState);
		int size = selection.getOptions().size();
		selection.selectByIndex(new Faker().number().numberBetween(1, size));
		String state = selection.getFirstSelectedOption().getText();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='myFaxOldLoadingCity']")));
		logger.info("Selecting State - " + state);
		return state;
	}

	public String setCity() {
		Select selection = new Select(ddlMyFaxChooseNumberOldCity);
		int size = selection.getOptions().size();
		selection.selectByIndex(new Faker().number().numberBetween(1, size));
		String city = selection.getFirstSelectedOption().getText();
		logger.info("Selecting City - " + city);
		return city;
	}

	public void proceed1() {
		btnMyFaxChooseNumberOldNextStep.click();
	}
	
	public void setName(String name) {
		wait.until(ExpectedConditions.elementToBeClickable(txtFirstName));
		txtFirstName.click();
		txtFirstName.sendKeys(name);
		logger.info("Entering Name as - " + name);
	}

	public void setEmail(String text) {
		txtEmailAddress.clear();
		txtEmailAddress.sendKeys(text);
		logger.info("email set to - " + text);
	}
	
	public void proceed2() {
		wait.until(ExpectedConditions.elementToBeClickable(btnAbandonedUser));
		btnAbandonedUser.click();
	}
	
	public void setCCHolderName(String text) {
		txtCreditCardNameBillingdomestic.clear();
		txtCreditCardNameBillingdomestic.sendKeys(text);
		logger.info("CC HolderName set to - " + text);
	}

	public void setCCType(String text) {
		Select selection = new Select(ddlBillingInfoCardTypeDDLCardType);
		selection.selectByVisibleText(text);
		logger.info("CC Type set to - " + text);
	}

	public void setBillingCreditCardNumber(String text) {
		txtBillingInfoCreditCardNumber.clear();
		txtBillingInfoCreditCardNumber.sendKeys(text);
		logger.info("Setting CardNumber to - " + text);
	}
	
	public void setBillingCreditCardMonth(String text) {
		Select selection = new Select(ddlBillingExpMonthFull);
		selection.selectByVisibleText(text);
		logger.info("Setting CardExpiringMonth to  - " + text);
	}

	public void setBillingCreditCardYear(String text) {
		Select selection = new Select(ddlBillingExpYearFull);
		selection.selectByVisibleText(text);
		logger.info("Setting CardExpiringYear to  - " + text);
	}

	public void setBillingCreditCardCVV(String text) {
		txtBillingInfoCVV.clear();
		txtBillingInfoCVV.sendKeys(text);
		logger.info("Setting CardCVV to  - " + text);
	}
	
	public void setBillingPhoneNumber(String text) {
		txtPhoneNumber.clear();
		txtPhoneNumber.sendKeys(text);
		logger.info("BillingPhoneNumber set to - " + text);
	}

	public void setBillingCompany(String text) {
		txtBillingInfoCompany.clear();
		txtBillingInfoCompany.sendKeys(text);
		logger.info("Billing Company set to - " + text);
	}

	public void setBillingCountry(String text) {
		Select selection = new Select(ddlBillingInfoCountry);
		selection.selectByVisibleText(text);
		logger.info("Billing Country set to - " + text);
	}

	public void setBillingAddress(String text) {
		txtBillingInfoAddress.clear();
		txtBillingInfoAddress.sendKeys(text);
		logger.info("Billing Address set to - " + text);
	}
	
	public void setBillingCity(String text) {
		txtBillingInfoCity.clear();
		txtBillingInfoCity.sendKeys(text);
		logger.info("Billing City set to - " + text);
	}
	
	public void setBillingState(String text) {
		wait.until(ExpectedConditions.elementToBeClickable(ddlBillingInfoState));
		Select selection = new Select(ddlBillingInfoState);
		selection.selectByVisibleText(text);
		logger.info("Selecting Billing State - " + text);
	}

	public void setBillingPostalCode(String text) {
		txtBillingInfoPostalCode.clear();
		txtBillingInfoPostalCode.sendKeys(text);
		logger.info("BillingPostalCode set to - " + text);
	}

	public void agreeToTermsConditions() {
		chkAgreementBillingdomestic.click();
		logger.info("Agreeing to Terms & Conditions");
	}

	public void activateAccount() {
		wait.until(ExpectedConditions.elementToBeClickable(btnBillingInfoSubmitEnable));
		btnBillingInfoSubmitEnable.click();
		logger.info("Attempting to Activate Account");
	}

	public boolean isSignUpSuccess() {
		wait.until(ExpectedConditions.visibilityOf(lblMyFaxNumberValue));
		wait.until(ExpectedConditions.visibilityOf(lblMyFaxPasswordValue));
		if (lblMyFaxPasswordValue.isDisplayed() && lblMyFaxPasswordValue.isDisplayed()) {
			logger.info(String.format("Registration successful - FaxNumber=%1$s Password=%2$s", lblMyFaxPasswordValue.getText(), lblMyFaxPasswordValue.getText()));
			return true;
		} else {
			logger.info("Registration un-successful.");
			return false;
		}
	}

	public void tryLogin() {
		logger.info("Attempting Auto log-in.");
		lblMyFaxLoginText.click();
	}

	public boolean isLoggedIn() {
		return (driver.findElements(By.id("logout")).size() > 0);
	}
	
}