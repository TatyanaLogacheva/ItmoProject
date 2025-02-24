package com.example.ItmoProject.service.impl;

import com.example.ItmoProject.exeption.CommonBackendException;
import com.example.ItmoProject.model.bd.entity.User;
import com.example.ItmoProject.model.bd.repository.UserRepository;
import com.example.ItmoProject.model.dto.request.UserInfoRequest;
import com.example.ItmoProject.model.dto.response.UserInfoResponse;
import com.example.ItmoProject.model.enums.Gender;
import com.example.ItmoProject.model.enums.UserStatus;
import com.example.ItmoProject.utils.PaginationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private  UserRepository userRepository;

    @Spy
    private  ObjectMapper mapper;

    @Test
    public void addUser() {
        UserInfoRequest req = new UserInfoRequest();
        req.setEmail("test@test.com");

        User user = new User();
        user.setId(1L);
        user.setEmail(req.getEmail());

        when(userRepository.save(any(User.class))).thenReturn(user);
        UserInfoResponse resp = userService.addUser(req);

        assertEquals(user.getId(), resp.getId());
    }

    @Test (expected = CommonBackendException.class)
    public void addUserInvalidEmail() {
        UserInfoRequest req = new UserInfoRequest();
        req.setEmail("testtest.com");

        userService.addUser(req);
    }

    @Test (expected = CommonBackendException.class)
    public void addUserExisted() {
        UserInfoRequest req = new UserInfoRequest();
        req.setEmail("test@test.com");

        User user = new User();
        user.setId(1L);
        user.setEmail(req.getEmail());

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        userService.addUser(req);
    }

    @Test
    public void getUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserInfoResponse userInfoResponse = userService.getUser(user.getId());
        assertEquals(user.getEmail(), userInfoResponse.getEmail());
    }

    @Test
    public void getUserFromDB() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User userFromDB = userService.getUserFromDB(user.getId());

        assertEquals(userFromDB.getEmail(), user.getEmail());
    }

    @Test(expected = CommonBackendException.class)
    public void getUserFromDBNotFound() {
        userService.getUserFromDB(1L);
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("DB@test.com");

        UserInfoRequest req = new UserInfoRequest();
        req.setEmail("test@mail.com");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.updateUser(user.getId(), req);
        assertEquals(req.getEmail(),user.getEmail());

    }

    @Test
    public void deleteUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteUser(user.getId());
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(UserStatus.DELETED, user.getStatus());
    }

    @Test
    public void getAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("test1@mail.ru");
        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("test2@test.com");
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(List.of(user1,user2));
        List<UserInfoResponse> userInfoResponses = userService.getAllUsers();
        assertEquals(users.get(0).getId(), userInfoResponses.get(0).getId());
        assertEquals(users.get(1).getId(), userInfoResponses.get(1).getId());

    }

    @Test
    public void updatedCarList() {
        User user = new User();
        user.setId(1L);
        userService.updatedCarList(user);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void getAllUsersOnPagesWithoutFilter() {
        Integer page = 3;
        Integer perPage = 1;
        String sort = "lastName";
        Sort.Direction order = Sort.Direction.ASC;
        String filter ="";
        Pageable pageReq = PaginationUtils.getPageRequest(page,perPage,sort, order);

        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("test1@mail.ru");
        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("test2@test.com");
        List<User> users = Arrays.asList(user1, user2);
        Page<User> pages = new PageImpl<>(users, pageReq, users.size());
        when(userRepository.findAll(any(Pageable.class))).thenReturn(pages);
        Page<UserInfoResponse> userInfoResponses = userService.getAllUsersOnPages(page, perPage, sort, order, filter);
        assertEquals(pages.getTotalPages(), userInfoResponses.getTotalPages());

    }
    @Test
    public void getAllUsersOnPagesWithFilter() {
        Integer page = 3;
        Integer perPage = 1;
        String sort = "lastName";
        Sort.Direction order = Sort.Direction.ASC;
        String filter ="id";
        Pageable pageReq = PaginationUtils.getPageRequest(page,perPage,sort, order);

        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("test1@mail.ru");
        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("test2@test.com");
        List<User> users = Arrays.asList(user1, user2);
        Page<User> pages = new PageImpl<>(users, pageReq, users.size());
        when(userRepository.findAllFiltered (pageReq, filter)).thenReturn(pages);
        Page<UserInfoResponse> userInfoResponses = userService.getAllUsersOnPages(page, perPage, sort, order, filter);
        assertEquals(pages.getTotalPages(), userInfoResponses.getTotalPages());

    }
}