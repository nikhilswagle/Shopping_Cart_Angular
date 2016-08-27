package com.jlearning.shopping.model;

import java.util.HashMap;
import java.util.Map;

public class ProductMap {
	
	private Map<String, Product> productMap;

	public Map<String, Product> getProductMap() {
		return productMap;
	}

	public void setProductMap(Map<String, Product> productMap) {
		this.productMap = productMap;
	}
	
	public void addToProductMap(String key, Product prod){
		if(null == productMap){
			productMap = new HashMap<String, Product>();
		}
		productMap.put(key, prod);
	}
}
