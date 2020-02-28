package org.j2.faxqa.microsites.funnel.pageobjects;

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
import org.j2.faxqa.efax.common.Config;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.j2.faxqa.microsites.funnel.CommonMethods;

public class SignUpPage extends CommonMethods {
	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public SignUpPage() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}

	@FindBy(id = "sel_pcity")
	private WebElement sel_pcity;

	@FindBy(id = "email")
	private WebElement email;

	@FindBy(id = "email2")
	private WebElement email2;

	@FindBy(id = "firstName")
	private WebElement firstName;

	@FindBy(id = "lastName")
	private WebElement lastName;

	@FindBy(id = "streetAddress")
	private WebElement streetAddress;

	@FindBy(id = "streetAddress2")
	private WebElement streetAddress2;

	@FindBy(id = "city")
	private WebElement city;

	@FindBy(id = "mailRegion")
	private WebElement mailRegion;

	@FindBy(id = "zipCode")
	private WebElement zipCode;

	@FindBy(id = "country")
	private WebElement country;

	@FindBy(id = "homePhone")
	private WebElement homePhone;

	@FindBy(id = "rad_visa")
	private WebElement rad_visa;

	@FindBy(id = "ccNumber")
	private WebElement ccNumber;

	@FindBy(id = "cvv")
	private WebElement cvv;

	@FindBy(id = "sel_expMonth")
	private WebElement sel_expMonth;

	@FindBy(id = "sel_expYear")
	private WebElement sel_expYear;

	@FindBy(id = "btn_submit")
	private WebElement btn_submit;

	@FindBy(id = "loginNumber")
	private WebElement loginNumber;

	@FindBy(xpath = "//span[@id='pin']")
	private WebElement pin;

	@FindBy(id = "lnk_login_congrats")
	private WebElement lnk_login_congrats;

	@FindBy(xpath = "//*[@id='loginForm']//input[@name='Submit']")
	private WebElement btnLogin;

	public String setCity() {
		wait.until(ExpectedConditions.elementToBeClickable(sel_pcity));
		Select selection = new Select(sel_pcity);
		int size = selection.getOptions().size();
		selection.selectByIndex(new Faker().random().nextInt(0, size));
		String city = selection.getFirstSelectedOption().getText();
		logger.info("Setting City to - " + city);
		return city;
	}

	public void setEmail(String text) {
		email.click();
		email.clear();
		email.sendKeys(text);

		email2.click();
		email2.clear();
		email2.sendKeys(text);

		logger.info("Setting email to - " + text);
	}

	public void setFirstName(String text) {
		firstName.click();
		firstName.clear();
		firstName.sendKeys(text);

		logger.info("Chosing First Name as " + text);
	}

	public void setLastName(String text) {
		lastName.click();
		lastName.clear();
		lastName.sendKeys(text);
		logger.info("Chosing Last Name as " + text);
	}

	public void setStreetAddress(String text) {
		streetAddress.click();
		streetAddress.clear();
		streetAddress.sendKeys(text);

		logger.info("Chosing StreetAddress as " + text);
	}

	public void setStreetAddress2(String text) {
		streetAddress2.click();
		streetAddress2.clear();
		streetAddress2.sendKeys(text);
		logger.info("Chosing StreetAddress2 as " + text);
	}

	public void setCityTown(String text) {
		city.click();
		city.clear();
		city.sendKeys(text);
		logger.info("Chosing city as " + text);
	}

	public void selectZipCode(String text) {
		zipCode.click();
		zipCode.clear();
		zipCode.sendKeys(text);
		logger.info("Chosing ZipCode as " + text);
	}

	public String selectCountry() {
		Select selection = new Select(country);
		int size = selection.getOptions().size();
		selection.selectByIndex(new Faker().random().nextInt(0, size));
		String country = selection.getFirstSelectedOption().getText();
		logger.info("Setting Country to - " + country);
		return country;
	}

	public void setPhoneNumber(String text) {
		homePhone.click();
		homePhone.clear();
		homePhone.sendKeys(text);
		logger.info("Chosing homePhone as " + text);
	}

	public void setCradType() {
		this.scrollToTheSpecificWebelement(rad_visa);
		rad_visa.click();
		logger.info("Setting CradType as VISA");
	}

	public void setccNumber(String text) {
		this.scrollToTheSpecificWebelement(ccNumber);
		ccNumber.click();
		ccNumber.clear();
		ccNumber.sendKeys(text);
		logger.info("Chosing Credit Card Number as " + text);
	}

	public void setccCVV(String text) {
		this.scrollToTheSpecificWebelement(cvv);
		cvv.click();
		cvv.clear();
		cvv.sendKeys(text);
		logger.info("Chosing Credit Card CVV as " + text);
	}

	public void setccMonth(String text) {
		Select selection = new Select(sel_expMonth);
		selection.selectByVisibleText(text);
		logger.info("Setting CreditCard expiry Month to  - " + text);
	}

	public void setccYear(String text) {
		Select selection = new Select(sel_expYear);
		selection.selectByVisibleText(text);
		logger.info("Setting CreditCard expiry Year to  - " + text);
	}

	public void activateAccount() {
		this.scrollToTheSpecificWebelement(btn_submit);
		btn_submit.click();
		logger.info("Attempting to Activate Account");
	}

	public boolean isSignUpSuccess() {
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.id("loginNumber"), 0));
		if (loginNumber.isDisplayed() && pin.isDisplayed()) {
			logger.info(String.format("Registration successful - FaxNumber=%1$s Password=%2$s", loginNumber.getText(),
					pin.getText()));
			return true;
		} else {
			logger.info("Registration un-successful.");
			return false;
		}
	}

	public boolean login() {
		lnk_login_congrats.click();
		btnLogin.click();
		logger.info("Attempting to login");
		if (driver.findElement(By.xpath("//*[@id='header']//a[text()='LOGOUT']")).isDisplayed()) {
			logger.info("login successful");
			return true;
		} else {
			logger.info("login unsuccessful");
			return false;
		}
	}
}