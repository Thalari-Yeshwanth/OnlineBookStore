package com.bridgelabz.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserException extends Exception {

	private String message;

	public enum ExceptionType {
		INVALID_USER, INVALID_CREDENTIALS,ALREADY_VERFIED
	}
	public ExceptionType type;

}
