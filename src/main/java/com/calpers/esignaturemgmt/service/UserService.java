package com.calpers.esignaturemgmt.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.calpers.esignaturemgmt.model.User;
import com.calpers.esignaturemgmt.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
}
