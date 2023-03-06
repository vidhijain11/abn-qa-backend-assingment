package com.abnamro.assignment.constants;

public class Endpoints {
//author_id, confidential, iids, labels, state
    public static String get_issues(){
        return "/issues";
    }

    public static String create_issue(String projectId){
        return "/projects/"+projectId+"/issues";
    }

    public static String edit_issue(String projectId, String issueIid){
        return "/projects/"+projectId+"/issues/"+ issueIid;
    }
    public static String delete_issue(String projectId, String issueIid){
        return "/projects/"+projectId+"/issues/"+ issueIid;
    }
}
