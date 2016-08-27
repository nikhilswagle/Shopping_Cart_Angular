package com.jlearning.shopping.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jlearning.shopping.action.CategoryAndProductManager;
import com.jlearning.shopping.action.CustomerManager;
import com.jlearning.shopping.model.Cart;
import com.jlearning.shopping.model.Category;
import com.jlearning.shopping.model.CategoryList;
import com.jlearning.shopping.model.Customer;
import com.jlearning.shopping.model.ProductMap;

@Controller
public class ShoppingCartController
{
	private static final Logger LOG = Logger.getLogger(ShoppingCartController.class);
	
	@Autowired
	@Qualifier("customerManager")
    private CustomerManager cm;
	
	@Autowired
	@Qualifier("catAndProdManager")
    private CategoryAndProductManager cpm;

	@RequestMapping(value="/login",method=RequestMethod.GET)
    public void login(HttpServletRequest request, HttpServletResponse response)
    {
        System.out.println("Inside /login");
        
    }
	
    @RequestMapping(value="/validateLogin",method=RequestMethod.POST)
    @ResponseBody
    public Customer validateLogin(@RequestBody Customer customer, HttpServletRequest request, HttpServletResponse response)
    {
        System.out.println("Inside /validateLogin");
        HttpSession session = request.getSession(false);
        /*if(isSessionValid(session))
        {
            System.out.println("Valid Session");
            return (Customer)session.getAttribute("customer");
        }
        System.out.println("Invalid Session");*/
        if(cm.isValidCustomer(customer))
        {
            System.out.println("Set up new session and a cart");
            session = request.getSession(true);
            Cart cart = new Cart();
            session.setAttribute("cart", cart);
            session.setAttribute("customer", customer);
            return customer;
        } else
        {
            response.setStatus(400);
            return null;
        }
    }

    @RequestMapping(value="/logout",method=RequestMethod.GET)
    public String logoutUser(HttpServletRequest request)
    {
        System.out.println("Inside logout.htm");
        LOG.info("Inside logout.htm");
        if(null != request.getSession(false))
        {
            LOG.info("Invalidate the Session");
            request.getSession().invalidate();
        }
        String message = "You have been logged out successfully";
        return message;
    }

    private boolean isSessionValid(HttpSession session)
    {
        /*if(null != session)
        {
            Object custObj = session.getAttribute("customer");
            Object cartObj = session.getAttribute("cart");
            if(null != custObj && null != cartObj && (custObj instanceof Customer) && (cartObj instanceof Cart))
            {
                Customer cust = (Customer)custObj;
                if(null != cust && !StringUtils.isEmpty(cust.getUsername()))
                    return true;
            }
        }
        return false;*/
    	return true;
    }

    @RequestMapping(value="/displayCategories",method=RequestMethod.GET)
    @ResponseBody
    public CategoryList displayCategories(HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session = request.getSession(false);
        if(isSessionValid(session))
        {
            CategoryList catList = cpm.retrieveProductCategories();
            //session.setAttribute("categoryList", catList);
            return catList;
        } else
        {
            response.setStatus(400);
            return null;
        }
    }

    @RequestMapping(value="/retrieveProducts", method=RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
    public ProductMap retrieveProducts(@RequestParam(required=true) int categoryId, HttpServletRequest request, HttpServletResponse response)
    {
    	// Retrieve the products for the selected category
		System.out.println("Inside retrieveProducts.htm "+categoryId);
		ProductMap prodMap = null;
		HttpSession session = request.getSession(false);
		if(isSessionValid(session)){
			Category category = new Category();
			category.setId(categoryId);
			prodMap = cpm.retrieveProductsByCategory(category);
			for(String key : prodMap.getProductMap().keySet()){
				System.out.println(prodMap.getProductMap().get(key).getName());
			}			
		}
		else{
			response.setStatus(400);
		}
		//response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
		return prodMap;
    }
}
