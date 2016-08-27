package com.jlearning.shopping.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jlearning.shopping.dao.CustomerDAO;
import com.jlearning.shopping.model.Customer;

/**
 * This class is used to verify if the login attempt was successful.
 * @author dwagle
 *
 */
@Service("customerManager")
public class CustomerManagerImpl implements CustomerManager{
	
	@Autowired
	@Qualifier("customerDao")
	private CustomerDAO dao;
	
	/**
	 * Used to verify if the login was successful
	 * @return
	 * True if the login was a success
	 * False if the login was a failure
	 */
	public boolean isValidCustomer(Customer customer){
		Customer customerInfo = dao.getCustomerInfo(customer);
		if(null != customerInfo && customerInfo.getPassword().equals(customer.getPassword())){
			customer.mapCustomerInfoToCustomerObject(customerInfo);
			return true;
		}
		else{
			return false;
		}
	}
}
