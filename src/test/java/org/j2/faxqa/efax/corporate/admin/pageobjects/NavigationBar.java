package org.j2.faxqa.efax.corporate.admin.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.j2.faxqa.efax.common.BasePage;
import org.j2.faxqa.efax.common.BaseTest;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavigationBar extends BasePage {

	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public NavigationBar() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}

	static final String settingsTab = "//*[@id='top-nav-btn-settings']/a/span[1]";
	static final String usersTab = "//*[@id='top-nav-btn-users']/a/span[1]";
	static final String groupsTab = "//*[@id='top-nav-btn-groups']/a/span[1]";
	static final String faxNumbersTab = "//*[@id='top-nav-btn-numbers']/a/span[1]";
	static final String usageTab = "//*[@id='top-nav-btn-usage']/a/span[1]";
	static final String homePageTab = "//*[@id='top-nav-btn-home']/a/span[1]";

	static final String myAccount = "//a[@class='icon_MyAccount']";
	static final String currentLanguage = "//a[@class='icon_World']";
	static final String help = "//a[@class='icon_HelpCircled']";
	static final String logoutButton = "//div[@class='topLinksBtnText' and text()='Logout']";

	@FindBy(xpath = settingsTab)
	private WebElement settingsTabWebElement;

	@FindBy(xpath = usersTab)
	private WebElement usersTabWebElement;

	@FindBy(xpath = groupsTab)
	private WebElement groupsTabWebElement;

	@FindBy(xpath = faxNumbersTab)
	private WebElement faxNumbersTabWebElement;

	@FindBy(xpath = usageTab)
	private WebElement usageTabWebElement;

	@FindBy(xpath = homePageTab)
	private WebElement homePageTabWebElement;

	@FindBy(xpath = myAccount)
	private WebElement myAccountWebElement;

	@FindBy(css = currentLanguage)
	private WebElement currentLanguageWebElement;

	@FindBy(xpath = help)
	private WebElement helpWebElement;

	@FindBy(xpath = logoutButton)
	private WebElement logoutButtonWebElement;

	public void clickUsersTab() {
		wait.until(ExpectedConditions.elementToBeClickable(usersTabWebElement));
		logger.info("Navigating to Users tab");
		usersTabWebElement.click();
	}

	public void clickSettingsTab() {
		wait.until(ExpectedConditions.elementToBeClickable(settingsTabWebElement));
		settingsTabWebElement.click();
		logger.info("Navigating to Settings tab.");
	}

	public void clickGroupsTab() {
		logger.info("Navigating to Groups tab.");
		wait.until(ExpectedConditions.elementToBeClickable(groupsTabWebElement));
		groupsTabWebElement.click();
	}

	public void clickFaxNumbersTab() {
		wait.until(ExpectedConditions.elementToBeClickable(faxNumbersTabWebElement));
		faxNumbersTabWebElement.click();
		logger.info("Navigating to FaxNumbers tab.");
	}

	public void clickUsageTab() {
		wait.until(ExpectedConditions.elementToBeClickable(usageTabWebElement));
		usageTabWebElement.click();
		logger.info("Navigating to Usage tab.");
	}

	public void clickHomeTab() {
		wait.until(ExpectedConditions.elementToBeClickable(homePageTabWebElement));
		homePageTabWebElement.click();
		logger.info("Navigating to Home tab.");
	}

	public boolean isUserLoggedIn() {
		wait.until(ExpectedConditions.elementToBeClickable(logoutButtonWebElement));
		if ( logoutButtonWebElement.getText().contains("Logout")) {
			logger.info("The user is logged-in.");
			return true;
		}
		return false;
	}

	public void logout() {
		logger.info("Logging-out");
		logoutButtonWebElement.click();
	}
}