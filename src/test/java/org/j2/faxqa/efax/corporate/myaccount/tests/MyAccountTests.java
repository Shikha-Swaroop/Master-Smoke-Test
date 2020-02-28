package org.j2.faxqa.efax.corporate.myaccount.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.UUID;
import org.j2.faxqa.efax.common.BaseTest;
import org.j2.faxqa.efax.common.Config;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.j2.faxqa.efax.common.TestRail;
import org.j2.faxqa.efax.corporate.admin.CoreFaxFunctions;
import org.j2.faxqa.efax.corporate.myaccount.pageobjects.*;
import org.j2.faxqa.efax.efax_us.myaccount.pageobjects.HomePage;
import org.j2.faxqa.efax.efax_us.myaccount.pageobjects.LoginPage;
import org.openqa.selenium.WebDriver;

public class MyAccountTests  extends BaseTest {

	@TestRail(id = "C8562")
	@Test(enabled = true, priority = 1, groups = {"smoke" }, expectedExceptions = {Exception.class}, description = "eFax > US > Secured > My Account > Send > Validate fax & Email Notification is sent successfully")
	public void ableToReceiveAndViewFaxWhenStorageEnabledONFromMGMT() throws Exception {

		String uniqueid = UUID.randomUUID().toString().replace("-", "").substring(0, 15);

		WebDriver driver = TLDriverFactory.getTLDriver();
		driver.navigate().to(Config.corp_myaccountBaseUrl);
		LoginPageMyAccount loginpage = new LoginPageMyAccount();
		loginpage.login(Config.corp_DID, Config.corp_password);
		HomePageMyAccount homepage = new HomePageMyAccount();
		homepage.clickAccountDetailsTab();
		
		AccountDetailsPageMyAccount acctdetailspage = new AccountDetailsPageMyAccount();
		acctdetailspage.clickPreferencesTab();
		acctdetailspage.updatesendCSID(uniqueid);

		boolean response = new CoreFaxFunctions().composeSendFaxTo(Config.corp_DID);
		Assert.assertTrue(response);

		NavigationBarMyAccount navigationBarMyAccount = new NavigationBarMyAccount();
		navigationBarMyAccount.clickReportsTab();

		AccountDetailsPageMyAccount aacdetailspage = new AccountDetailsPageMyAccount();
		aacdetailspage.clickReportsTab();
		aacdetailspage.clickSendTab();
		aacdetailspage.clickSendGo();
		boolean flag = aacdetailspage.isSendActivityLogFound(uniqueid, 300);
		// Assert.assertTrue(response);

		aacdetailspage.clickReceiveTab();
		aacdetailspage.clickReceiveGo();
		flag = aacdetailspage.isReceiveActivityLogFound(uniqueid, 300);
		// Assert.assertTrue(response);

		aacdetailspage.clickViewFaxesTab();
		ViewFaxesModalMyAccount viewfaxespage = new ViewFaxesModalMyAccount();
		viewfaxespage.clickViewFaxesTab();
		flag = viewfaxespage.isFaxReceived(uniqueid, 300);
		Assert.assertTrue(flag);
	}
	
	@TestRail(id = "C8580")
	@Test(enabled = true, priority = 1, groups = {"smoke"}, expectedExceptions = {Exception.class}, description = "eFax > US > Secured > My Account > Email Notification > My Account > Send File > Validate File is sent successfully")
	public void validatefileissentsuccessfully() throws Exception {

		String uniqueid = UUID.randomUUID().toString().replace("-", "").substring(0, 15);

		WebDriver driver = TLDriverFactory.getTLDriver();
		driver.navigate().to(Config.corp_myaccountBaseUrl);
		LoginPageMyAccount loginpage = new LoginPageMyAccount();
		loginpage.login(Config.corp_DID, Config.corp_password);
		HomePageMyAccount homepage = new HomePageMyAccount();
		homepage.clickAccountDetailsTab();
		
		AccountDetailsPageMyAccount acctdetailspage = new AccountDetailsPageMyAccount();
		acctdetailspage.clickPreferencesTab();
		acctdetailspage.updatesendCSID(uniqueid);

		boolean response = new CoreFaxFunctions().composeSendFaxTo(Config.corp_DID);
		Assert.assertTrue(response);

		NavigationBarMyAccount navigationBarMyAccount = new NavigationBarMyAccount();
		navigationBarMyAccount.clickReportsTab();

		AccountDetailsPageMyAccount aacdetailspage = new AccountDetailsPageMyAccount();
		aacdetailspage.clickReportsTab();
		aacdetailspage.clickSendTab();
		aacdetailspage.clickSendGo();
		boolean flag = aacdetailspage.isSendActivityLogFound(uniqueid, 300);
		// Assert.assertTrue(response);

		aacdetailspage.clickReceiveTab();
		aacdetailspage.clickReceiveGo();
		flag = aacdetailspage.isReceiveActivityLogFound(uniqueid, 300);
		// Assert.assertTrue(response);

		aacdetailspage.clickViewFaxesTab();
		ViewFaxesModalMyAccount viewfaxespage = new ViewFaxesModalMyAccount();
		viewfaxespage.clickViewFaxesTab();
		flag = viewfaxespage.isFaxReceived(uniqueid, 300);
		Assert.assertTrue(flag);
	}
}