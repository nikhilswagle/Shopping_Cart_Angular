package com.jlearning.shopping.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/**
	 * This method will be invoked when the AuthenticationException occurs. This will override the spring's default redirect on Authentication failure
	 * request - that resulted in an AuthenticationException
	 * response - so that the user agent can begin authentication
	 * authException - that caused the invocation
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		System.out.println("Inside RestAuthenticationEntryPoint#commence");
		authException.printStackTrace();
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}
}
