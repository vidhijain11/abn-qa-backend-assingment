package com.abnamro.assignment.datafactory;

import com.abnamro.assignment.models.request.BaseRequestModel;
import com.abnamro.assignment.models.request.QueryParamModel;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EditIssueDataProvider {

    public static final String STATE_EVENT = "state_event";
    public static final String TITLE = "title";
    public static final String ISSUE_TYPE = "issue_type";

    /** Prepare test data to test the edit issue request with a set of valid query parameters **/
    @DataProvider(name = "edit_issue_test_data")
    public static Iterator<Object> data_provider() {

        List<Object> listOfTestdata = new ArrayList<>();

        //----------query parameter=title-------------
        BaseRequestModel editTitleType = new BaseRequestModel();
        editTitleType.testName = "Test_title_issueType";
        editTitleType.params = new QueryParamModel[]{
                new QueryParamModel(TITLE, "Updatedissuetitle")
        };
        listOfTestdata.add(editTitleType);

        //---------query parameter=issue_type and state event--------------
        BaseRequestModel editState = new BaseRequestModel();
        editState.testName = "Test_state_event";
        editState.params = new QueryParamModel[]{
                new QueryParamModel(ISSUE_TYPE, "incident"),
                new QueryParamModel(STATE_EVENT, "close")
        };
        listOfTestdata.add(editState);

        //---------add more test data----------------

        return listOfTestdata.iterator();

    }
}
