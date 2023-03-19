package com.abnamro.assignment.helper;

import com.abnamro.assignment.constants.Endpoint;
import com.abnamro.assignment.models.request.QueryParamModel;
import com.abnamro.assignment.utils.HttpUtil;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.List;

public class IssueApiHelper {

    HttpUtil util = new HttpUtil();

    /** Create issue and get issue iid **/
    public String create_issue_and_get_issueIId(String projectId, JSONObject reqPayloadCreateIssue) {
        Response response = create_issue(projectId, reqPayloadCreateIssue);
        response.then().statusCode(201);

        return response.jsonPath().getString("iid");
    }

    /** Send create issue request with default valid header. This function returns Response object **/
    public Response create_issue(String projectId, Object reqPayloadCreateIssue) {
        return util.postRequest(Endpoint.create_issue(projectId), HttpUtil.getValidHeader(), reqPayloadCreateIssue);
    }
    /** Send get issues request with default valid header. This function returns Response object **/
    public Response get_issues(String requestPath) {
        return util.getRequest(requestPath, HttpUtil.getValidHeader());
    }
    /** Send delete issue request with default valid header. This function returns Response object **/
    public Response delete_issue(String projectId, String issueIid) {
        return util.deleteRequest(Endpoint.delete_issue(projectId, issueIid), HttpUtil.getValidHeader());
    }
    /** Send edit issue request with default valid header. This function returns Response object **/
    public Response edit_issue(String requestPath) {
        return util.putRequest(requestPath, HttpUtil.getValidHeader());
    }


    /**
     * Delete all the issues present under a project
     * @param projectId
     */
    public void delete_all_issues(String projectId) {
        Response response = get_issues(Endpoint.get_issues());
        response.then().statusCode(200);
        List<Integer> iids;
        iids = response.jsonPath().get("iid");
        for ( Integer id: iids
             ) {
            delete_issue(projectId, String.valueOf(id));
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

        String queryPath = "?"+queryParams;
        System.out.println("Request Query Path: "+ queryPath);

        return edit_issue(Endpoint.edit_issue(projectId, issueIid) + queryPath);
    }
}
