package com.example.ItmoProject.service.impl;

import com.example.ItmoProject.model.dto.request.UserInfoRequest;
import com.example.ItmoProject.model.dto.response.UserInfoResponse;
import com.example.ItmoProject.model.enums.Gender;
import com.example.ItmoProject.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ObjectMapper mapper;

    @Override
    public UserInfoResponse getUser(Long id) {
        return UserInfoResponse.builder()
                .id(1L)
                .email("test@test.ru")
                .age(40)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .gender(Gender.MALE)
                .password("12345")
                .build();
    }

    @Override
    public UserInfoResponse addUser(UserInfoRequest req) {
        UserInfoResponse userInfoResponse= mapper.convertValue(req, UserInfoResponse.class);
        userInfoResponse.setId(1L);
        return userInfoResponse;
    }

    @Override
    public UserInfoResponse updateUser(Long id, UserInfoRequest req) {
        if(id!=1L){
            log.error("User with id {} not found", id);
            return null;
        }
        return UserInfoResponse.builder()
                .id(1L)
                .email("test2@test.ru")
                .age(40)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrovich")
                .gender(Gender.MALE)
                .password("****")
                .build();
    }

    @Override
    public void deleteUser(Long id) {
        if (id != 1L){
            log.error("User with id {} not found", id);
            return;
        }
        log.info("User with id {} deleted", id);

    }

    @Override
    public UserInfoResponse getUser(String email, String lastName) {
        return getUser(1L);
    }

    @Override
    public List<UserInfoResponse> getAllUsers() {
        return List.of(UserInfoResponse.builder()
                .id(1L)
                .email("test2@test.ru")
                .age(40)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrovich")
                .gender(Gender.MALE)
                .password("****")
                .build());
    }
}
