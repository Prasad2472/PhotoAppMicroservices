package com.photoapp.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.photoapp.api.users.shared.UserDTO;

public interface UsersService extends UserDetailsService {
	UserDTO createUser(UserDTO userDto);

	UserDTO findByEmail(String email);

	UserDTO getUserByUserId(String userId);
}
