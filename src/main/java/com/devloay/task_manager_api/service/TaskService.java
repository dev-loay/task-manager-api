package com.devloay.task_manager_api.service;

import com.devloay.task_manager_api.exception.ConflictException;
import com.devloay.task_manager_api.exception.ResourceNotFoundException;
import com.devloay.task_manager_api.model.dto.TaskRequestDto;
import com.devloay.task_manager_api.model.dto.TaskResponseDto;
import com.devloay.task_manager_api.model.entity.Task;
import com.devloay.task_manager_api.model.entity.User;
import com.devloay.task_manager_api.repository.TaskRepository;
import com.devloay.task_manager_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskResponseDto createTask(TaskRequestDto dto, UUID userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("not found user by id: " + userId));

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setUser(user);
        task = taskRepository.save(task);

        TaskResponseDto taskResponseDto = new TaskResponseDto(
                task.getId(), task.getUser().getName(), task.getTitle(),
                task.getDescription(), task.getStatus().toString(), task.getCreatedAt(),
                task.getDueDate()
        );
        return taskResponseDto;
    }

    public TaskResponseDto getTaskById(Long id, UUID userId){

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found task by id: " + id));

        if (!task.getUser().getId().equals(userId)){
            throw new ConflictException("this user has not have task by id: " + id);
        }

        TaskResponseDto taskResponseDto = new TaskResponseDto(
                task.getId(), task.getUser().getName(), task.getTitle(),
                task.getDescription(), task.getStatus().toString(), task.getCreatedAt(),
                task.getDueDate()
        );
        return taskResponseDto;

    }

    public List<TaskResponseDto> getAllTasks(UUID userId){

        if (!userRepository.existsById(userId))
            throw new ResourceNotFoundException("not found user by id: " + userId);

        List<Task> taskList = taskRepository.findByUserId(userId);

        List<TaskResponseDto> taskResponseDtoList = taskList.stream().map(task -> {
            TaskResponseDto taskResponseDto = new TaskResponseDto(
                    task.getId(), task.getUser().getName(), task.getTitle(),
                    task.getDescription(), task.getStatus().toString(), task.getCreatedAt(),
                    task.getDueDate()
            );
            return taskResponseDto;
        }).toList();

        return taskResponseDtoList;
    }

    public TaskResponseDto updateTask(Long id, TaskRequestDto dto, UUID userId){

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found task by id: " + id));

        if (!task.getUser().getId().equals(userId)){
            throw new ConflictException("this user has not have task by id: " + id);
        }

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task = taskRepository.save(task);

        TaskResponseDto taskResponseDto = new TaskResponseDto(
                task.getId(), task.getUser().getName(), task.getTitle(),
                task.getDescription(), task.getStatus().toString(), task.getCreatedAt(),
                task.getDueDate()
        );
        return taskResponseDto;

    }

    public  void deleteTask(Long id, UUID userId){

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found task by id: " + id));

        if (!task.getUser().getId().equals(userId)){
            throw new ConflictException("this user has not have task by id: " + id);
        }

        taskRepository.delete(task);
    }

    public TaskResponseDto updateTaskStatus(Long id, Task.TaskStatus newStatus, UUID userId){

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found task by id: " + id));

        if (!task.getUser().getId().equals(userId)){
            throw new ConflictException("this user has not have task by id: " + id);
        }

        task.setStatus(newStatus);
        taskRepository.save(task);

        TaskResponseDto taskResponseDto = new TaskResponseDto(
                task.getId(), task.getUser().getName(), task.getTitle(),
                task.getDescription(), task.getStatus().toString(), task.getCreatedAt(),
                task.getDueDate()
        );
        return taskResponseDto;
    }
}
