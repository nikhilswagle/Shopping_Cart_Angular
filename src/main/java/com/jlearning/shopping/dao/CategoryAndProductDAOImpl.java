package com.jlearning.shopping.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.jlearning.shopping.model.Category;
import com.jlearning.shopping.model.CategoryList;
import com.jlearning.shopping.model.Product;
import com.jlearning.shopping.model.ProductList;
import com.jlearning.shopping.model.ProductMap;

@Service("catAndProdDao")
public class CategoryAndProductDAOImpl extends JdbcDaoSupport implements CategoryAndProductDAO {

	@Autowired
	public CategoryAndProductDAOImpl(DataSource datasource){
		setDataSource(datasource);
	}
	
	private static final String GET_PRODUCT_CATEGORIES = 
				"SELECT * FROM product_category"; 
	
	private static final String GET_PRODUCTS_BY_CATEGORY = 
				"SELECT p.prod_id,p.prod_name,p.prod_amt,p.prod_in_stock_qty,pc.category_id,pc.category_name " +
				"FROM product p " +
				"INNER JOIN product_category pc " +
				"ON p.category_id = pc.category_id " +
				"WHERE pc.category_id = ?";
	
	private static final String GET_PRODUCT_BY_ID = 
				"SELECT p.prod_id,p.prod_name,p.prod_amt,p.prod_in_stock_qty,pc.category_id,pc.category_name " +
				"FROM product p " +
				"INNER JOIN product_category pc " +
				"ON p.category_id = pc.category_id " +
				"WHERE p.prod_id = ?";
	
	@Override
	public CategoryList getProductCategories() {
		// TODO Auto-generated method stub
		CategoryList catList = null;
		try{
			catList = (CategoryList) getJdbcTemplate()
									.queryForObject(GET_PRODUCT_CATEGORIES, new CategoryMapper());
		}
		catch(EmptyResultDataAccessException emptyResultEx){
			System.out.println("Resultset is empty.");
		}
		return catList;
	}

	@Override
	public ProductList getProductsForSelectedCategory(Category category) {
		// TODO Auto-generated method stub
		ProductList prodList = null;
		try{
			prodList = (ProductList) getJdbcTemplate()
									.queryForObject(GET_PRODUCTS_BY_CATEGORY, 
											new Object[]{Integer.valueOf(category.getId())}, 
											new ProductMapper());
		}
		catch(EmptyResultDataAccessException emptyResultEx){
			System.out.println("Resultset is empty.");
		}
		return prodList;
	}
	
	@Override
	public ProductMap getProductsByCategory(Category category) {
		// TODO Auto-generated method stub
		ProductMap prodMap = null;
		try{
			prodMap = (ProductMap) getJdbcTemplate()
									.queryForObject(GET_PRODUCTS_BY_CATEGORY, 
											new Object[]{Integer.valueOf(category.getId())}, 
											new NewProductMapper());
		}
		catch(EmptyResultDataAccessException emptyResultEx){
			System.out.println("Resultset is empty.");
		}
		return prodMap;
	}
	
	@Override
	public Product getProductById(Integer productId) {
		Product product = null;
		try{
			product = (Product) getJdbcTemplate().queryForObject(GET_PRODUCT_BY_ID, new Object[]{productId}, new SingleProductMapper());
		}
		catch(EmptyResultDataAccessException emptyResultEx){
			System.out.println("No Product found with Id : "+productId);
		}
		return product;
	}
	
	private class CategoryMapper implements RowMapper<CategoryList>{

		@Override
		public CategoryList mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			// TODO Auto-generated method stub
			CategoryList catList = null;
			if(null != rs){
				catList = new CategoryList();
				do{
					Category cat = new Category();
					cat.setId(rs.getInt("category_id"));
					cat.setName(rs.getString("category_name"));
					catList.addToCategories(cat);
				}while(rs.next());
			}
			return catList;
		}		
	}
	
	private class ProductMapper implements RowMapper<ProductList>{

		@Override
		public ProductList mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			// TODO Auto-generated method stub
			ProductList prodList = null;
			if(null != rs){
				prodList = new ProductList();
				do{
					// Product
					Product prod = new Product();
					prod.setId(rs.getInt("prod_id"));
					prod.setName(rs.getString("prod_name"));
					prod.setInStockQty(rs.getInt("prod_in_stock_qty"));
					prod.setUnitPrice(rs.getDouble("prod_amt"));
					
					// Category
					Category cat = new Category();
					cat.setId(rs.getInt("category_id"));
					cat.setName(rs.getString("category_name"));
					
					prod.setCategory(cat);
					
					prodList.addToProductList(prod);
				}while(rs.next());
			}
			return prodList;
		}		
	}
	
	private class NewProductMapper implements RowMapper<ProductMap>{

		@Override
		public ProductMap mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			// TODO Auto-generated method stub
			ProductMap prodMap = null;
			if(null != rs){
				prodMap = new ProductMap();
				do{
					// Product
					Product prod = new Product();
					prod.setId(rs.getInt("prod_id"));
					prod.setName(rs.getString("prod_name"));
					prod.setInStockQty(rs.getInt("prod_in_stock_qty"));
					prod.setUnitPrice(rs.getDouble("prod_amt"));
					
					// Category
					Category cat = new Category();
					cat.setId(rs.getInt("category_id"));
					cat.setName(rs.getString("category_name"));
					
					prod.setCategory(cat);
					
					prodMap.addToProductMap(Integer.toString(prod.getId()),prod);
				}while(rs.next());
			}
			return prodMap;
		}		
	}
	
	private class SingleProductMapper implements RowMapper<Product>{

		@Override
		public Product mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			// TODO Auto-generated method stub
			Product product = null;
			if(null != rs){
				System.out.println("Number of products fetched : "+rs.getFetchSize());
				// Product
				product = new Product();
				product.setId(rs.getInt("prod_id"));
				product.setName(rs.getString("prod_name"));
				product.setInStockQty(rs.getInt("prod_in_stock_qty"));
				product.setUnitPrice(rs.getDouble("prod_amt"));
				
				// Category
				Category cat = new Category();
				cat.setId(rs.getInt("category_id"));
				cat.setName(rs.getString("category_name"));
				
				product.setCategory(cat);
			}
			return product;
		}		
	}
}
