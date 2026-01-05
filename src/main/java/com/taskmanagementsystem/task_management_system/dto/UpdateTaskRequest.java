package com.taskmanagementsystem.task_management_system.dto;


import com.taskmanagementsystem.task_management_system.model.TaskPriority;
import com.taskmanagementsystem.task_management_system.model.TaskStatus;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateTaskRequest {
    @Size(min = 3, max = 100)
    private String title;

    @Size(max = 500)
    private String description;

    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime dueDate;
    private Long assignedTo;
}
