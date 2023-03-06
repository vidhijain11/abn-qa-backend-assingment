package com.abnamro.assignment.specs.apitests;


import com.abnamro.assignment.datafactory.CreateIssueDataProvider;
import com.abnamro.assignment.helper.IssueApiHelper;
import com.abnamro.assignment.constants.Endpoints;
import com.abnamro.assignment.utils.HttpUtil;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.Assert;
import org.testng.annotations.*;
import com.abnamro.assignment.specs.TestBase;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetIssues extends TestBase {

    HttpUtil util =  new HttpUtil();
    IssueApiHelper commonfunc = new IssueApiHelper();

    @BeforeClass
    public void create_pre_req_data() {

        Map<String,Object> reqParams = new HashMap<>();
        reqParams.put("labels", List.of("bug"));
        commonfunc.create_issue_and_get_issueIId(projectId, CreateIssueDataProvider.get_create_issue_payload(reqParams));

        reqParams.put("labels", List.of("bug", "incident"));
        commonfunc.create_issue_and_get_issueIId(projectId, CreateIssueDataProvider.get_create_issue_payload(reqParams));
    }

    @Test (description = "[POSITIVE] Should list all the issues")
    public void list_all_issues() {
        //get issues response json schema
        InputStream getIssuesJsonSchema = getClass ().getClassLoader ()
                .getResourceAsStream ("getIssuesJsonSchema.json");
        //get all issues
        responseSpec = util.getRequest(Endpoints.get_issues());
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
        responseSpec = util.getRequest(Endpoints.get_issues()+ "?labels=bug");
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
