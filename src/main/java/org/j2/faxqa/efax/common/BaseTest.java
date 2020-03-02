package org.j2.faxqa.efax.common;

import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;

@Listeners({TestExecutionListener.class, TestNGReportListener.class})	
public class BaseTest implements IHookable {

	@Override
	public final void run(IHookCallBack callBack, ITestResult testResult) {
		//execute something before every tests case
		callBack.runTestMethod(testResult);
		//execute something after every tests case		
	}
}