package com.example.ItmoProject.controllers;

import com.example.ItmoProject.model.dto.request.UserInfoRequest;
import com.example.ItmoProject.model.dto.response.UserInfoResponse;
import com.example.ItmoProject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping ("/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по id")
    public UserInfoResponse getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PostMapping
    @Operation(summary = "Добавить пользователя")
    public UserInfoResponse addUser(@RequestBody @Valid UserInfoRequest req){
        return userService.addUser(req);
    }

    @PutMapping("/{id}")
    @Operation (summary = "Обновить данные пользователя по id")
    public UserInfoResponse updateUser(@PathVariable Long id, @RequestBody UserInfoRequest req){
        return userService.updateUser(id, req);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя по id")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);

    }

    @GetMapping("/all")
    @Operation(summary = "Получить список всех пользователей")
    public List<UserInfoResponse> getAllUsers(){
        return userService.getAllUsers();

    }

    @GetMapping("/pages")
    @Operation(summary = "Получить постаничный список всех пользователей")
    public Page<UserInfoResponse> getAllUsersOnPages(@RequestParam(defaultValue = "1") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer perPage,
                                                     @RequestParam(defaultValue = "lastName") String sort,
                                                     @RequestParam(defaultValue = "ASC") Sort.Direction order,
                                                     @RequestParam(required = false) String filter) {
        return userService.getAllUsersOnPages(page, perPage, sort, order, filter);
    }
}

