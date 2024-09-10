package com.nullabler.todo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nullabler.todo.entity.TaskEntity;
import com.nullabler.todo.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public Iterable<TaskEntity> getNodeAll()
    {
        return taskRepository.findAll();
    }

    public TaskEntity createNode(String title) {
        TaskEntity task = new TaskEntity();
        task.setTitle(title);
        task.setActive(true);
        this.taskRepository.save(task);

        return task;
    }

    public Optional<TaskEntity> viewNode(Integer id) {
        return this.taskRepository.findById(id);
    }

    public void deleteNode(Integer id) {
        this.taskRepository.delete(this.viewNode(id).get());
    }
}
