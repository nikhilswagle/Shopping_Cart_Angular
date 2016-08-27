package com.jlearning.shopping.model;

public class Product {

	private int id;
	private String name;
	private Category category;
	private double unitPrice;
	private int inStockQty;
	private int qtyOrdered;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getInStockQty() {
		return inStockQty;
	}
	public void setInStockQty(int inStockQty) {
		this.inStockQty = inStockQty;
	}
	public int getQtyOrdered() {
		return qtyOrdered;
	}
	public void setQtyOrdered(int qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}
}
