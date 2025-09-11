package com.learning.taskmanager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GroupResponse {
    private Long id;
    private String title;
    private Long taskCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
