package org.j2.faxqa.efax.common;

public class Config {


	public static int myccount_sendWait;
	public static int myccount_receiveWait;
	public static int myccount_inboxWait;

	/* efax US related config*/ 
	public static String efax_US_myaccountBaseUrl;
	public static String efax_US_funnelBaseUrl;
	public static String efax_US_landingpageBaseUrl;
	public static String DID_US;
	public static String PIN_US;
	public static String creditCard_US;
	 
	/* efax UK related config*/ 
	public static String efax_UK_myaccountBaseUrl;
	public static String efax_UK_funnelBaseUrl;
	public static String efax_UK_landingpageBaseUrl;
	public static String DID_UK;
	public static String PIN_UK;
	public static String creditCard_UK;
	
	/* efax JP related config*/ 
	public static String efax_JP_myaccountBaseUrl;
	public static String efax_JP_funnelBaseUrl;
	public static String efax_JP_landingpageBaseUrl;
	public static String DID_JP;
	public static String PIN_JP;
	public static String creditCard_JP;
	
	/* efax myfax related config*/ 
	public static String myfax_myaccountBaseUrl;
	public static String myfax_funnelBaseUrl;
	public static String myfax_landingpageBaseUrl;
	public static String myfax_DID;
	public static String myfax_Password;
	public static String myfax_creditCard;
	
	/* efax metrofax related config*/ 
	public static String metrofax_myaccountBaseUrl;
	public static String metrofax_funnelBaseUrl;
	public static String metrofax_landingpageBaseUrl;
	public static String metrofax_DID;
	public static String metrofax_PIN;
	public static String metrofax_creditCard;
	
	/* efax microsites related config*/ 
	public static String microsites_myaccountBaseUrl;
	public static String microsites_funnelBaseUrl;
	public static String microsites_landingpageBaseUrl;
	public static String microsites_DID;
	public static String microsites_PIN;
	public static String microsites_creditCard;
	
	/* efax microsites related config*/ 
	public static String corp_myaccountBaseUrl;
	public static String corp_DID;
	public static String corp_password;
	
	/*
	 * Add following entries to your host file
	 * (C:\Windows\System32\drivers\etc\hosts) 
	 * 64.125.38.77 qa.efaxcorporate.com
	 * 10.160.90.170 test.efaxcorporate.com 
	 * 10.128.90.18 offline.efaxcorporate.com
	 * 10.160.110.75 mfdev1.efaxcorporate.com 
	 * 10.160.110.74 sbdev.efaxcorporate.com
	 * 10.128.90.18 offline.efaxcorporate.com 
	 * 10.160.110.75 myfx1.efaxcorporate.com
	 * 
	 */
	// -------------------------------------------------------------------------------------//

	// Set value from the base.properties
	// ---------- URLs
	public static String mgmtBaseUrl;
	public static String myAccountBaseUrl;
	public static String adminCreateNewUser;
	public static String myAccountOverview;
	public static String getContactInfo;
	public static String sampleCoverPage;
	// ---------- DB Connection
	public static String dbConnectionUrl;
	public static String dbUserName;
	public static String dbPassword;
	// ---------- file path
	public static String filePath = "src//main//java//j2//eFaxCorporate//utils";
	// ---------- Secure MGMT Account
	public static String secureAccountNumberMGMT;
	public static String secureAdministratorNameMGMT;
	public static String securePasswordMGMT;
	// ---------- Secure MGMT Account_Expire
	public static String secureExpireAccountNumberMGMT;
	public static String secureExpireAdministratorNameMGMT;
	public static String secureExpirePasswordMGMT;
	// ---------- NonSecure MGMT Account
	public static String AccountNumberMGMT;
	public static String AdministratorNameMGMT;
	public static String PasswordMGMT;
	// ---------- NonSecure MGMT Account
	public static String nonsecureAccountNumber_MGMT; // Set value from the base.properties
	public static String nonsecureadministratorName_MGMT;
	public static String nonsecurepassword_MGMT;
	// ---------- NonSecure MGMT SuperAdmin Account
	public static String nonSecureAccountNumberMGMTSuperAdmin;
	public static String nonSecureAdministratorNameMGMTSuperAdmin;
	public static String nonSecurePasswordMGMTSuperAdmin;
	// ---------- NonSecure MyAccount_SendFax & ViewFax
	
}