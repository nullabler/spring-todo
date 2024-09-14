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

    public Iterable<TaskEntity> getRootAll() {
        return this.taskRepository.findAllByDeep(0);
    }

    public TaskEntity create(String title, Integer pid) {
        TaskEntity task = new TaskEntity();
        task.setTitle(title);
        task.setActive(true);
        if (pid != null) {
            TaskEntity parent = this.getTaskEntity(pid);
            task.setParent(parent);
            task.setDeep(parent.getDeep() + 1);
        }
        this.taskRepository.save(task);

        return task;
    }

    public void delete(Integer id) {
        this.taskRepository.delete(this.getTaskEntity(id));
    }

    public void toggleActive(Integer id, Boolean active) {
        TaskEntity task = this.getTaskEntity(id);
        task.setActive(active);

        this.taskRepository.save(task);
    }

    public TaskEntity getTaskEntity(Integer id) {
        Optional<TaskEntity> taskOptional = this.taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            throw new NotFoundException("Not found task by ID: " + id.toString());
        }

        return taskOptional.get();
    }
}
