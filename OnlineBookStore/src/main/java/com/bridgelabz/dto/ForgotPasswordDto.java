package com.bridgelabz.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ForgotPasswordDto {

	@NotEmpty
	@Email(message="Enter the valid emailId")
	private String  emailId;
}
