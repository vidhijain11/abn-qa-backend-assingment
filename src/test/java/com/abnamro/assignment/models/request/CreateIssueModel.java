package com.abnamro.assignment.models.request;

import com.abnamro.assignment.models.response.AuthorModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Setter;

import java.util.List;

@Setter
@Builder
public class CreateIssueModel {
    public Integer id;
    public Integer iid;
    @JsonProperty("project_id")
    public Integer projectId;
    public String title;
    public Object description;
    public String state;
    public List<String> labels;
    public AuthorModel author;
    public String type;
    public Boolean confidential;
    @JsonProperty("issue_type")
    public String issueType;
    @JsonProperty("web_url")
    public String webUrl;
}
