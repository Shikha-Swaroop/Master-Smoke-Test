package org.j2.faxqa.efax.corporate.myaccount.pageobjects;

import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

public class ViewFaxesModalMyAccount extends NavigationBarMyAccount {

	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public ViewFaxesModalMyAccount() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}

	static final String createNewFolderButton = "//table[@id='newFolder']//button";
	static final String createNewFolderName = "//input[@id='createnewFolderName']";
	static final String createFolderButton = ".//button[text()='Create Folder']";
	static final String cancelFolderButton = ".//button[text()='Cancel']";
	static final String yesButtonInDeleteFolderPopUp = ".//button[text()='Yes']";
	static final String noButtonInDeleteFolderPopUp = ".//button[text()='No']";
	static final String deleteFolderButton = ".//table[@id='deleteFolder']//span[text()='Delete']";
	static final String nextMessageButton = "//table[@id='mgBtnNext']//button";
	static final String messageTitle = "//div[@class='message-title']";
	static final String previousMessageButton = "//table[@id='mgBtnPrev']//button";
	static final String pageNumberTextbox = "//input[@id='ext-gen140']";
	static final String nextPageButton = "//div[@id='INBOXFolder']//button[@class='x-btn-text x-tbar-page-next']";
	static final String previousPageButton = "//div[@id='INBOXFolder']//button[@class='x-btn-text x-tbar-page-prev']";
	static final String firstPageButton = "//div[@id='INBOXFolder']//button[@class='x-btn-text x-tbar-page-first']";
	static final String lastPageButton = "//div[@id='INBOXFolder']//button[@class='x-btn-text x-tbar-page-last']";
	static final String pagingInfo = "//div[@id='ext-gen170']";
	static final String errorMessage = "//span[contains(text(),'renamed or deleted')]";
	static final String okButtonFromErrorPopup = "//button[@id='ext-gen436']";
	static final String renameButton = "/html[1]/body[1]/div[2]/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[3]/table[1]/tbody[1]/tr[1]/td[2]/em[1]/button[1]/span[1]";
	static final String editFolderName = "//input[@id='editnewFolderName']";
	static final String renameFolderButton = "//button[contains(text(),'Rename Folder')]";
	static final String didSelectionDropdown = "/html[1]/body[1]/div[2]/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]/img[1]";
	static final String inboxTextFoldersSection = ".//span[@id='extdd-3']";
	static final String printMessageValidationMyAccount = ".//img[@class='printMessage']";
	static final String defaultFirstFaxMessageCenter = "(.//div[contains(@class,'rowread')])[1]";
	static final String dateTimeLabelFromUploadedFileMessageCenter = "//div[1]/table//td[@class='x-grid3-col x-grid3-cell x-grid3-td-5 ']/div";
	static final String viewFaxLinkInPreviewPane = ".//div[@id='messageDetail']//div[contains(@class,'message-title')]//following::span[contains(@class,'attachments')]/a";
	static final String invalidMessage = "//div[@class='x-form-invalid-msg']";
	static final String contactsTab = "//*[@class='x-tab-strip-text contacts']//ancestor::li[1]";
	static final String contactsList = "//div[@id='contactsGrid']//table[contains(@class,'row-table')]";
	static final String selectAllCheckboxOfContactsTab = "//div[@id='contactsFolder']//div[@class='x-grid3-hd-checker']";
	static final String deleteContactsButton = "//div[@id='contactsGrid']//button[contains(text(),'Delete')]";
	static final String yesButtonFromTheConfirmDeleteContactsDialog = "//div[@class='x-window x-window-plain x-window-dlg']//button[contains(text(),'Yes')]";
	static final String okButtonFromTheDeleteContactsDialog = "//div[@class='x-window x-window-plain x-window-dlg']//button[contains(text(),'OK')]";
	static final String importButton = "//button[contains(text(),'Import')]";
	static final String uploadCSVFileButton = "//div[@id='win_importContacts']//input[@id='file-file']";
	static final String importButtonFromTheImportCSVFileOverlay = "//div[@class='modal-btn']/div[contains(text(),'Import')]";
	static final String firstNameListFromTheContactsTab = "//div[@id='contactsGrid']//table[contains(@class,'row-table')]//tr/td[3]/div";

	public String firstWinHandle;
	public String secondWinHandle;

	@FindBy(xpath = "//*/a/span[contains(text(),'INBOX ')]")
	private WebElement inbox;

	@FindBy(xpath = "//*/a/span[text()='Sent']")
	private WebElement sent;

	@FindBy(xpath = "//*/a/span[text()='Trash'")
	private WebElement trash;

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
	
	@FindBy(xpath = createNewFolderButton)
	private WebElement createNewFolderButtonWebElement;

	@FindBy(xpath = createNewFolderName)
	private WebElement createNewFolderNameWebElement;

	@FindBy(xpath = createFolderButton)
	private WebElement createFolderButtonWebElement;

	@FindBy(xpath = cancelFolderButton)
	private WebElement cancelFolderButtonWebElement;

	@FindBy(xpath = yesButtonInDeleteFolderPopUp)
	private WebElement yesButtonInDeleteFolderPopUpWebElement;

	@FindBy(xpath = deleteFolderButton)
	private WebElement deleteFolderButtonWebElement;

	@FindBy(xpath = nextMessageButton)
	private WebElement nextMessageButtonWebElement;

	@FindBy(xpath = messageTitle)
	private WebElement messageTitleWebElement;

	@FindBy(xpath = previousMessageButton)
	private WebElement previousMessageButtonWebElement;

	@FindBy(xpath = pageNumberTextbox)
	private WebElement pageNumberTextboxWebElement;

	@FindBy(xpath = nextPageButton)
	private WebElement nextPageButtonWebElement;

	@FindBy(xpath = previousPageButton)
	private WebElement previousPageButtonWebElement;

	@FindBy(xpath = firstPageButton)
	private WebElement firstPageButtonWebElement;

	@FindBy(xpath = lastPageButton)
	private WebElement lastPageButtonWebElement;

	@FindBy(xpath = pagingInfo)
	private WebElement pagingInfoWebElement;

	@FindBy(xpath = errorMessage)
	private WebElement errorMessageWebElement;

	@FindBy(xpath = okButtonFromErrorPopup)
	private WebElement okButtonFromErrorPopupWebElement;

	@FindBy(xpath = renameButton)
	private WebElement renameButtonWebElement;

	@FindBy(xpath = editFolderName)
	private WebElement editFolderNameWebElement;

	@FindBy(xpath = renameFolderButton)
	private WebElement renameFolderButtonWebElement;

	@FindBy(xpath = didSelectionDropdown)
	private WebElement didSelectionDropdownWebElement;

	@FindBy(xpath = inboxTextFoldersSection)
	private WebElement inboxTextFoldersSectionWebElement;

	@FindBy(xpath = printMessageValidationMyAccount)
	private WebElement printMessageValidationMyAccountWebElement;

	@FindBy(xpath = defaultFirstFaxMessageCenter)
	private WebElement defaultFirstFaxMessageCenterWebElement;

	@FindBy(xpath = dateTimeLabelFromUploadedFileMessageCenter)
	private WebElement dateTimeLabelFromUploadedFileMessageCenterWebElement;

	@FindBy(xpath = viewFaxLinkInPreviewPane)
	private WebElement viewFaxLinkInPreviewPaneWebElement;

	@FindBy(xpath = noButtonInDeleteFolderPopUp)
	private WebElement noButtonInDeleteFolderPopUpWebElement;

	@FindBy(xpath = invalidMessage)
	private WebElement invalidMessageWebElement;

	@FindBy(xpath = contactsTab)
	private WebElement contactsTabWebElement;

	@FindBy(xpath = contactsList)
	private List<WebElement> contactsListWebElement;

	@FindBy(xpath = firstNameListFromTheContactsTab)
	private List<WebElement> firstNameListFromTheContactsTabWebElement;

	@FindBy(xpath = selectAllCheckboxOfContactsTab)
	private WebElement selectAllCheckboxOfContactsTabWebElement;

	@FindBy(xpath = deleteContactsButton)
	private WebElement deleteContactsButtonWebElement;

	@FindBy(xpath = yesButtonFromTheConfirmDeleteContactsDialog)
	private WebElement yesButtonFromTheConfirmDeleteContactsDialogWebElement;

	@FindBy(xpath = okButtonFromTheDeleteContactsDialog)
	private WebElement okButtonFromTheDeleteContactsDialogWebElement;

	@FindBy(xpath = importButton)
	private WebElement importButtonWebElement;

	@FindBy(xpath = uploadCSVFileButton)
	private WebElement uploadCSVFileButtonWebElement;

	@FindBy(xpath = importButtonFromTheImportCSVFileOverlay)
	private WebElement importButtonFromTheImportCSVFileOverlayWebElement;


	public WebElement checkFolderExists(String folderName) {
		String locator = "//span[contains(text(),'" + folderName + "')]";
		WebElement element = driver.findElement(By.xpath(locator));
		return element;
	}

	public void clickNewFolderButton() {
		createNewFolderButtonWebElement.click();
	}

	public void clickCreateFolderButton() {
		createFolderButtonWebElement.click();
	}

	public void clickCancelFolderButton() {
		cancelFolderButtonWebElement.click();
	}

	public void enterNewFolderName(String name) {
		createNewFolderNameWebElement.sendKeys(name);
	}

	public WebElement selectFolder(String folderName) {
		String locator = "//span[contains(text(),'" + folderName + "')]";
		WebElement element = null;
		try {
			element = driver.findElement(By.xpath(locator));
				element.click();
		} catch (Exception e) {
				@SuppressWarnings("unused")
			WebElement elementCatch = driver.findElement(By.xpath(locator));
		}
		return element;
	}

	public void clickNextMessageButton() {
		nextMessageButtonWebElement.click();
	}

	public String messageTitle() {
		String title;
		title = messageTitleWebElement.getText();
		return title;
	}

	public String invalidMessage() {
		String invalidMessage;
		invalidMessage = invalidMessageWebElement.getText();
		return invalidMessage;
	}

	public void clickPreviousMessageButton() {
		previousMessageButtonWebElement.click();
	}

	public void pageNumberSearch(String pageNumber) throws Exception {
		pageNumberTextboxWebElement.click();
		pageNumberTextboxWebElement.clear();
		pageNumberTextboxWebElement.sendKeys(pageNumber);
		this.keyBoardClickInteraction(KeyEvent.VK_ENTER);
	}

	public void clickNextPageButton() throws Exception {
		this.clickUsingJavaScript(nextPageButtonWebElement);
	}

	public void clickPreviousPageButton() {
		this.clickUsingJavaScript(previousPageButtonWebElement);
	}

	public void clickFirstPageButton() {
		this.clickUsingJavaScript(firstPageButtonWebElement);
	}

	public void clickLastPageButton() {
		this.clickUsingJavaScript(lastPageButtonWebElement);
	}

	public String pagingInfo() {
		String pagingInfo;
		pagingInfo = pagingInfoWebElement.getText();
		return pagingInfo;
	}

	public void clickRenameFolderButton() {
		renameFolderButtonWebElement.click();
	}

	public void enterEditFolderName(String name) {
		editFolderNameWebElement.sendKeys(name);
	}

	public void clickRenameButton() {
		this.clickUsingJavaScript(renameButtonWebElement);
	}

	public void clickOkButtonFromErrorPopup() {
		okButtonFromErrorPopupWebElement.click();
	}

	public String errorMessage() {
		String errorMessage;
		errorMessage = errorMessageWebElement.getText();
		return errorMessage;
	}

	public void clickDIDSelectionDropdown() {
		didSelectionDropdownWebElement.click();
	}

	public void selectDID(String DID) throws Exception {
		this.clickDIDSelectionDropdown();
		String locator = ".//div[@class='x-combo-list-item' and text()='" + DID + "']";
		try {
			WebElement element = driver.findElement(By.xpath(locator));
				element.click();
		} catch (Exception e) {
				@SuppressWarnings("unused")
			WebElement element = driver.findElement(By.xpath(locator));
		}
	}

	public int returnMessageCountOfSelectedFolder(String folderName) throws Exception {
		String folder = this.selectFolder(folderName).getText();
		String[] arrOfStr0 = folder.split(" ");
		String firstReplace = arrOfStr0[1].replace('(', ' ');
		String secondReplace = firstReplace.replace(')', ' ');
		int count = Integer.parseInt(secondReplace.trim());
		return count;
	}

	public void clickYesButtonInDeleteFolderPopup() {
		yesButtonInDeleteFolderPopUpWebElement.click();
	}

	public void clickNoButtonInDeleteFolderPopup() {
		noButtonInDeleteFolderPopUpWebElement.click();
	}

	public void clickDeleteFolderButton() {
		deleteFolderButtonWebElement.click();
	}

	public void clickOnReceivedFaxFromMessageCenter(String accountNumber) {
		String locator = ".//div[contains(@class,'rowunread')]//td/div[contains(text(),'" + accountNumber
		+ "') and contains( @class,'Subject')]";
		try {
			WebElement element = driver.findElement(By.xpath(locator));
			element.click();
		} catch (Exception e) {
				@SuppressWarnings("unused")
			WebElement element = driver.findElement(By.xpath(locator));
		}
	}

	public void clickOnDefaultFirstFaxFromMessageCenter() {
		defaultFirstFaxMessageCenterWebElement.click();
	}

	public void selectNewlyCreatedFolderFromMoveDropDown(String folderName) {
		String locator = ".//a[text()='" + folderName + "']";
		try {
			WebElement element = driver.findElement(By.xpath(locator));
			element.click();
		} catch (Exception e) {
				@SuppressWarnings("unused")
			WebElement element = driver.findElement(By.xpath(locator));
		}
	}

	public WebElement checkReceiverFaxVisibleInMessageCenter(String accountNumber) {
		String locator = ".//div[contains(@class,'rowunread')]//td/div[contains(text(),'" + accountNumber + "') and contains( @class,'Subject')]";
		WebElement element = driver.findElement(By.xpath(locator));
		return element;
	}

	public String getCurrentDate(String dateFormat) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String strDate = sdf.format(date);
		return strDate;
	}

	public String getDateFormatFromUploadedFile() {
		String[] dateAndTime = dateTimeLabelFromUploadedFileMessageCenterWebElement.getText().split(" ");
		String dateVerify = dateAndTime[0];
		return dateVerify;
	}

	public WebElement receivedMessageInMessageCenter(String messageName) {
		String locator = ".//div[@id='INBOXFolder']//div[contains(text(),'" + messageName + "')]";
		WebElement element = driver.findElement(By.xpath(locator));
		return element;
	}

	public WebElement newTabMessageCenter(String messageName) {
		String locator = ".//li[@id='messageTabs__contactsFolder']//following-sibling::li//span[contains(text(),'"
		+ messageName + "')]";
		WebElement element = driver.findElement(By.xpath(locator));
		return element;
	}

	public void clickViewFaxLinkInPreviewPane() {
		viewFaxLinkInPreviewPaneWebElement.click();
	}

	public void clickContactsTab() {
		contactsTabWebElement.click();
	}

	public void clickSelectAllCheckboxOfContactsTab() {
		selectAllCheckboxOfContactsTabWebElement.click();
	}

	public WebElement previewIconOnMessage(String messageName) {
		String locator = ".//div[@id='INBOXFolder']//div[contains(text(),'" + messageName
		+ "')]/..//preceding-sibling::td[2]//div[@class='signature-button' and (contains(@onmouseover,'showThumbnail'))]";
		WebElement element = driver.findElement(By.xpath(locator));
		return element;
	}

	public void clickDeleteContactsButton() {
		deleteContactsButtonWebElement.click();
	}

	public void clickYesButtonFromTheConfirmDeleteContactsDialog() {
		yesButtonFromTheConfirmDeleteContactsDialogWebElement.click();
	}

	public void clickOkButtonFromTheDeleteContactsDialog() {
		okButtonFromTheDeleteContactsDialogWebElement.click();
	}

	public void clickImportButton() {
		importButtonWebElement.click();
	}

	public void createRenameDeleteFolderFromMenuListOptions(String option, WebElement element) throws Exception {
		String operation = null;
		if (option.contains("New Folder")) {
			operation = "New Folder";
		} else if (option.contains("Rename Folder")) {
			operation = "Rename Folder";
		} else if (option.contains("Delete Folder")) {
			operation = "Delete Folder";
		}
		this.rightClickAction(element);
		String locator = "//a[contains(text(),'" + operation + "')]";
		try {
			driver.findElement(By.xpath(locator)).click();
			} catch (Exception e) {
				@SuppressWarnings("unused")
			WebElement elementCatch = driver.findElement(By.xpath(locator));
		}
	}

	public void deleteAllContacts() throws Exception {
		if (contactsListWebElement.size() > 0) {
			clickSelectAllCheckboxOfContactsTab();
			clickDeleteContactsButton();
			clickYesButtonFromTheConfirmDeleteContactsDialog();
			clickOkButtonFromTheDeleteContactsDialog();
		}
	}

	public boolean verfiyContactFirstName(String firstName) {
		boolean result = false;
		if (firstNameListFromTheContactsTabWebElement.size() > 0) {
			List<WebElement> firstNameListFromTheContactsTabList = firstNameListFromTheContactsTabWebElement;
			for (WebElement element : firstNameListFromTheContactsTabList) {
		if (element.getText().equals(firstName)) {
			return true;
		}
			}

		}
		return result;
	}

	public void selectImportOption(String option) {
		clickImportButton();
		String locator = "//a[text()='" + option + "']";
		try {
			WebElement element = driver.findElement(By.xpath(locator));
				element.click();
		} catch (Exception e) {
				@SuppressWarnings("unused")
			WebElement element = driver.findElement(By.xpath(locator));
		}
	}

	public void uploadCSVFileButton(String fileName) {
		uploadCSVFileButtonWebElement.sendKeys(fileName);
	}

	public void clickImportButtonFromTheImportCSVFileOverlay() {
		importButtonFromTheImportCSVFileOverlayWebElement.click();
	}

	public void uploadFileImportContactCSV(String fileName) throws Exception {
		uploadCSVFileButton(fileName);
		clickImportButtonFromTheImportCSVFileOverlay();
	}

	public void switchWindow(Set<String> handles) {
		String winHandle = handles.iterator().next();
		if (winHandle != firstWinHandle) {
			secondWinHandle = winHandle;
		}
		driver.switchTo().window(secondWinHandle);
	}
	
	private WebElement getreceivedfax(String from) {	
		wait.until(ExpectedConditions.elementToBeClickable(inbox));
		wait.until(ExpectedConditions.invisibilityOf(TLDriverFactory.getTLDriver().findElement(By.xpath("//div[text()='Loading...']"))));
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
			Thread.sleep(10000);
			inbox.click();
			fax = getreceivedfax(from);
		} 
		if (fax != null) {
			printreceivedfax(fax);
			return true;
		}
		else
			return false;
	}

	public void printreceivedfax(WebElement fax) {
		fax.findElements(By.tagName("td")).stream().map(i -> i.getAttribute("innerText")).forEach(logger::info);
	}
}