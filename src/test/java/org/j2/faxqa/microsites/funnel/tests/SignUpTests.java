package org.j2.faxqa.microsites.funnel.tests;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.j2.faxqa.efax.common.BaseTest;
import org.j2.faxqa.efax.common.Config;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.j2.faxqa.efax.common.TestRail;
import org.j2.faxqa.microsites.funnel.pageobjects.SignUpPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class SignUpTests extends BaseTest {

	protected static final Logger logger = LogManager.getLogger();

	@TestRail(id = "C7862")
	@Test(enabled = true, groups = {"smoke" }, priority = 1, description = "Microsites > SignUp for a new user account")
	public void verifyNewUserSignUpLogin(ITestContext context) throws Exception {
		WebDriver driver = TLDriverFactory.getTLDriver();
		logger.info("Navigating to - " + Config.microsites_funnelBaseUrl);
		driver.navigate().to(Config.microsites_funnelBaseUrl);

		Faker testdata = new Faker();
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
		String firstname = "QA" + testdata.address().firstName();
		String lastname = testdata.address().lastName();
		String email = firstname + "." + lastname + random.substring(3, 8) + "@mailinator.com";

		String phone = String.format("%1$s%2$s", ThreadLocalRandom.current().nextInt(10000, 99999),
				ThreadLocalRandom.current().nextInt(10000, 99999));
		String address1 = new Faker().address().streetAddress();
		String address2 = new Faker().address().zipCode();
		String city = "";
		String pcode = String.format("%s", ThreadLocalRandom.current().nextInt(10000, 99999));
		String country = "";
		String creditcardnumber = Config.microsites_creditCard;
		String creditcardmonth = "December";
		String creditcardyear = "2025";
		String creditcardcvv = "321";

		SignUpPage signup = new SignUpPage();
		city = signup.setCity();
		signup.setEmail("jagadeesh.m@vensiti.com");
		signup.setFirstName(firstname);
		signup.setLastName(lastname);
		signup.setStreetAddress(address1);
		signup.setStreetAddress2(address2);
		signup.setCityTown(city);
		signup.selectZipCode(pcode);
		signup.selectCountry();
		signup.setPhoneNumber(phone);
		// signup.setCradType();
		signup.setccNumber(creditcardnumber);
		signup.setccCVV(creditcardcvv);
		signup.setccMonth(creditcardmonth);
		signup.setccYear(creditcardyear);

		signup.activateAccount();

		Assert.assertTrue(signup.isSignUpSuccess() && signup.login());
	}
}
