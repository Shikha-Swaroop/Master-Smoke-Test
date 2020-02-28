package org.j2.faxqa.efax.corporate.admin.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.j2.faxqa.efax.common.TLDriverFactory;
//import java.awt.event.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

public class FaxNumbersPage extends NavigationBar {

	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public FaxNumbersPage() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}

	static final String totalUnassignedFaxNumbers = "totalUnassigned";
	static final String totalAssignedFaxNumbers = "totalAssigned";
	static final String totalAllFaxNumbers = "totalAll";
	static final String searchMenu = "//div[@class='numbers-search-menu']";
	static final String searchByField = "numbersSearchField-button";
	static final String searchByFaxNumber = "//ul[@id='numbersSearchField-menu']//li[contains(text(),'Fax number')]";
	static final String searchByValueTextbox = "searchText";
	static final String searchButton = "//div[@class='btn-text'][contains(text(),'Search')]";
	static final String pageActionsDropdown = ".//span[@id='pa-select-1-button']/span[1]";
	static final String addFaxNumbersIcon = "//ul[@id='pa-select-1-menu']/li//span[contains(@class,'add-numbers-icon')]";
	static final String allTab = "//li[@id='nav-tab-all']//a[1]";
	static final String unassignedTab = "//li[@id='nav-tab-unassigned']//a[1]";
	static final String assignedTab = "//li[@id='nav-tab-assigned']//a[1]";
	static final String moveOption = "/html[1]/body[1]/div[12]/ul[1]/li[3]";
	static final String assignOption = "/html[1]/body[1]/div[12]/ul[1]/li[4]";
	static final String unassignOption = "/html[1]/body[1]/div[12]/ul[1]/li[5]";
	static final String errorMessageForAssignOption = "//div[@id='All']//div[@class='errorMessage off']//div[1]";
	static final String unassignedfaxes = "//div[@id='numbers-table-unassigned']/table/tbody";
	static final String assignedfaxes = "//div[@id='numbers-table-assigned']/table/tbody";
	static final String allfaxes = "//div[@id='numbers-table-all']/table/tbody";
	static final String enterUserSearchTerm = "//input[@placeholder='Enter user search term']";
	static final String assignFaxNumbersButton = "//div[@id='All']//div[@class='btn-text'][contains(text(),'Assign Fax Numbers')]";
	static final String succeededPopup = "//div[@id='modal-move-users-response-body']";
	static final String closeButtonFromSucceededPopup = "//button[contains(text(),'Close')]";
	static final String okButtonFromUnassignFaxNumbersPopup = "//button[contains(text(),'OK')]";
	static final String firstGroupFromMoveFaxNumberPopup = "//div[@class='tree-view-tray modal-move-users-group-tray jstree jstree-3 jstree-default']//a[@tabindex='-1']";
	static final String moveButtonFromMoveFaxNumberPopup = "//button[@id='moveNumbersAcceptBtn']";
	static final String faxNumbersPageTitle = "/html[1]/body[1]/nav[1]/div[2]/div[1]/ul[1]/li[5]/a[1]/span[2]";
	static final String searchSubGroupsCheckbox = "/html[1]/body[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[3]/input[1]";
	static final String selectGroup = "/html[1]/body[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/div[1]/div[1]";
	static final String searchBy = "/html[1]/body[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[4]/span[1]/span[1]";
	static final String searchOperator = "/html[1]/body[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[5]/span[1]/span[1]";
	static final String firstFaxNumberFromTheSearchResult = "/html[1]/body[1]/div[2]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[7]/table[1]/tbody[1]/tr[1]/td[2]/span[1]";
	static final String firstLocationFromTheSearchResult = "/html[1]/body[1]/div[2]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[7]/table[1]/tbody[1]/tr[1]/td[3]/span[1]";
	static final String firstUserNameFromTheSearchResult = "/html[1]/body[1]/div[2]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[7]/table[1]/tbody[1]/tr[1]/td[5]/form[1]/span[1]";
	
	@FindBy(id = totalUnassignedFaxNumbers)
	private WebElement totalUnassignedFaxNumbersWebElement;

	@FindBy(xpath = searchMenu)
	private WebElement searchMenuWebElement;

	@FindBy(id = searchByField)
	private WebElement searchByFieldWebElement;

	@FindBy(xpath = searchByFaxNumber)
	private WebElement searchByFaxNumberWebElement;

	@FindBy(id = searchByValueTextbox)
	private WebElement searchByValueTextboxWebElement;

	@FindBy(xpath = searchButton)
	private WebElement searchButtonWebElement;

	@FindBy(xpath = pageActionsDropdown)
	private WebElement pageActionsDropdownWebElement;

	@FindBy(xpath = addFaxNumbersIcon)
	private WebElement addFaxNumbersIconWebElement;

	@FindBy(xpath = allTab)
	private WebElement allTabWebElement;

	@FindBy(xpath = unassignedTab)
	private WebElement unassignedTabWebElement;

	@FindBy(xpath = assignedTab)
	private WebElement assignedTabWebElement;

	@FindBy(xpath = unassignedfaxes)
	private WebElement unassignedfaxesWebElement;

	@FindBy(xpath = assignedfaxes)
	private WebElement assignedfaxesWebElement;

	@FindBy(xpath = allfaxes)
	private WebElement allfaxesWebElement;
	
	@FindBy(xpath = moveOption)
	private WebElement moveOptionWebElement;

	@FindBy(xpath = assignOption)
	private WebElement assignOptionWebElement;

	@FindBy(xpath = unassignOption)
	private WebElement unassignOptionWebElement;

	@FindBy(xpath = errorMessageForAssignOption)
	private WebElement errorMessageForAssignOptionWebElement;

	@FindBy(xpath = enterUserSearchTerm)
	private WebElement enterUserSearchTermWebElement;

	@FindBy(xpath = assignFaxNumbersButton)
	private WebElement assignFaxNumbersButtonWebElement;

	@FindBy(xpath = succeededPopup)
	private WebElement succeededPopupWebElement;

	@FindBy(xpath = closeButtonFromSucceededPopup)
	private WebElement closeButtonFromSucceededPopupWebElement;

	@FindBy(xpath = okButtonFromUnassignFaxNumbersPopup)
	private WebElement okButtonFromUnassignFaxNumbersPopupWebElement;

	@FindBy(xpath = firstGroupFromMoveFaxNumberPopup)
	private WebElement firstGroupFromMoveFaxNumberPopupWebElement;

	@FindBy(xpath = moveButtonFromMoveFaxNumberPopup)
	private WebElement moveButtonFromMoveFaxNumberPopupWebElement;

	@FindBy(id = totalAssignedFaxNumbers)
	private WebElement totalAssignedFaxNumbersWebElement;

	@FindBy(id = totalAllFaxNumbers)
	private WebElement totalAllFaxNumbersWebElement;

	@FindBy(xpath = faxNumbersPageTitle)
	private WebElement faxNumbersPageTitleWebElement;

	@FindBy(xpath = searchSubGroupsCheckbox)
	private WebElement searchSubGroupsCheckboxWebElement;

	@FindBy(xpath = selectGroup)
	private WebElement selectGroupWebElement;

	@FindBy(xpath = searchBy)
	private WebElement searchByWebElement;

	@FindBy(xpath = searchOperator)
	private WebElement searchOperatorWebElement;

	@FindBy(xpath = firstFaxNumberFromTheSearchResult)
	private WebElement firstFaxNumberFromTheSearchResultWebElement;

	@FindBy(xpath = firstLocationFromTheSearchResult)
	private WebElement firstLocationFromTheSearchResultWebElement;

	@FindBy(xpath = firstUserNameFromTheSearchResult)
	private WebElement firstUserNameFromTheSearchResultWebElement;

	public String totalAllFaxNumbers() {
		wait.until(ExpectedConditions.elementToBeClickable(totalAllFaxNumbersWebElement));
		String totalAllFaxNumbers = totalAllFaxNumbersWebElement.getText();
		return totalAllFaxNumbers;
	}

	public String totalAssignedFaxNumbers() {
		wait.until(ExpectedConditions.elementToBeClickable(totalAssignedFaxNumbersWebElement));
		String totalAssignedFaxNumbers = totalAssignedFaxNumbersWebElement.getText();
		return totalAssignedFaxNumbers;
	}

	public String totalUnassignedFaxNumbers() {
		wait.until(ExpectedConditions.elementToBeClickable(totalUnassignedFaxNumbersWebElement));
		String totalUnassignedFaxNumbers = totalUnassignedFaxNumbersWebElement.getText();
		return totalUnassignedFaxNumbers;
	}

	public String errorMessageForAssignOption() {
		wait.until(ExpectedConditions.elementToBeClickable(errorMessageForAssignOptionWebElement));
		String errorMessageForAssignOption = errorMessageForAssignOptionWebElement.getText();
		return errorMessageForAssignOption;
	}

	public void searchByFaxNumber(String faxNumber) throws Exception {
		
		searchMenuWebElement.click();
		searchByFieldWebElement.click();
		searchByFaxNumberWebElement.click();
		searchByValueTextboxWebElement.click();
		searchByValueTextboxWebElement.clear();
		searchByValueTextboxWebElement.sendKeys(faxNumber);
		searchButtonWebElement.click();
	}

	public void clickPageActionsDropdown() {
		wait.until(ExpectedConditions.elementToBeClickable(pageActionsDropdownWebElement));
		pageActionsDropdownWebElement.click();
	}

	public void clickFirstGroupFromMoveFaxNumberPopup() {
		wait.until(ExpectedConditions.elementToBeClickable(firstGroupFromMoveFaxNumberPopupWebElement));
		firstGroupFromMoveFaxNumberPopupWebElement.click();
	}

	public void clickMoveButtonFromMoveFaxNumberPopup() {
		wait.until(ExpectedConditions.elementToBeClickable(moveButtonFromMoveFaxNumberPopupWebElement));
		moveButtonFromMoveFaxNumberPopupWebElement.click();
	}

	public void clickOkButtonFromUnassignFaxNumbersPopup() {
		okButtonFromUnassignFaxNumbersPopupWebElement.click();
	}

	public void clickCloseButtonFromSucceededPopup() {
		wait.until(ExpectedConditions.elementToBeClickable(closeButtonFromSucceededPopupWebElement));
		closeButtonFromSucceededPopupWebElement.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='All']//div[@class='loading']/img")));
	}

	public void clickAssignFaxNumbersButton() throws Exception {
		this.doubleClickAction(assignFaxNumbersButtonWebElement);
		logger.info("Assigning FaxNumbers....");
	}

	public void clickAddFaxNumbersIcon() {
		this.doubleClickAction(addFaxNumbersIconWebElement);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='numbersGrid']//div/img[@alt='loading']")));
	}

	public WebElement getSucceededPopup() {
		wait.until(ExpectedConditions.elementToBeClickable(succeededPopupWebElement)); 
		return succeededPopupWebElement;
	}
	
	public void clickMoveOption() throws Exception {
		clickPageActionsDropdown();
		moveOptionWebElement.click();
		logger.info("Selecting Move option");
	}

	public void clickAssignOption() throws Exception {
		clickPageActionsDropdown();
		assignOptionWebElement.click();
		logger.info("Selecting Assign option");
	}

	public void clickUnassignOption() throws Exception {
		

		clickPageActionsDropdown();

		unassignOptionWebElement.click();
	}

	public void enterUserSearchTerm(String user) throws Exception {
		enterUserSearchTermWebElement.click();

		enterUserSearchTermWebElement.sendKeys(user);
		String locator = "/html[1]/body[1]/ul[1]/li[1]";
		try {
			WebElement element = driver.findElement(By.xpath(locator));
			element.click();
		} catch (Exception e) {
			@SuppressWarnings("unused")
			WebElement element = driver.findElement(By.xpath(locator));
		}
		// keyBoardClickInteraction(KeyEvent.VK_DOWN);
		// keyBoardClickInteraction(KeyEvent.VK_ENTER);
	}

	public void searchIncludeSubGroups(String searchBy, String operator, String searchValue) throws Exception {
		
		searchMenuWebElement.click();

		String searchByLocator = "//li[contains(text(),'" + searchBy + "')]";
		String operatorLocator = "//li[contains(text(),'" + operator + "')]";
		try {
			searchByWebElement.click();
			WebElement elementSearchBy = driver.findElement((By.xpath(searchByLocator)));
			elementSearchBy.click();
	
			searchOperatorWebElement.click();
			WebElement elementOperator = driver.findElement((By.xpath(operatorLocator)));
			elementOperator.click();
	
			searchByValueTextboxWebElement.click();
			searchByValueTextboxWebElement.clear();
			searchByValueTextboxWebElement.sendKeys(searchValue);
	
			searchButtonWebElement.click();
		} catch (Exception e) {
			@SuppressWarnings("unused")
			WebElement elementSearchBy = driver.findElement((By.xpath(searchByLocator)));
			@SuppressWarnings("unused")
			WebElement elementOperator = driver.findElement((By.xpath(operatorLocator)));
		}
	}

	public void searchExcludedSubGroups(String groupName, String searchBy, String operator, String searchValue)
			throws Exception {
		
		searchMenuWebElement.click();
		String groupNameLocator = "//div[@class='tree-view-tray numbers-select-group-tray jstree jstree-1 jstree-default']//a[contains(text(),'"
				+ groupName + "')]";
		String searchByLocator = "//li[contains(text(),'" + searchBy + "')]";
		String operatorLocator = "//li[contains(text(),'" + operator + "')]";
		try {
			selectGroupWebElement.click();
			WebElement elementGroupName = driver.findElement((By.xpath(groupNameLocator)));
			elementGroupName.click();
	
			searchSubGroupsCheckboxWebElement.click();
	
			searchByWebElement.click();
			WebElement elementSearchBy = driver.findElement((By.xpath(searchByLocator)));
			elementSearchBy.click();
	
			searchOperatorWebElement.click();
			WebElement elementOperator = driver.findElement((By.xpath(operatorLocator)));
			elementOperator.click();
	
			searchByValueTextboxWebElement.click();
			searchByValueTextboxWebElement.clear();
			searchByValueTextboxWebElement.sendKeys(searchValue);
	
			searchButtonWebElement.click();
		} catch (Exception e) {
			@SuppressWarnings("unused")
			WebElement elementGroupName = driver.findElement((By.xpath(groupNameLocator)));
			@SuppressWarnings("unused")
			WebElement elementSearchBy = driver.findElement((By.xpath(searchByLocator)));
			@SuppressWarnings("unused")
			WebElement elementOperator = driver.findElement((By.xpath(operatorLocator)));
		}
	}

	public void selectFirstFaxNumberFromTheAllTab() {
		allfaxesWebElement.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).get(0).click();
		logger.info("Selecting first one from the filtered results");
	}

	public void selectFirstFaxNumberFromTheUnassignedTab() {
		unassignedfaxesWebElement.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).get(0).click();
		logger.info("Selecting first one from the filtered results");
	}
	
	public void selectFirstFaxNumberFromTheAssignedTab() {
		assignedfaxesWebElement.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).get(0).click();
		logger.info("Selecting first one from the filtered results");
	}
	
	public WebElement getFirstFaxNumberFromTheAllTab() {
		return allfaxesWebElement.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).get(1);
	}

	public WebElement getFirstFaxNumberFromTheUnassignedTab() {
		return unassignedfaxesWebElement.findElements(By.tagName("tr")).get(new Faker().number().numberBetween(1, 50)).findElements(By.tagName("td")).get(1);
	}
	
	public WebElement getFirstFaxNumberFromTheAssignedTab() {
		return assignedfaxesWebElement.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).get(1);
	}

	public void gotoAllFaxNumbersTab() {
		wait.until(ExpectedConditions.elementToBeClickable(allTabWebElement));
		allTabWebElement.click();
		logger.info("Navigating to All faxnumbers screen.");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='All']//div[@class='loading']/img")));
	}

	public void gotoAssignedFaxNumbersTab() {
		wait.until(ExpectedConditions.elementToBeClickable(assignedTabWebElement));
		assignedTabWebElement.click();
		logger.info("Navigating to Assigned faxnumbers screen.");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='Assigned']//div[@class='loading']/img")));
	}
	
	public void gotoUnassignedFaxNumbers() {
		wait.until(ExpectedConditions.elementToBeClickable(unassignedTabWebElement));
		unassignedTabWebElement.click();
		logger.info("Navigating to Unassigned faxnumbers screen.");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='Unassigned']//div[@class='loading']/img")));
	}
}