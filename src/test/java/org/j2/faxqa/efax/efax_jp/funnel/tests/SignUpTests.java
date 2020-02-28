package org.j2.faxqa.efax.efax_jp.funnel.tests;

import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.j2.faxqa.efax.common.BasePage;
import org.j2.faxqa.efax.common.BaseTest;
import org.j2.faxqa.efax.common.Config;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.j2.faxqa.efax.common.TestRail;
import org.j2.faxqa.efax.efax_jp.funnel.pageobjects.SignUpPage;
import org.j2.faxqa.efax.efax_jp.myaccount.pageobjects.LoginPage;
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
	@Test(enabled = true, groups = { "smoke" }, priority = 1, description = "eFax > Japan > Funnel > My Account > Validate Account Registration is Successful & Login to My Account without issues")
	public void verifyNewUserSignUpLogin(ITestContext context) throws Exception {
		WebDriver driver = null;

		driver = TLDriverFactory.getTLDriver();
		logger.info("Navigating to - " + Config.efax_JP_funnelBaseUrl);
		driver.get(Config.efax_JP_funnelBaseUrl);

		Faker testdata = new Faker(Locale.JAPAN);
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
		String firstname = "QA" + testdata.address().firstName().toUpperCase().replace("'", "");
		String lastname = testdata.address().lastName().toUpperCase().replace("'", "");
		String email = testdata.bothify("????##????") + testdata.number().digits(3) + "@mailinator.com";
		String phone = testdata.phoneNumber().cellPhone().toString();
		String address1 = testdata.address().buildingNumber();
		String address2 = testdata.address().streetAddress();
		String company = testdata.company().name();
		String city = "";
		String zip1 = "" + testdata.number().randomDigitNotZero();
		String zip2 = "" + testdata.number().numberBetween(10000, 99999);
		String country = "日本";
		String creditcardnumber = Config.creditCard_JP; // "4872906545490653"; // "441506691331";
		String creditcardmonth = "December";
		String creditcardyear = "2025";
		String creditcardcvv = "321";
		String loginId = "";
		String faxNumber = "";
		String password = "";

		SignUpPage signup = new SignUpPage();
		signup.selectCountry(country);
		signup.selectAreaCode();
		while (!signup.anyFaxNumbers()) {
			logger.info("No fax numbers found for the selected area, retrying...");
			signup.selectAreaCode();
		}
		signup.selectDIDNumber();
		signup.proceedNext1();
		signup.acceptTerms();
		signup.proceedNext2();
		signup.setEmail(email);
		signup.confirmEmail(email);
		signup.setFirstName(firstname);
		signup.setLastName(lastname);
		signup.setBillingCountry(country);
		signup.setBillingZipCode1(zip1);
		signup.setBillingZipCode2(zip2);
		signup.setBillingState();
		signup.setBillingAddress1(address1);
		signup.setBillingAddress2(address2);
		signup.setCompanyName(company);
		signup.setBillingPhoneNumber(phone);
		signup.setBillingCardTypeVisa();
		signup.setBillingCreditCardNumber(creditcardnumber);
		signup.setBillingCreditCardCVV(creditcardcvv);
		signup.setBillingCreditCardMonth(creditcardmonth);
		signup.setBillingCreditCardYear(creditcardyear);
		signup.proceedNext3();
		signup.activateAccount();

		boolean flag = signup.isSignUpSuccess();
		Assert.assertTrue(flag);

		String content = signup.getWelcomeEmail(email, "eFax Plus へようこそ！[eFax]", 60);
		logger.info(content);
		Matcher match1, match2, match3;
		
		match1=Pattern.compile("(eFaxログインID: )(\\d+)").matcher(content);
		match2=Pattern.compile("(PINコード\\(暗証番号\\): )(\\d+)").matcher(content);
		match3=Pattern.compile("(eFax番号: )(.*)").matcher(content);
		
		if (match1.find()) loginId = match1.group(2);
		if (match2.find()) password = match2.group(2);
		if (match3.find()) faxNumber = match3.group(2);
		
		logger.info(loginId + " & " + password + " & " + faxNumber);

		driver.get(Config.efax_JP_myaccountBaseUrl);
		signup.LoginWithCredentials(loginId, password);

		flag = signup.logout();
		Assert.assertTrue(flag);
	}
}
