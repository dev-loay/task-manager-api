package com.devloay.task_manager_api.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequestDto {

    @NotBlank(message = "Task title  is required")
    @Size(max = 50, message = "Task title must not exceed 30 characters")
    private String title;

    @Size(max = 500, message = "Task description must not exceed 30 characters")
    private String description;
    private LocalDate dueDate;
}
