package org.j2.faxqa.efax.common;

import java.io.File;
import java.io.IOException;
import java.rmi.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.testng.IConfigurationListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IRetryAnalyzer;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.zeroturnaround.zip.commons.FileUtils;

import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TestExecutionListener
		implements IInvokedMethodListener, ISuiteListener, ITestListener, IConfigurationListener {
	protected static final Logger logger = LogManager.getLogger();
	private List<Map<String, Object>> results = Collections.synchronizedList(new ArrayList<Map<String, Object>>());
	public String newtestrunid = null;
	public APIClient tr_client;
	protected WebDriver driver;

	@Override
	public void onStart(ISuite suite) {
		logger.info("Initializing...."); 
		try {
			EnvironmentSetup.setupEnvironment();
			//EnvironmentSetup.setupDNSEntries();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		Reporter.setCurrentTestResult(testResult);
		TLDriverFactory.setTLDriver();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		logger.info("Test failed - " + result.getMethod().getConstructorOrMethod().getMethod());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		logger.info("Test skipped - " + result.getMethod().getConstructorOrMethod().getMethod());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		logger.info("Test passed - " + result.getMethod().getConstructorOrMethod().getMethod());
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		Reporter.setCurrentTestResult(testResult);

		if (!testResult.isSuccess()) captureScreen(testResult);
			
		if (TLDriverFactory.getTLDriver() != null) {
			TLDriverFactory.getTLDriver().quit();
			logger.info("Quitting WebDriver...");
		}
		
		TestRail testrail = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(TestRail.class);
		
		if (testrail != null) {
			String testrail_caseid = testrail.id().substring(1);
			updateResult(testResult, testrail_caseid, testResult.getStatus() == ITestResult.SUCCESS);
		} else
			return;
	}

	@Override
	public void onFinish(ISuite suite) {
		logger.info("Done with the execution of all tests.");
		try {
			if (Boolean.parseBoolean(System.getProperty("uploadresults"))) {
				createTestrun(suite);
				uploadResults();
			} else {
				logger.info("'uploadresults=false' in POM.xml - Skipping upload result to TestRail.");
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void captureScreen(ITestResult testResult) {
		File src = (((TakesScreenshot) TLDriverFactory.getTLDriver()).getScreenshotAs(OutputType.FILE));
		String path = testResult.getTestContext().getOutputDirectory() + "\\screenshots\\"
				+ testResult.getMethod().getConstructorOrMethod().getMethod() + ".png";
		System.out.println(path);
		File dest = new File(path);
		try {
			FileUtils.copyFile(src, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Reporter.log("<br><img src='" + path + "' style=\"width: 33%; height: 33%\"");
	}

	private void updateResult(ITestResult testResult, String testrailid, boolean pass) {

		logger.info("Capturing test logs...");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("case_id", testrailid);
		result.put("status_id", testResult.getStatus() == 1 ? 1 : testResult.getStatus() == 2 ? 5 : 2);
		result.put("comment",
				(testResult.getStatus() != 1 ? "Unexpected error while executing this test." : "Executed successfully.")
						+ "\r\n" + Reporter.getOutput(testResult).stream().collect(Collectors.joining("\r\n")));
		result.put("elapsed", (testResult.getEndMillis() - testResult.getStartMillis()) / (1000 * 60));
		result.put("defects", "");
		result.put("version", "");
		// result.put("custom_step_results", new ArrayList<Map>());

		results.add(result);
	}

	private void createTestrun(ISuite suite) {

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String suite_name = System.getenv("JOB_NAME") == null ? "TestRun" : System.getenv("JOB_NAME") + " : ";
		suite_name = String.join("_", suite_name, System.getProperty("environment").toUpperCase());
		suite_name = String.join("_", suite_name, formatter.format(System.currentTimeMillis()));
		List<ITestNGMethod> temp = suite.getAllMethods().stream()
				.filter(m -> m.getConstructorOrMethod().getMethod().getAnnotation(TestRail.class) != null)
				.collect(Collectors.toList());
		ArrayList<String> testids = temp.stream()
				.map(m -> m.getConstructorOrMethod().getMethod().getAnnotation(TestRail.class).id().substring(1))
				.collect(Collectors.toCollection(ArrayList::new));

		tr_client = new APIClient(System.getProperty("testrailBaseUrl"));
		Map<String, Object> newrun = new HashMap<String, Object>();
		newrun.put("suite_id", suite.getXmlSuite().getAllParameters().get("testrailsuiteid").replace("S", ""));
		newrun.put("name", suite_name);
		// newrun.put("assignedto_id", null);
		// newrun.put("refs", null);
		newrun.put("include_all", false);
		// this is where you need to get all the TestCaseIds to create a list of TCs for
		// testrun
		newrun.put("case_ids", testids);
		JSONObject result = null;
		try {
			result = (JSONObject) tr_client.sendPost(String.format("add_run/%s",
					suite.getXmlSuite().getAllParameters().get("testrailprojectid").replace("P", "")), newrun);
			logger.info(new GsonBuilder().setPrettyPrinting().create().toJson(result));
		} catch (Exception e) {
			logger.error("TestRail Host Not found (VPN required?)");
			e.printStackTrace();
			return;
		}
		newtestrunid = result.get("id").toString();
		logger.info("***  TestRun [" + suite_name + "] created with id [" + newtestrunid + "] with testcases : "
				+ testids + "  ***");

	}

	private void uploadResults() {

		JSONArray result = null;

		try {
			Map<String, List<Map<String, Object>>> objresults = new HashMap<String, List<Map<String, Object>>>();
			objresults.put("results", results);
			result = (JSONArray) tr_client.sendPost(String.format("add_results_for_cases/%s", newtestrunid),
					objresults);
			logger.info(new GsonBuilder().setPrettyPrinting().create().toJson(result));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

}