package com.springboot.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.springboot.entities.User;
import  com.springboot.web.*;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
}
