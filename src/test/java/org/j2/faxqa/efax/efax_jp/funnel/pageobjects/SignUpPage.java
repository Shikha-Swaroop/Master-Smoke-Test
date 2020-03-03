package org.j2.faxqa.efax.efax_jp.funnel.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.github.javafaker.Faker;



//import com.google.common.*;
//import com.google.common.io.Files;

import java.util.concurrent.ThreadLocalRandom;
import org.apache.logging.log4j.*;
import org.j2.faxqa.efax.common.BasePage;
import org.j2.faxqa.efax.common.TLDriverFactory;

public class SignUpPage extends BasePage {
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

	@FindBy(id = "ddlChooseNumberCountryibjp")
	private WebElement ddlChooseNumberCountryibjp;

	@FindBy(id = "ddlChooseNumberSearchAreaCodeibjp")
	private WebElement ddlChooseNumberSearchAreaCodeibjp;

	@FindBy(id = "lstDidNumbersibjp")
	private WebElement lstDidNumbersibjp;

	@FindBy(id = "btnChooseNumberSubmitibjp")
	private WebElement btnChooseNumberSubmitibjp;

	@FindBy(id = "rdoAccept")
	private WebElement rdoAccept;
	
	@FindBy(id = "btncontinueenable")
	private WebElement btncontinueenable;
	
	@FindBy(id = "txtEmailAddressJP")
	private WebElement txtEmailAddressJP;
	
	@FindBy(id = "txtConfirmEmailAddressJP")
	private WebElement txtConfirmEmailAddressJP;
	
	@FindBy(id = "txtLastNameJP")
	private WebElement txtLastNameJP;

	@FindBy(id = "txtFirstNameJP")
	private WebElement txtFirstNameJP;
	
	@FindBy(id = "ddlCountryBillingjp")
	private WebElement ddlCountryBillingjp;
	
	@FindBy(id = "txtZipCodeBillingjp1")
	private WebElement txtZipCodeBillingjp1;

	@FindBy(id = "txtZipCodeBillingjp2")
	private WebElement txtZipCodeBillingjp2;

	@FindBy(id = "ddlStateBillingjp")
	private WebElement ddlStateBillingjp;

	@FindBy(id = "txtAddress1Billingjp")
	private WebElement txtAddress1Billingjp;

	@FindBy(id = "txtAddress2Billingjp")
	private WebElement txtAddress2Billingjp;

	@FindBy(id = "txtCompanynamejp")
	private WebElement txtCompanynamejp;
	
	@FindBy(id = "txtPhoneNumberBillingjp")
	private WebElement txtPhoneNumberBillingjp;
	
	@FindBy(id = "rad_VISA")
	private WebElement rad_VISA;

	@FindBy(id = "txtCreditCardBillingjp")
	private WebElement txtCreditCardBillingjp;

	@FindBy(id = "txtCVVBillingjp")
	private WebElement txtCVVBillingjp;
	
	@FindBy(id = "ddlMonthBillingjp")
	private WebElement ddlMonthBillingjp;

	@FindBy(id = "ddlYearBillingjp")
	private WebElement ddlYearBillingjp;

	@FindBy(id = "lnkBillingiSubmitjp")
	private WebElement lnkBillingiSubmitjp;
	
	@FindBy(id = "lnkBillingConfirmSubmitJP")
	private WebElement lnkBillingConfirmSubmitJP;
	
	@FindBy(id = "confirm")
	private WebElement confirm;
	
	@FindBy(id = "btnLogin")
	private WebElement btnLogin;

	@FindBy(id = "logout")
	private WebElement logout;
	
	
	public void selectCountry(String text) {
		wait.until(ExpectedConditions.elementToBeClickable(ddlChooseNumberCountryibjp));
		Select selection = new Select(ddlChooseNumberCountryibjp);
		selection.selectByVisibleText(text);
		logger.info("Setting faxnumber country to - " + text);
	}

