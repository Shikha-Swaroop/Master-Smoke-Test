package org.j2.faxqa.efax.efax_uk.funnel.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.github.javafaker.Faker;

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

	@FindBy(id = "NumberChooser")
	private WebElement NumberCountry;

	@FindBy(id = "rdState")
	private WebElement rdState;

	@FindBy(id = "NumberChooser-cityddl")
	private WebElement NumberChooser_cityddl;

	@FindBy(id = "errorMessage")
	private WebElement errorMessage;

	@FindBy(id = "phoneNumberPagination")
	private WebElement phoneNumberPagination;

	@FindBy(id = "PhoneItem0")
	private WebElement SelectedPhoneNumber;

	@FindBy(id = "sf_dyno-1")
	private WebElement btnSetupAccount;

	@FindBy(id = "FirstName")
	private WebElement FirstName;

	@FindBy(id = "LastName")
	private WebElement LastName;

	@FindBy(id = "EmailAddress1")
	private WebElement EmailAddress1;

	@FindBy(id = "EmailAddress2")
	private WebElement EmailAddress2;

	@FindBy(id = "PhoneNumber")
	private WebElement PhoneNumber;

	@FindBy(id = "sp_customerAgreement")
	private WebElement sp_customerAgreement;

	@FindBy(id = "sf_dyno-1")
	private WebElement btnContinue;

	@FindBy(id = "Country")
	private WebElement billingCountry;

	@FindBy(id = "Address1")
	private WebElement billingAddress1;

	@FindBy(id = "Address2")
	private WebElement billingAddress2;

	@FindBy(id = "City")
	private WebElement billingCity;

	@FindBy(id = "ZipCode")
	private WebElement billingZipCode;

	@FindBy(id = "creditcardordebitcard")
	private WebElement creditcardordebitcard;

	@FindBy(id = "CreditCardNumber")
	private WebElement CreditCardNumber;

	@FindBy(id = "CreditCardCVV")
	private WebElement CreditCardCVV;

	@FindBy(id = "CreditCardName")
	private WebElement CreditCardName;

	@FindBy(id = "CreditCardExpirationMonth")
	private WebElement CreditCardExpirationMonth;

	@FindBy(id = "CreditCardExpirationYear")
	private WebElement CreditCardExpirationYear;

	@FindBy(id = "sf_dyno-1")
	private WebElement btnStartFaxing;

	@FindBy(xpath = "//*[@id='loginform']/button")
	private WebElement btnLogin;
	
	//////////////////////////////////////////////////////////////////////////////

	public void setNumberCountry(String text) {
		wait.until(ExpectedConditions.elementToBeClickable(NumberCountry));
		Select selection = new Select(NumberCountry);
		selection.selectByVisibleText(text);
		logger.info("Selecting Fax Number Country as - " + text);
	}

	public void selectByLocalNumbers(){
		wait.until(ExpectedConditions.elementToBeClickable(rdState));
		rdState.click();
		logger.info("Searching by - 'Local Numbers' option");
	}

	public String selectACity() {
		wait.until(ExpectedConditions.elementToBeClickable(NumberChooser_cityddl));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='NumberChooser-cityddl']/option[1][text()='Select a City']")));
		Select selection = new Select(NumberChooser_cityddl);
		//this.doubleClickAction(NumberChooser_cityddl);
		int options = selection.getOptions().size();
		int random = new Faker().random().nextInt(1, options);
		//NumberChooser_cityddl.click();
		selection.selectByIndex(random);
		String city = selection.getFirstSelectedOption().getText();
		logger.info("Selected '" + city + "' as a random City");
		return city;
	}

	public boolean anyFaxNumbers() {
		wait.until(ExpectedConditions.elementToBeClickable(phoneNumberPagination));
		logger.info("Fax numbers for the selected region found.");
		return phoneNumberPagination.findElements(By.tagName("input")).size() > 0;
	}

	public String getDefaultFaxNumber() {
		//String number = phoneNumberPagination.findElements(By.xpath(".//input[@type='radio']")).get(1).getAttribute("data-val");
		String number = SelectedPhoneNumber.getAttribute("data-val");
		logger.info("Going by the suggested Fax Number - " + number);
		return number;
	}

	public void setupAccount() {
		btnSetupAccount.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("info")));
	}

	public void selectFirstName(String text) {
		wait.until(ExpectedConditions.elementToBeClickable(FirstName));
		FirstName.clear();
		FirstName.sendKeys(text);
		logger.info("FirstName - " + text);
	}
	
	public void selectLastName(String text) {
		wait.until(ExpectedConditions.elementToBeClickable(LastName));
		LastName.clear();
		LastName.sendKeys(text);
		logger.info("LastName - " + text);
	}

	public void selectEmail(String text) {
		wait.until(ExpectedConditions.elementToBeClickable(EmailAddress1));
		EmailAddress1.clear();
		EmailAddress1.sendKeys(text);
		logger.info("EmailAddress - " + text);
	}
	
	public void confirmEmail(String text) {
		wait.until(ExpectedConditions.elementToBeClickable(EmailAddress2));
		EmailAddress2.clear();
		EmailAddress2.sendKeys(text);
		logger.info("Confirming email address");
	}
	
	public void selectPhoneNumber(String text) {
		PhoneNumber.clear();
		PhoneNumber.sendKeys(text);
		logger.info("PhoneNumber - " + text);
	}
	
	public void acceptAgreement() {
		sp_customerAgreement.click();
		logger.info("Accepting Customer Agreement");
	}

	public void proceedNext() {
		btnContinue.click();
		logger.info("Continuing further with Sign-Up");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("info")));
	}

	public void selectBillingCountry(String text) {
		wait.until(ExpectedConditions.elementToBeClickable(billingCountry));
		Select selection = new Select(billingCountry);
		selection.selectByVisibleText(text);
		logger.info("Billing Country set to - " + text);
	}

	public void selectBillingAddress1(String text) {
		billingAddress1.clear();
		billingAddress1.sendKeys(text);
		logger.info("BillingAddress1 set to - " + text);
	}

	public void selectBillingAddress2(String text) {
		billingAddress2.clear();
		billingAddress2.sendKeys(text);
		logger.info("BillingAddress2 set to - " + text);
	}
	
	public void selectBillingCity(String text) {
		billingCity.click();
		billingCity.clear();
		billingCity.sendKeys(text);
		logger.info("BillingCity set to - " + text);
	}

	public void selectZipCode(String text) {
		billingZipCode.click();
		billingZipCode.clear();
		billingZipCode.sendKeys(text);
		logger.info("Billing ZipCode set to - " + text);
	}

	public void selectPaymentOption() {
		this.scrollToTheSpecificWebelement(creditcardordebitcard);
		creditcardordebitcard.click();
		logger.info("Payment Option set to 'Credit-Card or Debit-Card'");
	}

	public void selectCardNumber(String text) {
		this.scrollToTheSpecificWebelement(CreditCardNumber);
		CreditCardNumber.click();
		CreditCardNumber.sendKeys(text.replaceAll("....", "$0 "));
		logger.info("CreditCardNumber set to - " + text);
	}

	public void selectCardCVVNumber(String text) {
		CreditCardCVV.clear();
		CreditCardCVV.sendKeys(text);
		logger.info("CreditCardCVV set to - " + text);
	}

	public void selectCreditCardHolder(String text) {
		CreditCardName.click();
		CreditCardName.clear();
		CreditCardName.sendKeys(text);
		logger.info("Setting CreditCard Holder Name to - " + text);
	}

	public void setBillingCreditCardMonth(String text) {
		Select selection = new Select(CreditCardExpirationMonth);
		selection.selectByVisibleText(text);
		logger.info("Setting CardExpiringMonth to  - " + text);
	}

	public void setBillingCreditCardYear(String text) {
		Select selection = new Select(CreditCardExpirationYear);
		selection.selectByVisibleText(text);
		logger.info("Setting CardExpiringYear to  - " + text);
	}

	public void activateAccount() {
		this.scrollToTheSpecificWebelement(btnStartFaxing);
		btnStartFaxing.click();
		logger.info("Attempting to Activate Account");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("info")));
	}

	public boolean isSignUpSuccess() {
		wait.until(ExpectedConditions.visibilityOf(btnLogin));
		if (btnLogin.isDisplayed()) {
			logger.info("Registration successful");
			return true;
		} else {
			logger.info("Registration unsuccessful.");
			return false;
		}
	}
	
	public void clickLogin() {
		logger.info("Attempting Auto log-in.");
		btnLogin.click();
	}
	
	public boolean isLoggedIn() {
		return !(driver.findElements(By.xpath("//*[@id='loginForm']//input[@name='Submit']")).size() > 0); 
	}
}