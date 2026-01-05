package com.taskmanagementsystem.task_management_system.repository;


import com.taskmanagementsystem.task_management_system.model.Task;
import com.taskmanagementsystem.task_management_system.model.TaskPriority;
import com.taskmanagementsystem.task_management_system.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:priority IS NULL OR t.priority = :priority)")
    Page<Task> findAllByFilters(@Param("status") TaskStatus status,
                                @Param("priority") TaskPriority priority,
                                Pageable pageable);
}
