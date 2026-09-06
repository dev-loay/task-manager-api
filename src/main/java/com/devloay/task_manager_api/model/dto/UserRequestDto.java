package com.devloay.task_manager_api.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {

    @NotBlank(message = "username is required")
    @Size(max = 30, message = "Username must not exceed 30 characters")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

}
