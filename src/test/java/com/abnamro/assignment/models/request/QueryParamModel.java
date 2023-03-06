package com.abnamro.assignment.models.request;

public class QueryParamModel {
    public String paramName;
    public String paramValue;

    public QueryParamModel(String name, String value) {
        this.paramName = name;
        this.paramValue = value;
    }

}
