package org.j2.faxqa.efax.efax_us.myaccount.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.j2.faxqa.efax.common.*;
import org.j2.faxqa.efax.efax_us.myaccount.pageobjects.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

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

		///////////////////// FSTOR VALIDATION ///////////////////////////////////
		RestAssured.baseURI = Config.fstorhost;
		// https://amp.fstor.us.j2.com/oauth/token?grant_type=client_credentials
		Response response = RestAssured.given().auth().basic(Config.fstorclientId, Config.fstorclientSecret).contentType("application/x-www-form-urlencoded").param("grant_type", "client_credentials").when().post("/oauth/token");
		String repositoryid = null;

		JSONObject jsonObject = (JSONObject) (new JSONParser()).parse(response.getBody().asString());
		String accessToken = jsonObject.get("access_token").toString();
		String date = LocalDateTime.now().plusDays(-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		A: for (int i = 1; i <= 60; i++) {
			// https://amp.fstor.us.j2.com/fstor-oauth/faxes?fax_system=EFAX&element_name=servicekey&element_value=107728639
			response = RestAssured.given().header("Authorization", "Bearer " + accessToken).contentType("application/x-www-form-urlencoded").param("fax_system", "EFAX").param("element_name", "servicekey")
					.param("min_completed_timestamp", date).param("element_value", Config.efax_US_outbound_servicekey).when().get("/fstor-oauth/sentfaxes");
			JSONObject sentFaxes = (JSONObject) (new JSONParser()).parse(response.getBody().asString());
			for (Object obj : (JSONArray) sentFaxes.get("faxes")) {
				if (((JSONObject) obj).get("subject").toString().equalsIgnoreCase(senderid)) {
					repositoryid = ((JSONObject) sentFaxes.get("faxes")).get("repository_id").toString();
					break A;
				}
			}
			System.out.println("Not yet found, waiting 5 seconds before a retry");
			Thread.sleep(5000);
		}
		response = RestAssured.given().header("Authorization", "Bearer " + accessToken).contentType("application/json").param("fax_system", "EFAX").param("element_name", "servicekey").param("min_completed_timestamp", date)
				.param("element_value", Config.efax_US_outbound_servicekey).when().get("/fstor-oauth/faxes/" + repositoryid + "/images");
		assertEquals("200", response.statusCode());
		JSONObject image = (JSONObject) (new JSONParser()).parse(response.getBody().asString());
		String finename = image.get("file_name").toString();
		Assert.assertEquals(true, (finename.length() > 0));

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
		// Assert.assertTrue(flag);

		acctdetailspage.switchToReceiveLogs();
		acctdetailspage = new AccountDetailsPage();
		flag = acctdetailspage.isReceiveActivityLogFound(senderid, Config.myccount_receiveWait);
		// Assert.assertTrue(flag);

		NavigationBar navigate = new NavigationBar();
		navigate.clickViewFaxesTab();

		ViewFaxesPage viewfaxespage = new ViewFaxesPage();
		flag = viewfaxespage.isFaxReceived(senderid, Config.myccount_inboxWait);
		Assert.assertTrue(flag);
	}

	@TestRail(id = "C8523")
	@Test(enabled = true, groups = { "smoke" }, priority = 1, description = "eFax > US > Non-Secured > Receive > FSTOR > Validate Meta data & Image is retrieved successfully")
	public void nonsecureValidateReceiveMetadataAndImageIsStoredSuccessfully(ITestContext context) throws Exception {
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

		///////////////////// FSTOR VALIDATION ///////////////////////////////////
		RestAssured.baseURI = Config.fstorhost;
		// https://amp.fstor.us.j2.com/oauth/token?grant_type=client_credentials
		Response response = RestAssured.given().auth().basic(Config.fstorclientId, Config.fstorclientSecret).contentType("application/x-www-form-urlencoded").param("grant_type", "client_credentials").when().post("/oauth/token");
		String repositoryid = null;
		JSONObject jsonObject = (JSONObject) (new JSONParser()).parse(response.getBody().asString());
		String accessToken = jsonObject.get("access_token").toString();
		String date = LocalDateTime.now().plusDays(-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

		A: for (int i = 1; i <= 60; i++) {
			response = RestAssured.given().header("Authorization", "Bearer " + accessToken).contentType("application/x-www-form-urlencoded").param("fax_system", "EFAX").param("element_name", "servicekey")
					.param("min_completed_timestamp", date).param("element_value", Config.efax_US_inbound_servicekey).when().get("/fstor-oauth/receivedfaxes");

			JSONObject receivedFaxes = (JSONObject) (new JSONParser()).parse(response.getBody().asString());
			for (Object obj : (JSONArray) receivedFaxes.get("faxes")) {
				if (((JSONObject) obj).get("subject").toString().contains(senderid)) {
					repositoryid = ((JSONObject) receivedFaxes.get("faxes")).get("repository_id").toString();
					break A;
				}

			}
			System.out.println("Receive MetaData Not yet found, waiting 5 seconds before a retry");
			Thread.sleep(5000);
		}

		response = RestAssured.given().header("Authorization", "Bearer " + accessToken).contentType("application/json").param("fax_system", "EFAX").param("element_name", "servicekey").param("min_completed_timestamp", date)
				.param("element_value", Config.efax_US_outbound_servicekey).when().get("/fstor-oauth/faxes/" + repositoryid + "/images");
		assertEquals("200", response.statusCode());
		JSONObject image = (JSONObject) (new JSONParser()).parse(response.getBody().asString());
		String finename = image.get("file_name").toString();
		Assert.assertEquals(true, (finename.length() > 0));
	}

	@TestRail(id = "C8524")
	@Test(enabled = true, groups = { "smoke" }, priority = 1, description = "eFax > US > Secured > My Account > Send > Validate Fax is sent successfully")
	public void secureMyAccountValidateFaxIsSentSuccessfully(ITestContext context) throws Exception {
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

	@TestRail(id = "C8525")
	@Test(enabled = true, groups = { "smoke" }, priority = 1, description = "eFax > US > Secured > Send > FSTOR > Validate Meta data & Image is stored successfully ")
	public void secureSendValidateMetadataAndImageIsStoredSuccessfully(ITestContext context) throws Exception {
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

		///////////////////// FSTOR VALIDATION ///////////////////////////////////
		RestAssured.baseURI = Config.fstorhost;
		// https://amp.fstor.us.j2.com/oauth/token?grant_type=client_credentials
		Response response = RestAssured.given().auth().basic(Config.fstorclientId, Config.fstorclientSecret).contentType("application/x-www-form-urlencoded").param("grant_type", "client_credentials").when().post("/oauth/token");
		String repositoryid = null;

		JSONObject jsonObject = (JSONObject) (new JSONParser()).parse(response.getBody().asString());
		String accessToken = jsonObject.get("access_token").toString();
		String date = LocalDateTime.now().plusDays(-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		A: for (int i = 1; i <= 60; i++) {
			// https://amp.fstor.us.j2.com/fstor-oauth/faxes?fax_system=EFAX&element_name=servicekey&element_value=107728639
			response = RestAssured.given().header("Authorization", "Bearer " + accessToken).contentType("application/x-www-form-urlencoded").param("fax_system", "EFAX").param("element_name", "servicekey")
					.param("min_completed_timestamp", date).param("element_value", Config.efax_US_outbound_servicekey).when().get("/fstor-oauth/sentfaxes");
			JSONObject sentFaxes = (JSONObject) (new JSONParser()).parse(response.getBody().asString());
			for (Object obj : (JSONArray) sentFaxes.get("faxes")) {
				if (((JSONObject) obj).get("subject").toString().equalsIgnoreCase(senderid)) {
					repositoryid = ((JSONObject) sentFaxes.get("faxes")).get("repository_id").toString();
					break A;
				}
			}
			System.out.println("Not yet found, waiting 5 seconds before a retry");
			Thread.sleep(5000);
		}
		response = RestAssured.given().header("Authorization", "Bearer " + accessToken).contentType("application/json").param("fax_system", "EFAX").param("element_name", "servicekey").param("min_completed_timestamp", date)
				.param("element_value", Config.efax_US_outbound_servicekey).when().get("/fstor-oauth/faxes/" + repositoryid + "/images");
		assertEquals("200", response.statusCode());
		JSONObject image = (JSONObject) (new JSONParser()).parse(response.getBody().asString());
		String finename = image.get("file_name").toString();
		Assert.assertEquals(true, (finename.length() > 0));

	}

	@TestRail(id = "C8526")
	@Test(enabled = true, groups = { "smoke" }, priority = 1, description = "eFax > US > Secured > My Account > Receive > Validate fax is received successfully ")
	public void secureMyAccountValidateFaxIsReceivedSuccessfully(ITestContext context) throws Exception {
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
		// Assert.assertTrue(flag);

		acctdetailspage.switchToReceiveLogs();
		acctdetailspage = new AccountDetailsPage();
		flag = acctdetailspage.isReceiveActivityLogFound(senderid, Config.myccount_receiveWait);
		// Assert.assertTrue(flag);

		NavigationBar navigate = new NavigationBar();
		navigate.clickViewFaxesTab();

		ViewFaxesPage viewfaxespage = new ViewFaxesPage();
		flag = viewfaxespage.isFaxReceived(senderid, Config.myccount_inboxWait);
		Assert.assertTrue(flag);
	}

	@TestRail(id = "C8526")
	@Test(enabled = true, groups = { "smoke" }, priority = 1, description = "eFax > US > Secured > Receive > FSTOR > Validate Meta data & Image is retrieved successfully ")
	public void secureValidateReceiveMetadataAndImageIsStoredSuccessfully(ITestContext context) throws Exception {
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

		///////////////////// FSTOR VALIDATION ///////////////////////////////////
		RestAssured.baseURI = Config.fstorhost;
		// https://amp.fstor.us.j2.com/oauth/token?grant_type=client_credentials
		Response response = RestAssured.given().auth().basic(Config.fstorclientId, Config.fstorclientSecret).contentType("application/x-www-form-urlencoded").param("grant_type", "client_credentials").when().post("/oauth/token");
		String repositoryid = null;
		JSONObject jsonObject = (JSONObject) (new JSONParser()).parse(response.getBody().asString());
		String accessToken = jsonObject.get("access_token").toString();
		String date = LocalDateTime.now().plusDays(-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

		A: for (int i = 1; i <= 60; i++) {
			response = RestAssured.given().header("Authorization", "Bearer " + accessToken).contentType("application/x-www-form-urlencoded").param("fax_system", "EFAX").param("element_name", "servicekey")
					.param("min_completed_timestamp", date).param("element_value", Config.efax_US_inbound_servicekey).when().get("/fstor-oauth/receivedfaxes");

			JSONObject receivedFaxes = (JSONObject) (new JSONParser()).parse(response.getBody().asString());
			for (Object obj : (JSONArray) receivedFaxes.get("faxes")) {
				if (((JSONObject) obj).get("subject").toString().contains(senderid)) {
					repositoryid = ((JSONObject) receivedFaxes.get("faxes")).get("repository_id").toString();
					break A;
				}

			}
			System.out.println("Receive MetaData Not yet found, waiting 5 seconds before a retry");
			Thread.sleep(5000);
		}

		response = RestAssured.given().header("Authorization", "Bearer " + accessToken).contentType("application/json").param("fax_system", "EFAX").param("element_name", "servicekey").param("min_completed_timestamp", date)
				.param("element_value", Config.efax_US_outbound_servicekey).when().get("/fstor-oauth/faxes/" + repositoryid + "/images");
		assertEquals("200", response.statusCode());
		JSONObject image = (JSONObject) (new JSONParser()).parse(response.getBody().asString());
		String finename = image.get("file_name").toString();
		Assert.assertEquals(true, (finename.length() > 0));
	}
}
