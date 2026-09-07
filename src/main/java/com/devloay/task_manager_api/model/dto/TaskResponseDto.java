package com.devloay.task_manager_api.model.dto;

import com.devloay.task_manager_api.model.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDto {

    private Long id;
    private String userName;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDate dueDate;
}
