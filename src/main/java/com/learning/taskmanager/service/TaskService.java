package com.learning.taskmanager.service;

import com.learning.taskmanager.dto.request.TaskRequest;
import com.learning.taskmanager.dto.response.TaskResponse;
import com.learning.taskmanager.exception.ResourceNotFoundException;
import com.learning.taskmanager.model.Group;
import com.learning.taskmanager.model.Task;
import com.learning.taskmanager.repository.GroupRepository;
import com.learning.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    // Service methods will be implemented here
    private final TaskRepository taskRepository;
    private final GroupRepository groupRepository;

    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(this::convertToResponse).toList();
    }

    public List<TaskResponse> getAllUngroupedTasks() {
        List<Task> tasks = taskRepository.findAllByGroupNull();
        return tasks.stream().map(this::convertToResponse).toList();
    }

    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return convertToResponse(task);
    }

    public TaskResponse createTask(TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted(request.getCompleted() != null ? request.getCompleted() : false);

        if (request.getGroupId() != null) {
            Group group = groupRepository.findById(request.getGroupId()).orElseThrow(() -> new ResourceNotFoundException("Group not found"));
            task.setGroup(group);
        }

        Task savedTask = taskRepository.save(task);
        return convertToResponse(savedTask);
    }

    public TaskResponse updateTask(Long id, TaskRequest updatedTask) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setDescription(updatedTask.getDescription());
        task.setTitle(updatedTask.getTitle());
        task.setCompleted(updatedTask.getCompleted() != null ? updatedTask.getCompleted() : false);
        if (updatedTask.getGroupId() != null) {
            Group group = groupRepository.findById(updatedTask.getGroupId()).orElseThrow(() -> new ResourceNotFoundException("Group not found"));
            task.setGroup(group);
        } else {
            task.setGroup(null);
        }
        Task savedTask = taskRepository.save(task);
        return convertToResponse(savedTask);
    }
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public TaskResponse assignTaskToGroup(Long taskId, Long groupId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        var group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));
        task.setGroup(group);
        Task updatedTask = taskRepository.save(task);
        return convertToResponse(updatedTask);
    }

    public TaskResponse removeTaskFromGroup(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        task.setGroup(null);
        Task updatedTask = taskRepository.save(task);
        return convertToResponse(updatedTask);
    }


    private TaskResponse convertToResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setCompleted(task.isCompleted());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());

        if (task.getGroup() != null) {
            response.setGroupId(task.getGroup().getId());
            response.setGroupTitle(task.getGroup().getTitle());
        }
        return response;
    }

}
