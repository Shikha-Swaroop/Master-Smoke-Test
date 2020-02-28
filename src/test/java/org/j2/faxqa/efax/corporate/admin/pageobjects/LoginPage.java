package org.j2.faxqa.efax.corporate.admin.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.j2.faxqa.efax.common.BasePage;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

	static final String accountNumberField = "adminId";
	static final String administratorNameField = "gAdmin";
	static final String passwordField = "adminpassword";
	static final String loginButton = "adminLoginButton";
	static final String forgotPasswordLink = "forgotPassword";
	static final String errorMessage = "//div[contains(text(),'Sorry, we were not able to log you')]";
	static final String rememberMeCheckbox = "rememberUsernameAndPasswordChk";
	static final String solutionsLinkHeader = "//ul[@class='nav-list']//a[contains(text(),'Solutions')]";
	static final String industryLinkHeader = "//ul[@class='nav-list']//a[contains(text(),'Industry')]";
	static final String resourcesLinkHeader = "//ul[@class='nav-list']//a[contains(text(),'Resources')]";
	static final String aboutLinkHeader = "//a[contains(text(),'About')]";
	static final String partnersLinkHeader = "//a[contains(text(),'Partners')]";
	static final String blogLinkHeader = "//ul[@class='nav-list']//a[contains(text(),'Blog')]";
	static final String getAQuoteLinkHeader = "//a[contains(text(),'Get a Quote')]";
	static final String userTab = "//div[@class='fake-user-tab']";
	static final String partnerTab = "//li[@id='partnerTab']";
	static final String developerTab = "//li[@id='developerTab']//a[contains(text(),'Developer')]";
	static final String onlineFaxingLinkFooter = "//a[contains(text(),'Online Faxing')]";
	static final String solutionsLinkFooter = "//div[@class='text-center']//a[contains(text(),'Solutions')]";
	static final String industryLinkFooter = "//div[@class='text-center']//a[contains(text(),'Industry')]";
	static final String resourcesLinkFooter = "//div[@class='text-center']//a[contains(text(),'Resources')]";
	static final String eventsLinkFooter = "//a[contains(text(),'Events')]";
	static final String cloudServicesLinkFooter = "//a[contains(text(),'Cloud Services')]";
	static final String blogLinkFooter = "//div[@class='text-center']//a[contains(text(),'Blog')]";
	static final String contactUsLinkFooter = "//a[contains(text(),'Contact Us')]";
	static final String smallBusinessLinkFooter = "//a[contains(text(),'Small Business')]";
	static final String mediumSizeBusinessLinkFooter = "//a[contains(text(),'Medium Size Business')]";
	static final String enterpriseBusinessLinkFooter = "//a[contains(text(),'Enterprise Business')]";
	static final String customerAgreementLinkFooter = "//a[contains(text(),'Customer Agreement')]";
	static final String privacyPolicyLinkFooter = "//a[contains(text(),'Privacy Policy')]";
	static final String legalNoticesLinkFooter = "//a[contains(text(),'Legal Notices')]";
	static final String footerBrandingImage = "//p[@class='footer-img']//a//img";
	static final String sitemapLinkFooter = "//a[contains(text(),'Sitemap')]";
	static final String errorMessageForExpiredPasswordTTLScenario = ".//table[@id='formErrorGeneralTableloginFormErrors']//div[@class='formErrorLarge']";
	static final String currentLanguage = "//div[@class='current-language']";
	static final String requiredFieldsValidationMessage = "//div[@id='formErrorJavascriptTableloginFormErrorsAdmin']//td[@class='formMessage']";
	static final String cookie_understand = "cookie-understand";
	
	@FindBy(id = accountNumberField)
	private WebElement accountNumberFieldWebElement;

	@FindBy(id = administratorNameField)
	private WebElement administratorNameFieldWebElement;

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

	@FindBy(xpath = solutionsLinkHeader)
	private WebElement solutionsLinkHeaderWebElement;

	@FindBy(xpath = industryLinkHeader)
	private WebElement industryLinkHeaderWebElement;

	@FindBy(xpath = resourcesLinkHeader)
	private WebElement resourcesLinkHeaderWebElement;

	@FindBy(xpath = aboutLinkHeader)
	private WebElement aboutLinkHeaderWebElement;

	@FindBy(xpath = partnersLinkHeader)
	private WebElement partnersLinkHeaderWebElement;

	@FindBy(xpath = blogLinkHeader)
	private WebElement blogLinkHeaderWebElement;

	@FindBy(xpath = getAQuoteLinkHeader)
	private WebElement getAQuoteLinkHeaderWebElement;

	@FindBy(xpath = userTab)
	private WebElement userTabWebElement;

	@FindBy(xpath = partnerTab)
	private WebElement partnerTabWebElement;

	@FindBy(xpath = developerTab)
	private WebElement developerTabWebElement;

	@FindBy(xpath = onlineFaxingLinkFooter)
	private WebElement onlineFaxingLinkFooterWebElement;

	@FindBy(xpath = solutionsLinkFooter)
	private WebElement solutionsLinkFooterWebElement;

	@FindBy(xpath = industryLinkFooter)
	private WebElement industryLinkFooterWebElement;

	@FindBy(xpath = resourcesLinkFooter)
	private WebElement resourcesLinkFooterWebElement;

	@FindBy(xpath = eventsLinkFooter)
	private WebElement eventsLinkFooterWebElement;

	@FindBy(xpath = cloudServicesLinkFooter)
	private WebElement cloudServicesLinkFooterWebElement;

	@FindBy(xpath = blogLinkFooter)
	private WebElement blogLinkFooterWebElement;

	@FindBy(xpath = contactUsLinkFooter)
	private WebElement contactUsLinkFooterWebElement;

	@FindBy(xpath = smallBusinessLinkFooter)
	private WebElement smallBusinessLinkFooterWebElement;

	@FindBy(xpath = mediumSizeBusinessLinkFooter)
	private WebElement mediumSizeBusinessLinkFooterWebElement;

	@FindBy(xpath = enterpriseBusinessLinkFooter)
	private WebElement enterpriseBusinessLinkFooterWebElement;

	@FindBy(xpath = customerAgreementLinkFooter)
	private WebElement customerAgreementLinkFooterWebElement;

	@FindBy(xpath = privacyPolicyLinkFooter)
	private WebElement privacyPolicyLinkFooterWebElement;

	@FindBy(xpath = legalNoticesLinkFooter)
	private WebElement legalNoticesLinkFooterWebElement;

	@FindBy(xpath = sitemapLinkFooter)
	private WebElement sitemapLinkFooterWebElement;

	@FindBy(xpath = footerBrandingImage)
	private WebElement footerBrandingImageWebElement;

	@FindBy(xpath = errorMessageForExpiredPasswordTTLScenario)
	private WebElement errorMessageForExpiredPasswordTTLScenarioWebElement;

	@FindBy(xpath = currentLanguage)
	private WebElement currentLanguageWebElement;

	@FindBy(xpath = requiredFieldsValidationMessage)
	private WebElement requiredFieldsValidationMessageWebElement;

	@FindBy(id = cookie_understand)
	private WebElement cookie_understandWebElement;
	
	public String errorMessage() {
		String errorMessage = errorMessageWebElement.getText();
		return errorMessage;
	}

	public void enterAccountNumber(String AccountNumber) {
		accountNumberFieldWebElement.clear();
		accountNumberFieldWebElement.sendKeys(AccountNumber);
	}

	public void enterAdministratorName(String AdministratorName) {
		administratorNameFieldWebElement.clear();
		administratorNameFieldWebElement.sendKeys(AdministratorName);
	}

	public void enterPassword(String password) {
		passwordFieldWebElement.clear();
		passwordFieldWebElement.sendKeys(password);
	}

	public void clickLoginButton() {
		loginButtonWebElement.click();
	}

	public void login(String AccountNumber, String AdministratorName, String password) {
		logger.info("Loging-in as Group Administrator...");
		this.enterAccountNumber(AccountNumber);
		logger.info("AccountNumber=" + AccountNumber);
		this.enterAdministratorName(AdministratorName);
		logger.info("AdministratorName=" + AdministratorName);
		this.enterPassword(password);
		logger.info("Password=" + password);
		if (cookie_understandWebElement.isDisplayed())
			cookie_understandWebElement.click();
		this.clickLoginButton();
	}

	public void clickForgotPasswordLink() {

		forgotPasswordLinkWebElement.click();
	}

	public void clickSolutionsLinkHeader() {

		solutionsLinkHeaderWebElement.click();
	}

	public void clickIndustryLinkHeader() {

		industryLinkHeaderWebElement.click();
	}

	public void clickResourcesLinkHeader() {

		resourcesLinkHeaderWebElement.click();
	}

	public void clickAboutLinkHeader() {

		aboutLinkHeaderWebElement.click();
	}

	public void clickPartnersLinkHeader() {

		partnersLinkHeaderWebElement.click();
	}

	public void clickBlogLinkHeader() {

		blogLinkHeaderWebElement.click();
	}

	public void clickAQuoteLinkHeader() {

		getAQuoteLinkHeaderWebElement.click();
	}

	public void clickUserTab() {

		userTabWebElement.click();
	}

	public void clickPartnerTab() {

		partnerTabWebElement.click();
	}

	public void clickDeveloperTab() {

		developerTabWebElement.click();
	}

	public void clickOnlineFaxingLinkFooter() {

		onlineFaxingLinkFooterWebElement.click();
	}

	public void clickSolutionsLinkFooter() {

		solutionsLinkFooterWebElement.click();
	}

	public void clickIndustryLinkFooter() {

		industryLinkFooterWebElement.click();
	}

	public void clickResourcesLinkFooter() {

		resourcesLinkFooterWebElement.click();
	}

	public void clickEventsLinkFooter() {

		eventsLinkFooterWebElement.click();
	}

	public void clickCloudServicesLinkFooter() {

		cloudServicesLinkFooterWebElement.click();
	}

	public void clickBlogLinkFooter() {

		blogLinkFooterWebElement.click();
	}

	public void clickContactUsLinkFooter() {

		contactUsLinkFooterWebElement.click();
	}

	public void clickSmallBusinessLinkFooter() {

		smallBusinessLinkFooterWebElement.click();
	}

	public void clickMediumSizeBusinessLinkFooter() {

		mediumSizeBusinessLinkFooterWebElement.click();
	}

	public void clickEnterpriseBusinessLinkFooter() {

		enterpriseBusinessLinkFooterWebElement.click();
	}

	public void clickCustomerAgreementLinkFooter() {

		customerAgreementLinkFooterWebElement.click();
	}

	public void clickPrivacyPolicyLinkFooter() {

		privacyPolicyLinkFooterWebElement.click();
	}

	public void clickLegalNoticesLinkFooter() {

		legalNoticesLinkFooterWebElement.click();
	}

	public void clickSitemapLinkFooter() {

		sitemapLinkFooterWebElement.click();
	}

	public void clickFooterBrandingImage() {

		footerBrandingImageWebElement.click();
	}

	public String getErrorMessageTextForExpiredPassword() {
		String errorMessage = errorMessageForExpiredPasswordTTLScenarioWebElement.getText();
		return errorMessage;
	}

	public String currentLanguage() throws Exception {
		this.scrollToTheSpecificWebelement(currentLanguageWebElement);
		String language = currentLanguageWebElement.getText();
		return language;
	}

	public String requiredFieldsValidationMessage() {
		String validationMessage = requiredFieldsValidationMessageWebElement.getText();
		return validationMessage;
	}

	public boolean isPasswordChangePrompted() {
		boolean flag = driver.findElements(By.id("confirmNewPin")).size() > 0;
		if (flag) {
			logger.info("Password chnage prompted");
			return true;
		} else
			return false;
	}

	public String changePassword(String password) {
		String newpassword = password+"4";
		driver.findElement(By.id("currentPassword")).sendKeys(password);
		driver.findElement(By.id("newPin")).sendKeys(newpassword);
		driver.findElement(By.id("confirmNewPin")).sendKeys(newpassword);
		driver.findElement(By.id("updatePasswordSubmit")).click();
		logger.info("Updating password to " + newpassword);
		return newpassword;
	}
}