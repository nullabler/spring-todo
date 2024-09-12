package com.nullabler.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.nullabler.todo.entity.TaskEntity;

public interface TaskRepository extends CrudRepository<TaskEntity, Integer> {

    @Query("SELECT t FROM TaskEntity t WHERE t.deep = :deep")
    List<TaskEntity> findAllByDeep(Integer deep);
}
