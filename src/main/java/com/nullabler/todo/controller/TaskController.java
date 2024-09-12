package com.nullabler.todo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nullabler.todo.dto.request.CreateNodeRequest;
import com.nullabler.todo.dto.request.DeleteNodeRequest;
import com.nullabler.todo.dto.request.ToggleActiveTaskRequest;
import com.nullabler.todo.dto.response.ErrorResponse;
import com.nullabler.todo.dto.response.MessageResponse;
import com.nullabler.todo.entity.TaskEntity;
import com.nullabler.todo.exception.NotFoundException;
import com.nullabler.todo.service.TaskService;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/task")
    @ResponseBody 
    public Iterable<TaskEntity> getNodeAll() {
        return this.taskService.getRootNodeAll();
    }

    @PutMapping("/task")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public TaskEntity createNode(@RequestBody CreateNodeRequest createNodeRequest) {
        Integer pid = null;
        if (!createNodeRequest.isEmptyParentId()) {
            pid = createNodeRequest.getParentId();
        }

        return this.taskService.createNode(createNodeRequest.getTitle(), pid);
    }

    @GetMapping("/task/{id}")
    @ResponseBody 
    public Optional<TaskEntity> viewNode(@PathVariable("id") Integer id) {
        return this.taskService.viewNode(id);
    }

    @DeleteMapping("/task")
    @ResponseBody
    public MessageResponse deleteNode(@RequestBody DeleteNodeRequest deleteNodeRequest) {
        this.taskService.deleteNode(deleteNodeRequest.getId());

        return new MessageResponse("Delete");
    }

    @PostMapping("/task/toggle")
    @ResponseBody
    public MessageResponse toggleActive(@RequestBody ToggleActiveTaskRequest toggleActiveTaskRequest) {
        this.taskService.toggleActive(toggleActiveTaskRequest.getId(), toggleActiveTaskRequest.isActive());

        return new MessageResponse("Changed");
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleException(NotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }
}
