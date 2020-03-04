package org.j2.faxqa.efax.common;

import org.testng.annotations.Listeners;

@Listeners({ TestNGReportListener.class, TestExecutionListener.class })	
public class BaseTest {
	//This is where the common code across all the Tests goes 
}