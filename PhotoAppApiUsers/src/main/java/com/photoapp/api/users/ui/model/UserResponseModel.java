package com.photoapp.api.users.ui.model;

import java.util.List;

public class UserResponseModel {
	private String firstName;
	private String lastName;
	private String email;
	private String userId;
	private List<AlbumResponseModel> albumResponseModels;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<AlbumResponseModel> getAlbumResponseModels() {
		return albumResponseModels;
	}

	public void setAlbumResponseModels(List<AlbumResponseModel> albumResponseModels) {
		this.albumResponseModels = albumResponseModels;
	}
}
