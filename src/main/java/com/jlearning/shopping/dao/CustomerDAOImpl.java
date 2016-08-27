package com.jlearning.shopping.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.jlearning.shopping.model.Customer;

@Service("customerDao")
public class CustomerDAOImpl extends JdbcDaoSupport implements CustomerDAO {

	@Autowired
	public CustomerDAOImpl(DataSource dataSource) {
	    setDataSource(dataSource);
	}
	
	private static final String GET_USER_INFO_BY_USERNAME = 
				"SELECT * FROM customer WHERE cust_username = ?";
	
	@Override
	public Customer getCustomerInfo(Customer customer) {
		// TODO Auto-generated method stub
		Customer customerInfo = null;
		try{
			customerInfo = getJdbcTemplate().queryForObject(GET_USER_INFO_BY_USERNAME, 
							new Object[]{customer.getUsername()},
							new CustomerMapper());
		}
		catch(EmptyResultDataAccessException emptyResultEx){
			System.out.println("Resultset is empty.");
		}
		return customerInfo;
	}
	
	// Class to map the resultset to the Customer pojo.
	private class CustomerMapper implements RowMapper<Customer>{

	    @Override
	    public Customer mapRow(ResultSet rs, int rowNum)
	        throws SQLException {
	      Customer customer = null; 
	      if(null != rs){
		      customer = new Customer();
		      customer.setFirstName(rs.getString("first_name"));
		      customer.setLastName(rs.getString("last_name"));
		      customer.setUsername(rs.getString("cust_username"));
		      customer.setPassword(rs.getString("cust_password"));
	      }
	      return customer;
	    }	    
	}
}
