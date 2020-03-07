package org.j2.faxqa.efax.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.Credibility;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

public class EnvironmentSetup extends org.testng.Reporter {
	protected static final Logger logger = LogManager.getLogger();

	public static void setupEnvironment() {

		Properties prop = new Properties();
		String file = "/environment." + System.getProperty("environment") + ".properties";
		InputStream inputstream = EnvironmentSetup.class.getResourceAsStream(file);
		try {
			logger.info("Current Environment = " + System.getProperty("environment"));
			logger.info("Setting-up environment specific configuration.");
			logger.info("Picking-up config values from '" + file + "'");
			prop.load(inputstream);
		} catch (Throwable e) {
			logger.error("Failed to load '%s'", file);
			logger.error(e.getCause());
			e.printStackTrace();
			return;
		}

		//********************** Consumer **************************************//
		Config.retryAttempts = Integer.parseInt(prop.getProperty("retryAttempts"));
		Config.myccount_sendWait = Integer.parseInt(prop.getProperty("myccount_sendWait"));
		Config.myccount_receiveWait = Integer.parseInt(prop.getProperty("myccount_receiveWait"));
		Config.myccount_inboxWait = Integer.parseInt(prop.getProperty("myccount_inboxWait"));
		
		Config.fstorhost = prop.getProperty("fstorhost");
		Config.fstorclientId = prop.getProperty("fstorclientId");
		Config.fstorclientSecret = prop.getProperty("fstorclientSecret");
		Config.fstorregion = prop.getProperty("fstorregion");
		Config.fstorservicekey = prop.getProperty("fstorservicekey");
		
		Config.efax_US_myaccountBaseUrl = prop.getProperty("efax_US_myaccountBaseUrl");
		Config.efax_US_funnelBaseUrl = prop.getProperty("efax_US_funnelBaseUrl");
		Config.efax_US_landingpageBaseUrl = prop.getProperty("efax_US_landingpagelBaseUrl");
		Config.DID_US = prop.getProperty("DID_US");
		Config.PIN_US = prop.getProperty("PIN_US");
		Config.creditCard_US = prop.getProperty("CreditCard_US");

		Config.efax_UK_myaccountBaseUrl = prop.getProperty("efax_UK_myaccountBaseUrl");
		Config.efax_UK_funnelBaseUrl = prop.getProperty("efax_UK_funnelBaseUrl");
		Config.efax_UK_landingpageBaseUrl = prop.getProperty("efax_UK_landingpagelBaseUrl");
		Config.DID_UK = prop.getProperty("DID_UK");
		Config.PIN_UK = prop.getProperty("PIN_UK");
		Config.creditCard_UK = prop.getProperty("CreditCard_UK");
		
		Config.efax_JP_myaccountBaseUrl = prop.getProperty("efax_JP_myaccountBaseUrl");
		Config.efax_JP_funnelBaseUrl = prop.getProperty("efax_JP_funnelBaseUrl");
		Config.efax_JP_landingpageBaseUrl = prop.getProperty("efax_JP_landingpagelBaseUrl");
		Config.DID_JP = prop.getProperty("DID_JP");
		Config.PIN_JP = prop.getProperty("PIN_JP");
		Config.creditCard_JP = prop.getProperty("CreditCard_JP");
		
		Config.myfax_myaccountBaseUrl = prop.getProperty("myfax_myaccountBaseUrl");
		Config.myfax_funnelBaseUrl = prop.getProperty("myfax_funnelBaseUrl");
		Config.myfax_landingpageBaseUrl = prop.getProperty("myfax_landingpagelBaseUrl");
		Config.myfax_DID = prop.getProperty("myfax_DID");
		Config.myfax_Password = prop.getProperty("myfax_Password");
		Config.myfax_creditCard = prop.getProperty("myfax_CreditCard");
		
		Config.metrofax_myaccountBaseUrl = prop.getProperty("metrofax_myaccountBaseUrl");
		Config.metrofax_funnelBaseUrl = prop.getProperty("metrofax_funnelBaseUrl");
		Config.metrofax_landingpageBaseUrl = prop.getProperty("metrofax_landingpagelBaseUrl");
		Config.metrofax_DID = prop.getProperty("metrofax_DID");
		Config.metrofax_PIN = prop.getProperty("metrofax_PIN");
		Config.metrofax_creditCard = prop.getProperty("metrofax_creditCard");
		
		Config.microsites_myaccountBaseUrl = prop.getProperty("microsites_myaccountBaseUrl");
		Config.microsites_funnelBaseUrl = prop.getProperty("microsites_funnelBaseUrl");
		Config.microsites_landingpageBaseUrl = prop.getProperty("microsites_landingpagelBaseUrl");
		Config.microsites_DID = prop.getProperty("microsites_DID");
		Config.microsites_PIN = prop.getProperty("microsites_PIN");
		Config.microsites_creditCard = prop.getProperty("microsites_CreditCard");
		
		Config.corp_myaccountBaseUrl = prop.getProperty("corp_myaccountBaseUrl");
		Config.corp_DID = prop.getProperty("corp_DID");
		Config.corp_password = prop.getProperty("corp_password");
		
		//********************** Corporate **************************************//
		
		
		Config.mgmtBaseUrl = prop.getProperty("baseUrl_MGMT");
		Config.myAccountBaseUrl = prop.getProperty("baseUrl_MyAccount");
		Config.dbConnectionUrl = prop.getProperty("dbConnectionUrl");

		Config.dbUserName = prop.getProperty("dbUserName");
		Config.dbPassword = prop.getProperty("dbPassword");

		Config.AccountNumberMGMT = prop.getProperty("secureAccount_MGMT_AccountNumber");
		Config.AdministratorNameMGMT = prop.getProperty("secureAccount_MGMT_AdministratorName");
		Config.PasswordMGMT = prop.getProperty("secureAccount_MGMT_Password");

		Config.nonsecureAccountNumber_MGMT = prop.getProperty("nonSecureAccount_MGMT_AccountNumber");
		Config.nonsecureadministratorName_MGMT = prop.getProperty("nonSecureAccount_MGMT_AdministratorName");
		Config.nonsecurepassword_MGMT = prop.getProperty("nonSecureAccount_MGMT_Password");

	}

}