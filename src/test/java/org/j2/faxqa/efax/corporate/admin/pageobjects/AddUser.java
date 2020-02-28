package org.j2.faxqa.efax.corporate.admin.pageobjects;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.j2.faxqa.efax.common.Config;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

public class AddUser extends NavigationBar {
	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public AddUser() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}

	static final String adminAccountCheckbox = "/html[1]/body[1]/div[1]/div[2]/div[1]/div[1]/input[1]";
	static final String firstName = "firstName";
	static final String lastName = "lastName";
	static final String contactEmail = "contactEmail";
	static final String emailPrefix = "emailPrefix";
	static final String password = "newPin";
	static final String confirmPassword = "confirmNewPin";
	static final String administratorName = "newGroupAdminUserName";
	static final String createUser = "//div[@id='create-new-user-section']/div[5]/button";
	static final String passwordValidation = "errorStatusMsg";
	static final String faxNumber = "//span[@id='availableNumberList-button']/span[@class='ui-selectmenu-text']";
	static final String createUserValidationMessage = "//span[@id='successStatusMsg']";
	static final String clickEmailDomain = ".//span[contains(@id,'emailDomain-button')]/span[1]";
	static final String validationMessageForFirstName = "//body[@class='users createNewUser has-js']/div[@class='wrap main']/div[@class='mainContent wrap']/div[@class='mainContent wrap']/div[@id='create-new-user-section']/div[@class='block']/div[2]/div[1]/span[1]";
	static final String validationMessageForLastName = "//div[@class='field right-col-left-pad']//span[@class='has-error-text'][contains(text(),'This is a required field.')]";
	static final String validationMessageForEmailAddress = "//div[@class='edit-user-email-prefix-holder']//span[@class='has-error-text'][contains(text(),'This is a required field.')]";
	static final String validationMessageForAdministratorName = "//div[@class='row adminUser']//span[@class='has-error-text'][contains(text(),'This is a required field.')]";
	static final String validationMessageForPasswordConfirmPassword = "//div[@id='formErrorJavascriptMessage']";
	static final String advancedOptions = "//div[@class='advanced-settings-button']";
	static final String userMaySendFaxes = "//label[@class='onoffswitch-label' and @for='sendEnabled']/span[2]";
	//
	static final String readOnlyAdmin = "//div[@class='field right-col-left-pad']//span[@class='onoffswitch-switch']";

	@FindBy(xpath = adminAccountCheckbox)
	private WebElement adminAccountCheckboxWebElement;

	@FindBy(id = firstName)
	private WebElement firstNameWebElement;

	@FindBy(id = lastName)
	private WebElement lastNameWebElement;

	@FindBy(id = contactEmail)
	private WebElement contactEmailElement;

	@FindBy(id = emailPrefix)
	private WebElement emailPrefixElement;
	
	@FindBy(id = password)
	private WebElement passwordWebElement;

	@FindBy(id = confirmPassword)
	private WebElement confirmPasswordWebElement;

	@FindBy(id = administratorName)
	private WebElement administratorNameWebElement;

	@FindBy(xpath = createUser)
	private WebElement createUserWebElement;

	@FindBy(id = passwordValidation)
	private WebElement passwordValidationWebElement;

	@FindBy(xpath = faxNumber)
	private WebElement faxNumberWebElement;

	@FindBy(xpath = createUserValidationMessage)
	private WebElement createUserValidationMessageWebElement;

	@FindBy(xpath = clickEmailDomain)
	private WebElement clickEmailDomainWebElement;

	@FindBy(xpath = validationMessageForFirstName)
	private WebElement validationMessageForFirstNameWebElement;

	@FindBy(xpath = validationMessageForLastName)
	private WebElement validationMessageForLastNameWebElement;

	@FindBy(xpath = validationMessageForEmailAddress)
	private WebElement validationMessageForEmailAddressWebElement;

	@FindBy(xpath = validationMessageForAdministratorName)
	private WebElement validationMessageForAdministratorNameWebElement;

	@FindBy(xpath = validationMessageForPasswordConfirmPassword)
	private WebElement validationMessageForPasswordConfirmPasswordWebElement;

	@FindBy(xpath = userMaySendFaxes)
	private WebElement userMaySendFaxesWebElement;

	@FindBy(xpath = advancedOptions)
	private WebElement advancedOptionsWebElement;

	@FindBy(xpath = readOnlyAdmin)
	private WebElement readOnlyAdminWebElement;

	public WebElement readOnlyAdmin() {
		return readOnlyAdminWebElement;
	}

	public void tickAdminAccountCheckbox(boolean flag) {
		wait.until(ExpectedConditions.elementToBeClickable(adminAccountCheckboxWebElement));
		if (flag && !adminAccountCheckboxWebElement.isSelected()) {
			logger.info("Selecting Admin check-box");
			adminAccountCheckboxWebElement.click();
		}
		if (!flag && adminAccountCheckboxWebElement.isSelected()) {
			logger.info("De-selecting Admin check-box");
			adminAccountCheckboxWebElement.click();
		}
	}

	public void enterFirstName(String text) {
		firstNameWebElement.clear();
		firstNameWebElement.sendKeys(text);
		logger.info("FirstName=" + text);
	}

	public void enterLastName(String text) {
		lastNameWebElement.clear();
		lastNameWebElement.sendKeys(text);
		logger.info("LastName=" + text);
	}

	public void enterPrimaryEmailAddress(String text) {
		contactEmailElement.clear();
		contactEmailElement.sendKeys(text);
		logger.info("PrimaryEmail=" + text);
	}
	
	public void enterPrimaryEmailPrefix(String text) {
		emailPrefixElement.clear();
		emailPrefixElement.sendKeys(text.split("@")[0]);
		logger.info("PrimaryEmail=" + text.split("@")[0]);
	}

	public void enterSenderEmailAddress(String text) {
		contactEmailElement.clear();
		contactEmailElement.sendKeys(text);
		logger.info("SenderEmail=" + text);
	}

	public void enterPassword(String setPassword) {
		passwordWebElement.sendKeys(setPassword);
		logger.info("Password=" + setPassword);
	}

	public void enterConfirmPassword(String setConfirmPassword) {
		confirmPasswordWebElement.sendKeys(setConfirmPassword);
		logger.info("Password=" + setConfirmPassword);
	}

	public void enterAdministratorName(String text) {
		administratorNameWebElement.clear();
		administratorNameWebElement.sendKeys(text);
		logger.info("AdministratorName=" + text);
	}

	public void clickCreateUserButton() {
		wait.until(ExpectedConditions.elementToBeClickable(createUserWebElement));
		createUserWebElement.click();
		logger.info("Creating user account...");
	}

	public void clickReadOnlyAdminToggle() {

		readOnlyAdminWebElement.click();
	}

	public void userMaySendFaxesToggle(boolean flag) {
		wait.until(ExpectedConditions.elementToBeClickable(userMaySendFaxesWebElement));
		this.scrollToTheSpecificWebelement(userMaySendFaxesWebElement);
		if (!TLDriverFactory.getTLDriver().findElement(By.id("sendEnabled")).isSelected() && flag) {
			logger.info("Allowing the User to Send Faxes");
			userMaySendFaxesWebElement.click();}
		else if (TLDriverFactory.getTLDriver().findElement(By.id("sendEnabled")).isSelected() && !flag) {
			logger.info("Disallowing the User to Send Faxes");
			userMaySendFaxesWebElement.click();}
	}

	public String getPasswordValidationMessage() {
		String validationMessageForCommonlyUsedPasswords = passwordValidationWebElement.getText();
		return validationMessageForCommonlyUsedPasswords;
	}

	public String getCreateUserValidationMessage() {
		String SucessCreateUserValidationMessage = createUserValidationMessageWebElement.getText();
		return SucessCreateUserValidationMessage;
	}

	public String getFaxNumber() {
		wait.until(ExpectedConditions.elementToBeClickable(faxNumberWebElement));
		List<WebElement> numberelements = new Select(
				TLDriverFactory.getTLDriver().findElement(By.id("availableNumberList"))).getOptions();
		List<String> numbers = numberelements.stream().map(e -> e.getAttribute("value")).collect(Collectors.toList());
		String number = numbers.get(new Faker().number().numberBetween(1, numbers.size() + 1) - 1);
		faxNumberWebElement.click();
		TLDriverFactory.getTLDriver().findElement(By.id("availableNumberList-menu"))
				.findElement(By.xpath("//li[text()='" + number + "']")).click();
		logger.info("FaxNumber="+faxNumberWebElement.getText());
		return faxNumberWebElement.getText();
	}

	public void selectEmailDomain(String mailname) throws Exception {
		clickEmailDomainWebElement.click();
		String locator = ".//ul[@id='emailDomain-menu']/li[contains(text(),'" + mailname + "')]";
		try {
			WebElement subdrop = driver
					.findElement(By.xpath(".//ul[@id='emailDomain-menu']/li[contains(text(),'" + mailname + "')]"));
			Actions action = new Actions(driver);
			action.moveToElement(subdrop).perform();
			action.click(subdrop).perform();
		} catch (Exception e) {
			@SuppressWarnings("unused")
			WebElement element = driver
					.findElement(By.xpath(".//ul[@id='emailDomain-menu']/li[contains(text(),'" + mailname + "')]"));
		}
	}

	public String validationMessageForFirstName() {
		String validationMessageForFirstName = validationMessageForFirstNameWebElement.getText();
		return validationMessageForFirstName;
	}

	public String validationMessageForLastName() {
		String validationMessageForLastName = validationMessageForLastNameWebElement.getText();
		return validationMessageForLastName;
	}

	public String validationMessageForEmailAddress() {
		String validationMessageForEmailAddress = validationMessageForEmailAddressWebElement.getText();
		return validationMessageForEmailAddress;
	}

	public String validationMessageForAdministratorName() {
		String validationMessageForAdministratorName = validationMessageForAdministratorNameWebElement.getText();
		return validationMessageForAdministratorName;
	}

	public String validationMessageForPasswordConfirmPassword() {
		String validationMessageForPasswordConfirmPassword = validationMessageForPasswordConfirmPasswordWebElement
				.getText();
		return validationMessageForPasswordConfirmPassword;
	}

	public void clickAdvancedOptions() {
		advancedOptionsWebElement.click();
	}

	public String createUserValidationMessage() {
		wait.until(ExpectedConditions.elementToBeClickable(createUserValidationMessageWebElement));
		logger.info(createUserValidationMessageWebElement.getText());
		return createUserValidationMessageWebElement.getText();
	}
}