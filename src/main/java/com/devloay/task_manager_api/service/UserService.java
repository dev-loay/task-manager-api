package com.devloay.task_manager_api.service;

import com.devloay.task_manager_api.exception.ConflictException;
import com.devloay.task_manager_api.exception.ResourceNotFoundException;
import com.devloay.task_manager_api.model.dto.UserRequestDto;
import com.devloay.task_manager_api.model.dto.UserResponseDto;
import com.devloay.task_manager_api.model.entity.User;
import com.devloay.task_manager_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto createUser(UserRequestDto dto){

       if (userRepository.existsByEmail(dto.getEmail())) {
           throw new ConflictException("This email is already register");
       }

       User user = new User();
       user.setName(dto.getName());
       user.setEmail(dto.getEmail());
       user = userRepository.save(user);

       UserResponseDto userResponseDto = new UserResponseDto();
       userResponseDto.setId(user.getId());
       userResponseDto.setName(user.getName());
       userResponseDto.setEmail(user.getEmail());

       return userResponseDto;
    }

    public UserResponseDto getUserById(UUID id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found user by id: " + id));

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setEmail(user.getEmail());

        return userResponseDto;
    }

    public List<UserResponseDto> getAllUsers(){

        List<User> userList = userRepository.findAll();
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();

        userList.forEach(user -> {
            UserResponseDto userResponseDto = new UserResponseDto(
                    user.getId(),
                    user.getName(),
                    user.getEmail()
            );
            userResponseDtoList.add(userResponseDto);
        });

        return userResponseDtoList;

    }

    public UserResponseDto updateUser(UUID id, UserRequestDto dto){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found user by id: " + id));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        userRepository.save(user);

        UserResponseDto userResponseDto = new UserResponseDto(
                user.getId(), user.getName(), user.getEmail()
        );
        return userResponseDto;
    }

    public void deleteUser(UUID id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found user by id: " + id));

        userRepository.delete(user);
    }
}
