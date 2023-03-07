package com.abnamro.assignment.constants;

public class Endpoint {

    /** get issues endpoint **/
    public static String get_issues(){
        return "/issues";
    }
    /** create issue endpoint **/
    public static String create_issue(String projectId){
        return "/projects/"+projectId+"/issues";
    }
    /** edit issue endpoint **/
    public static String edit_issue(String projectId, String issueIid){
        return "/projects/"+projectId+"/issues/"+ issueIid;
    }
    /** delete issue endpoint **/
    public static String delete_issue(String projectId, String issueIid){
        return "/projects/"+projectId+"/issues/"+ issueIid;
    }
}
