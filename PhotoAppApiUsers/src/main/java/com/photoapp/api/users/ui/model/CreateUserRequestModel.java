package com.photoapp.api.users.ui.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateUserRequestModel {
	@NotNull(message = "First Name cannot be null")
	@Size(min = 2, max = 16, message = "First Name must be in between 2 to 16 Chars")
	private String firstName;
	@NotNull(message = "Last Name cannot be null")
	@Size(min = 2, max = 10, message = "Last Name must be in between 2 to 10 Chars")
	private String lastName;
	@Email(message = "Enter valid Email Id")
	private String email;

	@Size(min = 8, max = 16, message = "Password must be greater than 8 chars and less than 16 characters")
	@NotNull(message = " Password Cannot be null")
	private String password;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "CreateUserRequestModel [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + "]";
	}

}
