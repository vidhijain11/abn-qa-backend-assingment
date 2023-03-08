package com.abnamro.assignment.specs.apitests;

import com.abnamro.assignment.datafactory.CreateIssueDataProvider;
import com.abnamro.assignment.datafactory.EditIssueDataProvider;
import com.abnamro.assignment.helper.IssueApiHelper;
import com.abnamro.assignment.models.request.BaseRequestModel;
import com.abnamro.assignment.models.request.QueryParamModel;
import com.abnamro.assignment.models.response.IssueModel;
import com.abnamro.assignment.specs.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EditIssueTest extends TestBase {

    IssueApiHelper apiHelper = new IssueApiHelper();

    @Test (dataProvider = "edit_issue_test_data", dataProviderClass = EditIssueDataProvider.class, description = "[POSITIVE] Should be able to update issue.")
    public void edit_issue(BaseRequestModel testData) {
        //create issue
        String preReqIssueIId = apiHelper.create_issue_and_get_issueIId(projectId, CreateIssueDataProvider.default_create_issue_payload());
        //edit issue
        responseSpec = apiHelper.edit_issue(projectId, preReqIssueIId, testData.params);
        //validate issue is updated
        responseSpec.then().assertThat().statusCode(200);
        IssueModel issue = responseSpec.as(IssueModel.class);

        for ( QueryParamModel param: testData.params
             ) {
         switch (param.paramName) {
             case EditIssueDataProvider.TITLE:
                 Assert.assertEquals(issue.title, param.paramValue);
                 break;
             case EditIssueDataProvider.ISSUE_TYPE:
                 Assert.assertEquals(issue.issueType, param.paramValue);
                 break;
             case EditIssueDataProvider.STATE_EVENT:
                 Assert.assertEquals(issue.state, param.paramValue+"d");
                 break;
             default:
                 throw new IllegalStateException("Invalid parameter: " + param.paramName);
         }
        }
    }

}
