package com.photoapp.api.users.ui.controllers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photoapp.api.users.service.UsersService;
import com.photoapp.api.users.shared.UserDTO;
import com.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.photoapp.api.users.ui.model.ResponseModel;
import com.photoapp.api.users.ui.model.UserResponseModel;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private Environment environment;
	@Autowired
	private UsersService service;
	@Value("${token.expriration_time}")
	private String tokenexpiration;

	@GetMapping("/status/check")
	public String status() {
		return "Running on Port:" + environment.getProperty("local.server.port") + ", tokenexpiration:"
				+ tokenexpiration;
	}

	@PostMapping
	public ResponseEntity<ResponseModel> createUser(@RequestBody CreateUserRequestModel createUserRequestModel) {

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO userDTO = mapper.map(createUserRequestModel, UserDTO.class);
		UserDTO createduser = service.createUser(userDTO);
		ResponseModel responseModel = mapper.map(createduser, ResponseModel.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
	}

	@GetMapping(value = "{userId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserResponseModel> getUser(@PathVariable String userId) {

		UserDTO dto = service.getUserByUserId(userId);
		ModelMapper mapper = new ModelMapper();
		UserResponseModel userResponseModel = mapper.map(dto, UserResponseModel.class);

		return ResponseEntity.status(HttpStatus.OK).body(userResponseModel);
	}

}
