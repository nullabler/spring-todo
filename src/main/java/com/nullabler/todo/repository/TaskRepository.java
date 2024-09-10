package com.nullabler.todo.repository;

import org.springframework.data.repository.CrudRepository;

import com.nullabler.todo.entity.TaskEntity;

public interface TaskRepository extends CrudRepository<TaskEntity, Integer> {

}
