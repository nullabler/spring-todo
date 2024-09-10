package com.nullabler.todo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nullabler.todo.entity.TaskEntity;
import com.nullabler.todo.service.TaskService;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/task")
    @ResponseBody 
    public Iterable<TaskEntity> getNodeAll() {
        return this.taskService.getNodeAll();
    }

    @PostMapping("/create")
    @ResponseBody
    public TaskEntity createNode(
        @RequestParam String title
    ) {
        return this.taskService.createNode(title);
    }

    @GetMapping("/view/{id}")
    @ResponseBody 
    public Optional<TaskEntity> viewNode(
        @PathVariable("id") Integer id
    ) {
        return this.taskService.viewNode(id);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public String deleteNode(@RequestParam("id") Integer id) {
        this.taskService.deleteNode(id);

        return "Delete";
    }
}
