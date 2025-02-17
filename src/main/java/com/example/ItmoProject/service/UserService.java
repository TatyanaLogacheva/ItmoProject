package com.example.ItmoProject.service;

import com.example.ItmoProject.model.bd.entity.User;
import com.example.ItmoProject.model.dto.request.UserInfoRequest;
import com.example.ItmoProject.model.dto.response.CarInfoResp;
import com.example.ItmoProject.model.dto.response.UserInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
 UserInfoResponse getUser(Long id);

 UserInfoResponse addUser(UserInfoRequest req);

 User getUserFromDB(Long id);

 UserInfoResponse updateUser(Long id, UserInfoRequest req);

 List<UserInfoResponse> getAllUsers();

 void deleteUser(Long id);

 User updatedCarList(User updatedUser);

 Page<UserInfoResponse> getAllUsersOnPages(Integer page, Integer perPage, String sort, Sort.Direction order, String filter);
}
