package com.example.ItmoProject.controllers;

import com.example.ItmoProject.model.dto.request.UserInfoRequest;
import com.example.ItmoProject.model.dto.response.UserInfoResponse;
import com.example.ItmoProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserInfoResponse getUser(@PathVariable Long id){
        return userService.getUser(id);
    }
    @PostMapping
    public UserInfoResponse addUser(@RequestBody UserInfoRequest req){
        return userService.addUser(req);
    }

    @PutMapping("/{id}")
    public UserInfoResponse updateUser(@PathVariable Long id, @RequestBody UserInfoRequest req){
        return userService.updateUser(id, req);

    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);

    }

    @GetMapping("/all")
    public List<UserInfoResponse> getAllUsers(){
        return userService.getAllUsers();

    }

    @GetMapping("/pages")
    public Page<UserInfoResponse> getAllUsersOnPages(@RequestParam(defaultValue = "1") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer perPage,
                                                     @RequestParam(defaultValue = "lastName") String sort,
                                                     @RequestParam(defaultValue = "ASC") Sort.Direction order,
                                                     @RequestParam(required = false) String filter) {
        return userService.getAllUsersOnPages(page, perPage, sort, order, filter);
    }
}

