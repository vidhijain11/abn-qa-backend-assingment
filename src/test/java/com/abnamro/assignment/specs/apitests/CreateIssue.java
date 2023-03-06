package com.abnamro.assignment.specs.apitests;

import com.abnamro.assignment.datafactory.CreateIssueDataProvider;
import com.abnamro.assignment.helper.IssueApiHelper;
import com.abnamro.assignment.helper.ValidationHelper;
import com.abnamro.assignment.models.request.CreateIssueModel;
import com.abnamro.assignment.models.response.IssueModel;
import com.abnamro.assignment.specs.TestBase;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class CreateIssue extends TestBase {

    IssueApiHelper apiHelper = new IssueApiHelper();
    ValidationHelper valdHelper = new ValidationHelper();

    @Test (dataProvider = "create_issue_test_data", dataProviderClass = CreateIssueDataProvider.class, description = "[POSITIVE] Should be able to create issue")
    public void create_issue(CreateIssueModel testData) {

        //create issue
         responseSpec = apiHelper.create_issue(projectId, testData.payload);
        //validate issue is created
        responseSpec.then().assertThat().statusCode(201);
        IssueModel issue = responseSpec.as(IssueModel.class);
    }

    @Test (dataProvider = "create_issue_invalid_request", dataProviderClass = CreateIssueDataProvider.class, description = "[NEGATIVE] Should not be able to create issue with invalid data")
    public void create_issue_with_invalid_data(Object testData) {

        JSONObject data = new JSONObject(testData.toString());
        //create issue with invalid data
        responseSpec = apiHelper.create_issue(projectId, data.getJSONObject("request"));
        //validate error message
       valdHelper.validate_error_message(responseSpec, data.getJSONObject("response"));
    }

}
