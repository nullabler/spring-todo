package com.nullabler.todo.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTaskRequest {

    private String title;

    private Integer parentId;

    public CreateTaskRequest(
        String title,
        Integer parentId
    ) {
        this.title = title;
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getParentId() {
        return parentId;
    }
    
    public Boolean isEmptyParentId() {
        return parentId == null;
    }
}