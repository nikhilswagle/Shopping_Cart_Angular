package com.jlearning.shopping.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.io.IOException;
import java.security.Key;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.jlearning.shopping.model.Customer;

public class JWTAuthenticationFilter extends
		AbstractAuthenticationProcessingFilter {
	
	private static final Key key = MacProvider.generateKey();
	
	protected JWTAuthenticationFilter() {
		super("/**");
	}	
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("JWTAuthenticationFilter#doFilter");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		try{
			attemptAuthentication(request, response);
			return;
		}
		catch(AuthenticationException ex){
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		}
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
		System.out.println("JWTAuthenticationFilter#attemptAuthentication");
		String authorizationHdr = request.getHeader("Authorization");
		if(null != authorizationHdr && !StringUtils.isEmpty(authorizationHdr) && authorizationHdr.startsWith("Bearer ")){
			String[] authHdrArr = authorizationHdr.split(" ");
			String token = authHdrArr[1];
			System.out.println("Token received : "+token);
			Customer c = parseToken(token);
		}
		else{
			throw new JWTTokenMissingException("No JWT token found in request headers");
		}
		return null;
	}
	
	public Customer parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();

            Customer c = new Customer();
            c.setUsername(body.getSubject());
            return c;

        } catch (JwtException ex) {
        	ex.printStackTrace();
            return null;
        }
    }
	
	public String generateToken(Customer c) {
        Claims claims = Jwts.claims().setSubject(c.getUsername());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
	
	/*
	@Override
	protected boolean requiresAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("JWTAuthenticationFilter#requiresAuthentication");
		return true;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("JWTAuthenticationFilter#successfulAuthentication");
		super.successfulAuthentication(request, response, chain, authResult);
		
		// We want to continue filtering by proceeding with the filter chain.
		// By default spring would stop the filter chain after first successful authentication and this is just header authentication 
		//chain.doFilter(request, response);
	}
	*/
}
