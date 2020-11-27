package com.example.unitTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.AutoConfigureDataNeo4j;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import com.example.unitTest.controller.UserController;
import com.example.unitTest.entity.Address;
import com.example.unitTest.entity.User;
import com.example.unitTest.repository.AddressRepository;
import com.example.unitTest.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import javassist.ClassPath;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = UserController.class)
@SpringBootTest(properties = { "spring.jpa.hibernate.ddl-auto=update", "spring.liquibase.enabled=false",
		"spring.flyway.enabled=false" })
@AutoConfigureMockMvc
public class UserMvcTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	AddressRepository addressRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void whenValidInput_returns200() throws Exception {

		Address address = new Address();
		address.setCity("Skopje");
		address.setStreet("Partizanska");
		address.setZip("1000");

		mockMvc.perform(post("/update/{userId}", 1L).contentType("application/json")
				.content(objectMapper.writeValueAsString(address))).andExpect(status().isOk());

	}

	@Test
	void receive_Hellо() throws Exception {
		mockMvc.perform(get("/hello").contentType("application/json")).andExpect(status().isOk());

	}

	// input validation: check if user contain fields that are anotated with Not
	// Null
	@Test
	void verifyInputValidation() throws Exception {

		Address address = null;

		User user = new User();
		user.setId(3L);
		user.setName(null);
		user.setEmail("email@email.com");
		user.setAddress(address);
		userRepository.save(user);

		mockMvc.perform(post("/update/{userId}", 3L).contentType("application/json")
				.content(objectMapper.writeValueAsString(address))).andExpect(status().isBadRequest());

	}

	// check if method response is valid
	@Test
	void verifyResponse_ifRequestIsValid() throws Exception {

		MvcResult mvcResult = mockMvc.perform(get("/hello").contentType("application/json")) // request
				.andReturn();

//			  User expectedResponseBody = ;//what is expected as result

		String expectedResponseBody = "Hello";
		String actualResponseBody = mvcResult.getResponse().getContentAsString();

		assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
//						objectMapper.writeValueAsString(expectedResponseBody));
	}

	// check valid response in JSON format
	@Test
	void whenValidInput_thenReturnsUserResource_withFluentApi() throws Exception {
//	  User user = ...;

//		{
//		    "id": 1,
//		    "name": "Dimitar",
//		    "email": "jdimitar@gmail.com",
//		    "registrationDate": null,
//		    "address": {
//		        "id": 11,
//		        "street": "Partizanska",
//		        "city": "Skopje",
//		        "zip": "1000"
//		    }
//		}
		Address expectedAddress = new Address();
		expectedAddress.setId(11L);
		expectedAddress.setCity("Skopje");
		expectedAddress.setStreet("Partizanska");
		expectedAddress.setZip("1000");

		User expectedUserResponse = new User(1L, "Dimitar", "jdimitar@gmail.com", null, expectedAddress);

		mockMvc.perform(post("/update/{userId}", 1L).contentType("application/json")
				.content(objectMapper.writeValueAsString(expectedAddress)))
				.andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(expectedUserResponse, User.class));
	}

}
