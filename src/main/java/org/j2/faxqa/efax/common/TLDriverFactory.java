package org.j2.faxqa.efax.common;

import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;

public class TLDriverFactory {

	private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

	public synchronized static void setTLDriver() {

		LogManager.getLogger().info("WebBrowser = " + System.getProperty("webbrowser"));
		switch (System.getProperty("webbrowser").toLowerCase()) {

		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions choptions = new ChromeOptions();
			//choptions.setCapability("InPrivate", true);
			choptions.setExperimentalOption("excludeSwitches", Arrays.asList("disable-component-update")); 
			choptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			choptions.addArguments("--ignore-certificate-errors");		
			choptions.addArguments("--disable-features=EnableEphemeralFlashPermission");
			choptions.addArguments("--disable-features=VizDisplayCompositor");
			choptions.addArguments("--start-maximized");
			choptions.addArguments("--disable-infobars");
			choptions.setExperimentalOption("useAutomationExtension", false);
			
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("profile.content_settings.exceptions.plugins.*,*.setting", 1);
			prefs.put("profile.default_content_settings.state.flash",1);
			choptions.setExperimentalOption("prefs", prefs);
			
			tlDriver.set(new ChromeDriver(choptions));
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions firefoxoptions = new FirefoxOptions();
			firefoxoptions.setCapability("marionette", true);
			tlDriver.set(new FirefoxDriver(firefoxoptions));
			break;

		case "ie":
			WebDriverManager.iedriver().setup();
			InternetExplorerOptions ieoptions = new InternetExplorerOptions();
			ieoptions.setCapability("InPrivate", true);
			ieoptions.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			tlDriver.set(new InternetExplorerDriver(ieoptions));
			break;

		case "phantomjs":
			WebDriverManager.phantomjs().setup();
			tlDriver.set((WebDriver) new PhantomJsDriverManager());
			break;
			
		case "edge":
			WebDriverManager.edgedriver().setup();
			EdgeOptions edgeoptions = new EdgeOptions();
			edgeoptions.setCapability("InPrivate", true);
			tlDriver.set(new EdgeDriver(edgeoptions));
			break;
			
		case "opera":
			WebDriverManager.operadriver().setup();
			OperaOptions operaoptions = new OperaOptions();
			operaoptions.setCapability("InPrivate", true);
			tlDriver.set(new OperaDriver(operaoptions));
			break;
			
		default:
			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeoptions = new ChromeOptions();
			chromeoptions.setCapability("InPrivate", true);
			chromeoptions.addArguments("--ignore-certificate-errors");
			chromeoptions.addArguments("--disable-features=VizDisplayCompositor");
			chromeoptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			chromeoptions.addArguments("--start-maximized");
			chromeoptions.addArguments("--disable-infobars");			
			chromeoptions.setExperimentalOption("useAutomationExtension", false);
			tlDriver.set(new ChromeDriver(chromeoptions));
			break;

		}

		LogManager.getLogger().info("Created ThreadLocal webdriver.");
		
		int wait = 45;
		tlDriver.get().manage().deleteAllCookies();
		tlDriver.get().manage().window().maximize();
		tlDriver.get().manage().timeouts().implicitlyWait(wait, TimeUnit.SECONDS);
		LogManager.getLogger().info("Setting  implicit-wait to '" + wait + "' seconds");
	}

	public synchronized static WebDriver getTLDriver() {
		return tlDriver.get();
	}

}