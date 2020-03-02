package org.j2.faxqa.efax.corporate.admin;

import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.j2.faxqa.efax.common.Config;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.j2.faxqa.efax.corporate.admin.pageobjects.AddUser;
import org.j2.faxqa.efax.corporate.admin.pageobjects.EditUser;
import org.j2.faxqa.efax.corporate.admin.pageobjects.HomePage;
import org.j2.faxqa.efax.corporate.admin.pageobjects.LoginPage;
import org.j2.faxqa.efax.corporate.admin.pageobjects.NavigationBar;
import org.j2.faxqa.efax.corporate.admin.pageobjects.UsersPage;
import org.j2.faxqa.efax.corporate.myaccount.pageobjects.HomePageMyAccount;
import org.j2.faxqa.efax.corporate.myaccount.pageobjects.LoginPageMyAccount;
import org.j2.faxqa.efax.corporate.myaccount.pageobjects.NavigationBarMyAccount;
import org.j2.faxqa.efax.corporate.myaccount.pageobjects.SendFaxesModalMyAccount;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.zeroturnaround.zip.ZipUtil;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

public class CoreFaxFunctions {

	public Map<String, String> createNewMyAccountUser() throws Exception {
		
		String firstname = "QA" + new Faker().address().firstName();
		String lastname = new Faker().address().lastName();
		String primaryemail = firstname + lastname + new Faker().number().digits(3) + "@mailinator.com";
		String password = "Welcome@123";
		String companyName = new Faker().company().name();
		String accountId = new Faker().bothify("ACCID?????###?");
		String clientName = new Faker().company().name();
		String matter = accountId + " - " + clientName;
		String subject = "Test Send Fax - " + new Faker().bothify("####????");
		String messageBody = "Message Body - " + new Faker().bothify("####????");
		String referenceId = new Faker().bothify("REF?????###?");
		String quality = "Standard";
		String country = "United States";
		String senderMail = null;
		String faxNumber = null;

	
			TLDriverFactory.getTLDriver().navigate().to(Config.mgmtBaseUrl);
			LoginPage loginPage = new LoginPage();
			loginPage.login(Config.AccountNumberMGMT, Config.AdministratorNameMGMT, Config.PasswordMGMT);
			HomePage home = new HomePage();
			home.clickUsersTab();
			
			UsersPage usersPage = new UsersPage();
			usersPage.clickUsersTab();
			usersPage.clickPageActionsMenu();
			usersPage.clickAddUsersOption();

			AddUser addUser = new AddUser();

			addUser.enterFirstName(firstname);
			addUser.enterLastName(lastname);
			addUser.enterPrimaryEmailAddress(primaryemail);
			addUser.enterPassword(password);
			System.out.println(password);
			addUser.enterConfirmPassword(password);
			faxNumber = addUser.getFaxNumber();
			addUser.userMaySendFaxesToggle(true);
			addUser.clickCreateUserButton();

			String expectedValidationMessage = "The account has been created.";
			String actualValidationMessage = addUser.getCreateUserValidationMessage();

			Assert.assertEquals(actualValidationMessage, expectedValidationMessage);

			EditUser editUser = new EditUser();
			editUser.clickAdvancedOptions();
			editUser.userMustResetPasswordToggle(false);
			editUser.clickUpdateUserButton();

			if (addUser.isUserLoggedIn())
				addUser.logout();

			TLDriverFactory.getTLDriver().navigate().to(Config.myAccountBaseUrl);

			LoginPageMyAccount loginPageMA = new LoginPageMyAccount();
			loginPageMA.login(faxNumber, password);

			HomePageMyAccount homePageMyAccount = new HomePageMyAccount();

			if (homePageMyAccount.isPasswordUpdatePrompted())
				password = homePageMyAccount.updatePassword(password);

			Assert.assertEquals(homePageMyAccount.isUserLoggedIn(), true);

			if (homePageMyAccount.isUserLoggedIn())
				homePageMyAccount.logout();
			
			Map<String, String> map = new HashMap<>();
			map.put("firstname", firstname);
			map.put("lastname", lastname);
			map.put("primaryemail", primaryemail);
			map.put("password", password);
			map.put("companyName", companyName);
			map.put("accountId", accountId);
			map.put("clientName", clientName);
			map.put("matter", matter);
			map.put("subject", subject);
			map.put("messageBody", messageBody);
			map.put("referenceId", referenceId);
			map.put("quality", quality);
			map.put("country", country);
			map.put("senderMail", senderMail);
			map.put("faxNumber", faxNumber);
			
			return map;

	}
	
	public boolean composeSendFaxTo(String toFaxNumber) throws Exception {
		
		String firstname = "QA" + new Faker().address().firstName();
		String lastname = new Faker().address().lastName();
		String companyName = new Faker().company().name();
		String clientName = new Faker().company().name();
		String matter = "message to " + companyName + clientName;
		String subject = "Test Send Fax - " + new Faker().bothify("####????");
		String messageBody = "Message Body - " + new Faker().bothify("####????");
		String referenceId = new Faker().bothify("REF?????###?");
		String accountId = new Faker().bothify("ACCID?????###?");
		String quality = "Standard";
		
		NavigationBarMyAccount navigationBarMyAccount = new NavigationBarMyAccount();
		navigationBarMyAccount.clickSendFaxesTab();

		String attachments;
		Path folder = Paths.get((new java.io.File(".")).getCanonicalPath(), "src/test/resources/sendrast");
		Stream<Path> pathstream = Files.list(folder).filter(f -> f.getFileName().toString().endsWith(".txt"));
		attachments = Files.list(folder).filter(f -> f.getFileName().toString().endsWith(".txt")).limit(1)
				.map(f -> f.toAbsolutePath().toString()).collect(Collectors.joining("|"));
		
		SendFaxesModalMyAccount sendFaxesModalMyAccount = new SendFaxesModalMyAccount();
		boolean response = sendFaxesModalMyAccount.sendfax(firstname, lastname, companyName, toFaxNumber, subject,
				messageBody, referenceId, accountId, clientName, matter, quality, attachments);
		
		return response;
	}

	public void deleteAccount(String account, String admin, String password, String email) {
		
		WebDriver driver = TLDriverFactory.getTLDriver();
		driver.navigate().to(Config.mgmtBaseUrl);
		LoginPage loginPage = new LoginPage();
		loginPage.login(account, admin, password);
		HomePage homepage = new HomePage();
		homepage.clickUsersTab();
		UsersPage page = new UsersPage();
		page.searchUsersByEmail(email.split("@")[0]);
		page.selectAllResults();
		page.clickPageActionsMenu();
		page.clickDeleteUserOption();
		page.confirmDelete();
		LogManager.getLogger().info(page.successRemovalValidationMessgae());
	}
	
}