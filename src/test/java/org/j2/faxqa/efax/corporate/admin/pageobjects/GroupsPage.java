package org.j2.faxqa.efax.corporate.admin.pageobjects;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GroupsPage extends NavigationBar {
	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public GroupsPage() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}

	static final String myAccountEditButton = "groups-edit-btn-text";
	static final String lnkchildGroup = "(//div[@class='groups-edit-btn'])[2]";
	static final String lnkSubGroup = "(.//div[@class='groups-edit-btn']/..)[3]";
	static final String myAccountChildrenGroups = "//a[contains(text(),'My Account')]//following-sibling::ul[contains(@class,'jstree-children')]//li[contains(@class,'jstree-node')]//a[contains(@class,'jstree-anchor')]";
	static final String users = "/html[1]/body[1]/div[1]/div[2]/div[2]/div[10]/div[2]";
	static final String groups = "/html[1]/body[1]/div[1]/div[2]/div[2]/div[11]/div[2]";
	
	@FindBy(className = myAccountEditButton)
	private WebElement myAccountEditButtonWebElement;

	@FindBy(xpath = lnkchildGroup)
	private WebElement lnkchildGroupWebElement;

	@FindBy(xpath = lnkSubGroup)
	private WebElement lnkSubGroupWebElement;

	@FindBy(xpath = users)
	private WebElement usersWebElement;

	@FindBy(xpath = groups)
	private WebElement groupsWebElement;


	public void clickMyAccountEditButton() {
		
		myAccountEditButtonWebElement.click();
	}

	public void clickEditChildGroupLink() {
		this.scrollToTheSpecificWebelement(lnkchildGroupWebElement);
		lnkchildGroupWebElement.click();
	}

	public String getSubGroupName() {
		this.scrollToTheSpecificWebelement(lnkSubGroupWebElement);
		return lnkSubGroupWebElement.getText().replace("Edit", "").trim();
	}

	public List<WebElement> getMyAccountChildrenGrpList() {
		List<WebElement> elements = driver.findElements(By.xpath(myAccountChildrenGroups));
		return elements;
	}

	public boolean checkChildGrpUnderMyAccount(String childGrpName) {
		Boolean IsChild = false;
		List<WebElement> options = getMyAccountChildrenGrpList();
		if (options.size() == 0) {
			return false;
		}
		for (int i = 0; i < options.size(); i++) {
			if (options.get(i).getText().replace("Edit", "").trim().equalsIgnoreCase(childGrpName)) {
				IsChild = true;
			}
		}
		return IsChild;
	}

	public WebElement groupName(String groupName) {
		WebElement element = null;
		String locator = ".//a[text()='" + groupName + "']";
		try {
			element = driver.findElement(By.xpath(".//a[text()='" + groupName + "']"));
		} catch (Exception e) {
			WebElement element_catch = driver.findElement(By.xpath(".//a[text()='" + groupName + "']"));
		}
		return element;
	}

	public void editGroupByName(String groupName) throws Exception {
		String locator = "//a[contains(text(),'" + groupName + "')]/div";
		try {
			WebElement element = driver.findElement((By.xpath(locator)));
			this.scrollToTheSpecificWebelement(element);
			element.click();
		} catch (Exception e) {
			@SuppressWarnings("unused")
			WebElement element = driver.findElement((By.xpath(locator)));
		}
	}

	public int groupsCount() throws Exception {
		String groups = groupsWebElement.getText();
		return Integer.parseInt(groups);
	}

	public int usersCount() throws Exception {
		String users = usersWebElement.getText();
		return Integer.parseInt(users);
	}
}