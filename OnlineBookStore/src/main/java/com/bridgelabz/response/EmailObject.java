package com.bridgelabz.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


public class EmailObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String email;
	private String subject;
	private String message;

	public EmailObject(String email, String subject, String message) {
		this.email = email;
		this.subject = subject;
		this.message = message;
	}

	public String getEmail() {
		return this.email;
	}

	public String getSubject() {
		return this.subject;
	}

	public String getMessage() {
		return this.message;
	}
}
