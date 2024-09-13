package com.nullabler.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nullabler.todo.dto.request.CreateTaskRequest;
import com.nullabler.todo.dto.request.ToggleActiveTaskRequest;
import com.nullabler.todo.dto.response.ErrorResponse;
import com.nullabler.todo.dto.response.MessageResponse;
import com.nullabler.todo.entity.TaskEntity;
import com.nullabler.todo.exception.NotFoundException;
import com.nullabler.todo.service.TaskService;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping
    @ResponseBody 
    public Iterable<TaskEntity> list() {
        return this.taskService.getRootAll();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public TaskEntity create(@RequestBody CreateTaskRequest createTaskRequest) {
        Integer pid = null;
        if (!createTaskRequest.isEmptyParentId()) {
            pid = createTaskRequest.getParentId();
        }

        return this.taskService.create(createTaskRequest.getTitle(), pid);
    }

    @GetMapping("/{id}")
    @ResponseBody 
    public TaskEntity view(@PathVariable("id") Integer id) {
        return this.taskService.getTaskEntity(id);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public MessageResponse delete(@PathVariable("id") Integer id) {
        this.taskService.delete(id);

        return new MessageResponse("Deleted");
    }

    @PostMapping("/toggle")
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
