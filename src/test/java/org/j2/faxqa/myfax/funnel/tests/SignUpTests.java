package org.j2.faxqa.myfax.funnel.tests;

import java.util.UUID;
import org.j2.faxqa.efax.common.BaseTest;
import org.j2.faxqa.efax.common.Config;
import org.j2.faxqa.efax.common.Retry;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.j2.faxqa.efax.common.TestRail;
import org.j2.faxqa.myfax.funnel.pageobjects.SignUpPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

//@Listeners({TestExecutionListener.class, TestNGReportListener.class})
public class SignUpTests extends BaseTest {

	@TestRail(id = "C8539")
	@Test(enabled = true, retryAnalyzer = Retry.class, groups = { "smoke" }, priority = 1, description = "MyFax > Funnel > My Account > Validate Account Registration is Successful & Login to My Account without issues")
	public void verifyNewUserSignUpLogin(ITestContext context) throws Exception {
		WebDriver driver = null;

		driver = TLDriverFactory.getTLDriver();
		driver.get(Config.myfax_funnelBaseUrl);
		driver.navigate().refresh();
		driver.navigate().refresh();
		Faker testdata = new Faker();
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
		String firstname = "QA" + testdata.address().firstName().toUpperCase().replace("'", "");
		String lastname = testdata.address().lastName().toUpperCase().replace("'", "");
		String email = firstname + "." + lastname + testdata.number().digits(3) + "@mailinator.com";
		String phone = testdata.number().digits(10);
		String address1 = testdata.address().buildingNumber();
		String address2 = testdata.address().streetAddress();
		String state = "";
		String city = "";
		String zipCode = testdata.address().zipCode();
		String country = "United States";
		String creditcardnumber = Config.myfax_creditCard; // "4872906545490653"; // "441506691331";
		String creditcardmonth = "DEC";
		String creditcardyear = "2025";
		String creditcardcvv = "321";
		String faxNumber = "";
		String password = "";

		SignUpPage signup = new SignUpPage();
		signup.setCountryUnitedStates();
		state = signup.setState();
		city = signup.setCity();
		signup.proceed1();
		
		signup.setName(firstname + " " + lastname);
		signup.setEmail(email);
		signup.proceed2();
		
		signup.setCCHolderName(firstname + " " + lastname);
		signup.setCCType("Visa");
		signup.setBillingCreditCardNumber(creditcardnumber);
		signup.setBillingCreditCardMonth(creditcardmonth);
		signup.setBillingCreditCardYear(creditcardyear);
		signup.setBillingCreditCardCVV(creditcardcvv);
		signup.setBillingPhoneNumber(phone);
		
		signup.setBillingCompany(address1);
		signup.setBillingCountry(country);
		signup.setBillingAddress(address1 + ", " + address2);
		signup.setBillingState(state);
		signup.setBillingCity(city);	
		signup.setBillingPostalCode(zipCode);

		signup.agreeToTermsConditions();
		signup.activateAccount();

		boolean flag = signup.isSignUpSuccess();
		Assert.assertTrue(flag);

		signup.tryLogin();

		Assert.assertTrue(signup.isLoggedIn());
	}
}
