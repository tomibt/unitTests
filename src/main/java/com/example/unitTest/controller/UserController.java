package com.example.unitTest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.unitTest.entity.Address;
import com.example.unitTest.entity.User;
import com.example.unitTest.repository.AddressRepository;
import com.example.unitTest.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@PostMapping("/update/{userId}")
	public User updateAddress(@PathVariable("userId")Long userId, @RequestBody Address address) {
		
		User user = userRepository.findById(userId).get();
		
		addressRepository.save(address);
		user.setAddress(address);
		userRepository.save(user);
		
		return user;
	}
	
	
	@GetMapping("/hello")
	public String hello() {
		
		return "Hello";
	}
	

}
