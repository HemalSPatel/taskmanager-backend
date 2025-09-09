package com.learning.taskmanager.dto.request;

import lombok.Data;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private Boolean completed;
    private Long groupId;  // Just the ID
}

