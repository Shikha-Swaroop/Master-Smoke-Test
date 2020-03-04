package org.j2.faxqa.efax.efax_uk.funnel.tests;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.j2.faxqa.efax.common.BaseTest;
import org.j2.faxqa.efax.common.Config;
import org.j2.faxqa.efax.common.Retry;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.j2.faxqa.efax.common.TestRail;
import org.j2.faxqa.efax.efax_uk.funnel.pageobjects.SignUpPage;
import org.j2.faxqa.efax.efax_uk.myaccount.pageobjects.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

//@Listeners({TestExecutionListener.class, TestNGReportListener.class})
public class SignUpTests extends BaseTest {

	protected static final Logger logger = LogManager.getLogger();

	// If uploadresults=true, then the results get uploaded to location
	// https://testrail.test.j2noc.com/

	@TestRail(id = "C8538")
	@Test(enabled = true, retryAnalyzer = Retry.class, groups = { "smoke" }, priority = 1, description = "eFax > UK > Funnel > My Account > Validate Account Registration is Successful & Login to My Account without issues")
	public void verifyNewUserSignUpLogin(ITestContext context) throws Exception {
		WebDriver driver = null;

		driver = TLDriverFactory.getTLDriver();
		logger.info("Navigating to - " + Config.efax_UK_funnelBaseUrl);
		driver.navigate().to(Config.efax_UK_funnelBaseUrl);

		Faker testdata = new Faker();
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
		String firstname = "QA" + testdata.address().firstName().toUpperCase().replace("'", "");
		String lastname = testdata.address().lastName().toUpperCase().replace("'", "");
		String email = firstname + "." + lastname + testdata.number().digits(3) + "@mailinator.com";
		String phone = testdata.phoneNumber().cellPhone().toString();
		String address1 = new Faker().address().buildingNumber();
		String address2 = new Faker().address().streetAddress();
		String city = "";
		String zipCode = "EC1A 1BB";
		String country = "United Kingdom";
		String creditcardnumber = Config.creditCard_UK; // "4872906545490653"; // "441506691331";
		String creditcardmonth = "12";
		String creditcardyear = "2025";
		String creditcardcvv = "321";
		String faxNumber = "";
		String password = "";
		
		SignUpPage signup = new SignUpPage();
		signup.setNumberCountry(country);
		signup.selectByLocalNumbers();
		city = signup.selectACity();

		while (!signup.anyFaxNumbers()) {
			logger.info("No fax numbers found for the selected region, retrying...");
			city = signup.selectACity();
		}
		
		faxNumber = signup.getDefaultFaxNumber();
		signup.setupAccount();
		
		signup.selectFirstName(firstname);
		signup.selectLastName(lastname);
		signup.selectEmail(email);
		signup.confirmEmail(email);
		signup.selectPhoneNumber(phone);
		signup.acceptAgreement();
		signup.proceedNext();
		
		signup.selectBillingCountry(country);
		signup.selectBillingAddress1(address1);
		signup.selectBillingAddress2(address2);
		signup.selectBillingCity(city);
		signup.selectZipCode(zipCode);
		signup.selectPaymentOption();
		signup.selectCardNumber(creditcardnumber);
		signup.selectCardCVVNumber(creditcardcvv);
		signup.selectCreditCardHolder(firstname + " " + lastname);
		signup.setBillingCreditCardMonth(creditcardmonth);
		signup.setBillingCreditCardYear(creditcardyear);
		signup.activateAccount();

		boolean flag = signup.isSignUpSuccess();
		Assert.assertTrue(flag);

		String content = signup.getWelcomeEmail(email, "Thank you for choosing eFax", 60);
		logger.info(content);
		Matcher match1, match2;
		
		match1=Pattern.compile("(Your eFax Number:\\r\\n)(\\d+)").matcher(content);
		match2=Pattern.compile("(PIN: )(\\d+)").matcher(content);
				
		if (match1.find()) faxNumber = match1.group(1);
		if (match2.find()) password = match2.group(2);
		
		logger.info(faxNumber + " & " + password);

		driver.get(Config.efax_UK_myaccountBaseUrl);
		LoginPage login = new LoginPage();
		login.login(faxNumber, password);
		
		Assert.assertTrue(signup.isLoggedIn());
		
	}
}
