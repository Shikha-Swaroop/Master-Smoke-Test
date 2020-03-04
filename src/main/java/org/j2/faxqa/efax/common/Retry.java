package org.j2.faxqa.efax.common;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
	 
    private int count = 0;
    private int maxTry = 0;
 
    @Override
    public boolean retry(ITestResult iTestResult) {
    	maxTry = Config.retryAttempts;
        if (!iTestResult.isSuccess()) {                      
            if (count < maxTry) {                            
            	iTestResult.getTestContext().getFailedTests().removeResult(iTestResult);
            	iTestResult.getTestContext().getSkippedTests().removeResult(iTestResult);
                count++;                                     
                iTestResult.setStatus(ITestResult.FAILURE);  
                return true;                                 
            } else {
                iTestResult.setStatus(ITestResult.FAILURE);  
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS);      
        }
        return false;
    }
 
}