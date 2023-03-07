package com.abnamro.assignment.datafactory;

import org.json.JSONException;
import org.testng.annotations.DataProvider;

public class CommonDataProvider {

    public static final String expiredToken = "glpat-cZiBdLYsxJ_CvB1ecxJU";

    @DataProvider(name = "invalid_id")
    public Object[] invalid_id() throws JSONException {
        Object[] data = { 24125151 };
        return data;
    }
}
