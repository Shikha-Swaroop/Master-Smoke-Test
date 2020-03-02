package org.j2.faxqa.efax.efax_us.myaccount.tests;

import java.util.UUID;

import org.j2.faxqa.efax.common.BaseTest;
import org.j2.faxqa.efax.common.Config;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.j2.faxqa.efax.common.TestRail;
import org.j2.faxqa.efax.efax_us.myaccount.pageobjects.AccountDetailsPage;
import org.j2.faxqa.efax.efax_us.myaccount.pageobjects.HomePage;
import org.j2.faxqa.efax.efax_us.myaccount.pageobjects.LoginPage;
import org.j2.faxqa.efax.efax_us.myaccount.pageobjects.NavigationBar;
import org.j2.faxqa.efax.efax_us.myaccount.pageobjects.SendFaxesPage;
import org.j2.faxqa.efax.efax_us.myaccount.pageobjects.ViewFaxesPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

//@Listeners({TestExecutionListener.class, TestNGReportListener.class})
public class SendfaxTests extends BaseTest {

	@TestRail(id = "C8520")
	@Test(enabled = true, groups = { "smoke" }, priority = 1, description = "eFax > US > Non-Secured > My Account > Send > Validate Fax is sent successfully")
	public void nonsecureMyAccountValidateFaxIsSentSuccessfully(ITestContext context) throws Exception {
		WebDriver driver = TLDriverFactory.getTLDriver();
		driver.navigate().to(Config.efax_US_myaccountBaseUrl);
		LoginPage loginpage = new LoginPage();
		loginpage.login(Config.DID_US, Config.PIN_US);

		HomePage homepage = new HomePage();
		homepage.gotoacctdetailsview();

		String senderid = UUID.randomUUID().toString().replace("-", "").substring(0, 15);
		AccountDetailsPage acctdetailspage = new AccountDetailsPage();
		acctdetailspage.updatesendCSID(senderid);

		homepage = new HomePage();
		homepage.gotosendfaxesview();

		SendFaxesPage sendpage = new SendFaxesPage();
		sendpage.sendfax(senderid);
		Assert.assertTrue(sendpage.confirmationVerify());

	}
	
	@TestRail(id = "C8521")
	@Test(enabled = true, groups = { "smoke" }, priority = 1, description = "eFax > US > Non-Secured > Send > FSTOR > Validate Meta data & Image is stored successfully")
	public void nonsecureSendValidateMetadataAndImageIsStoredSuccessfully(ITestContext context) throws Exception {
		throw new Exception("NotImplementedException");
	}
	
	@TestRail(id = "C8522")
	@Test(enabled = true, groups = { "smoke" }, priority = 1, description = "eFax > US > Non-Secured > My Account > Receive > Validate fax is received successfully ")
	public void nonsecureMyAccountValidateFaxIsReceivedSuccessfully(ITestContext context) throws Exception {
		WebDriver driver = TLDriverFactory.getTLDriver();
		driver.navigate().to(Config.efax_US_myaccountBaseUrl);
		LoginPage loginpage = new LoginPage();
		loginpage.login(Config.DID_US, Config.PIN_US);

		HomePage homepage = new HomePage();
		homepage.gotoacctdetailsview();

		String senderid = UUID.randomUUID().toString().replace("-", "").substring(0, 15);
		AccountDetailsPage acctdetailspage = new AccountDetailsPage();
		acctdetailspage.updatesendCSID(senderid);

		homepage = new HomePage();
		homepage.gotosendfaxesview();

		SendFaxesPage sendpage = new SendFaxesPage();
		sendpage.sendfax(senderid);
		sendpage.confirmationVerify();
		sendpage.closeconfirmation();
		boolean flag;

		homepage = new HomePage();
		homepage.gotoacctdetailsview();
		acctdetailspage = new AccountDetailsPage();
		flag = acctdetailspage.isSendActivityLogFound(senderid, Config.myccount_sendWait);
		//Assert.assertTrue(flag);

		acctdetailspage.switchToReceiveLogs();
		acctdetailspage = new AccountDetailsPage();
		flag = acctdetailspage.isReceiveActivityLogFound(senderid, Config.myccount_receiveWait);
		//Assert.assertTrue(flag);

		NavigationBar navigate = new NavigationBar();
		navigate.clickViewFaxesTab();

		ViewFaxesPage viewfaxespage = new ViewFaxesPage();
		flag = viewfaxespage.isFaxReceived(senderid, Config.myccount_inboxWait);
		Assert.assertTrue(flag);

	}
	
	@TestRail(id = "C8523")
	@Test(enabled = true, groups = { "smoke" }, priority = 1, description = "eFax > US > Non-Secured > Receive > FSTOR > Validate Meta data & Image is retrieved successfully")
	public void nonsecureValidateReceiveMetadataAndImageIsStoredSuccessfully(ITestContext context) throws Exception {
		throw new Exception("NotImplementedException");
	}

	@TestRail(id = "C8524")
	@Test(enabled = true, groups = { "smoke" }, priority = 1, description = "eFax > US > Secured > My Account > Send > Validate Fax is sent successfully")
	public void secureMyAccountValidateFaxIsSentSuccessfully(ITestContext context) throws Exception {
		throw new Exception("NotImplementedException");
	}
	
	@TestRail(id = "C8525")
	@Test(enabled = true, groups = { "smoke" }, priority = 1, description = "eFax > US > Secured > Send > FSTOR > Validate Meta data & Image is stored successfully ")
	public void secureSendValidateMetadataAndImageIsStoredSuccessfully(ITestContext context) throws Exception {
		throw new Exception("NotImplementedException");
	}
	
	@TestRail(id = "C8526")
	@Test(enabled = true, groups = { "smoke" }, priority = 1, description = "eFax > US > Secured > My Account > Receive > Validate fax is received successfully ")
	public void secureMyAccountValidateFaxIsReceivedSuccessfully(ITestContext context) throws Exception {
		throw new Exception("NotImplementedException");
	}
	
	@TestRail(id = "C8526")
	@Test(enabled = true, groups = { "smoke" }, priority = 1, description = "eFax > US > Secured > Receive > FSTOR > Validate Meta data & Image is retrieved successfully ")
	public void secureValidateReceiveMetadataAndImageIsStoredSuccessfully(ITestContext context) throws Exception {
		throw new Exception("NotImplementedException");
	}
}
