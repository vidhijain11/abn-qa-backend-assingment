package com.abnamro.assignment.specs.apitests;

import com.abnamro.assignment.constants.Endpoint;
import com.abnamro.assignment.datafactory.CreateIssueDataProvider;
import com.abnamro.assignment.helper.IssueApiHelper;
import com.abnamro.assignment.helper.ValidationHelper;
import com.abnamro.assignment.models.request.BaseRequestModel;
import com.abnamro.assignment.models.request.CreateIssueModel;
import com.abnamro.assignment.models.response.IssueModel;
import com.abnamro.assignment.specs.TestBase;
import com.abnamro.assignment.utils.HttpUtil;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateIssueTest extends TestBase {

    IssueApiHelper apiHelper = new IssueApiHelper();
    ValidationHelper valdHelper = new ValidationHelper();
    HttpUtil util = new HttpUtil();

    @Test (dataProvider = "create_issue_test_data", dataProviderClass = CreateIssueDataProvider.class, description = "[POSITIVE] Should be able to create issue")
    public void create_issue(BaseRequestModel requestModel) {

        CreateIssueModel testData = requestModel.createIssueRequestPayload;
        //create issue
         responseSpec = apiHelper.create_issue(projectId, testData);
        //validate issue is created
        responseSpec.then().assertThat().statusCode(201);
        IssueModel actualResponse = responseSpec.as(IssueModel.class);

        Assert.assertNotNull(actualResponse.id);
        Assert.assertNotNull(actualResponse.projectId);
        Assert.assertEquals(actualResponse.title, testData.title);
        Assert.assertEquals(actualResponse.description, testData.description);
        Assert.assertEquals(actualResponse.state, "opened");
        Assert.assertEquals(actualResponse.type, "ISSUE");
        Assert.assertEquals(actualResponse.issueType, "issue");
        if(testData.confidential != null)
            Assert.assertEquals(actualResponse.confidential, testData.confidential);
        else
            Assert.assertFalse(actualResponse.confidential);
        if(testData.labels != null)
            Assert.assertEquals(actualResponse.labels, testData.labels);
        else
            Assert.assertTrue(actualResponse.labels.isEmpty());
    }

    @Test (dataProvider = "create_issue_with_invalid_request_payload", dataProviderClass = CreateIssueDataProvider.class, description = "[NEGATIVE] Should not be able to create issue with invalid data")
    public void create_issue_with_invalid_request_payload(Object testData) {

        JSONObject data = new JSONObject(testData.toString());
        //create issue with invalid data
        responseSpec = apiHelper.create_issue(projectId, data.getJSONObject("request").toString());
        //validate error message
       valdHelper.validate_invalid_create_issue_request_error(responseSpec, data.getJSONObject("response"));
    }

    @Test (description = "[NEGATIVE] Should not be able to create issue with expired access token")
    public void create_issue_with_expired_token() {
        //create issue with expired token
        responseSpec = util.postRequest(Endpoint.create_issue(projectId),
                HttpUtil.getExpiredTokenHeader(), CreateIssueDataProvider.default_create_issue_payload());
        //validate error message
        valdHelper.validate_invalid_token_error(responseSpec);
    }

}
