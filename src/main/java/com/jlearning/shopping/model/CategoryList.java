package com.jlearning.shopping.model;

import java.util.ArrayList;
import java.util.List;

public class CategoryList {
	
	private List<Category> categories;

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categoryList) {
		this.categories = categoryList;
	}
	
	public void addToCategories(Category category){
		if(null == categories){
			categories = new ArrayList<Category>();			
		}
		categories.add(category);
	}
}
