package com.learning.taskmanager.controller;

import com.learning.taskmanager.dto.response.GroupResponse;
import com.learning.taskmanager.dto.response.TaskResponse;
import com.learning.taskmanager.model.Group;
import com.learning.taskmanager.model.Task;
import com.learning.taskmanager.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        List<GroupResponse> groups = groupService.findAll();
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{id}")
    public GroupResponse getGroupById(@PathVariable Long id) {
        return groupService.findByIdWithTaskCount(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResponse createGroup(@RequestBody Group group) {
        return groupService.createGroup(group);
    }

    @PutMapping("/{id}")
    public GroupResponse updateGroup(@PathVariable Long id, @RequestBody Group updatedGroup) {
        return groupService.updateGroup(id, updatedGroup);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroupWithTasks(@PathVariable Long id) {
        groupService.deleteById(id);
    }

    @DeleteMapping("/{id}/keep-tasks")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroupWithoutTasks(@PathVariable Long id) {
        Group group = groupService.findById(id);
        List<Task> tasks = group.getTasks();
        for (Task task : tasks) {
            task.setGroup(null);
        }
        groupService.deleteById(id);
    }

    @GetMapping("{id}/tasks")
    public List<TaskResponse> getTasksByGroupId(@PathVariable Long id) {
        return groupService.getTasksByGroupId(id);
    }
}
