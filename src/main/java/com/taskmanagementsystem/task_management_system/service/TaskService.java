package com.taskmanagementsystem.task_management_system.service;


import com.taskmanagementsystem.task_management_system.dto.CreateTaskRequest;
import com.taskmanagementsystem.task_management_system.dto.TaskDTO;
import com.taskmanagementsystem.task_management_system.dto.UpdateTaskRequest;
import com.taskmanagementsystem.task_management_system.exception.ResourceNotFoundException;
import com.taskmanagementsystem.task_management_system.model.Task;
import com.taskmanagementsystem.task_management_system.model.TaskPriority;
import com.taskmanagementsystem.task_management_system.model.TaskStatus;
import com.taskmanagementsystem.task_management_system.model.User;
import com.taskmanagementsystem.task_management_system.repository.TaskRepository;
import com.taskmanagementsystem.task_management_system.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskDTO createTask(@Valid CreateTaskRequest request) {
        User assignedUser = null;

        if (request.getAssignedTo() != null) {
            assignedUser = userRepository.findById(request.getAssignedTo())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Assigned user not found with id: " + request.getAssignedTo()));
        }

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .assignedTo(assignedUser)
                .build();

        return TaskDTO.fromEntity(taskRepository.save(task));
    }

    public Page<TaskDTO> getAllTasks(TaskStatus status, TaskPriority priority, Pageable pageable) {
        return taskRepository.findAllByFilters(status, priority, pageable)
                .map(TaskDTO::fromEntity);
    }

    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(TaskDTO::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    public TaskDTO updateTask(Long id, UpdateTaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        if (request.getTitle() != null)
            task.setTitle(request.getTitle());
        if (request.getDescription() != null)
            task.setDescription(request.getDescription());
        if (request.getStatus() != null)
            task.setStatus(request.getStatus());
        if (request.getPriority() != null)
            task.setPriority(request.getPriority());
        if (request.getDueDate() != null)
            task.setDueDate(request.getDueDate());

        if (request.getAssignedTo() != null) {
            User assignedUser = userRepository.findById(request.getAssignedTo())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Assigned user not found with id: " + request.getAssignedTo()));
            task.setAssignedTo(assignedUser);
        }

        return TaskDTO.fromEntity(taskRepository.save(task));
    }

    public TaskDTO updateTaskStatus(Long id, TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setStatus(status);
        return TaskDTO.fromEntity(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}
