package com.learning.taskmanager.controller;

import com.learning.taskmanager.dto.request.TaskRequest;
import com.learning.taskmanager.dto.response.TaskResponse;
import com.learning.taskmanager.model.Task;
import com.learning.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/ungrouped")
    public ResponseEntity<List<Task>> getAllUngroupedTasks() {
        List<Task> tasks = taskService.getAllUngroupedTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@RequestBody TaskRequest task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        try {
            Task task = taskService.updateTask(id, updatedTask);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PutMapping("/{taskId}/group/{groupId}")
    public ResponseEntity<Task> assignTaskToGroup(@PathVariable Long taskId, @PathVariable Long groupId) {
        try {
            Task task = taskService.assignTaskToGroup(taskId, groupId);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{taskId}/remove-group")
    public ResponseEntity<Task> removeTaskFromGroup(@PathVariable Long taskId) {
        try {
            Task task = taskService.removeTaskFromGroup(taskId);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}