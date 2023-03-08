package com.abnamro.assignment.utils;

import com.abnamro.assignment.datafactory.CommonDataProvider;
import com.abnamro.assignment.specs.TestBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class HttpUtil {


    public static Map<String, String> getValidHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("PRIVATE-TOKEN", TestBase.accessToken);
        header.put("Content-Type", "application/json");
        return header;
    }

    public static Map<String, String> getExpiredTokenHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("PRIVATE-TOKEN", CommonDataProvider.expiredToken);
        header.put("Content-Type", "application/json");
        return header;
    }

    /**
     * GET request
     * @param requestPath
     * @return Response
     */
    public synchronized Response getRequest(String requestPath, Map<String, String> headers){

        return RestAssured.given()
                .headers(headers)
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
    public synchronized Response putRequest(String requestPath, Map<String, String> headers){

        return RestAssured.given()
                .headers(headers)
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
    public synchronized Response postRequest(String requestPath , Map<String, String> headers, Object val){

        return RestAssured.given()
                .headers(headers)
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
    public synchronized Response deleteRequest(String requestPath, Map<String, String> headers){

        return RestAssured.given()
                .headers(headers)
                .when().log().all()
                .delete(requestPath)
                .then().log().body()
                .extract()
                .response();
    }
}
