package com.abnamro.assignment.specs;

import com.abnamro.assignment.helper.IssueApiHelper;
import com.abnamro.assignment.helper.ValidationHelper;
import io.restassured.response.Response;
import com.abnamro.assignment.constants.DataFiles;
import com.abnamro.assignment.helper.TestReportHelper;
import org.testng.annotations.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestResult;
import java.lang.reflect.Method;
public class TestBase {

    private Properties p;
    public ValidationHelper resMsg = new ValidationHelper();
    public Response responseSpec;
    public static String projectId;
    public static String baseUri;
    public static String accessToken;
    private ThreadLocal<String> testName = new ThreadLocal<>();

    public TestBase() {

        String  environment = "staging";
        p = new Properties();

        if (System.getenv("ENVIRONMENT") != null) environment = System.getenv("ENVIRONMENT");
        switch (environment){
            case "staging":
                try {
                    p.load(new FileReader(DataFiles.file_config_staging));
                } catch (IOException e) {
                    TestReportHelper.logError(e.getLocalizedMessage());
                }
                break;
            default:
                System.out.println("Please provide environment name : valid values are - staging ");
        }

        projectId =getProperty("PROJECT_ID");
        baseUri = getProperty("BASE_URI");
        accessToken = getProperty("PRIVATE-TOKEN");
    }

    public String getProperty(String propertyName){
        return p.getProperty(propertyName);
    }

    @BeforeTest
    public void setUpData() {
        IssueApiHelper commonfunc = new IssueApiHelper();
        commonfunc.delete_all_issues(projectId);
    }

    @BeforeMethod
    public void beforeMethod(Method method, Object[] testData) {
        TestReportHelper.startTest(method.getName(), "Base URI : " + baseUri);
    }

    @AfterMethod
    public void afterMethod(ITestResult iTestResult) {
        //Checking the status of Test and saving it
        if (iTestResult.getStatus() == ITestResult.FAILURE) {
            TestReportHelper.getTest().log(LogStatus.FAIL, iTestResult.getMethod().getDescription());
            TestReportHelper.logError("TEST FAILED - Error is: " + iTestResult.getThrowable());
        } else if (iTestResult.getStatus() == ITestResult.SKIP) {
            TestReportHelper.getTest().log(LogStatus.SKIP, iTestResult.getMethod().getDescription());
            TestReportHelper.logError("TEST SKIPPED - Error is" + iTestResult.getThrowable());
        } else {
            TestReportHelper.getTest().log(LogStatus.PASS, iTestResult.getMethod().getDescription());
            TestReportHelper.logPass("TEST PASSED \n");
        }

        TestReportHelper.logInfo("Class name --> " + iTestResult.getTestClass().getRealClass().getName() + ", Method name --> "+ iTestResult.getMethod().getMethodName());  //log class name, method name in report

        Object[] Object_Array= iTestResult.getParameters(); //log test parameters in report
        String[] String_Array=new String[Object_Array.length];

        for (int i=0;i<String_Array.length;i++)
            TestReportHelper.logInfo("Test parameters - " + Object_Array[i].toString());

        if(responseSpec !=null) {
            TestReportHelper.logInfo("Response code --> " + responseSpec.getStatusCode() + " " + responseSpec.getStatusLine());
            TestReportHelper.logInfo("Response body --> " + responseSpec.getBody().asPrettyString());
        }
        //To log the testcase result in Extent Report
        TestReportHelper.getReporter().endTest(TestReportHelper.getTest());
        TestReportHelper.getReporter().flush();
    }

}
