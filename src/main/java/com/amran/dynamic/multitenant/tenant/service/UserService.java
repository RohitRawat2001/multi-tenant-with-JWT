package com.amran.dynamic.multitenant.tenant.service;

import com.amran.dynamic.multitenant.tenant.entity.User;
import com.amran.dynamic.multitenant.tenant.entity.UserResponseDto;

import java.util.List;

public interface UserService {

    User createUser(User user);
    List<UserResponseDto> getAllUsers();
}
