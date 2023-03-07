package com.abnamro.assignment.helper;

import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;

import static org.hamcrest.Matchers.equalTo;

public class ValidationHelper {

    /** validates project id not found error **/
    public void validate_projectId_not_found(Response responseSpec) throws JSONException {
        responseSpec
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("404 Project Not Found"));
    }

    /** validates issue iid not found error **/
    public void validate_issue_not_found(Response responseSpec) throws JSONException {
        responseSpec
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("404 Issue Not Found"));
    }

     /** validate invalid request payload error message **/
    public void validate_invalid_create_issue_request_error(Response responseSpec, JSONObject expectedResponse) throws JSONException {
        responseSpec
                .then()
                .assertThat()
                .statusCode(400)
                .body("error", equalTo(expectedResponse.getString("error")));
    }

    /** validate expired auth token error message **/
    public void validate_invalid_token_error(Response responseSpec) throws JSONException {
        responseSpec
                .then()
                .assertThat()
                .statusCode(401)
                .body("error", equalTo("invalid_token"))
                .body("error_description", equalTo("Token was revoked. You have to re-authorize from the user."));
    }
}
