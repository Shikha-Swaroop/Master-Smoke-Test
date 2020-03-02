package org.j2.faxqa.efax.efax_us.myaccount.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import org.apache.logging.log4j.*;
import org.j2.faxqa.efax.common.BasePage;
import org.j2.faxqa.efax.common.TLDriverFactory;

public class AccountDetailsPage extends BasePage {
	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public AccountDetailsPage() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
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

	@FindBy(id = "receive_usageGrid")
	private WebElement receive_usageGrid;

	@FindBy(id = "send_usageGrid")
	private WebElement send_usageGrid;

	@FindBy(id = "date_sendToDate")
	private WebElement date_sendToDate;

	@FindBy(id = "date_receiveToDate")
	private WebElement date_receiveToDate;

	@FindBy(id = "btn_receiveLog")
	private WebElement btn_receiveLog;

	@FindBy(id = "btn_sendLog")
	private WebElement btn_sendLog;

	public void updatesendCSID(String sender) {
		wait.until(ExpectedConditions.elementToBeClickable(sendfaxoptionsedit));
		sendfaxoptionsedit.click();
		sendCSID.clear();
		sendCSID.sendKeys(sender);
		setdeliverFaxReceipts(false);
		setdefaultEmailAddress();
		update.click();
		logger.info("CSID is set to " + sender);
		wait.until(ExpectedConditions.elementToBeClickable(myaccthometab));
		myaccthometab.click();
	}

	private void setdeliverFaxReceipts(boolean check) {
		if (check && !deliverFaxReceipts.isSelected())
			deliverFaxReceipts.click();
		else if (!check && deliverFaxReceipts.isSelected())
			deliverFaxReceipts.click();
		logger.info("coverpage option enabled.");
	}

	private void setdefaultEmailAddress() {
		Select receipt = new Select(defaultEmailAddress);
		// receipt.selectByVisibleText(text);
		receipt.selectByIndex(1);
	}

	public void clickUsageTab() {
		tabusage.click();
	}

	public void clickSendActivityDetails() {
		lnkUsageActivityLogSent.click();
	}

	public void clickReceiveActivityDetails() {
		lnkUsageActivityLogReceive.click();
	}

	public void clickReceiveGo() {
		wait.until(ExpectedConditions.elementToBeClickable(btn_receiveLog));
		btn_receiveLog.click();
	}

	public void clickSendGo() {
		wait.until(ExpectedConditions.elementToBeClickable(btn_sendLog));
		btn_sendLog.click();
	}

	////////////////////////////////////////////////////// Receive Logs
	////////////////////////////////////////////////////// /////////////////////////////////////////////////////

	private WebElement getExpectedReceiveRecordLog(String senderid) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='load_receive_usageGrid' and text()='Loading...']")));
		if (receive_usageGrid.findElements(By.tagName("tr")).size() > 1) {
			wait.until(ExpectedConditions.elementToBeClickable(receive_usageGrid));
			for (WebElement element : receive_usageGrid.findElements(By.xpath(".//tbody/tr[@class='ui-widget-content jqgrow ui-row-ltr']")))
				if (element.findElements(By.tagName("td")).get(4).getText().contains(senderid)) {
					logger.info("Log record found.");
					return element;
				}
		}
		return null;
	}

	public boolean isReceiveActivityLogFound(String senderid, int timeout) throws InterruptedException {
		setReceiveDate();
		clickReceiveGo();

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='load_receive_usageGrid' and text()='Loading...']")));
		Instant waittime = Instant.now().plusSeconds(timeout);
		logger.info("Expected Receive Activity Log Timeout set to " + timeout + " seconds.");
		WebElement log = null;
		while (log == null && waittime.isAfter(Instant.now())) {
			logger.info("Waiting for the log record...");
			Thread.sleep(10000);
			clickReceiveGo();
			log = getReceiveRecord(senderid);
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

	private WebElement getReceiveRecord(String senderid)
	{
		if (driver.findElements(By.xpath("//*[@id='receive_usageGrid']//td[contains(text(),'" + senderid + "')]/..")).size() > 0)
			return driver.findElement(By.xpath("//*[@id='receive_usageGrid']//td[contains(text(),'" + senderid + "')]/.."));
		return null;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private WebElement getSendRecord(String senderid)
	{
		if (driver.findElements(By.xpath("//*[@id='send_usageGrid']//td[contains(text(),'" + senderid + "')]/..")).size() > 0)
			return driver.findElement(By.xpath("//*[@id='send_usageGrid']//td[contains(text(),'" + senderid + "')]/.."));
		return null;
	}

	private WebElement getExpectedSendRecordLog(String senderid) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='load_send_usageGrid' and text()='Loading...']")));
		if (send_usageGrid.findElements(By.tagName("tr")).size() > 1) {
			wait.until(ExpectedConditions.visibilityOfAllElements(send_usageGrid));
			for (WebElement element : send_usageGrid.findElements(By.xpath(".//tbody/tr[@class='ui-widget-content jqgrow ui-row-ltr']"))) {
				if (element.findElements(By.tagName("td")).get(4).getText().contains(senderid)) {
					logger.info("Log record found.");
					return element;
				}
			}
		}
		return null;
	}

	public boolean isSendActivityLogFound(String senderid, int timeout) throws InterruptedException {
		clickUsageTab();
		clickSendActivityDetails();
		setSendDate();
		clickSendGo();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='load_send_usageGrid' and text()='Loading...']")));
		Instant waittime = Instant.now().plusSeconds(timeout);
		logger.info("Expected Send Activity Log Timeout set to " + timeout + " seconds.");
		WebElement log = null;
		while (log == null && waittime.isAfter(Instant.now())) {
			logger.info("Waiting for the log record...");
			Thread.sleep(10000);
			clickSendGo();
			log = getSendRecord(senderid);
		}

		if (log != null) {
			printSendActivityLog1(log);
			return true;
		} else {
			logger.warn("Wait '" + timeout + "' seconds timed-out, expected log not found.");
			return false;
		}

	}

	private void setSendDate() {
		String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
		((JavascriptExecutor) this.driver).executeScript("document.getElementById('date_sendToDate').value = '" + today + "';", date_sendToDate);
	}

	private void setReceiveDate() {
		String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
		((JavascriptExecutor) this.driver).executeScript("document.getElementById('date_receiveToDate').value = '" + today + "';", date_receiveToDate);
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