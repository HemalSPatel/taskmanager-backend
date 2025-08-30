package com.learning.taskmanager.service;

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
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task createTask(Task task) {
        if (task.getGroup() != null && groupRepository.existsById(task.getGroup().getId())) {
            return taskRepository.save(task);
        } else if (task.getGroup() == null) {
            return taskRepository.save(task);
        } else {
            throw new RuntimeException("Group not found");
        }
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setDescription(updatedTask.getDescription());
        task.setTitle(updatedTask.getTitle());
        task.setCompleted(updatedTask.isCompleted());
        return taskRepository.save(task);
    }
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task assignTaskToGroup(Long taskId, Long groupId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        var group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        task.setGroup(group);
        return taskRepository.save(task);
    }

    public Task removeTaskFromGroup(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setGroup(null);
        return taskRepository.save(task);
    }

}
