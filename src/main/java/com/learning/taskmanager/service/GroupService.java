package com.learning.taskmanager.service;

import com.learning.taskmanager.dto.response.GroupResponse;
import com.learning.taskmanager.dto.response.TaskResponse;
import com.learning.taskmanager.exception.ResourceNotFoundException;
import com.learning.taskmanager.model.Group;
import com.learning.taskmanager.model.Task;
import com.learning.taskmanager.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public List<GroupResponse> findAll() {
        return groupRepository.findAllWithTaskCount();
    }

    public Group findById(Long id) {
        return groupRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Group not found"));
    }

    public GroupResponse findByIdWithTaskCount(Long id) {
        try {
            GroupResponse groupResponse = groupRepository.findByIdWithTaskCount(id);
            if (groupResponse == null) {
                throw new ResourceNotFoundException("Group not found with id: " + id);
            }
            return groupResponse;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Group not found with id: " + id);
        }
    }

    public GroupResponse createGroup(Group group) {
        return convertToResponse(groupRepository.save(group));
    }

    public GroupResponse updateGroup(Long id, Group updatedGroup) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + id));
        group.setTitle(updatedGroup.getTitle());
        return convertToResponse(groupRepository.save(group));
    }

    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }

    public List<TaskResponse> getTasksByGroupId(Long id) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + id));
        List<Task> tasks = group.getTasks();
        return tasks.stream().map(this::convertToResponse).toList();
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

    private GroupResponse convertToResponse(Group group) {
        return new GroupResponse(
                group.getId(),
                group.getTitle(),
                (long) (group.getTasks() != null ? group.getTasks().size() : 0),
                group.getCreatedAt(),
                group.getUpdatedAt()
        );
    }
}
