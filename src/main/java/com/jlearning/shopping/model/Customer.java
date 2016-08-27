package com.jlearning.shopping.model;

import org.hibernate.validator.constraints.NotEmpty;


public class Customer {
	
	private String firstName;
	
	private String lastName;
	
	@NotEmpty
	private String username;
	
	@NotEmpty
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void mapCustomerInfoToCustomerObject(Customer customerInfo){
		this.setFirstName(customerInfo.getFirstName());
		this.setLastName(customerInfo.getLastName());
	}
}
