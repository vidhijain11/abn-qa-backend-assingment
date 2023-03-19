package com.abnamro.assignment.specs.apitests;

import com.abnamro.assignment.datafactory.CreateIssueDataProvider;
import com.abnamro.assignment.datafactory.EditIssueDataProvider;
import com.abnamro.assignment.helper.IssueApiHelper;
import com.abnamro.assignment.helper.ValidationHelper;
import com.abnamro.assignment.models.request.BaseRequestModel;
import com.abnamro.assignment.models.response.IssueModel;
import com.abnamro.assignment.specs.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class EditIssueTest extends TestBase {

    IssueApiHelper apiHelper = new IssueApiHelper();
    ValidationHelper valdHelper = new ValidationHelper();

    @Test (dataProvider = "edit_issue_test_data", dataProviderClass = EditIssueDataProvider.class, description = "[POSITIVE] Should be able to update issue.")
    public void edit_issue(BaseRequestModel testData) {
        //create issue
        String preReqIssueIId = apiHelper.create_issue_and_get_issueIId(projectId, CreateIssueDataProvider.default_create_issue_payload());
        //edit issue
        responseSpec = apiHelper.edit_issue(projectId, preReqIssueIId, testData.params);
        //validate issue is updated
        responseSpec.then().assertThat().statusCode(200);
        IssueModel issue = responseSpec.as(IssueModel.class);
        // returns a collection-view of the map
        for (Map.Entry<String, String> entry : testData.params.entrySet()) {
            {
                switch (entry.getKey()) {
                    case EditIssueDataProvider.TITLE:
                        Assert.assertEquals(issue.title, entry.getValue());
                        break;
                    case EditIssueDataProvider.ISSUE_TYPE:
                        Assert.assertEquals(issue.issueType, entry.getValue());
                        break;
                    case EditIssueDataProvider.STATE_EVENT:
                        Assert.assertEquals(issue.state, entry.getValue() + "d");
                        break;
                    default:
                        throw new IllegalStateException("Invalid parameter: " + entry.getKey());
                }
            }
        }
    }


    @Test (description = "[NEGATIVE] Should get an error message when user sends edit request without query parameter")
    public void edit_issue_without_query_parameter() {
        //create issue
        String preReqIssueIId = apiHelper.create_issue_and_get_issueIId(projectId, CreateIssueDataProvider.default_create_issue_payload());
        //edit issue
        responseSpec = apiHelper.edit_issue(projectId, preReqIssueIId, new HashMap<>());
        responseSpec.then().assertThat().statusCode(400);
        valdHelper.validate_query_parameter_is_mandatory_to_edit_issue(responseSpec);

    }

}
