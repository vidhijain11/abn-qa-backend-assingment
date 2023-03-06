package com.abnamro.assignment.datafactory;

import com.abnamro.assignment.constants.DataFiles;
import com.abnamro.assignment.utils.HttpUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.testng.annotations.DataProvider;

public class CommonDataProvider {

    @DataProvider(name = "invalid_id")
    public Object[] invalid_id() throws JSONException {
        JSONArray data = DataFiles.get_api_test_data().getJSONArray("invalid_id");
        return new HttpUtil().toObjectArray(data);
    }
}
