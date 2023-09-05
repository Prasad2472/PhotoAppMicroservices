package com.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.photoapp.api.users.data.AlbumsServiceClient;
import com.photoapp.api.users.data.UserEntity;
import com.photoapp.api.users.data.UsersRepository;
import com.photoapp.api.users.shared.UserDTO;
import com.photoapp.api.users.ui.model.AlbumResponseModel;

@Service
public class UsersServiceImpl implements UsersService {
private static Logger LOG = LoggerFactory.getLogger(UsersServiceImpl.class);
	
	private UsersRepository repository;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	
	private AlbumsServiceClient albumsServiceClient;

	@Autowired
	public UsersServiceImpl(UsersRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder,
			AlbumsServiceClient albumsServiceClient) {
		this.repository = repository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.albumsServiceClient = albumsServiceClient;
	}

	@Override
	public UserDTO createUser(UserDTO userDto) {

		userDto.setUserId(UUID.randomUUID().toString());
		userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = mapper.map(userDto, UserEntity.class);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		repository.save(userEntity);
		UserDTO createdUser = mapper.map(userEntity, UserDTO.class);
		return createdUser;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity entity = repository.findByEmail(username);
		if (entity == null)
			throw new UsernameNotFoundException(username);
		return new User(entity.getEmail(), entity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public UserDTO findByEmail(String email) {
		UserEntity entity = repository.findByEmail(email);
		if (entity == null)
			throw new UsernameNotFoundException(email);

		return new ModelMapper().map(entity, UserDTO.class);
	}

	@Override
	public UserDTO getUserByUserId(String userId) {
		UserEntity entity = repository.findByUserId(userId);
		if (entity == null)
			throw new UsernameNotFoundException("User not found with userID:" + userId);
		
		UserDTO userDto = new ModelMapper().map(entity, UserDTO.class);
		/**
		 * RestTemplate is no longer needed now.
		 */
		/*
		 String url = String.format(environment.getProperty("albums.service.url"), userId);
		 ResponseEntity<List<AlbumResponseModel>> albumListResponse = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<AlbumResponseModel>>() {
				});
		List<AlbumResponseModel> albumResponseModels = albumListResponse.getBody();*/
		LOG.debug("Before calling Album Micro Service");
		List<AlbumResponseModel> albumResponseModels = albumsServiceClient.getAlbums(userId);
		LOG.debug("After calling Album Micro Service");
		userDto.setAlbumResponseModels(albumResponseModels);

		return userDto;
	}

}
