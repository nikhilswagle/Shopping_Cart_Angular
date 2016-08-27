package com.jlearning.shopping.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jlearning.shopping.dao.CategoryAndProductDAO;
import com.jlearning.shopping.model.Category;
import com.jlearning.shopping.model.CategoryList;
import com.jlearning.shopping.model.Product;
import com.jlearning.shopping.model.ProductList;
import com.jlearning.shopping.model.ProductMap;

@Service("catAndProdManager")
public class CategoryAndProductManagerImpl implements CategoryAndProductManager {

	@Autowired
	@Qualifier("catAndProdDao")
	private CategoryAndProductDAO dao;
	
	@Override
	public CategoryList retrieveProductCategories() {		
		return dao.getProductCategories();
	}

	@Override
	public ProductList retrieveProductsForSelectedCategory(Category category) {
		return dao.getProductsForSelectedCategory(category);
	}

	@Override
	public ProductMap retrieveProductsByCategory(Category category) {
		return dao.getProductsByCategory(category);
	}

	@Override
	public Product retrieveProductById(Integer productId) {
		return dao.getProductById(productId);
	}
}
