package com.dmm.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dmm.task.entity.Tasks;

public interface TasksRepository extends JpaRepository<Tasks, String> {

}
