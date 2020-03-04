package org.j2.faxqa.efax.efax_us.funnel.pageobjects;

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

public class SignUpPage {
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

	@FindBy(id = "ddlChooseNumberCountryRed")
	private WebElement ddlChooseNumberCountryRed;

	@FindBy(xpath = "//input[@value='Area Code']")
	private WebElement radioAreaCode;

	@FindBy(id = "txtChooseNumberByAreaCode")
	private WebElement txtChooseNumberByAreaCode;

	@FindBy(id = "hyplinkChooseNumberGoAreaCode")
	private WebElement hyplinkChooseNumberGoAreaCode;

	@FindBy(xpath = "//input[@value='State']")
	private WebElement radioState;

	@FindBy(id = "ddlChooseNumberState")
	private WebElement ddlChooseNumberState;

	@FindBy(id = "ddlChooseNumberCity")
	private WebElement ddlChooseNumberCity;

	@FindBy(id = "chooseNumberMultiColumn")
	private WebElement chooseNumberMultiColumn;

	@FindBy(id = "btnChooseNumberSubmit")
	private WebElement btnChooseNumberSubmit;

	@FindBy(id = "noInventory")
	private WebElement noInventory;

	@FindBy(id = "txtFirstName")
	private WebElement txtFirstName;

	@FindBy(id = "txtLastName")
	private WebElement txtLastName;

	@FindBy(id = "txtEmailAddress")
	private WebElement txtEmailAddress;

	@FindBy(id = "btnAbandonedUser")
	private WebElement btnAbandonedUser;

	@FindBy(id = "txtCreditCardNameBillingdomestic")
	private WebElement txtCreditCardNameBillingdomestic;

	@FindBy(id = "txtPhoneNumber")
	private WebElement txtPhoneNumber;

	@FindBy(id = "txtAddress1Billingdomestic")
	private WebElement txtAddress1Billingdomestic;

	@FindBy(id = "txtAddress2Billingdomestic")
	private WebElement txtAddress2Billingdomestic;

	@FindBy(id = "txtCityBillingdomestic")
	private WebElement txtCityBillingdomestic;

	@FindBy(id = "ddlStateBillingdomestic")
	private WebElement ddlStateBillingdomestic;

	@FindBy(id = "txtZipCodeBillingdomestic")
	private WebElement txtZipCodeBillingdomestic;

	@FindBy(id = "ddlCountryBillingdomestic")
	private WebElement ddlCountryBillingdomestic;

	@FindBy(id = "ltrSelectCardTypeBillingdomestic")
	private WebElement ltrSelectCardTypeBillingdomestic;

	@FindBy(id = "rad_VISA")
	private WebElement rad_VISA;

	@FindBy(id = "txtCreditCardBillingdomestic")
	private WebElement txtCreditCardBillingdomestic;

	@FindBy(id = "ddlMonthBillingdomestic")
	private WebElement ddlMonthBillingdomestic;

	@FindBy(id = "ddlYearBillingdomestic")
	private WebElement ddlYearBillingdomestic;

	@FindBy(id = "txtCVVBillingdomestic")
	private WebElement txtCVVBillingdomestic;

	@FindBy(id = "chkAgreementBillingdomestic")
	private WebElement chkAgreementBillingdomestic;

	@FindBy(id = "btnBillingSubmitEnableBillingdomestic")
	private WebElement btnBillingSubmitEnableBillingdomestic;

	@FindBy(id = "error-msg_billingdomestic")
	private WebElement error_msg_billingdomestic;

	@FindBy(id = "lbleFaxNumberTitle")
	private WebElement lbleFaxNumberTitle;

	@FindBy(id = "lbleFaxNumberValue")
	private WebElement lbleFaxNumberValue;

	@FindBy(id = "lblPasswordValue")
	private WebElement lblPasswordValue;

	@FindBy(id = "BtnLogin")
	private WebElement BtnLogin;

	public String setCity() {
		wait.until(ExpectedConditions.elementToBeClickable(ddlChooseNumberCity));
		Select selection = new Select(ddlChooseNumberCity);
		selection.selectByIndex(ThreadLocalRandom.current().nextInt(1, selection.getOptions().size()));
		String city = selection.getFirstSelectedOption().getText();
		logger.info("Setting City to - " + city);
		return city;
	}

