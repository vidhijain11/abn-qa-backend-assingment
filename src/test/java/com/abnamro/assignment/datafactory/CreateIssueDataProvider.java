package com.abnamro.assignment.datafactory;

import com.abnamro.assignment.constants.DataFiles;
import com.abnamro.assignment.models.request.BaseRequestModel;
import com.abnamro.assignment.models.request.CreateIssueModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class CreateIssueDataProvider {

    /** returns json object to create issue with title **/
    public static CreateIssueModel default_create_issue_payload() {

        CreateIssueModel defaultPayload =  CreateIssueModel
                .builder()
                .title("Title field is mandatory to create issue")
                .confidential(false)
                .build();
        return defaultPayload;
    }

    /** Prepare test data to test the create issue request with a set of valid test data **/
    @DataProvider(name = "create_issue_test_data")
    public static Iterator<Object> data_provider() {

        List<Object> listOfTestdata = new ArrayList<>();

        //------test data 1-----------------
        BaseRequestModel titleDesc =  new BaseRequestModel();
        titleDesc.testName = "Create_issue_with_title_and_description";
        titleDesc.createIssueRequestPayload =  CreateIssueModel
                .builder()
                .title("This is title to create issue")
                .description("This is test issue ❤️\uD83D\uDE0A some emojis !@#$%^&*()_-+=[]{}:;'\",<.>/?`~ with special characters")
                .confidential(false)
                .build();


        listOfTestdata.add(titleDesc);

        //-------test data 2----------------
        BaseRequestModel withLabel =  new BaseRequestModel();
        titleDesc.testName = "Create_confidential_issue_with_service_label";
        withLabel.createIssueRequestPayload = CreateIssueModel
                .builder()
                .title("This is title to create issue")
                .description("long description with language specific characters. Bulgarian-И и Й й (й) К к П п Ф ф Ш ш (ш) Ю ю Я я Д д/д " +
                        "emergency note Polish-ĄĆĘŁŃÓŚŹŻąćęłńóśźż. portuguese-ÇÃO LÉM ô. Swiss-Ü,ü,Ö,ö,Ä,ä. Denmark/norway-Æ,æ,Ø,ø,Å,å. Israel-א בּ ב ג. " +
                        "Luxembourg-əʊ̯ əɪ ̯æːʊ̯ ɑʊ̯ æːɪ̯ ɑɪ̯ ɛːɐ̯ aː iːɐ̯ oːɐ̯ uːɐ̯. Czech-é s čá")
                .labels(List.of("service"))
                .confidential(true)
                .build();
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
}
