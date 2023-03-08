package com.abnamro.assignment.specs.apitests;

import com.abnamro.assignment.datafactory.CommonDataProvider;
import com.abnamro.assignment.datafactory.CreateIssueDataProvider;
import com.abnamro.assignment.helper.IssueApiHelper;
import com.abnamro.assignment.constants.Endpoint;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.abnamro.assignment.specs.TestBase;

public class DeleteIssueTest extends TestBase {

    IssueApiHelper apiHelper = new IssueApiHelper();
    String preReqIssueIId;

    @BeforeClass
    private void create_pre_req_data() {
        preReqIssueIId = apiHelper.create_issue_and_get_issueIId(projectId, CreateIssueDataProvider.default_create_issue_payload());
    }

    @Test(description = "[POSITIVE] should be able to delete issue")
    public void delete_issue() {
        //create issue
        String iid = apiHelper.create_issue_and_get_issueIId(projectId, CreateIssueDataProvider.default_create_issue_payload());   //create issue
        //delete issue
        responseSpec = apiHelper.delete_issue(projectId, iid);
        responseSpec.then().assertThat().statusCode(204);
        //get all issues
        responseSpec = apiHelper.get_issues(Endpoint.get_issues());
        responseSpec.then().assertThat().statusCode(200);
        //should not contain deleted issue
        Assert.assertFalse(responseSpec.jsonPath().getList("iid").contains(iid));
    }

    @Test(dataProvider = "invalid_projectId", dataProviderClass = CommonDataProvider.class, description = "[NEGATIVE] Should return 404 error code when user deletes issue by invalid projectId")
    public void delete_issue_by_invalid_projectId(Object testProjectId) {
        responseSpec = apiHelper.delete_issue(String.valueOf(testProjectId), preReqIssueIId);
        resMsg.validate_projectId_not_found(responseSpec);
    }

    @Test(dataProvider = "invalid_issueIid", dataProviderClass = CommonDataProvider.class, description = "[NEGATIVE] Should return 404 error code when user deletes issue by invalid issue iid")
    public void delete_issue_by_invalid_issue_iid(Object testIssueIid) {
        responseSpec = apiHelper.delete_issue(projectId, String.valueOf(testIssueIid));
        resMsg.validate_issue_not_found(responseSpec);
    }
}
