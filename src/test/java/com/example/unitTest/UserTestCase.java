package com.example.unitTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import com.example.unitTest.controller.UserService;
import com.example.unitTest.entity.User;
import com.example.unitTest.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserTestCase {
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
//	Ist part of tests
//	@BeforeEach
//	void initTestCase() {
//		userService = new UserService(userRepository);
//	}
	
	
	
	@Test
	void savedUserHasRegistrationDate() {
		
		User user = new User();
		user.setId(1L);
		user.setName("Dimitar");
		user.setEmail("jdimitar@gmail.com");
		
		User savedUser = userService.createUserDirectly(user);
		assertThat(savedUser.getRegistrationDate()).isNotNull();
		
	}
	
	@Test
	void savedUserHasRegistrationDateMock() {
		
		User user = new User();
		user.setId(1L);
		user.setName("Dimitar");
		user.setEmail("jdimitar@gmail.com");
		
		userService.createUser(user);
		assertThat(user.getRegistrationDate()).isNotNull();
		
	}

}
