package com.example.ItmoProject.controllers;

import com.example.ItmoProject.model.dto.request.UserInfoRequest;
import com.example.ItmoProject.model.dto.response.UserInfoResponse;
import com.example.ItmoProject.model.enums.Gender;
import com.example.ItmoProject.service.UserService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public UserInfoResponse getUserWithParams(@RequestParam (required = false) String email, @RequestParam String lastName){
        return userService.getUser(email, lastName);

    }
}

