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

    @DataProvider(name = "edit_issue_test_data")
    public static Iterator<Object> data_provider() {

        List<Object> listOfTestdata = new ArrayList<>();

        //-----------------------
        BaseRequestModel editTitleType = new BaseRequestModel();
        editTitleType.testName = "Test_title_issueType";
        editTitleType.params = new QueryParamModel[]{
                new QueryParamModel(TITLE, "Updatedissuetitle")
        };
        listOfTestdata.add(editTitleType);

        //-----------------------
        BaseRequestModel editState = new BaseRequestModel();
        editState.testName = "Test_state_event";
        editState.params = new QueryParamModel[]{
                new QueryParamModel(ISSUE_TYPE, "incident"),
                new QueryParamModel(STATE_EVENT, "close")
        };
        listOfTestdata.add(editState);

        //-------------------------

        return listOfTestdata.iterator();

    }
}
