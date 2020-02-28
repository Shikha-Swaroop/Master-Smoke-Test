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
import org.openqa.selenium.support.ui.WebDriverWait;

public class GroupDetailsPage extends BasePage {

	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public GroupDetailsPage() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}

	static final String advancedSettingsButton = "//div[contains(@class,'advanced-settings-button')]";
	static final String inputHiddenStorageEnabled = "//input[@id='NonSecureStorageEnabled']";
	static final String inputHiddenEditProfile = "//input[@id='userEditProfile']";
	static final String addUser = "adminCreateAccount";
	
	@FindBy(xpath = advancedSettingsButton)
	private WebElement advancedSettingsButtonWebElement;

	@FindBy(xpath = inputHiddenStorageEnabled)
	private WebElement inputHiddenStorageEnabledWebElement;

	@FindBy(xpath = inputHiddenEditProfile)
	private WebElement inputHiddenEditProfileWebElement;

	@FindBy(id = addUser)
	private WebElement addUserWebElement;
	
	public WebElement getUnassignedFaxEmailField() {
		WebElement unassignedFaxEmailFieldWebElement = null;
		String locator = "unassignedDIDEmail";
		try {
			unassignedFaxEmailFieldWebElement = driver.findElement(By.id("unassignedDIDEmail"));
		} catch (Exception e) {
			@SuppressWarnings("unused")
			WebElement element = driver.findElement(By.id("unassignedDIDEmail"));
		}
		return unassignedFaxEmailFieldWebElement;
	}

	public void clickAdvancedSettingsButton() {
		this.scrollToTheSpecificWebelement(advancedSettingsButtonWebElement);
		advancedSettingsButtonWebElement.click();
	}

	public boolean isStorageEnabled() {
		this.scrollToTheSpecificWebelement(inputHiddenStorageEnabledWebElement);
		return inputHiddenStorageEnabledWebElement.isSelected();
	}

	public boolean isEditProfileEnabled() {
		this.scrollToTheSpecificWebelement(inputHiddenEditProfileWebElement);
		return inputHiddenEditProfileWebElement.isSelected();
	}
	
	public void clickAddUser()
	{
		this.scrollToTheSpecificWebelement(addUserWebElement);
		addUserWebElement.click();
	}
	
}