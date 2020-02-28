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

public class AddGroupPage extends BasePage {

	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public AddGroupPage() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}

	static final String btnAddGroup = "//button[@class='button blue']//div[contains(text(),'Add Group')]";
	static final String groupRequiredToggleCheckbox = "billingCodeToggle";
	static final String groupRequiredToggleBtn = "//small[contains(text(),'Billing Code Required')]//ancestor::div[@class='field']//div[@class='onoffswitch']";
	static final String groupPoolRequiredCheckbox = "maxNumberToggle";
	static final String groupPoolRequiredToggleBtn = "//label[contains(text(),'Private Fax Number Pool')]//parent::div//div[@class='onoffswitch']";
	static final String txtGroupName = "//input[@id='groupName']";
	static final String btnCreateGroup = "//div[@class='add-user-bottom-buttons']//button[@class='create-user-btn']";
	static final String groupSuccessValidationMessage = "successStatusMsg";
	static final String dropEditParentGroupDiv = "//div[contains(@class,'edit-groups-tree-view-holder')]";
	static final String createGroupParentGroupInput = "//form[@id='saveNewGroup']//input[@placeholder='My Account']";
	static final String privateFaxNumberPoolToggle = "//div[@id='privateFaxNumPoolOnOffSwitch']//span[@class='onoffswitch-switch']";
	static final String privateFaxNumberPoolField = "//input[@id='maxPoolNumber']";
	static final String privateFaxNumberPoolFieldValidationMessage = "//form[@id='saveNewGroup']//span[@class='warning']";

	@FindBy(xpath = btnAddGroup)
	private WebElement btnAddGroupWebElement;

	@FindBy(id = groupRequiredToggleCheckbox)
	private WebElement groupRequiredToggleCheckboxWebElement;

	@FindBy(xpath = groupRequiredToggleBtn)
	private WebElement groupRequiredToggleBtnWebElement;

	@FindBy(id = groupPoolRequiredCheckbox)
	private WebElement groupPoolRequiredCheckboxWebElement;

	@FindBy(xpath = groupPoolRequiredToggleBtn)
	private WebElement groupPoolRequiredToggleBtnWebElement;

	@FindBy(xpath = txtGroupName)
	private WebElement txtGroupNameWebElement;

	@FindBy(xpath = btnCreateGroup)
	private WebElement btnCreateGroupWebElement;

	@FindBy(id = groupSuccessValidationMessage)
	private WebElement groupSuccessValidationMessageWebElement;

	@FindBy(xpath = dropEditParentGroupDiv)
	private WebElement dropEditParentGroupDivWebElement;

	@FindBy(xpath = createGroupParentGroupInput)
	private WebElement createGroupParentGroupInputWebElement;

	@FindBy(xpath = privateFaxNumberPoolToggle)
	private WebElement privateFaxNumberPoolToggleWebElement;

	@FindBy(xpath = privateFaxNumberPoolField)
	private WebElement privateFaxNumberPoolFieldWebElement;

	@FindBy(xpath = privateFaxNumberPoolFieldValidationMessage)
	private WebElement privateFaxNumberPoolFieldValidationMessageWebElement;

	public void clickGetBtnAddGroup() {
		btnAddGroupWebElement.click();
	}

	public void toggleBillingCodeRequiredBtn(boolean flag) throws Exception {
		if (!groupRequiredToggleCheckboxWebElement.isSelected() && flag) {
			logger.info("Enabling Billing code required.");
			groupRequiredToggleCheckboxWebElement.click();
		} else if (groupRequiredToggleCheckboxWebElement.isSelected() && !flag) {
			logger.info("Disabling Billing code required.");
			groupRequiredToggleCheckboxWebElement.click();
		}
	}

	public void togglePrivatePoolRequiredBtn(boolean flag) throws Exception {
		if (!groupPoolRequiredCheckboxWebElement.isSelected() && flag) {
			logger.info("Enabling PrivatePoolRequired.");
			groupPoolRequiredCheckboxWebElement.click();
		} else if (groupPoolRequiredCheckboxWebElement.isSelected() && !flag) {
			logger.info("Disabling PrivatePoolRequired.");
			groupPoolRequiredCheckboxWebElement.click();
		}
	}

	public void enterGroupName(String name) {
		// Reporter.verifyElementToBeClickableOnPage(getTxtGroupName(),
		// AddGroupPageLocators.txtGroupName);
		txtGroupNameWebElement.sendKeys(name);
	}

	public void clickGetBtnCreateGroup() {
		scrollToTheSpecificWebelement(btnCreateGroupWebElement);
		btnCreateGroupWebElement.click();
		logger.info("Creating group.");
	}

	public String getSuccessGroupValidationMessage() {
		// Reporter.verifyElementToBeVisibleOnPage(getGroupSuccessValidationMessage(),
		// AddGroupPageLocators.groupSuccessValidationMessage);
		System.out.println(groupSuccessValidationMessageWebElement.getText());
		logger.info(groupSuccessValidationMessageWebElement.getText());
		return groupSuccessValidationMessageWebElement.getText();
	}

	public void clickDropEditParentGroupDiv() {
		// Reporter.verifyElementToBeVisibleOnPage(getDropEditParentGroupDiv(),
		// AddGroupPageLocators.dropEditParentGroupDiv);
		dropEditParentGroupDivWebElement.click();
	}

	public void clickChildSelectedSubGroup(String SubGrpName) {
		String locator = "//div[contains(@class,'edit-groups-tree-view-holder')]//ul//a[contains(text(),'" + SubGrpName
				+ "')]";
		try {
			WebElement element = driver.findElement(
					By.xpath("//div[contains(@class,'edit-groups-tree-view-holder')]//ul//a[contains(text(),'"
							+ SubGrpName + "')]"));
			// Reporter.elementIsAvailableOnWebPage(locator);
			scrollToTheSpecificWebelement(element);
			element.click();
		} catch (Exception e) {
			// Reporter.elementIsNotAvailableOnWebPage(locator);
			@SuppressWarnings("unused")
			WebElement element = driver.findElement(
					By.xpath("//div[contains(@class,'edit-groups-tree-view-holder')]//ul//a[contains(text(),'"
							+ SubGrpName + "')]"));
		}
	}

	public void clickParentGroupDropdown() {
		// Reporter.verifyElementToBeClickableOnPage(getCreateGroupParentGroupInput(),
		// AddGroupPageLocators.createGroupParentGroupInput);
		createGroupParentGroupInputWebElement.click();
	}

	public void enterParentGroupName(String parentGroupName) {
		txtGroupNameWebElement.clear();
		txtGroupNameWebElement.sendKeys(parentGroupName);
	}

	public void clickmainGrpName(String groupName) {
		String locator = "//a[contains(.,'" + groupName + "')]";
		try {
			WebElement element = driver.findElement(By.xpath("//a[contains(.,'" + groupName + "')]"));
			// Reporter.elementIsAvailableOnWebPage(locator);
			this.scrollToTheSpecificWebelement(element);
			element.click();
		} catch (Exception e) {
			// Reporter.elementIsNotAvailableOnWebPage(locator);
			@SuppressWarnings("unused")
			WebElement element = driver.findElement(By.xpath("//a[contains(.,'" + groupName + "')]"));
		}
	}

	public void clickPrivateFaxNumberPoolToggle() {
		// Reporter.verifyElementToBeClickableOnPage(getPrivateFaxNumberPoolToggle(),
		// AddGroupPageLocators.privateFaxNumberPoolToggle);
		privateFaxNumberPoolToggleWebElement.click();
	}

	public void enterValueIntoPrivateFaxNumberPoolField(String value) {
		// Reporter.verifyElementToBeVisibleOnPage(getPrivateFaxNumberPoolField(),
		// AddGroupPageLocators.privateFaxNumberPoolField);
		privateFaxNumberPoolFieldWebElement.sendKeys(value);
	}

	public String PrivateFaxNumberPoolFieldValidationMessage() {
		// Reporter.verifyElementToBeVisibleOnPage(getPrivateFaxNumberPoolFieldValidationMessage(),
		// AddGroupPageLocators.privateFaxNumberPoolFieldValidationMessage);
		String message = privateFaxNumberPoolFieldValidationMessageWebElement.getText();
		return message;
	}

	public void enterNewGroupName(String groupname) {
		clickGetBtnAddGroup();
		enterGroupName(groupname);
		logger.info("Entering wew group name as " + groupname);
	}
}