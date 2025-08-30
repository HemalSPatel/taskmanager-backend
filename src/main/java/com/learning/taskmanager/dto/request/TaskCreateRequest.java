package com.learning.taskmanager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskCreateRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 175)
    private String title;
    private String description;
    private Long groupId;
}
