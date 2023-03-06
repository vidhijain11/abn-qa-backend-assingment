package com.abnamro.assignment.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueModel {

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
