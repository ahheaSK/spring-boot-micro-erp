package com.doemmakara.service;

import java.util.Optional;

import com.doemmakara.config.security.AuthUser;


public interface UserService {

	Optional<AuthUser> findUserByUsername(String username);

}
