package com.abnamro.assignment.utils;

import com.abnamro.assignment.specs.TestBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONException;

public class HttpUtil extends TestBase{

    RequestSpecification requestSpec;

    public HttpUtil() {
        requestSpec = RestAssured
                .given()
                .baseUri(baseUri).urlEncodingEnabled(true)
                .header("PRIVATE-TOKEN", accessToken)
                .header("Content-Type", "application/json");
    }
    /**
     * GET request
     *
     * @param requestPath
     * @return Response
     */
    public synchronized Response getRequest(String requestPath){

        return requestSpec
                .when().log().all()
                .get(requestPath)
                .then().log().body()
                .extract()
                .response();
    }

    /**
     * PUT request
     * @param requestPath
     * @return Response
     */
    public synchronized Response putRequest(String requestPath){

        return requestSpec
                .when().log().all()
                .put(requestPath)
                .then().log().body()
                .extract()
                .response();
    }

    /**
     * POST request
     * @param requestPath
     * @param val
     * @return Response
     */
    public synchronized Response postRequest(String requestPath , Object val){

        return requestSpec
                .body(val.toString())
                .when().log().all()
                .post(requestPath)
                .then().log().body()
                .extract()
                .response();
    }

    /**
     * POST request
     * @param requestPath
     * @return Response
     */
    public synchronized Response deleteRequest(String requestPath){

        return requestSpec
                .when().log().all()
                .delete(requestPath)
                .then().log().body()
                .extract()
                .response();
    }

    /**
     * Convert JSON array to Object array
     * @param ja
     * @return
     * @throws JSONException
     */
    public synchronized Object[] toObjectArray(JSONArray ja) throws JSONException {

        Object[] obj = new Object[ja.length()];
        for(int i=0; i< ja.length(); i++){
            obj[i] = ja.get(i).toString();
        }
        return obj;
    }
}
