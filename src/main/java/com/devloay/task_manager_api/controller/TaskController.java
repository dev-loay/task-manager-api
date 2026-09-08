package com.devloay.task_manager_api.controller;

import com.devloay.task_manager_api.model.dto.TaskRequestDto;
import com.devloay.task_manager_api.model.dto.TaskResponseDto;
import com.devloay.task_manager_api.model.entity.Task;
import com.devloay.task_manager_api.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/user/{userId}/task")
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody @Valid TaskRequestDto dto, @PathVariable UUID userId){

        return new ResponseEntity<>(taskService.createTask(dto, userId), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/task/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id, @PathVariable UUID userId){

        return new ResponseEntity<>(taskService.getTaskById(id, userId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/tasks")
    public ResponseEntity<List<TaskResponseDto>> getAllTasks(@PathVariable UUID userId){

        return new ResponseEntity<>(taskService.getAllTasks(userId), HttpStatus.OK);
    }

    @PutMapping("/user/{userId}/task/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable Long id,
            @RequestBody @Valid TaskRequestDto dto,
            @PathVariable UUID userId){

        return new ResponseEntity<>(taskService.updateTask(id, dto, userId), HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, @PathVariable UUID userId){

        taskService.deleteTask(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/user/{userId}/task/{id}/status")
    public ResponseEntity<TaskResponseDto> updateTaskStatus(
            @PathVariable Long id,
            @RequestParam Task.TaskStatus newStatus,
            @PathVariable UUID userId){

        return new ResponseEntity<>(taskService.updateTaskStatus(id, newStatus, userId), HttpStatus.OK);
    }
}
