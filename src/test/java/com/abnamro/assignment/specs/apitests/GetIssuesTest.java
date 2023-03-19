package com.abnamro.assignment.specs.apitests;

import com.abnamro.assignment.datafactory.CreateIssueDataProvider;
import com.abnamro.assignment.helper.IssueApiHelper;
import com.abnamro.assignment.constants.Endpoint;
import com.abnamro.assignment.models.request.CreateIssueModel;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.Assert;
import org.testng.annotations.*;
import com.abnamro.assignment.specs.TestBase;

import java.io.InputStream;
import java.util.List;

public class GetIssuesTest extends TestBase {

    IssueApiHelper apiHelper = new IssueApiHelper();

    @BeforeClass
    public void create_pre_req_data() {

        CreateIssueModel reqPayload = CreateIssueDataProvider.default_create_issue_payload();
        reqPayload.setLabels(List.of("bug"));
        apiHelper.create_issue_and_get_issueIId(projectId, reqPayload);

        reqPayload.setLabels(List.of("bug", "incident"));
        apiHelper.create_issue_and_get_issueIId(projectId, reqPayload);
    }

    @Test (description = "[POSITIVE] Should list all the issues")
    public void list_all_issues() {
        //get issues response json schema
        InputStream getIssuesJsonSchema = getClass ().getClassLoader ()
                .getResourceAsStream ("getIssuesJsonSchema.json");
        //get all issues
        responseSpec = apiHelper.get_issues(Endpoint.get_issues());
        //validate json schema
        responseSpec
                .then()
                .assertThat()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(getIssuesJsonSchema));
    }

    @Test (description = "[POSITIVE] Should filter issues by label")
    public void filter_issue_list_by_label() {
        //filter issue type with label
        responseSpec = apiHelper.get_issues(Endpoint.get_issues()+ "?labels=bug");
        //validate every issue is labeled as bug
        responseSpec
                .then()
                .assertThat()
                .statusCode(200);
        List<List<String>> labelList = responseSpec.jsonPath().getList("labels");
        for ( List<String> label: labelList
             ) {
            Assert.assertTrue(label.contains("bug"));
        }
    }

}
