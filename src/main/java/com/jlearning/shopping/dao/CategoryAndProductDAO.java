package com.jlearning.shopping.dao;

import com.jlearning.shopping.model.Category;
import com.jlearning.shopping.model.CategoryList;
import com.jlearning.shopping.model.Product;
import com.jlearning.shopping.model.ProductList;
import com.jlearning.shopping.model.ProductMap;

public interface CategoryAndProductDAO {
	
	public CategoryList getProductCategories();
	
	public ProductList getProductsForSelectedCategory(Category category);
	
	public ProductMap getProductsByCategory(Category category);
	
	public Product getProductById(Integer productId);
}
