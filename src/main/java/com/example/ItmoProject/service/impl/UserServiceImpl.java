package com.example.ItmoProject.service.impl;

import com.example.ItmoProject.exeption.CommonBackendException;
import com.example.ItmoProject.model.bd.entity.User;
import com.example.ItmoProject.model.bd.repository.UserRepository;
import com.example.ItmoProject.model.dto.request.UserInfoRequest;
import com.example.ItmoProject.model.dto.response.UserInfoResponse;
import com.example.ItmoProject.model.enums.UserStatus;
import com.example.ItmoProject.service.UserService;
import com.example.ItmoProject.utils.PaginationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ObjectMapper mapper;
    private final UserRepository userRepository;

    @Override
    public UserInfoResponse addUser(UserInfoRequest req) {
        if(!EmailValidator.getInstance().isValid(req.getEmail())){
            throw new CommonBackendException("Email is invalid", HttpStatus.BAD_REQUEST);
        }

        userRepository.findByEmail(req.getEmail()).ifPresent(user -> {
            throw new CommonBackendException("User already existed", HttpStatus.CONFLICT);
        });

        User user = mapper.convertValue(req, User.class);
        user.setStatus(UserStatus.CREATED);

        User save = userRepository.save(user);
        return mapper.convertValue(save, UserInfoResponse.class);
    }

    @Override
    public UserInfoResponse getUser(Long id) {
        User user = getUserFromDB(id);
        return mapper.convertValue(user, UserInfoResponse.class);
    }

    @Override
    public User getUserFromDB(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        final String errMsg = String.format("User with id %s not found", id);
        return optionalUser.orElseThrow(()-> new CommonBackendException(errMsg, HttpStatus.NOT_FOUND));
    }



    @Override
    public UserInfoResponse updateUser(Long id, UserInfoRequest req) {
        if(!EmailValidator.getInstance().isValid(req.getEmail())){
            throw new CommonBackendException("Email is invalid", HttpStatus.BAD_REQUEST);
        }
        User userFromDB = getUserFromDB(id);
        User userReq = mapper.convertValue(req, User.class);

        userFromDB.setEmail(userReq.getEmail()==null ? userFromDB.getEmail() : userReq.getEmail());
        userFromDB.setPassword(userReq.getPassword()==null ? userFromDB.getPassword() : userReq.getPassword());
        userFromDB.setFirstName(userReq.getFirstName()==null ? userFromDB.getFirstName() : userReq.getFirstName());
        userFromDB.setLastName(userReq.getLastName()==null ? userFromDB.getLastName() : userReq.getLastName());
        userFromDB.setMiddleName(userReq.getMiddleName()==null ? userFromDB.getMiddleName() : userReq.getMiddleName());
        userFromDB.setAge(userReq.getAge()==null ? userFromDB.getAge() : userReq.getAge());
        userFromDB.setGender(userReq.getGender()==null ? userFromDB.getGender() : userReq.getGender());

        userFromDB = userRepository.save(userFromDB);
        return mapper.convertValue(userFromDB, UserInfoResponse.class);
    }

    @Override
    public void deleteUser(Long id) {
        User userFromDB = getUserFromDB(id);
        userFromDB.setStatus(UserStatus.DELETED);
        userRepository.save(userFromDB);
//        userRepository.deleteById(id);

    }

    @Override
    public List<UserInfoResponse> getAllUsers() {
       return userRepository.findAll().stream()
                .map(user -> mapper.convertValue(user, UserInfoResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public User updatedCarList(User updatedUser) {
        return userRepository.save(updatedUser);
    }

    @Override
    public Page<UserInfoResponse> getAllUsersOnPages(Integer page, Integer perPage, String sort, Sort.Direction order, String filter) {
        Pageable pageRequest = PaginationUtils.getPageRequest(page, perPage, sort, order);
        Page<User> users;
        if(StringUtils.hasText(filter)){
            users = userRepository.findAllFiltered(pageRequest, filter);

        } else{
            users = userRepository.findAll(pageRequest);

        }
        List<UserInfoResponse> content = users.stream()
                .map(u -> mapper.convertValue(u, UserInfoResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageRequest, users.getTotalElements());

    }
}
