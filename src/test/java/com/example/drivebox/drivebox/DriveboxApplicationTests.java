package com.example.drivebox.drivebox;

import com.example.drivebox.drivebox.Controllers.UserController;
import com.example.drivebox.drivebox.dto.CreateUser;
import com.example.drivebox.drivebox.entity.User;
import com.example.drivebox.drivebox.repositroy.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;

import org.springframework.test.annotation.DirtiesContext;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.test.web.servlet.MockMvc;



@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DriveboxApplicationTests {
	@Autowired
	private UserController userController;
	@Autowired
	private UserRepo userRepo;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {

		userRepo.deleteAll();
	}
	@Test
	public void testUserRegistration() {
		CreateUser createUserDto = new CreateUser();
		createUserDto.setUsername("testuser");
		createUserDto.setPassword("testpassword");
		createUserDto.setEmail("testuser@example.com");

		ResponseEntity<User> responseEntity = userController.createUser(createUserDto);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		User registeredUser = responseEntity.getBody();
		assertNotNull(registeredUser);
		assertEquals("testuser", registeredUser.getUsername());
	}



}




