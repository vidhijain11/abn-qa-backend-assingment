package com.abnamro.assignment.datafactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.testng.annotations.DataProvider;

public class CommonDataProvider {

    public static final String expiredToken = "glpat-cZiBdLYsxJ_CvB1ecxJU";

    @DataProvider(name = "invalid_projectId")
    public Object[] invalid_projectId() throws JSONException {
        Object[] data = { 24125151, null };
        return data;
    }

    @DataProvider(name = "invalid_issueIid")
    public Object[] invalid_issueIid() throws JSONException {
        Object[] data = { 24125151 };
        return data;
    }

    //Convert JSON array to Object array
    public Object[] toObjectArray(JSONArray ja) throws JSONException {

        Object[] obj = new Object[ja.length()];
        for(int i=0; i< ja.length(); i++){
            obj[i] = ja.get(i).toString();
        }
        return obj;
    }
}
