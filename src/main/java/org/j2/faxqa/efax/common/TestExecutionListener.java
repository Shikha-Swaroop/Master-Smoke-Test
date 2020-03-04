package org.j2.faxqa.efax.common;

import java.io.File;
import java.io.IOException;
import java.rmi.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TestExecutionListener implements IInvokedMethodListener, ISuiteListener, ITestListener, IConfigurationListener {
	protected static final Logger logger = LogManager.getLogger();
	private List<Map<String, Object>> results = Collections.synchronizedList(new ArrayList<Map<String, Object>>());
	public String testRunId = null;
	public APIClient tr_client;
	protected WebDriver driver;

	@Override
	public void onStart(ISuite suite) {
		testRunId = System.getProperty("testRunId");
		logger.info("Initializing....");
		EnvironmentSetup.setupEnvironment();
		if (System.getProperty("os.name").contains("Windows")) {
			Process process = null;
			try {
				process = Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe, /IM IEDriverServer.exe, /IM edgedriver.exe, /IM geckodriver.exe, /IM operadriver.exe /T");
			} catch (IOException e) {
				e.printStackTrace();
			}
			process.destroy();
		}
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		Reporter.setCurrentTestResult(testResult);
		TLDriverFactory.setTLDriver();
	}

	@Override
	public void onTestFailure(ITestResult testResult) {
		if (testResult.getThrowable() != null) {
			testResult.getThrowable().printStackTrace();
			LogManager.getLogger().error(ExceptionUtils.getFullStackTrace(testResult.getThrowable()));
		}
		logger.info("Test failed : " + testResult.getMethod().getConstructorOrMethod().getMethod());
	}

	@Override
	public void onTestSkipped(ITestResult testResult) {
		if (testResult.getThrowable() != null) {
			testResult.getThrowable().printStackTrace();
			LogManager.getLogger().error(ExceptionUtils.getFullStackTrace(testResult.getThrowable()));
		}
		logger.info("Test skipped : " + testResult.getMethod().getConstructorOrMethod().getMethod());
		testResult.setStatus(ITestResult.FAILURE);
		logger.info("Marking Skipped Test as Failure to Retry again.");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		logger.info("Test passed - " + result.getMethod().getConstructorOrMethod().getMethod());
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		Reporter.setCurrentTestResult(testResult);

		if (!testResult.isSuccess())
			captureScreen(testResult);

		if (TLDriverFactory.getTLDriver() != null) {
			TLDriverFactory.getTLDriver().quit();
			logger.info("Quitting WebDriver...");
		}

		TestRail testrail = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(TestRail.class);

		if (testrail != null) {
			String testrail_caseid = testrail.id().substring(1);
			logger.info("Updating testresult.");
			updateResult(testResult, testrail_caseid, testResult.getStatus() == ITestResult.SUCCESS);
		} else
			return;
	}

	@Override
	public void onFinish(ISuite suite) {
		try {
			if (Boolean.parseBoolean(System.getProperty("uploadResults"))) {
				//logger.info("Creating a new TestRun in TestRail.");
				//createTestrun(suite);
				logger.info("Uploading Results to TestRail against TestRunId : " + testRunId);
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
		String path = testResult.getTestContext().getOutputDirectory();
		String relPath = "\\screenshots\\" + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new Date()) + ".png";
		String fullPath = path + relPath;
		System.out.println(fullPath);
		File dest = new File(fullPath);
		try {
			FileUtils.copyFile(src, dest);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		Reporter.log("<br><img src='" + ".\\Suite" + relPath + "' style=\"width: 33%; height: 33%\"/></br>");
	}

	private void updateResult(ITestResult testResult, String testrailid, boolean pass) {

		logger.info("Capturing test logs...");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("case_id", testrailid);
		result.put("status_id", testResult.getStatus() == 1 ? 1 : testResult.getStatus() == 2 ? 5 : 2);
		result.put("comment", (testResult.getStatus() != 1 ? "Unexpected error while executing this test." : "Executed successfully.") + "\r\n" + Reporter.getOutput(testResult).stream().collect(Collectors.joining("\r\n")));
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
		List<ITestNGMethod> temp = suite.getAllMethods().stream().filter(m -> m.getConstructorOrMethod().getMethod().getAnnotation(TestRail.class) != null).collect(Collectors.toList());
		ArrayList<String> testids = temp.stream().map(m -> m.getConstructorOrMethod().getMethod().getAnnotation(TestRail.class).id().substring(1)).collect(Collectors.toCollection(ArrayList::new));

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
			result = (JSONObject) tr_client.sendPost(String.format("add_run/%s", suite.getXmlSuite().getAllParameters().get("testrailprojectid").replace("P", "")), newrun);
			logger.info(new GsonBuilder().setPrettyPrinting().create().toJson(result));
		} catch (Throwable e) {
			logger.error("TestRail Host Not found. [Please check if VPN is enabled.]");
			e.printStackTrace();
			return;
		}
		testRunId = result.get("id").toString();
		logger.info("***  TestRun [" + suite_name + "] created with id [" + testRunId + "] with testcases : " + testids + "  ***");

	}

	private void uploadResults() {
		tr_client = new APIClient(System.getProperty("testrailBaseUrl"));
		JSONArray result = null;

		try {
			Map<String, List<Map<String, Object>>> objresults = new HashMap<String, List<Map<String, Object>>>();
			objresults.put("results", results);
			logger.info(new GsonBuilder().setPrettyPrinting().create().toJson(objresults));
			result = (JSONArray) tr_client.sendPost(String.format("add_results_for_cases/%s", testRunId.replace("R", "")), objresults);
			logger.info(new GsonBuilder().setPrettyPrinting().create().toJson(result));
		} catch (Throwable e) {
			e.printStackTrace();
			return;
		}
	}

}