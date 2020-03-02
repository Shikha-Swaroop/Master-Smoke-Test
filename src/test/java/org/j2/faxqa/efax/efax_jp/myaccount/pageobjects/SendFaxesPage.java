package org.j2.faxqa.efax.efax_jp.myaccount.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.github.javafaker.Faker;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.*;
import org.j2.faxqa.efax.common.Config;
import org.j2.faxqa.efax.common.TLDriverFactory;

public class SendFaxesPage {
	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public SendFaxesPage() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}

	@FindBy(id = "txt_websend_recipientName")
	private WebElement recipientFirstName;

	@FindBy(id = "txt_websend_recipientCompany")
	private WebElement recipientCompany;

	@FindBy(id = "txt_websend_faxNumber")
	private WebElement faxNumber;

	@FindBy(id = "sel_websend_toCountry")
	private WebElement toCountry;

	@FindBy(id = "active_add_btn")
	private WebElement add_btn;

	@FindBy(id = "chk_includeCoverPage")
	private WebElement includeCoverPage;

	@FindBy(id = "txt_websend_faxSubject")
	private WebElement faxSubject;

	@FindBy(id = "txt_websend_faxBody")
	private WebElement faxBody;

	@FindBy(id = "uploadFiles")
	private WebElement uploadFiles;

	@FindBy(id = "txt_websend_referenceId")
	private WebElement referenceId;

	@FindBy(id = "sel_websend_sendReceipt")
	private WebElement sendReceipt;

	@FindBy(id = "sel_websend_faxMode")
	private WebElement faxMode;

	@FindBy(id = "btnWebsend")
	private WebElement websendBtn;

/////////////////////////////// Confirmation dialog locators /////////////////////////////////////////
	@FindBy(id = "confirmation_sendto")
	private WebElement confirmation_sendto;

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

	String random = "";
	String mode = "";

	public void sendfax(String senderid) throws Exception {

		Faker testdata = new Faker(Locale.JAPANESE);
		random = senderid;
		mode = "ドラフトモード";
		String firstname = "QA" + testdata.address().firstName().toUpperCase().replace("'", "");
		String lastname = testdata.address().lastName().toUpperCase().replace("'", "");
		String email = firstname + "." + lastname + testdata.number().digits(3) + "@mailinator.com";
		String phone = testdata.phoneNumber().cellPhone().toString();
		String company = testdata.company().name();
		String country = "アメリカ合衆国";

		Path folder = Paths.get((new java.io.File(".")).getCanonicalPath(), "src/test/resources/sendrast");
		Stream<Path> pathstream = Files.list(folder).filter(f -> f.getFileName().toString().endsWith(".txt"));
		String attachments = Files.list(folder).filter(f -> f.getFileName().toString().endsWith(".txt")).limit(1).map(f -> f.toAbsolutePath().toString()).collect(Collectors.joining("|"));

		setrecipientName(firstname + " " + lastname);
		setrecipientCompany(random);
		settoCountry(country);
		setfaxNumber(Config.DID_JP);
		setaddContact();
		setincludeCoverPage(true);
		setfaxSubject(random);
		setfaxBody(random);
		setuploadFiles(attachments);
		setsendReceipt(email);
		setfaxMode(mode);
		send();
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

	private void setsendReceipt(String text) {
		Select receipt = new Select(sendReceipt);
		// receipt.selectByVisibleText(text);
		receipt.selectByIndex(0);
		logger.info("Send Receipt field set to deafult first email.");
	}

	private void setuploadFiles(String absolutepaths) {
		for (String file : absolutepaths.split("\\|")) {
			wait.until(ExpectedConditions.elementToBeClickable(uploadFiles));
			uploadFiles.sendKeys(file);
			logger.info("Uploading attachment - " + file);
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
		} else {
			logger.error("Add contact button disabled.");
			throw new Exception("ERROR: Add contact button disabled.");
		}
	}

	private void settoCountry(String text) {
		Select country = new Select(toCountry);
		country.selectByVisibleText(text);
		logger.info("Country field set to " + text);
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

	private void setrecipientName(String text) {
		wait.until(ExpectedConditions.elementToBeClickable(recipientFirstName));
		recipientFirstName.sendKeys(text);
		logger.info("Recipient FirstName field set to " + text);
	}

	public boolean confirmationVerify(String sender) {
		wait.until(ExpectedConditions.visibilityOf(confirmation_sendto));
		return confirmation_sendto.getText().contains(sender);
	}

	public void closeconfirmation() {
		confirmation_close.click();
	}
}