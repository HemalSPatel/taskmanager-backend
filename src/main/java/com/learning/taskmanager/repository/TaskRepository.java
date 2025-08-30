package com.learning.taskmanager.repository;

import com.learning.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Additional query methods can be defined here if needed
    @Query("SELECT t FROM Task t WHERE t.group IS NULL")
    List<Task> findAllByGroupNull();
}
