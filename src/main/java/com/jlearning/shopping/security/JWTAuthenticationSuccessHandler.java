package com.jlearning.shopping.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {

	/**
	 * Called when user is successfully authenticated.
	 * Empty implementation to prevent spring's default redirect upon success
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {
		System.out.println("Inside JWTAuthenticationSuccessHandler#onAuthenticationSuccess");
	}

}