	public void selectAreaCode() throws InterruptedException {
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='ddlChooseNumberSearchAreaCodeibjp']/option[contains(text(), '市外局番を選択してください')]")));
		Select selection = new Select(ddlChooseNumberSearchAreaCodeibjp);
		int i = new Faker().number().numberBetween(1, selection.getOptions().size());
		selection.selectByIndex(i);
		String acrea = selection.getFirstSelectedOption().getText();
		logger.info("Setting SearchAreaCode to - " + acrea);
	}

	public boolean anyFaxNumbers() {
		return driver.findElements((By.xpath("//*[@id='lstDidNumbersibjp']//input"))).size() > 0;
	}

	public String selectDIDNumber() {
		String did = "";
		wait.until(ExpectedConditions.visibilityOfAllElements(lstDidNumbersibjp));
		lstDidNumbersibjp.findElements(By.tagName("input")).get(0).click();
		did = lstDidNumbersibjp.findElements(By.tagName("input")).get(0).getAttribute("data-val");
		logger.info("Picked a faxnumber from available numbers - " + did);
		return did;
	}

	public void proceedNext1() {
		btnChooseNumberSubmitibjp.click();
		logger.info("Continuing further with Sign-Up");
	}

	public void proceedNext2() {
		btncontinueenable.click();
		logger.info("Continuing further with Sign-Up");
	}
	
	public void setEmail(String text) {
		txtEmailAddressJP.click();
		txtEmailAddressJP.clear();
		txtEmailAddressJP.sendKeys(text);
		logger.info("eMail set to - " + text);
	}
	
	public void confirmEmail(String text) {
		txtConfirmEmailAddressJP.click();
		txtConfirmEmailAddressJP.clear();
		txtConfirmEmailAddressJP.sendKeys(text);
		logger.info("eMail set to - " + text);
	}
	
	public void setFirstName(String text) {
		txtFirstNameJP.click();
		txtFirstNameJP.clear();
		txtFirstNameJP.sendKeys(text);
		logger.info("FirstName set to - " + text);
	}

	public void setLastName(String text) {
		txtLastNameJP.click();
		txtLastNameJP.clear();
		txtLastNameJP.sendKeys(text);
		logger.info("LastName set to - " + text);
	}

	public void setBillingCountry(String text) {
		Select selection = new Select(ddlCountryBillingjp);
		selection.selectByVisibleText(text);
		logger.info("BillingCountry set to - " + text);
	}

	public void setBillingZipCode1(String text) {
		txtZipCodeBillingjp1.click();
		txtZipCodeBillingjp1.clear();
		txtZipCodeBillingjp1.sendKeys(text);
		logger.info("ZipCode 1 set to - " + text);
	}

	public void setBillingZipCode2(String text) {
		txtZipCodeBillingjp2.click();
		txtZipCodeBillingjp2.clear();
		txtZipCodeBillingjp2.sendKeys(text);
		logger.info("ZipCode 2 set to - " + text);
	}
	
	public void setBillingState() {
		wait.until(ExpectedConditions.elementToBeClickable(ddlStateBillingjp));
		Select selection = new Select(ddlStateBillingjp);
		selection.selectByIndex(ThreadLocalRandom.current().nextInt(1, selection.getOptions().size()));
		String state = selection.getFirstSelectedOption().getText();
		logger.info("Setting SearchAreaCode to - " + state);
	}
	
	public void setBillingAddress1(String text) {
		txtAddress1Billingjp.click();
		txtAddress1Billingjp.clear();
		txtAddress1Billingjp.sendKeys(text);
		logger.info("Address 1 set to - " + text);
	}

	public void setBillingAddress2(String text) {
		txtAddress2Billingjp.click();
		txtAddress2Billingjp.clear();
		txtAddress2Billingjp.sendKeys(text);
		logger.info("Address 2 set to - " + text);
	}
	
	public void setCompanyName(String text) {
		txtCompanynamejp.click();
		txtCompanynamejp.clear();
		txtCompanynamejp.sendKeys(text);
		logger.info("Company name set to - " + text);
	}
	
	public void setBillingPhoneNumber(String text) {
		txtPhoneNumberBillingjp.click();
		txtPhoneNumberBillingjp.clear();
		txtPhoneNumberBillingjp.sendKeys(text);
		logger.info("BillingPhoneNumber set to - " + text);
	}
	
	public void setBillingCardTypeVisa() {
		rad_VISA.click();
		logger.info("Setting CardType to - " + rad_VISA.getAttribute("value"));
	}
	
	public void setBillingCreditCardNumber(String text) {
		txtCreditCardBillingjp.click();
		txtCreditCardBillingjp.clear();
		txtCreditCardBillingjp.sendKeys(text);
		logger.info("Setting CardNumber to - " + text);
	}
	
	public void setBillingCreditCardCVV(String text) {
		txtCVVBillingjp.click();
		txtCVVBillingjp.clear();
		txtCVVBillingjp.sendKeys(text);
		logger.info("Setting CardCVV to  - " + text);
	}
	
	public void setBillingCreditCardMonth(String text) {
		Select selection = new Select(ddlMonthBillingjp);
		selection.selectByIndex(11);
		logger.info("Setting CardExpiringMonth to  - " + text);
	}

	public void setBillingCreditCardYear(String text) {
		Select selection = new Select(ddlYearBillingjp);
		selection.selectByVisibleText(text);
		logger.info("Setting CardExpiringYear to  - " + text);
	}

	public void activateAccount() {
		wait.until(ExpectedConditions.visibilityOfAllElements(lnkBillingConfirmSubmitJP));
		lnkBillingConfirmSubmitJP.click();
		logger.info("Attempting to Activate Account");
	}

	public void proceedNext3() {
		wait.until(ExpectedConditions.visibilityOfAllElements(lnkBillingiSubmitjp));
		lnkBillingiSubmitjp.click();
		logger.info("Proceeding Next");
	}
	
	public boolean isSignUpSuccess() {
		wait.until(ExpectedConditions.elementToBeClickable(btnLogin));
		if (driver.findElements(By.id("btnLogin")).size() > 0) {
			logger.info(String.format("Registration successful"));
			return true;
		} else {
			logger.info("Registration un-successful.");
			return false;
		}
	}


	public void clickLogin() {
		logger.info("Attempting Auto log-in.");
		driver.findElement(By.id("btnLogin")).click();
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
	
	public void LoginWithCredentials(String faxnumber, String pin) {
		logger.info("Signing-in with credentials.");
		driver.findElement(By.id("phoneNumber")).sendKeys(faxnumber);
		driver.findElement(By.id("pin")).sendKeys(pin);
		driver.findElement(By.xpath("//*[@id='loginForm']//input[@value='Submit']")).click();
	}

	public boolean logout() {
		if (driver.findElement(By.id("logout")).isDisplayed()) {
			driver.findElement(By.id("logout")).click();
			logger.info("Sign-out successful.");
			return true;
		} else {
			logger.info("Sign-out unsuccessful.");
			return false;
		}
	}

	public void acceptTerms() {
		wait.until(ExpectedConditions.elementToBeClickable(rdoAccept));
		this.scrollToTheSpecificWebelement(rdoAccept);
		rdoAccept.click();
		
	}
	
	public void scrollToTheSpecificWebelement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}
}