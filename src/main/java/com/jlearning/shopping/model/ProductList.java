package com.jlearning.shopping.model;

import java.util.ArrayList;
import java.util.List;

public class ProductList {

	private List<Product> products;

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public void addToProductList(Product product){
		if(null == products){
			products = new ArrayList<Product>();
		}
		products.add(product);
	}
}
