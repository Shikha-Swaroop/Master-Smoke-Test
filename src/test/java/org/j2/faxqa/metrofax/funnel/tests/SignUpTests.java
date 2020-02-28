package org.j2.faxqa.metrofax.funnel.tests;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.j2.faxqa.efax.common.BaseTest;
import org.j2.faxqa.efax.common.Config;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.j2.faxqa.efax.common.TestRail;
import org.j2.faxqa.efax.efax_us.funnel.pageobjects.SignUpPage;
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

	@TestRail(id = "C8540")
	@Test(enabled = true, groups = { "smoke" }, priority = 1, description = "MetroFax > Funnel > My Account > Validate Account Registration is Successful & Login to My Account without issues")
	public void verifyNewUserSignUpLogin(ITestContext context) throws Exception {
		WebDriver driver = null;

		driver = TLDriverFactory.getTLDriver();
		logger.info("Navigating to - " + Config.efax_US_funnelBaseUrl);
		driver.navigate().to(Config.efax_US_funnelBaseUrl);

		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
		String firstname = "QATest";
		String lastname = new Faker().address().firstName();
		String email = firstname + "." + lastname + random.substring(3, 8) + "@mailinator.com";
		String phone = String.format("%1$s%2$s", ThreadLocalRandom.current().nextInt(10000, 99999),
				ThreadLocalRandom.current().nextInt(10000, 99999));
		String address1 = new Faker().address().streetAddress();
		String address2 = new Faker().address().zipCode();
		String city = "";
		String pcode = String.format("%s", ThreadLocalRandom.current().nextInt(10000, 99999));
		String state = "";
		String country = "United States";
		String creditcardnumber = "4133738662043055"; // "4872906545490653"; // "441506691331";
		String creditcardmonth = "DEC";
		String creditcardyear = "2025";
		String creditcardcvv = "321";

		SignUpPage signup = new SignUpPage();
		signup.selectCountry(country);
		// signup.selectAreaCode();
		// signup.enterAreaCode("212");
		signup.selectState();
		state = signup.setState();
		city = signup.setCity();
		while (signup.noInventory()) {
			logger.info("No fax numbers found for the selected region, retrying...");
			state = signup.setState();
			city = signup.setCity();
		}
		signup.proceedNext();
		signup.setFirstName(firstname);
		signup.setLastName(lastname);
		signup.setEmail(email);
		signup.proceedToBilling();
		signup.setBillingCardName(firstname + " " + lastname);
		signup.setBillingPhoneNumber(phone);
		signup.setBillingAddress1(address1);
		signup.setBillingAddress2(address2);
		signup.setBillingCountry(country);
		signup.setBillingState(state);
		signup.setBillingCity(city);
		signup.setBillingPostalCode(pcode);
		signup.setBillingCardTypeVisa();
		signup.setBillingCreditCardNumber(creditcardnumber);
		signup.setBillingCreditCardMonth(creditcardmonth);
		signup.setBillingCreditCardYear(creditcardyear);
		signup.setBillingCreditCardCVV(creditcardcvv);
		signup.agreeToTermsConditions();
		signup.activateAccount();

		boolean flag = signup.isSignUpSuccess();
		Assert.assertTrue(flag);

		String[] login = signup.getLoginDetails().split(";");
		String fax = login[0];
		String pin = login[1];

		if (signup.isLoginBtnAvailable()) {
			signup.clickLogin();
		}

		if (signup.isLoggedIn()) {
			signup.clickLogin();
		} else {
			signup.LoginWithCredentials(fax, pin);
		}

		Assert.assertTrue(flag);

		flag = signup.logout();
		Assert.assertTrue(flag);
	}
}
