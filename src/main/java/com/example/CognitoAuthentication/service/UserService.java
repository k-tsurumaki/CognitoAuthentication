package com.example.CognitoAuthentication.service;

import com.example.CognitoAuthentication.entity.User;
import com.example.CognitoAuthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Long createUser(String username, String email) {
		User user = User.builder()
				.username(username)
				.email(email)
				.build();
		user = userRepository.save(user);
		return user.getUserId();
	}
}
