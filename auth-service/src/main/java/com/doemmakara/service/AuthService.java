package com.doemmakara.service;

import com.doemmakara.dto.request.LoginRequest;
import com.doemmakara.dto.request.SignupRequest;
import com.doemmakara.dto.response.LoginResponse;
import com.doemmakara.entity.User;

public interface AuthService {
	User createUser(SignupRequest request);
	LoginResponse authenticateUser(LoginRequest loginRequest);

}
