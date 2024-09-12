package com.nullabler.todo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nullabler.todo.entity.TaskEntity;
import com.nullabler.todo.exception.NotFoundException;
import com.nullabler.todo.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public Iterable<TaskEntity> getRootNodeAll() {
        return this.taskRepository.findAllByDeep(0);
    }

    public TaskEntity createNode(String title, Integer pid) {
        TaskEntity task = new TaskEntity();
        task.setTitle(title);
        task.setActive(true);
        if (pid != null) {
            Optional<TaskEntity> parentOptional = this.taskRepository.findById(pid);
            if (parentOptional.isPresent()) {
                TaskEntity parent = parentOptional.get();
                task.setParent(parent);
                task.setDeep(parent.getDeep() + 1);
            }
        }
        this.taskRepository.save(task);

        return task;
    }

    public Optional<TaskEntity> viewNode(Integer id) {
        return this.taskRepository.findById(id);
    }

    public void deleteNode(Integer id) {
        this.taskRepository.delete(this.viewNode(id).get());
    }

    public void toggleActive(Integer id, Boolean active) {
        Optional<TaskEntity> taskOptional = this.taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            throw new NotFoundException("Not found task by ID: " + id.toString());
        }

        TaskEntity task = taskOptional.get();
        task.setActive(active);

        this.taskRepository.save(task);
    }
}
