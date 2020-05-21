package com.dreams.security;

import org.springframework.security.core.AuthenticationException;

public class CodeException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public CodeException(String msg) {
		super(msg);
	}

}
