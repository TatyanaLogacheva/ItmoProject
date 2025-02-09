package com.example.ItmoProject.service;

import com.example.ItmoProject.model.dto.request.UserInfoRequest;
import com.example.ItmoProject.model.dto.response.UserInfoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
   UserInfoResponse getUser(Long id);

    UserInfoResponse addUser(UserInfoRequest req);

    UserInfoResponse updateUser(Long id, UserInfoRequest req);

    List<UserInfoResponse> getAllUsers();

    void deleteUser(Long id);

    UserInfoResponse getUser(String email, String lastName);
}
