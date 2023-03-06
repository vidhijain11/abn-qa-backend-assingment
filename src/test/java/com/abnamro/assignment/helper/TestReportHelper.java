package com.abnamro.assignment.helper;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.util.HashMap;
import java.util.Map;

public class TestReportHelper {

    static ExtentReports extent;
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    final static String filePath = "./TestReport.html";

    //ExtentManager - Creates the TestReport.html
    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            extent = new ExtentReports(filePath, true);
        }
        return extent;
    }

    //To Start the Testcase in Extent Report
    public static synchronized ExtentTest startTest(String testName, String desc) {
        extent = getReporter();
        ExtentTest test = extent.startTest(testName, desc);
        extentTestMap.put((int)(Thread.currentThread().getId()), test);
        return test;
    }

    //To End the Testcase in Extent Report
    public static synchronized void endTest() {
        extent.endTest((ExtentTest) extentTestMap.get((int) (Thread.currentThread().getId())));
    }

    //To get the Testcase from Extent
    public static synchronized ExtentTest getTest() {
        return (ExtentTest) extentTestMap.get((int)(Thread.currentThread().getId()));
    }

    /**
     * Prints Log Information to report
     * @param info
     */
    public static void logInfo(String info) {
        TestReportHelper.getTest().log(LogStatus.INFO, info);
    }

    /**
     * Prints Test status as Pass to report
     * @param info
     */
    public static void logPass(String info) {
        TestReportHelper.getTest().log(LogStatus.PASS, info);
    }

    /**
     * Prints Test status as Error to report
     * @param error
     */
    public static void logError(String error) {
        TestReportHelper.getTest().log(LogStatus.ERROR, error);
    }

    /**
     * Prints Test status as warning to report
     * @param error
     */
    public static void logWarn(String error) {
        TestReportHelper.getTest().log(LogStatus.WARNING, error);
    }
}
