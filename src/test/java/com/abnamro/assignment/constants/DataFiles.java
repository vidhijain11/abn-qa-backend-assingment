package com.abnamro.assignment.constants;

import io.restassured.internal.support.FileReader;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;

public class DataFiles {
    static final public String file_config = "./src/test/resources/configfiles/config.properties";
    static final public String file_api_test_data = "./src/test/resources/testdata/apiTestData.json";

    public static JSONObject get_api_test_data() throws JSONException {
        return new JSONObject(FileReader.readToString(new File(file_api_test_data), "UTF-8"));
    }

}

