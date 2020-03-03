
package org.j2.faxqa.efax.efax_jp.myaccount.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


//import com.google.common.*;
//import com.google.common.io.Files;

import java.time.Instant;
import java.util.List;
import org.apache.logging.log4j.*;
import org.j2.faxqa.efax.common.BasePage;
import org.j2.faxqa.efax.common.TLDriverFactory;

public class ViewFaxesPage extends BasePage {
	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public ViewFaxesPage() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}

	@FindBy(xpath = "//*/a/span[contains(text(),'受信トレイ')]")
	private WebElement inbox;

	@FindBy(xpath = "//*[@id='messageCenter']//li[@class='x-tree-node']")
	private WebElement folders;

	@FindBy(xpath = "//*/div[@id='inboxFolder']//table[@class='x-grid3-row-table']")
	private List<WebElement> inboxfaxes;

	@FindBy(xpath = "//*/div[@id='inboxFolder']//table[@class='x-grid3-row-table']")
	private List<WebElement> sentfaxes;

	@FindBy(xpath = "//*/div[@id='inboxFolder']//table[@class='x-grid3-row-table']")
	private List<WebElement> trashfaxes;

	@FindBy(id = "messageDetail")
	private WebElement messageDetail;

	private WebElement getreceivedfax(String from) throws InterruptedException {	
		wait.until(ExpectedConditions.elementToBeClickable(inbox));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[text()='Loading...']")));
		for (WebElement e : inboxfaxes)
		{
			if (e.findElements(By.tagName("td")).get(3).getCssValue("font-weight").contains("700") && e.findElements(By.tagName("td")).get(3).getText().contains(from))
				return e;
		}
		return null;
	}
	
	public boolean isFaxReceived(String from, int timeout) throws Exception {
		Instant waittime = Instant.now().plusSeconds(timeout);
		logger.info("Expected inbound fax Timeout set to "+ timeout +" seconds.");
		logger.info("SenderID = " + from);
		WebElement fax = null;
		while (fax == null && waittime.isAfter(Instant.now())) {
			logger.info("Waiting for the fax...");
			Thread.sleep(30000);
			inbox.click();
			fax = getreceivedfax(from);
		} 
		if (fax != null) {
			printreceivedfax(fax);
			return true;
		}
		else {
			logger.info("Wait '" + timeout + "' seconds timedout, expected fax not received.");
			return false;
		}
			
	}

	public void printreceivedfax(WebElement fax) {
		logger.info("Expected fax received successfully.");
		logger.info("From Sender = " + fax.findElements(By.tagName("td")).get(3).getText());
		logger.info("With Subject = " + fax.findElements(By.tagName("td")).get(4).getText());
		logger.info("On Date Time = " + fax.findElements(By.tagName("td")).get(5).getText());
	}

}