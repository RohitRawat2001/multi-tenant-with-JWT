package com.amran.dynamic.multitenant.tenant.controller;

import com.amran.dynamic.multitenant.tenant.entity.User;
import com.amran.dynamic.multitenant.tenant.entity.UserResponseDto;
import com.amran.dynamic.multitenant.tenant.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public User createUser(@RequestBody User user) {
        try {

            String userJson1 = objectMapper.writeValueAsString(user);
            log.info("User before created: {}", userJson1);
            User user1 = userService.createUser(user);
            String userJson = objectMapper.writeValueAsString(user1);
            log.info("User created: {}", userJson);
            return user1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }
}
