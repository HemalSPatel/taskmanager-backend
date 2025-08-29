package com.learning.taskmanager.repository;

import com.learning.taskmanager.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    // Additional query methods can be defined here if needed
}
