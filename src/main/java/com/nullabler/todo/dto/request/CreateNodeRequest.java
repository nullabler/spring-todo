package com.nullabler.todo.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateNodeRequest {

    private String title;

    private Integer parentId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getParentId() {
        return parentId;
    }
    
    public Boolean isEmptyParentId() {
        return parentId == null;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
