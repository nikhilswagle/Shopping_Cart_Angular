package com.jlearning.shopping.action;

import com.jlearning.shopping.model.Category;
import com.jlearning.shopping.model.CategoryList;
import com.jlearning.shopping.model.Product;
import com.jlearning.shopping.model.ProductList;
import com.jlearning.shopping.model.ProductMap;

public interface CategoryAndProductManager {

		public CategoryList retrieveProductCategories();
		
		public ProductList retrieveProductsForSelectedCategory(Category category);
		
		public ProductMap retrieveProductsByCategory(Category category);
		
		public Product retrieveProductById(Integer productId);
		
}
