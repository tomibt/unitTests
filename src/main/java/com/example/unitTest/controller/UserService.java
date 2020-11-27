package com.example.unitTest.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.unitTest.entity.User;
import com.example.unitTest.repository.UserRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
//@RequiredArgsConstructor
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
//	1st case scenario
//	private final UserRepository userRepository;
//	
//	public UserService(UserRepository userRepository) {
//		this.userRepository = userRepository;
//	}
	
	
	public User createUserDirectly(User user) {
		user.setRegistrationDate(LocalDateTime.now());
		return user;
	}
	
	public User createUser(User user) {
		user.setRegistrationDate(LocalDateTime.now());
		return userRepository.save(user);
	}

}
