package org.j2.faxqa.efax.corporate.myaccount.pageobjects;

import java.time.Instant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.j2.faxqa.efax.corporate.myaccount.CommonMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountDetailsPageMyAccount extends NavigationBarMyAccount {
	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public AccountDetailsPageMyAccount() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 15);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}

	@FindBy(id = "tabs-prefs")
	private WebElement tabprefs;

	@FindBy(id = "tabs-billing")
	private WebElement tabbilling;

	@FindBy(id = "tabs-usage")
	private WebElement tabusage;

	@FindBy(id = "tabs-profile")
	private WebElement tabprofile;

	@FindBy(xpath = "//*/div[contains(text(), 'Send Fax Options:')]/../*/a[text()='Edit']")
	private WebElement sendfaxoptionsedit;

	@FindBy(id = "txt_sendCSID")
	private WebElement sendCSID;

	@FindBy(xpath = "//form[@id='form_sendPrefs']//input[@alt='Update']")
	private WebElement update;

	@FindBy(id = "myaccthometab")
	private WebElement myaccthometab;

	@FindBy(id = "chk_deliverFaxReceipts")
	private WebElement deliverFaxReceipts;

	@FindBy(id = "sel_defaultEmailAddress")
	private WebElement defaultEmailAddress;

	@FindBy(id = "lnkUsageActivityLogReceive")
	private WebElement lnkUsageActivityLogReceive;

	@FindBy(id = "lnkUsageActivityLogSent")
	private WebElement lnkUsageActivityLogSent;

	@FindBy(id = "recieveReports-tab-link")
	private WebElement recieveReports_tab_link;

	@FindBy(id = "sendReports-tab-link")
	private WebElement sendReports_tab_link;

	@FindBy(id = "btn_receiveLog")
	private WebElement btn_receiveLog;

	@FindBy(id = "btn_sendLog")
	private WebElement btn_sendLog;
	
	@FindBy(id = "receive_reportGrid")
	private WebElement receive_reportGrid;

	@FindBy(id = "send_reportGrid")
	private WebElement send_reportGrid;
		
	public void clickPreferencesTab()
	{
		logger.info("Switching to Preferences Tab");
		tabprefs.click();
	}
	
	public void updatesendCSID(String sender) {
		wait.until(ExpectedConditions.elementToBeClickable(sendfaxoptionsedit));
		sendfaxoptionsedit.click();
		sendCSID.clear();
		sendCSID.sendKeys(sender);
		//setdeliverFaxReceipts(false);
		//setdefaultEmailAddress();
		update.click();
		logger.info("CSID is set to " + sender);
		wait.until(ExpectedConditions.elementToBeClickable(myaccthometab));
		myaccthometab.click();
	}

	private void setdeliverFaxReceipts(boolean check) {
		if (check && !deliverFaxReceipts.isSelected()) {
			logger.info("Enabling Fax receipts delivery");
			deliverFaxReceipts.click();}
		else if (!check && deliverFaxReceipts.isSelected()) {
			logger.info("Disabling Fax receipts delivery");
			deliverFaxReceipts.click(); }
	}

	private void setdefaultEmailAddress() {
		logger.info("Selecting default email address");
		Select receipt = new Select(defaultEmailAddress);
		// receipt.selectByVisibleText(text);
		receipt.selectByIndex(1);
	}
	
	public void clickReceiveTab() {
		logger.info("Clicking Receive Tab");
		wait.until(ExpectedConditions.elementToBeClickable(recieveReports_tab_link));
		recieveReports_tab_link.click();
	}

	public void clickSendTab() {
		logger.info("Clicking Send Tab");
		wait.until(ExpectedConditions.elementToBeClickable(sendReports_tab_link));
		sendReports_tab_link.click();
	}
	
	public void clickReceiveGo() {
		wait.until(ExpectedConditions.elementToBeClickable(btn_receiveLog));
		btn_receiveLog.click();
	}

	public void clickSendGo() {
		wait.until(ExpectedConditions.elementToBeClickable(btn_sendLog));
		btn_sendLog.click();
	}
	
	//////////////////////////////////////////// Receive Logs /////////////////////////////////////////////////////

	private WebElement getExpectedReceiveRecordLog(String senderid) {
		wait.until(ExpectedConditions
				.invisibilityOfElementLocated(By.xpath("//div[@id='load_receive_reportGrid' and text()='Loading...']")));
		if (receive_reportGrid.findElements(By.tagName("tr")).size() > 1)
			for (WebElement element : receive_reportGrid
					.findElements(By.xpath(".//tbody/tr[@class='ui-widget-content jqgrow ui-row-ltr']")))
				if (element.findElements(By.tagName("td")).get(4).getText().contains(senderid)) {
					logger.info("Log record found.");
					return element;
				}

		return null;
	}

	public boolean isReceiveActivityLogFound(String senderid, int timeout) throws InterruptedException {
		clickReceiveGo();

		wait.until(ExpectedConditions
				.invisibilityOfElementLocated(By.xpath("//div[@id='load_receive_reportGrid' and text()='Loading...']")));
		Instant waittime = Instant.now().plusSeconds(timeout);
		logger.info("Expected Receive Activity Log Timeout set to " + timeout + " seconds.");
		WebElement log = null;
		while (log == null && waittime.isAfter(Instant.now())) {
			logger.info("Waiting for the log record...");
			Thread.sleep(10000);
			clickReceiveGo();
			log = getExpectedReceiveRecordLog(senderid);
		}

		if (log != null) {
			printReceiveActivityLog(log);
			return true;
		} else {
			logger.warn("Wait '" + timeout + "' seconds timed-out, expected log not found.");
			return false;
		}

	}

	public void printReceiveActivityLog(WebElement log) {
		logger.info("Expected fax received successfully.");
		logger.info("Date = " + log.findElements(By.tagName("td")).get(0).getText());
		logger.info("Pages = " + log.findElements(By.tagName("td")).get(1).getText());
		logger.info("Type = " + log.findElements(By.tagName("td")).get(2).getText());
		logger.info("Duration = " + log.findElements(By.tagName("td")).get(3).getText());
		logger.info("From = " + log.findElements(By.tagName("td")).get(4).getText());
	}

	//////////////////////////////////////////////////////// Send Logs
	//////////////////////////////////////////////////////// /////////////////////////////////////////////////////////////

	private WebElement getExpectedSendRecordLog(String senderid) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='load_send_reportGrid' and text()='Loading...']")));
		if (send_reportGrid.findElements(By.tagName("tr")).size() > 1)
			for (WebElement element : send_reportGrid
					.findElements(By.xpath(".//tbody/tr[@class='ui-widget-content jqgrow ui-row-ltr']"))) {
				if (element.findElements(By.tagName("td")).get(4).getText().contains(senderid)) {
					logger.info("Log record found.");
					return element;
				}
			}
		return null;
	}

	public boolean isSendActivityLogFound(String senderid, int timeout) throws InterruptedException {

		clickSendGo();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='load_send_reportGrid' and text()='Loading...']")));
		Instant waittime = Instant.now().plusSeconds(timeout);
		logger.info("Expected Send Activity Log Timeout set to " + timeout + " seconds.");
		WebElement log = null;
		while (log == null && waittime.isAfter(Instant.now())) {
			logger.info("Waiting for the log record...");
			Thread.sleep(10000);
			clickSendGo();
			log = getExpectedSendRecordLog(senderid);
		}

		if (log != null) {
			printSendActivityLog1(log);
			return true;
		} else {
			logger.warn("Wait '" + timeout + "' seconds timed-out, expected log not found.");
			return false;
		}

	}

	public void printSendActivityLog1(WebElement log) {
		logger.info("Expected fax received successfully.");
		logger.info("Date = " + log.findElements(By.tagName("td")).get(0).getText());
		logger.info("To = " + log.findElements(By.tagName("td")).get(1).getText());
		logger.info("Pages = " + log.findElements(By.tagName("td")).get(2).getText());
		logger.info("Charge = " + log.findElements(By.tagName("td")).get(3).getText());
		logger.info("Subject = " + log.findElements(By.tagName("td")).get(4).getText());
		logger.info("Status = " + log.findElements(By.tagName("td")).get(5).getText());
	}

	public void switchToReceiveLogs() {
		driver.findElement(By.xpath("//a[text()='View Receive Logs']")).click();
	}
}