package org.j2.faxqa.efax.corporate.myaccount.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.j2.faxqa.efax.common.BaseTest;
import org.j2.faxqa.efax.common.Config;
import org.j2.faxqa.efax.common.FlashPolicyHelper;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.j2.faxqa.efax.common.TestRail;
import org.j2.faxqa.efax.corporate.admin.CoreFaxFunctions;
import org.j2.faxqa.efax.corporate.myaccount.pageobjects.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SendfaxTests  extends BaseTest {

	@TestRail(id = "C8562")
	@Test(enabled = true, priority = 1, groups = {"smoke" }, description = "eFax > US > Secured > My Account > Send > Validate fax & Email Notification is sent successfully")
	public void uploadAndSendALargeFileAndVerify() throws Exception {

		String uniqueid = UUID.randomUUID().toString().replace("-", "").substring(0, 15);

		WebDriver driver = TLDriverFactory.getTLDriver();
		
		driver.navigate().to(Config.corp_myaccountBaseUrl);
		LoginPageMyAccount loginpage = new LoginPageMyAccount();
		loginpage.login(Config.corp_DID, Config.corp_password);
		HomePageMyAccount homepage = new HomePageMyAccount();
		homepage.clickFileshareTab();
		
		Path folder = Paths.get((new java.io.File(".")).getCanonicalPath(), "src/test/resources/sendrast");
		Stream<Path> pathstream = Files.list(folder).filter(f -> f.getFileName().toString().endsWith(".txt"));
		String attachments = Files.list(folder).filter(f -> f.getFileName().toString().endsWith(".txt")).limit(1)
				.map(f -> f.toAbsolutePath().toString()).collect(Collectors.joining("|"));
		
		driver.switchTo().frame("myAccount_largeFileTransfer_iframe");
		SendLargeFileModalMyAccount largeFile = new SendLargeFileModalMyAccount();		
		boolean status = largeFile.sendLargeFile(attachments, uniqueid+"@mailinator.com");
		
		Assert.assertTrue(status);

	}
	
	@TestRail(id = "C8562")
	@Test(enabled = true, priority = 1, groups = {"smoke" }, description = "eFax > US > Secured > My Account > Send > Validate fax & Email Notification is sent successfully")
	public void sendAFaxToSelfAndVerifyFaxIsReceived() throws Exception {

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
		boolean flag = aacdetailspage.isSendActivityLogFound(uniqueid, Config.myccount_sendWait);
		// Assert.assertTrue(response);

		aacdetailspage.clickReceiveTab();
		aacdetailspage.clickReceiveGo();
		flag = aacdetailspage.isReceiveActivityLogFound(uniqueid, Config.myccount_receiveWait);
		// Assert.assertTrue(response);

		aacdetailspage.clickViewFaxesTab();
		ViewFaxesModalMyAccount viewfaxespage = new ViewFaxesModalMyAccount();
		viewfaxespage.clickViewFaxesTab();
		flag = viewfaxespage.isFaxReceived(uniqueid, Config.myccount_inboxWait);
		Assert.assertTrue(flag);
	}
}