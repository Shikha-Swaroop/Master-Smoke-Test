
package org.j2.faxqa.microsites.myaccount.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.*;
import org.j2.faxqa.efax.common.TLDriverFactory;

public class HomePage extends NavigationBar {
	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public HomePage() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}

	private void clearAlerts() {
		if (driver.findElements(By.xpath("//div[@id='continueDiv']/a[1]")).size() != 0)
			driver.findElement(By.xpath("//div[@id='continueDiv']/a[1]")).click();
	}

	@FindBy(id = "sel_phoneNumbers")
	private WebElement phoneNumbers;

	@FindBy(id = "viewfaxesdash")
	private WebElement viewfaxesdash;

	@FindBy(id = "sendfaxesdash")
	private WebElement sendfaxesdash;

	@FindBy(id = "updateacctdash")
	private WebElement updateacctdash;

	@FindBy(id = "helpdash")
	private WebElement helpdash;

	@FindBy(id = "slfbtn")
	private WebElement largefileshareBtn;

	@FindBy(id = "logout")
	private WebElement logout;
	
	public void gotosendfaxesview() {
		wait.until(ExpectedConditions.elementToBeClickable(sendfaxesdash));
		sendfaxesdash.click();
		logger.info("Opened Send Faxes view.");
	}

	public void gotoacctdetailsview() {
		wait.until(ExpectedConditions.elementToBeClickable(updateacctdash));
		updateacctdash.click();
		logger.info("Opened Account Details view.");
	}
	
	public void gotoviewfaxesview() {
		wait.until(ExpectedConditions.elementToBeClickable(viewfaxesdash));
		viewfaxesdash.click();
		logger.info("Opened View Faxes view.");
	}
	
	public void gotohelpview() {
		wait.until(ExpectedConditions.elementToBeClickable(helpdash));
		helpdash.click();
		logger.info("Opened Help view.");
	}

}