package com.rso40.userservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rso40.userservice.dto.UserRequest;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class UserServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:3.2.4");
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry){
		dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateUsers() throws Exception {
		UserRequest userRequest = getUserRequest();
		String userRequestString = objectMapper.writeValueAsString(userRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userRequestString))
			.andExpect(MockMvcResultMatchers.status().isCreated());
		
	}

	private UserRequest getUserRequest() {
		return UserRequest.builder()
			.name("Anja")
			.email("ab0555@student.uni-lj.si")
			.password("rso2022")
			.build();
	}

}
