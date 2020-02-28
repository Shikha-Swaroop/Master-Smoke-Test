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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SendFaxesModalMyAccount extends BasePage {

	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public SendFaxesModalMyAccount() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}
	
	@FindBy(id = "txt_websend_recipientFirstName")
	private WebElement recipientFirstName;

	@FindBy(id = "txt_websend_recipientLastName")
	private WebElement recipientLastName;

	@FindBy(id = "txt_websend_recipientCompany")
	private WebElement recipientCompany;

	@FindBy(id = "txt_websend_faxNumber")
	private WebElement faxNumber;

	@FindBy(id = "chk_addContact")
	private WebElement selectContact;

	@FindBy(id = "active_add_btn")
	private WebElement add_btn;

	@FindBy(id = "txt_websend_referenceId")
	private WebElement referenceId;
	
	@FindBy(id = "txt_websend_clientId")
	private WebElement txt_websend_clientId;
	
	@FindBy(id = "txt_websend_clientName")
	private WebElement txt_websend_clientName;
	
	@FindBy(id = "txt_websend_clientMatter")
	private WebElement txt_websend_clientMatter;
	
	@FindBy(id = "sel_websend_faxMode")
	private WebElement faxMode;
	
	@FindBy(id = "sel_websend_sendReceipt")
	private WebElement sendReceipt;
	
	@FindBy(id = "chk_includeCoverPage")
	private WebElement includeCoverPage;

	@FindBy(id = "txt_websend_faxSubject")
	private WebElement faxSubject;

	@FindBy(id = "txt_websend_faxBody")
	private WebElement faxBody;
	
	@FindBy(id = "uploadFiles")
	private WebElement uploadFiles;

	@FindBy(id = "btnWebsend")
	private WebElement websendBtn;

/////////////////////////////// Confirmation dialog locators /////////////////////////////////////////
	@FindBy(id = "confirmation_sendto")
	private WebElement confirmation_sendto;

	@FindBy(id = "confirmation_recipientFirstName")
	private WebElement confirmation_recipientFirstName;

	@FindBy(id = "confirmation_recipientLastName")
	private WebElement confirmation_recipientLastName;

	@FindBy(id = "confirmation_recipientCompany")
	private WebElement confirmation_recipientCompany;

	@FindBy(id = "confirmation_recipientClientId")
	private WebElement confirmation_recipientClientId;
	
	@FindBy(id = "confirmation_recipientClientName")
	private WebElement confirmation_recipientClientName;
	
	@FindBy(id = "confirmation_recipientClientMatter")
	private WebElement confirmation_recipientClientMatter;

	@FindBy(id = "confirmation_refID")
	private WebElement confirmation_refID;

	@FindBy(id = "confirmation_faxQuality")
	private WebElement confirmation_faxQuality;

	@FindBy(id = "confirmation_subject")
	private WebElement confirmation_subject;

	@FindBy(id = "confirmation_coverPage")
	private WebElement confirmation_coverPage;
	
	@FindBy(xpath = "//div[@id='dialog_websendConfirmation']//img[@alt='close']")
	private WebElement confirmation_close;
	
	public boolean sendfax(String firstname, String lastname, String company, String faxnumber, String subject, String body, String reference, String clientaccount, String client, String matter, String quality, String attachments) throws Exception {
	
		setrecipientFirstName(firstname);
		setrecipientLastName(lastname);
		setrecipientCompany(company);
		setfaxNumber(faxnumber);
		setselectContact(true);
		setaddContact();
		setreferenceId(reference);
		setclientAccountId(clientaccount);	
		setClient(client);
		setMatter(matter);
		setfaxMode(quality);
		setsendReceipt();
		setincludeCoverPage(true);
		setfaxSubject(subject);
		setfaxBody(body);
		setuploadFiles(attachments);
		send();
		return confirmationVerify(firstname, lastname, company, faxnumber, subject, body, reference, clientaccount, client, matter, quality);
	}

	private void setMatter(String matter) {
		txt_websend_clientMatter.clear();
		txt_websend_clientMatter.sendKeys(matter);
	}

	private void setClient(String client) {
		txt_websend_clientName.clear();
		txt_websend_clientName.sendKeys(client);
	}

	private void setclientAccountId(String clientaccount) {
		txt_websend_clientId.clear();
		txt_websend_clientId.sendKeys(clientaccount);	
	}

	private void send() throws Exception {
		websendBtn.click();
		logger.info("Sending fax...");
	}

	private void setfaxMode(String text) {
		Select mode = new Select(faxMode);
		mode.selectByVisibleText(text);
		logger.info("Fax Quality field set to " + text);
	}

	private void setsendReceipt() {
		Select receipt = new Select(sendReceipt);
		//receipt.selectByVisibleText(text);
		receipt.selectByIndex(0);
		logger.info("Send Receipt field set to deafult first email.");
	}

	private void setreferenceId(String text) {
		referenceId.sendKeys(text);
		logger.info("reference Id field set to " + text);
	}

	private void setuploadFiles(String absolutepaths) {
		for(String file : absolutepaths.split("\\|"))
		{
			wait.until(ExpectedConditions.elementToBeClickable(uploadFiles));
			uploadFiles.sendKeys(file);
			logger.info("Uploaded attachment - " + file);
		}
	}

	private void setfaxBody(String text) {
		faxBody.sendKeys(text);
		logger.info("Message body field set to " + text);
	}
	
	private void setfaxSubject(String text) {
		faxSubject.sendKeys(text);
		logger.info("Subject field set to " + text);
	}

	private void setincludeCoverPage(boolean check) {
		if (check && !includeCoverPage.isSelected())
			includeCoverPage.click();
		else if (!check && includeCoverPage.isSelected())
			includeCoverPage.click();
		logger.info("Coverpage option enabled.");
	}

	private void setaddContact() throws Exception {
		wait.until(ExpectedConditions.elementToBeClickable(add_btn));
		if (add_btn.isEnabled()) {
			logger.info("Add contact button enabled.");
			add_btn.click();
		}
		else
		{
			logger.error("Add contact button disabled.");
			throw new Exception("ERROR: Add contact button disabled.");
		}
	}

	private void setselectContact(boolean check) {
		if (check && !selectContact.isSelected())
			selectContact.click();
		else if (!check && selectContact.isSelected())
			selectContact.click();
	}

	private void setfaxNumber(String text) {
		faxNumber.clear();
		faxNumber.sendKeys(text);
		logger.info("Fax Number field set to " + text);
	}

	private void setrecipientCompany(String text) {
		recipientCompany.sendKeys(text);
		logger.info("Receipent Company field set to " + text);
	}

	private void setrecipientLastName(String text) {
		recipientLastName.sendKeys(text);
		logger.info("Recipient LastName field set to " + text);
	}

	private void setrecipientFirstName(String text) {
		recipientFirstName.sendKeys(text);
		logger.info("Recipient FirstName field set to " + text);
	}
	
	public boolean confirmationVerify(String firstname, String lastname, String company, String faxnumber, String subject, String body, String reference, String clientaccount, String client, String matter, String quality) {
		wait.until(ExpectedConditions.elementToBeClickable(confirmation_sendto));
		if (confirmation_sendto.getText().contains(faxnumber))
			return true;
		else
			return false;
	}
	
	public void closeconfirmation() {
		wait.until(ExpectedConditions.elementToBeClickable(confirmation_close));
		confirmation_close.click();
	}
}