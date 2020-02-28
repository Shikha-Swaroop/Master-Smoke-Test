package org.j2.faxqa.efax.corporate.myaccount.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePageMyAccount extends NavigationBarMyAccount {

	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public HomePageMyAccount() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}

	static final String logoutButton = "logout";
	static final String learnAboutTheNewSiteButton = "learn-button";
	
	@FindBy(id = logoutButton)
	private WebElement logoutButtonWebElement;

	@FindBy(className = learnAboutTheNewSiteButton)
	private WebElement learnAboutTheNewSiteButtonWebElement;

	public WebElement getLogoutButton() {
		return logoutButtonWebElement;
	}

	public WebElement getLearnAboutTheNewSiteButton() {
		return learnAboutTheNewSiteButtonWebElement;
	}

	public void clickLogoutButton() {
		getLogoutButton().click();
	}

	public void hoverMouseOverLearnAboutTheNewSiteButton() {
		this.mouseHoverOn(getLearnAboutTheNewSiteButton());
	}
	
	public boolean isUserLoggedIn() {
		wait.until(ExpectedConditions.elementToBeClickable(logoutButtonWebElement));
		return logoutButtonWebElement.getText().equalsIgnoreCase("Logout");
	}
	
	public void logout() {
		logoutButtonWebElement.click();
	}
	
	public boolean isPasswordUpdatePrompted()
	{
		return (TLDriverFactory.getTLDriver().findElements(By.id("txt_currentPIN")).size() >= 1);
	}
	
	public String updatePassword(String password)
	{
		TLDriverFactory.getTLDriver().findElement(By.id("txt_currentPIN")).sendKeys(password);
		TLDriverFactory.getTLDriver().findElement(By.id("txt_newPIN")).sendKeys(password+"3");
		TLDriverFactory.getTLDriver().findElement(By.id("txt_confirmPIN")).sendKeys(password+"3");
		TLDriverFactory.getTLDriver().findElement(By.xpath("//*[@id='form_profilePIN']/div[3]/input")).click();
		return password + "3";
	}
}