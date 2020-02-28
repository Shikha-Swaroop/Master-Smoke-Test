package org.j2.faxqa.efax.corporate.myaccount.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.j2.faxqa.efax.common.BasePage;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavigationBarMyAccount extends BasePage {

	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public NavigationBarMyAccount() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}

	static final String myaccthometab = "myaccthometab";
	static final String viewfaxestab = "viewfaxestab";
	static final String sendfaxestab = "sendfaxestab";
	static final String reportstab = "reportstab";
	static final String updateaccttab = "updateaccttab";
	static final String filesharetab = "filesharetab";
	static final String helptab = "helptab";
	static final String logout = "logout";
	
	@FindBy(id = myaccthometab)
	private WebElement myaccthometabElement;

	@FindBy(id = viewfaxestab)
	private WebElement viewfaxestabElement;

	@FindBy(id = sendfaxestab)
	private WebElement sendfaxestabElement;

	@FindBy(id = reportstab)
	private WebElement reportstabElement;

	@FindBy(id = updateaccttab)
	private WebElement updateaccttabElement;

	@FindBy(id = filesharetab)
	private WebElement filesharetabElement;

	@FindBy(id = helptab)
	private WebElement helptabElement;

	@FindBy(id = logout)
	private WebElement logoutElement;
	

	public void clickSendFaxesTab() {
		wait.until(ExpectedConditions.elementToBeClickable(sendfaxestabElement));
		sendfaxestabElement.click();
	}

	public void clickAccountDetailsTab() {
		wait.until(ExpectedConditions.elementToBeClickable(updateaccttabElement));
		updateaccttabElement.click();
	}

	public void clickViewFaxesTab() {
		wait.until(ExpectedConditions.elementToBeClickable(viewfaxestabElement));
		viewfaxestabElement.click();
	}

	public void clickReportsTab() {
		wait.until(ExpectedConditions.elementToBeClickable(reportstabElement));
		reportstabElement.click();
	}

	public void clickFileshareTab() {
		wait.until(ExpectedConditions.elementToBeClickable(filesharetabElement));
		filesharetabElement.click();
	}
	
	public void clickHelpTab() {
		wait.until(ExpectedConditions.elementToBeClickable(helptabElement));
		helptabElement.click();
	}
	
	public void logout() {
		wait.until(ExpectedConditions.elementToBeClickable(helptabElement));
		helptabElement.click();
	}
	
}