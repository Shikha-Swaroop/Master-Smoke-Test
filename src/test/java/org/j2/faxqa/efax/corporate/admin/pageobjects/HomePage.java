package org.j2.faxqa.efax.corporate.admin.pageobjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

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

	static final String logoutButton = "icon_LogoutCircle";
	static final String addWidgetButton = "addWidgets";
	static final String languageSelectDialog = "changeLanguage";
	static final String germanLanguageOption = "//*[@id='languageSelectDialog']/div[2]/div/div[4]/div[2]/button";
	static final String frenchLanguageOption = "//*[@id='languageSelectDialog']/div[2]/div/div[4]/div[3]/button";
	static final String assignedFaxNumber_FaxNumberSummaryWidget = "//div[@class='widget-fax-number-summary-col-1']//div[1]";
	static final String unassignedFaxNumber_FaxNumberSummaryWidget = "//div[@class='widget-fax-number-summary-col-1']//div[2]";
	static final String totalFaxNumber_FaxNumberSummaryWidget = "//div[@class='widget-fax-number-summary-col-1']//div[3]";
	static final String assignedFaxNumberColor_FaxNumberSummaryWidget = "/html[1]/body[1]/div[1]/div[2]/div[1]/ul[1]/li[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/span[1]";
	static final String unassignedFaxNumberColor_FaxNumberSummaryWidget = "/html[1]/body[1]/div[1]/div[2]/div[1]/ul[1]/li[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/span[1]";
	static final String widgetCloseButton = "//div[contains(@class,'widget-delete-btn')]";
	static final String widgetTitle = "//div[@class='widget-title']";
	static final String groups_UserSummaryWidget = "/html[1]/body[1]/div[1]/div[2]/div[1]/ul[1]/li[1]/div[1]/div[2]/div[2]/div[1]/div[1]/p[1]";
	static final String users_UserSummaryWidget = "/html[1]/body[1]/div[1]/div[2]/div[1]/ul[1]/li[1]/div[1]/div[2]/div[2]/div[1]/div[1]/p[3]";
	static final String dashboardPageTitle = "/html[1]/body[1]/nav[1]/div[2]/div[1]/ul[1]/li[1]/a[1]/span[2]";
	static final String profileNameHomePage = "//div[@class='profile-name']";
	static final String closeIconListRecentActivityWidgets = "//div[contains(text(),'Recent Activity')]/ancestor::li//div[contains(@class,'widget-delete-btn')]";
	static final String recentActivityGroupNameTitle = "//div[@class='widget-title' and contains(text(),'Recent Activity')]/ancestor::div[@class='box']//h3[contains(@id,'groupTitle')]";
	
	@FindBy(id = addWidgetButton)
	private WebElement addWidgetButtonWebElement;

	@FindBy(id = languageSelectDialog)
	private WebElement languageSelectDialogWebElement;

	@FindBy(xpath = germanLanguageOption)
	private WebElement germanLanguageOptionWebElement;

	@FindBy(xpath = frenchLanguageOption)
	private WebElement frenchLanguageOptionWebElement;

	@FindBy(xpath = assignedFaxNumber_FaxNumberSummaryWidget)
	private WebElement assignedFaxNumber_FaxNumberSummaryWidgetWebElement;

	@FindBy(xpath = unassignedFaxNumber_FaxNumberSummaryWidget)
	private WebElement unassignedFaxNumber_FaxNumberSummaryWidgetWebElement;

	@FindBy(xpath = totalFaxNumber_FaxNumberSummaryWidget)
	private WebElement totalFaxNumber_FaxNumberSummaryWidgetWebElement;

	@FindBy(xpath = assignedFaxNumberColor_FaxNumberSummaryWidget)
	private WebElement assignedFaxNumberColor_FaxNumberSummaryWidgetWebElement;

	@FindBy(xpath = unassignedFaxNumberColor_FaxNumberSummaryWidget)
	private WebElement unassignedFaxNumberColor_FaxNumberSummaryWidgetWebElement;

	@FindBy(xpath = widgetCloseButton)
	private List<WebElement> widgetCloseButtonWebElement;

	@FindBy(xpath = widgetTitle)
	private WebElement widgetTitleWebElement;

	@FindBy(xpath = groups_UserSummaryWidget)
	private WebElement groups_UserSummaryWidgetWebElement;

	@FindBy(xpath = users_UserSummaryWidget)
	private WebElement users_UserSummaryWidgetWebElement;

	@FindBy(xpath = dashboardPageTitle)
	private WebElement dashboardPageTitleWebElement;

	@FindBy(xpath = profileNameHomePage)
	private WebElement profileNameHomePageWebElement;

	@FindBy(xpath = closeIconListRecentActivityWidgets)
	private List<WebElement> closeIconListRecentActivityWidgetsWebElement;

	@FindBy(xpath = recentActivityGroupNameTitle)
	private WebElement recentActivityGroupNameTitleWebElement;

	public void clickAddWidgetButton() {
		
		addWidgetButtonWebElement.click();
	}

	public void hoverMouseOverAddWidgetButton() {
		this.mouseHoverOn(addWidgetButtonWebElement);
	}

	public String widgetTitle() {
		String widgetTitle;
		widgetTitle = widgetTitleWebElement.getText();
		return widgetTitle;
	}

	public void switchSiteLanguageToGerman() throws Exception {
		languageSelectDialogWebElement.click();
		germanLanguageOptionWebElement.click();
	}

	public void switchSiteLanguageToFrench() throws Exception {
		languageSelectDialogWebElement.click();
		frenchLanguageOptionWebElement.click();
	}

	public int assignedFaxNumberCount_FaxNumberSummaryWidget() throws Exception {
		String value = assignedFaxNumber_FaxNumberSummaryWidgetWebElement.getText();
		String[] result = new String[1];
		result = value.split(": ");
		String actualValue = result[1];
		int assignedFaxNumberCount = Integer.parseInt(actualValue);
		return assignedFaxNumberCount;
	}

	public int unassignedFaxNumberCount_FaxNumberSummaryWidget() throws Exception {
		String value = unassignedFaxNumber_FaxNumberSummaryWidgetWebElement.getText();
		String[] result = new String[1];
		result = value.split(": ");
		String actualValue = result[1];
		int unassignedFaxNumberCount = Integer.parseInt(actualValue);
		return unassignedFaxNumberCount;
	}

	public int totalFaxNumber_FaxNumberSummaryWidget() throws Exception {
		String value = totalFaxNumber_FaxNumberSummaryWidgetWebElement.getText();
		String[] result = new String[1];
		result = value.split(": ");
		String actualValue = result[1];
		int totalFaxNumber = Integer.parseInt(actualValue);
		return totalFaxNumber;
	}

	public String assignedFaxNumberColor_FaxNumberSummaryWidget() throws Exception {
		String value = assignedFaxNumberColor_FaxNumberSummaryWidgetWebElement.getAttribute("style");
		String[] result1 = new String[1];
		result1 = value.split(": ");
		String actualValue1 = result1[1];
		String[] result2 = new String[1];
		result2 = actualValue1.split(";");
		String assignedFaxNumberColor = result2[0];
		return assignedFaxNumberColor;
	}

	public String unassignedFaxNumberColor_FaxNumberSummaryWidget() throws Exception {
		String value = unassignedFaxNumberColor_FaxNumberSummaryWidgetWebElement.getAttribute("style");
		String[] result1 = new String[1];
		result1 = value.split(": ");
		String actualValue1 = result1[1];
		String[] result2 = new String[1];
		result2 = actualValue1.split(";");
		String unassignedFaxNumberColor = result2[0];
		return unassignedFaxNumberColor;
	}

	public int usersCount_UserSummaryWidget() throws Exception {
		String value = users_UserSummaryWidgetWebElement.getText();
		String[] result1 = new String[1];
		result1 = value.split(": ");
		String users = result1[1];
		return Integer.parseInt(users);
	}

	public int groupsCount_UserSummaryWidget() throws Exception {
		String value = groups_UserSummaryWidgetWebElement.getText();
		String[] result1 = new String[1];
		result1 = value.split(": ");
		String groups = result1[1];
		return Integer.parseInt(groups);
	}


	public String getProfileName() {
		String name = profileNameHomePageWebElement.getText();
		return name;
	}

	public void removeActivityWidgets() {
		int time = 3000;
		List<WebElement> divButton = closeIconListRecentActivityWidgetsWebElement;
		if (divButton.size() > 0) {
			for (int i = 0; i < divButton.size(); i++) {
				divButton.get(i).click();
				driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
			}
		}
	}

	public String getTitleRecentActivity() {
		String title = recentActivityGroupNameTitleWebElement.getText();
		return title;
	}

}