package com.abnamro.assignment.datafactory;

import com.abnamro.assignment.constants.DataFiles;
import com.abnamro.assignment.models.request.CreateIssueModel;
import com.abnamro.assignment.utils.HttpUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CreateIssueDataProvider {

    @DataProvider(name = "create_issue_test_data")
    public static Iterator<Object> data_provider() {

        List<Object> listOfTestdata = new ArrayList<>();

        //-----------------------
        CreateIssueModel onlyTitle = new CreateIssueModel();
        onlyTitle.testName = "Create_issue_with_mandatory_attribute_title";
        onlyTitle.payload = default_create_issue_payload();

        listOfTestdata.add(onlyTitle);

        //-----------------------
        CreateIssueModel withLabel = new CreateIssueModel();
        withLabel.testName = "Create_confidential_issue_with_service_label";
        withLabel.payload = default_create_issue_payload();
        withLabel.payload.put("labels", List.of("service"));
        withLabel.payload.put("confidential", true);
        listOfTestdata.add(withLabel);

        //-------------------------

        return listOfTestdata.iterator();
    }

    @DataProvider(name = "create_issue_invalid_request")
    public Object[] create_issue_invalid_request() throws JSONException {
        JSONArray data = DataFiles.get_api_test_data().getJSONArray("create_issue_invalid_request");
        return new HttpUtil().toObjectArray(data);
    }

    public static JSONObject default_create_issue_payload() {

      JSONObject payload = new JSONObject();
      payload.put("title", "Title field is mandatory to create issue");
      return payload;
    }

    public static JSONObject get_create_issue_payload(Map<String, Object> reqParams) {
        JSONObject reqPayloadCreateIssue = default_create_issue_payload();
        reqParams.forEach(reqPayloadCreateIssue::put);
        return reqPayloadCreateIssue;
    }
}
