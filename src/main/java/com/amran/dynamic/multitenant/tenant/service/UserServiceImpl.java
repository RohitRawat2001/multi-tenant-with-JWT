package com.amran.dynamic.multitenant.tenant.service;

import com.amran.dynamic.multitenant.tenant.entity.User;
import com.amran.dynamic.multitenant.tenant.entity.UserResponseDto;
import com.amran.dynamic.multitenant.tenant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User createUser(User user) {
        String password = generateRandomPassword(10);
        user.setPassword(passwordEncoder.encode(password));
        user.setPass(password);
        user.setStatus("ACTIVE");

      return   userRepository.save(user);

    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> savedUsers = userRepository.findAll();
        return savedUsers.stream()
                .map(user -> new UserResponseDto(
                        user.getUserId(),
                        user.getFullName(),
                        user.getGender(),
                        user.getUserName(),
                        user.getPassword(),
                        user.getPass(),
                        user.getStatus()
                ))
                .collect(Collectors.toList());
    }
    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#&$";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }
}
