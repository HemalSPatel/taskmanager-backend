package com.learning.taskmanager.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private Long groupId;
    private String groupTitle; // optional include title for display
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}