	public String setState() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(ddlChooseNumberState));
		Select selection = new Select(ddlChooseNumberState);
		selection.selectByIndex(ThreadLocalRandom.current().nextInt(1, selection.getOptions().size()));
		String state = selection.getFirstSelectedOption().getText();
		logger.info("Setting State to - " + state);
		return state;
	}

	public void selectState() {
		wait.until(ExpectedConditions.elementToBeClickable(radioState));
		logger.info("Chosing to look for faxnumbers by State");
		radioState.click();
	}

	public void selectCountry(String text) {
		wait.until(ExpectedConditions.elementToBeClickable(ddlChooseNumberCountryRed));
		Select selection = new Select(ddlChooseNumberCountryRed);
		selection.selectByVisibleText(text);
		logger.info("Setting faxnumber country to - " + text);
	}

	public void selectAreaCode() {
		wait.until(ExpectedConditions.elementToBeClickable(radioAreaCode));
		radioAreaCode.click();
		logger.info("Going by AreaCodes");
	}

	public boolean noInventory() {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading_ChooseNumber")));
		if (noInventory.isDisplayed()) {
			logger.info(noInventory.getText());
			return true;
		}
		return false;
	}

	public void enterAreaCode(String text) {
		wait.until(ExpectedConditions.elementToBeClickable(txtChooseNumberByAreaCode));
		txtChooseNumberByAreaCode.clear();
		txtChooseNumberByAreaCode.sendKeys(text);
		hyplinkChooseNumberGoAreaCode.click();
		logger.info("Area Code set to - " + text);
	}

	public String selectDIDNumber() {
		String did = "";
		wait.until(ExpectedConditions.visibilityOfAllElements(chooseNumberMultiColumn));
		chooseNumberMultiColumn.findElements(By.tagName("input")).get(0).click();
		did = chooseNumberMultiColumn.findElements(By.tagName("input")).get(0).getAttribute("data-val");
		logger.info("Picked-up faxnumber from available numbers - " + did);
		return did;
	}

	public void proceedNext() {
		wait.until(ExpectedConditions.elementToBeClickable(btnChooseNumberSubmit));
		btnChooseNumberSubmit.click();
		logger.info("Continuing further with Sign-Up");
	}

	public void setFirstName(String text) {
		wait.until(ExpectedConditions.visibilityOfAllElements(txtFirstName));
		txtFirstName.clear();
		txtFirstName.sendKeys(text);
		logger.info("FirstName set to - " + text);
	}

	public void setLastName(String text) {
		txtLastName.clear();
		txtLastName.sendKeys(text);
		logger.info("LastName set to - " + text);
	}

	public void setEmail(String text) {
		txtEmailAddress.clear();
		txtEmailAddress.sendKeys(text);
		logger.info("eMail set to - " + text);
	}

	public void proceedToBilling() {
		wait.until(ExpectedConditions.elementToBeClickable(btnAbandonedUser));
		btnAbandonedUser.click();
		logger.info("Continuig further to Billing...");
	}

	public void setBillingCardName(String text) {
		wait.until(ExpectedConditions.elementToBeClickable(txtCreditCardNameBillingdomestic));
		txtCreditCardNameBillingdomestic.clear();
		txtCreditCardNameBillingdomestic.sendKeys(text);
		logger.info("BillingCardName set to - " + text);
	}

	public void setBillingPhoneNumber(String text) {
		txtPhoneNumber.clear();
		txtPhoneNumber.sendKeys(text);
		logger.info("BillingPhoneNumber set to - " + text);
	}

	public void setBillingAddress1(String text) {
		txtAddress1Billingdomestic.clear();
		txtAddress1Billingdomestic.sendKeys(text);
		logger.info("BillingAddress1 set to - " + text);
	}

	public void setBillingAddress2(String text) {
		txtAddress2Billingdomestic.clear();
		txtAddress2Billingdomestic.sendKeys(text);
		logger.info("BillingAddress2 set to - " + text);
	}

	public void setBillingCity(String text) {
		txtCityBillingdomestic.clear();
		txtCityBillingdomestic.sendKeys(text);
		logger.info("BillingCity set to - " + text);
	}

	public void setBillingState(String text) {
		wait.until(ExpectedConditions.elementToBeClickable(ddlStateBillingdomestic));
		if (ddlStateBillingdomestic.isEnabled()) {
			Select selection = new Select(ddlStateBillingdomestic);
			selection.selectByVisibleText(text);
			logger.info("BillingState set to - " + text);
		} else
			logger.info("BillingState disabled.");
	}

	public void setBillingPostalCode(String text) {
		txtZipCodeBillingdomestic.clear();
		txtZipCodeBillingdomestic.sendKeys(text);
		logger.info("BillingPostalCode set to - " + text);
	}

	public void setBillingCountry(String text) {
		Select selection = new Select(ddlCountryBillingdomestic);
		selection.selectByVisibleText(text);
		logger.info("BillingCountry set to - " + text);
	}

	public void setBillingCardTypeVisa() {
		rad_VISA.click();
		logger.info("Setting CardType to - " + rad_VISA.getAttribute("value"));
	}

	public void setBillingCreditCardNumber(String text) {
		txtCreditCardBillingdomestic.clear();
		txtCreditCardBillingdomestic.sendKeys(text);
		logger.info("Setting CardNumber to - " + text);
	}

	public void setBillingCreditCardMonth(String text) {
		Select selection = new Select(ddlMonthBillingdomestic);
		selection.selectByVisibleText(text);
		logger.info("Setting CardExpiringMonth to  - " + text);
	}

	public void setBillingCreditCardYear(String text) {
		Select selection = new Select(ddlYearBillingdomestic);
		selection.selectByVisibleText(text);
		logger.info("Setting CardExpiringYear to  - " + text);
	}

	public void setBillingCreditCardCVV(String text) {
		txtCVVBillingdomestic.clear();
		txtCVVBillingdomestic.sendKeys(text);
		logger.info("Setting CardCVV to  - " + text);
	}

	public void agreeToTermsConditions() {
		chkAgreementBillingdomestic.click();
		logger.info("Agreeing to Terms & Conditions");
	}

	public void activateAccount() {
		btnBillingSubmitEnableBillingdomestic.click();
		logger.info("Attempting to Activate Account");
	}

	public boolean isSignUpSuccess() {
		wait.until(ExpectedConditions.visibilityOf(lbleFaxNumberValue));
		wait.until(ExpectedConditions.visibilityOf(lblPasswordValue));
		if (lbleFaxNumberValue.isDisplayed() && lblPasswordValue.isDisplayed()) {
			logger.info(String.format("Registration successful - FaxNumber=%1$s Password=%2$s", lbleFaxNumberValue.getText(), lblPasswordValue.getText()));
			return true;
		} else {
			logger.info("Registration un-successful.");
			return false;
		}
	}
	
	public String getLoginDetails()
	{
		return lbleFaxNumberValue.getText() + ";" + lblPasswordValue.getText();	
	}

	public boolean isLoginBtnAvailable() {
		wait.until(ExpectedConditions.elementToBeClickable(BtnLogin));
		boolean flag = BtnLogin.isDisplayed();
		return flag;
	}

	public void clickLogin() {
		wait.until(ExpectedConditions.elementToBeClickable(BtnLogin));
		logger.info("Attempting Auto log-in.");
		BtnLogin.click();
	}

	public boolean isLoggedIn() {
		if (!(driver.findElements(By.id("loginSubmitBtn")).size() > 0)) {
			logger.info("Auto Sign-in successful.");
			return true;
		} else {
			logger.info("Auto Sign-in unsuccessful.");
			return false;
		}
	}
	
	public boolean LoginWithCredentials(String faxnumber, String pin) {
		logger.info("Signing-in with credentials.");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("phoneNumber")));
		driver.findElement(By.id("phoneNumber")).sendKeys(faxnumber);
		driver.findElement(By.id("pin")).sendKeys(pin);
		driver.findElement(By.id("loginSubmitBtn")).click();
		if (driver.findElements(By.id("loginSubmitBtn")).size() > 0)
			return false;
		else
			return true;
	}
}