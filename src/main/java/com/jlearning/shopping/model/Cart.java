package com.jlearning.shopping.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {

	private Map<String, Product> items;

	public Cart(){
		items = new HashMap<String, Product>();
	}
	
	public Map<String, Product> getItems() {
		return items;
	}

	public void setItems(Map<String, Product> items) {
		this.items = items;
	}
	
	/*public void addToCart(ProductMap prodMap, Selection selection){
		Set<String> keys = selection.getItemsSelected().keySet();
		for(String key : keys){
			if(items.containsKey(key)){
				int qtyOrdered = items.get(key).getQtyOrdered() + selection.getItemsSelected().get(key);
				items.get(key).setQtyOrdered(qtyOrdered);
			}
			else{
				if(0 < selection.getItemsSelected().get(key)){
					items.put(key, prodMap.getProductMap().get(key));
					items.get(key).setQtyOrdered(selection.getItemsSelected().get(key));
				}
			}
		}
	}*/
	
	public void addToCart(Product product){
		
		String key = Integer.valueOf(product.getId()).toString();
		if(items.containsKey(key)){
			int qtyOrdered = items.get(key).getQtyOrdered() + product.getQtyOrdered();
			items.get(key).setQtyOrdered(qtyOrdered);
		}
		else{
			items.put(key, product);
		}
	}
	
	/*public void updateCart(Selection selection){
		Set<String> keys = selection.getItemsSelected().keySet();
		for(String key : keys){
			if(0 == selection.getItemsSelected().get(key)){
				items.remove(key);
			}
			else{
				items.get(key).setQtyOrdered(selection.getItemsSelected().get(key));
			}
		}
	}*/
	
	public void updateCart(Product product){
		String key = Integer.valueOf(product.getId()).toString();
		if(0 == product.getQtyOrdered()){
			items.remove(key);
		}
		else{
			items.get(key).setQtyOrdered(product.getQtyOrdered());
		}
	}
}
