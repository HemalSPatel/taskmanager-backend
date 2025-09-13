package com.learning.taskmanager.repository;

import com.learning.taskmanager.dto.response.GroupResponse;
import com.learning.taskmanager.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    // Additional query methods can be defined here if needed
    @Query("SELECT new com.learning.taskmanager.dto.response.GroupResponse(g.id, g.title, COUNT(t), g.createdAt, g.updatedAt) " +
            "FROM Group g LEFT JOIN g.tasks t " +
            "GROUP BY g.id, g.title, g.createdAt, g.updatedAt")
    List<GroupResponse> findAllWithTaskCount();

    @Query("SELECT new com.learning.taskmanager.dto.response.GroupResponse(g.id, g.title, COUNT(t), g.createdAt, g.updatedAt) " +
            "FROM Group g LEFT JOIN g.tasks t " +
            "WHERE g.id = :id " +
            "GROUP BY g.id, g.title, g.createdAt, g.updatedAt")
    GroupResponse findByIdWithTaskCount(Long id);
}
