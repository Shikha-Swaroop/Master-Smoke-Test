package org.j2.faxqa.efax.corporate.admin.tests;

import java.io.IOException;

import org.j2.faxqa.efax.common.BaseTest;
import org.j2.faxqa.efax.common.Config;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.j2.faxqa.efax.common.TestRail;
import org.j2.faxqa.efax.corporate.admin.CoreFaxFunctions;
import org.j2.faxqa.efax.corporate.admin.pageobjects.AddFaxNumbersOverlay;
import org.j2.faxqa.efax.corporate.admin.pageobjects.AddGroupPage;
import org.j2.faxqa.efax.corporate.admin.pageobjects.AddUser;
import org.j2.faxqa.efax.corporate.admin.pageobjects.EditUser;
import org.j2.faxqa.efax.corporate.admin.pageobjects.FaxNumbersPage;
import org.j2.faxqa.efax.corporate.admin.pageobjects.GroupDetailsPage;
import org.j2.faxqa.efax.corporate.admin.pageobjects.GroupsPage;
import org.j2.faxqa.efax.corporate.admin.pageobjects.HomePage;
import org.j2.faxqa.efax.corporate.admin.pageobjects.LoginPage;
import org.j2.faxqa.efax.corporate.admin.pageobjects.UsersPage;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class AdminTests extends BaseTest {

	@TestRail(id = "C8144")
	@Test(enabled = true, priority = 0, groups = {"smoke", "regression" }, description = "Verify that new admin user should be created successfully")
	public void adminUserShouldBeCreatedSuccessfully() throws Exception {
		WebDriver driver = null;
		String primaryemail = null;

		driver = TLDriverFactory.getTLDriver();
		String firstname = "QA" + new Faker().address().firstName();
		String lastname = new Faker().address().lastName() + new Faker().bothify("??????");
		primaryemail = firstname + lastname + new Faker().number().digits(3) + "@mailinator.com";
		String password = "Welcome@123";

		String expectedValidationMessage = "Group Admin Created Successfully.";
		driver.navigate().to(Config.mgmtBaseUrl);
		LoginPage loginPage = new LoginPage();
		loginPage.login(Config.AccountNumberMGMT, Config.AdministratorNameMGMT, Config.PasswordMGMT);
		HomePage homepage = new HomePage();
		homepage.clickUsersTab();
		UsersPage usersPage = new UsersPage();
		usersPage.clickPageActionsMenu();
		usersPage.clickAddUsersOption();
		AddUser adduserpage = new AddUser();
		adduserpage.tickAdminAccountCheckbox(true);
		adduserpage.enterFirstName(firstname);
		adduserpage.enterLastName(lastname);
		adduserpage.enterPrimaryEmailAddress(primaryemail);
		adduserpage.enterPassword(password);
		adduserpage.enterConfirmPassword(password);
		adduserpage.enterAdministratorName(firstname + lastname);
		adduserpage.clickCreateUserButton();
		Assert.assertEquals(adduserpage.createUserValidationMessage(), expectedValidationMessage);

		homepage = new HomePage();
		homepage.logout();
		// BasePage.getWelcomeEmail(primaryemail);
		loginPage = new LoginPage();
		loginPage.login(Config.AccountNumberMGMT, firstname + lastname, password);
		homepage = new HomePage();
		Assert.assertEquals(true, homepage.isUserLoggedIn());
		homepage.logout();
		new CoreFaxFunctions().deleteAccount(Config.AccountNumberMGMT, Config.AdministratorNameMGMT, Config.PasswordMGMT, primaryemail);
	}

	@TestRail(id = "C8145")
	@Test(enabled = true, priority = 1, groups = {"smoke", "regression" }, description = "Verify that new group should be created/added successfully")
	public void testAddGroupUsingGroupAdmin() throws Exception {
		WebDriver driver = TLDriverFactory.getTLDriver();
		String firstname = "QA" + new Faker().address().firstName();
		String lastname = new Faker().address().lastName() + new Faker().bothify("??????");
		String primaryemail = firstname + lastname + new Faker().number().digits(3) + "@mailinator.com";
		String password = "Welcome@123";

		String expectedValidationMessage = "Group Admin Created Successfully.";
		driver.navigate().to(Config.mgmtBaseUrl);
		LoginPage loginPage = new LoginPage();
		loginPage.login(Config.AccountNumberMGMT, Config.AdministratorNameMGMT, Config.PasswordMGMT);
		HomePage homepage = new HomePage();
		homepage.clickUsersTab();
		UsersPage usersPage = new UsersPage();
		usersPage.clickPageActionsMenu();
		usersPage.clickAddUsersOption();
		AddUser adduserpage = new AddUser();
		adduserpage.tickAdminAccountCheckbox(true);
		adduserpage.enterFirstName(firstname);
		adduserpage.enterLastName(lastname);
		adduserpage.enterPrimaryEmailAddress(primaryemail);
		adduserpage.enterPassword(password);
		adduserpage.enterConfirmPassword(password);
		adduserpage.enterAdministratorName(firstname + lastname);
		adduserpage.clickCreateUserButton();
		Assert.assertEquals(adduserpage.createUserValidationMessage(), expectedValidationMessage);
		homepage = new HomePage();
		homepage.logout();

		loginPage = new LoginPage();
		loginPage.login(Config.AccountNumberMGMT, firstname + lastname, password);
		if (loginPage.isPasswordChangePrompted())
			password = loginPage.changePassword(password);

		driver.navigate().refresh();
		homepage = new HomePage();
		homepage.clickGroupsTab();

		AddGroupPage addGroupPage = new AddGroupPage();
		String newGrpName = "QA_Automation_" + new Faker().number().digits(5);
		addGroupPage.enterNewGroupName(newGrpName);
		addGroupPage.toggleBillingCodeRequiredBtn(false);
		addGroupPage.togglePrivatePoolRequiredBtn(false);
		addGroupPage.clickGetBtnCreateGroup();

		String expected = "Group created successfully.";
		Assert.assertEquals(addGroupPage.getSuccessGroupValidationMessage(), expected);
		new CoreFaxFunctions().deleteAccount(Config.AccountNumberMGMT, Config.AdministratorNameMGMT, Config.PasswordMGMT, primaryemail);
	}

	@TestRail(id = "C8146")
	@Test(enabled = true, priority = 1, groups = {"smoke", "regression" }, description = "Verify that new user should be created from the Group Details page and setting of that user should be inherited from the Group")
	public void newUserShouldBeCreatedAndSettingShouldBeInheritedFromTheGroup() throws Exception {
		String firstname = "QATest" + new Faker().address().firstName();
		String lastname = new Faker().address().lastName() + new Faker().bothify("??????");
		String contactemail = String.join(".", firstname, lastname + "@mailinator.com");
		String password = "Welcome@123";

		TLDriverFactory.getTLDriver().navigate().to(Config.mgmtBaseUrl);
		LoginPage loginPage = new LoginPage();
		loginPage.login(Config.nonsecureAccountNumber_MGMT, Config.nonsecureadministratorName_MGMT,
				Config.nonsecurepassword_MGMT);
		HomePage homepage = new HomePage();
		homepage.clickGroupsTab();
		GroupsPage groupsPage = new GroupsPage();
		groupsPage.clickMyAccountEditButton();
		GroupDetailsPage groupDetailsPage = new GroupDetailsPage();
		groupDetailsPage.clickAdvancedSettingsButton();
		boolean groupSetting1 = groupDetailsPage.isStorageEnabled();
		boolean groupSetting2 = groupDetailsPage.isEditProfileEnabled();

		groupDetailsPage.clickAddUser();
		AddUser addUser = new AddUser();
		addUser.enterFirstName(firstname);
		addUser.enterLastName(lastname);
		addUser.enterPrimaryEmailAddress(contactemail);
		addUser.enterPassword(password);
		addUser.enterConfirmPassword(password);
		addUser.clickCreateUserButton();
		
		EditUser editUser = new EditUser();
		editUser.clickAdvancedSettingsButton();

		boolean userSetting1 = editUser.isStorageEnabled();
		boolean userSetting2 = editUser.isEditProfileEnabled();
		editUser.logout();
		Assert.assertEquals(groupSetting1, userSetting1);
		Assert.assertEquals(groupSetting2, userSetting2);
		
		new CoreFaxFunctions().deleteAccount(Config.nonsecureAccountNumber_MGMT, Config.nonsecureadministratorName_MGMT, Config.nonsecurepassword_MGMT, contactemail);
	}

	@TestRail(id = "C8147")
	@Test(enabled = true, priority = 1, groups = {"smoke", "regression" }, description = "Verify that new Fax Numbers should be added successfully to account using 'Search By - State' option")
	public void addFaxNumberUsingSearchByState() throws Exception {
		TLDriverFactory.getTLDriver().navigate().to(Config.mgmtBaseUrl);
		LoginPage loginPage = new LoginPage();
		loginPage.login(Config.AccountNumberMGMT, Config.AdministratorNameMGMT, Config.PasswordMGMT);

		HomePage homepage = new HomePage();
		homepage.clickFaxNumbersTab();

		FaxNumbersPage faxNumbersPage = new FaxNumbersPage();
		String unassignedFaxNumberCountOne = faxNumbersPage.totalUnassignedFaxNumbers();
		int unassignedFaxNumberCount1 = Integer.parseInt(unassignedFaxNumberCountOne);
		faxNumbersPage.clickPageActionsDropdown();
		faxNumbersPage.clickAddFaxNumbersIcon();

		AddFaxNumbersOverlay addFaxNumbersOverlay = new AddFaxNumbersOverlay();
		addFaxNumbersOverlay.searchByState(1);

		String actualText = addFaxNumbersOverlay.getValidationMessageText();
		Assert.assertTrue(actualText.contains("You have successfully added numbers to your account"));
		addFaxNumbersOverlay.clickCloseIcon();

		faxNumbersPage.refresh();
		String unassignedFaxNumberCountTwo = faxNumbersPage.totalUnassignedFaxNumbers();
		int unassignedFaxNumberCount2 = Integer.parseInt(unassignedFaxNumberCountTwo);
		Assert.assertEquals(unassignedFaxNumberCount1 + 1, unassignedFaxNumberCount2);
	}

	@TestRail(id = "C8148")
	@Test(enabled = true, priority = 1, groups = {"smoke", "regression" }, description = "Admin > Test assigning fax numbers functionality to myaccount users")
	public void testAssignFunctionality() throws Exception {

		String firstname = "QA" + new Faker().address().firstName();
		String lastname = new Faker().address().lastName() + new Faker().bothify("??????");
		String primaryemail = firstname + lastname + new Faker().number().digits(3) + "@mailinator.com";
		String password = "Welcome@123";

		String unassignedFaxNumberCount;
		int count;
		LoginPage loginPage = new LoginPage();
		HomePage homepage = new HomePage();

		TLDriverFactory.getTLDriver().navigate().to(Config.mgmtBaseUrl);
		loginPage.login(Config.AccountNumberMGMT, Config.AdministratorNameMGMT, Config.PasswordMGMT);
		homepage.clickUsersTab();

		UsersPage usersPage = new UsersPage();
		usersPage.clickPageActionsMenu();
		usersPage.clickAddUsersOption();

		AddUser addUser = new AddUser();
		addUser.enterFirstName(firstname);
		addUser.enterLastName(lastname);
		addUser.enterPrimaryEmailAddress(primaryemail);
		addUser.clickCreateUserButton();
		addUser.clickFaxNumbersTab();

		FaxNumbersPage faxNumbersPage = new FaxNumbersPage();
		faxNumbersPage.gotoUnassignedFaxNumbers();
		String faxNumber = faxNumbersPage.getFirstFaxNumberFromTheUnassignedTab().getText();
		faxNumbersPage.gotoAllFaxNumbersTab();
		faxNumbersPage.searchByFaxNumber(faxNumber);
		faxNumbersPage.selectFirstFaxNumberFromTheAllTab();
		
		faxNumbersPage.clickMoveOption();
		faxNumbersPage.clickFirstGroupFromMoveFaxNumberPopup();
		faxNumbersPage.clickMoveButtonFromMoveFaxNumberPopup();
		faxNumbersPage.clickCloseButtonFromSucceededPopup();

		homepage = new HomePage();
		homepage.clickFaxNumbersTab();
		homepage.refresh();

		faxNumbersPage.gotoAllFaxNumbersTab();
		faxNumbersPage.searchByFaxNumber(faxNumber);
		faxNumbersPage.selectFirstFaxNumberFromTheAllTab();
		faxNumbersPage.clickAssignOption();
		faxNumbersPage.enterUserSearchTerm(lastname);
		faxNumbersPage.clickAssignFaxNumbersButton();
		
		String expectedResult_1 = "All selected fax numbers were successfully assigned!";
		String actualResult_1 = faxNumbersPage.getSucceededPopup().getText();
		Assert.assertEquals(actualResult_1, expectedResult_1);
		faxNumbersPage.clickCloseButtonFromSucceededPopup();
		unassignedFaxNumberCount = faxNumbersPage.totalUnassignedFaxNumbers();
		count = Integer.parseInt(unassignedFaxNumberCount);
		faxNumbersPage.logout();
		
		Assert.assertEquals(count, 0);
		new CoreFaxFunctions().deleteAccount(Config.AccountNumberMGMT, Config.AdministratorNameMGMT, Config.PasswordMGMT, primaryemail);
	}
}