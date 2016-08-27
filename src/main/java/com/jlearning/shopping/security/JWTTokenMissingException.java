package com.jlearning.shopping.security;

import org.springframework.security.core.AuthenticationException;

public class JWTTokenMissingException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5896043491397889121L;

	public JWTTokenMissingException(String msg) {
		super(msg);
	}
}
