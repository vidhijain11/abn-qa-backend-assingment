package com.abnamro.assignment.helper;

import com.abnamro.assignment.constants.Endpoints;
import com.abnamro.assignment.models.request.QueryParamModel;
import com.abnamro.assignment.utils.HttpUtil;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.List;

public class IssueApiHelper {

    HttpUtil util = new HttpUtil();

    /**
     * Create issue and get issue iid
     * @param projectId
     * @param reqPayloadCreateIssue
     * @return
     */
    public String create_issue_and_get_issueIId(String projectId, JSONObject reqPayloadCreateIssue) {
        Response response = create_issue(projectId, reqPayloadCreateIssue);
        response.then().statusCode(201);

        return response.jsonPath().getString("iid");
    }

    /**
     * Create issue
     * @param projectId
     * @param reqPayloadCreateIssue
     * @return
     */
    public Response create_issue(String projectId, Object reqPayloadCreateIssue) {
        return util.postRequest(Endpoints.create_issue(projectId), reqPayloadCreateIssue);
    }

    /**
     * Delete all the issues present under a project
     * @param projectId
     */
    public void delete_all_issues(String projectId) {
        Response response = util.getRequest(Endpoints.get_issues());
        response.then().statusCode(200);
        List<Integer> iids;
        iids = response.jsonPath().get("iid");
        for ( Integer id: iids
             ) {
            util.deleteRequest(Endpoints.delete_issue(projectId, String.valueOf(id)));
        }
    }

    /**
     * Edit/update issue - To update an issue send parameter name and value as query parameter.
     * @param projectId
     * @param issueIid
     * @param listOfParams append all query parameters present in the list
     * @return
     */
    public Response edit_issue(String projectId, String issueIid, QueryParamModel[] listOfParams) {

        //Creating the filter String
        StringBuilder queryParams = new StringBuilder();
        for(QueryParamModel param : listOfParams){
            queryParams.append(param.paramName).append("=").append(param.paramValue).append("&");
        }
        //delete last character &
        queryParams.deleteCharAt(queryParams.length()-1);

        String uri = "?"+queryParams;
        System.out.println("Request URI: "+uri);

        return new HttpUtil().putRequest(Endpoints.edit_issue(projectId, issueIid) + uri);
    }
}
