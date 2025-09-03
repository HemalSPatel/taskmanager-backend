package com.learning.taskmanager.service;

import com.learning.taskmanager.exception.ResourceNotFoundException;
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

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getAllUngroupedTasks() {
        return taskRepository.findAllByGroupNull();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    public Task createTask(Task task) {
        if (task.getGroup() != null && groupRepository.existsById(task.getGroup().getId())) {
            return taskRepository.save(task);
        } else if (task.getGroup() == null) {
            return taskRepository.save(task);
        } else {
            throw new ResourceNotFoundException("Group not found with id: " + task.getGroup().getId());
        }
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setDescription(updatedTask.getDescription());
        task.setTitle(updatedTask.getTitle());
        task.setCompleted(updatedTask.isCompleted());
        return taskRepository.save(task);
    }
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task assignTaskToGroup(Long taskId, Long groupId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        var group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));
        task.setGroup(group);
        return taskRepository.save(task);
    }

    public Task removeTaskFromGroup(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        task.setGroup(null);
        return taskRepository.save(task);
    }

}
