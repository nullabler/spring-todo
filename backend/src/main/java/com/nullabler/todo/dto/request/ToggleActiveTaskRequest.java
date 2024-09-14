package com.nullabler.todo.dto.request;

public class ToggleActiveTaskRequest {

    private Integer id;

    private Boolean active;

    public ToggleActiveTaskRequest(
        Integer id,
        Boolean active
    ) {
        this.id = id;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public Boolean isActive() {
        return active;
    }
}