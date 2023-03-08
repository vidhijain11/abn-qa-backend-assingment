package com.abnamro.assignment.datafactory;

import com.abnamro.assignment.constants.DataFiles;
import com.abnamro.assignment.models.request.CreateIssueModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CreateIssueDataProvider {

    /** Prepare test data to test the create issue request with a set of valid test data **/
    @DataProvider(name = "create_issue_test_data")
    public static Iterator<Object> data_provider() {

        List<Object> listOfTestdata = new ArrayList<>();

        //------test data 1-----------------
        CreateIssueModel titleDesc = new CreateIssueModel();
        titleDesc.testName = "Create_issue_with_title_and_description";
        titleDesc.payload = default_create_issue_payload();
        titleDesc.payload.put("description", "This is test issue ❤️\uD83D\uDE0A some emojis !@#$%^&*()_-+=[]{}:;'\",<.>/?`~ with special characters");

        listOfTestdata.add(titleDesc);

        //-------test data 2----------------
        CreateIssueModel withLabel = new CreateIssueModel();
        withLabel.testName = "Create_confidential_issue_with_service_label";
        withLabel.payload = default_create_issue_payload();
        withLabel.payload.put("description", "long description with language specific characters. Bulgarian-И и Й й (й) К к П п Ф ф Ш ш (ш) Ю ю Я я Д д/д " +
                "emergency note Polish-ĄĆĘŁŃÓŚŹŻąćęłńóśźż. portuguese-ÇÃO LÉM ô. Swiss-Ü,ü,Ö,ö,Ä,ä. Denmark/norway-Æ,æ,Ø,ø,Å,å. Israel-א בּ ב ג. " +
                "Luxembourg-əʊ̯ əɪ ̯æːʊ̯ ɑʊ̯ æːɪ̯ ɑɪ̯ ɛːɐ̯ aː iːɐ̯ oːɐ̯ uːɐ̯. Czech-é s čá");
        withLabel.payload.put("labels", List.of("service"));
        withLabel.payload.put("confidential", true);
        listOfTestdata.add(withLabel);

        //-------add more test data------------------

        return listOfTestdata.iterator();
    }

    /** To test the create issue request with set of invalid data. This method fetches data from apiTestData.json file **/
    @DataProvider(name = "create_issue_with_invalid_request_payload")
    public Object[] create_issue_with_invalid_request_payload() throws JSONException {
        JSONArray data = DataFiles.get_api_test_data().getJSONArray("create_issue_invalid_request");
        return new CommonDataProvider().toObjectArray(data);
    }

    /** returns json object to create issue with title **/
    public static JSONObject default_create_issue_payload() {

      JSONObject payload = new JSONObject();
      payload.put("title", "Title field is mandatory to create issue");
      return payload;
    }

    /** returns json object to create issue with additional request parameters **/
    public static JSONObject get_create_issue_payload(Map<String, Object> reqParams) {
        JSONObject reqPayloadCreateIssue = default_create_issue_payload();
        reqParams.forEach(reqPayloadCreateIssue::put);
        return reqPayloadCreateIssue;
    }
}
