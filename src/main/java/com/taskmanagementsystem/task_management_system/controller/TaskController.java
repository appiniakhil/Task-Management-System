package com.taskmanagementsystem.task_management_system.controller;

import com.taskmanagementsystem.task_management_system.dto.CreateTaskRequest;
import com.taskmanagementsystem.task_management_system.dto.TaskDTO;
import com.taskmanagementsystem.task_management_system.dto.UpdateTaskRequest;
import com.taskmanagementsystem.task_management_system.model.TaskPriority;
import com.taskmanagementsystem.task_management_system.model.TaskStatus;
import com.taskmanagementsystem.task_management_system.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody CreateTaskRequest request) {
        TaskDTO createdTask = taskService.createTask(request);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<TaskDTO>> getAllTasks(@RequestParam(required = false) TaskStatus status,
                                                     @RequestParam(required = false) TaskPriority priority,
                                                     @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(taskService.getAllTasks(status, priority, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskDTO> updateTaskStatus(
            @PathVariable Long id,
            @RequestBody Map<String, TaskStatus> statusUpdate) {
        // statusUpdate expected as { "status": "IN_PROGRESS" }
        TaskStatus status = statusUpdate.get("status");
        if (status == null) {
            throw new IllegalArgumentException("Status is required");
        }
        return ResponseEntity.ok(taskService.updateTaskStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}

