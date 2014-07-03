package com.codepath.yardsale.model;

import java.util.HashMap;
import java.util.Map;

public enum Category {
	TOYS_GAMES("Toys & games"), 
	FURNITURE("Furniture"), 
	ELECTRONICS("Electronics"), 
	CLOTHING_ACCESSRIES("CLothing & Accessories"),
	CELL_PHONES("Cell Phones"),
	BOOKS_MAGAZINES("Books & Magazines"),
	APPLIANCES("Appliances"),
	CARS("Cars & Trucks"),
	COMPUTERS("Computers");
	
	private static final Map<String, Category> categories = new HashMap<String, Category>();
	static{
		for (Category c : Category.values()){
			categories.put(c.name, c);
		}
	}
	
	private final String name;
	
	Category(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
	
	/**
	 * find the Category enum matching the given name
	 * @param name
	 * @return
	 */
	public Category fromName(String name){
		return categories.get(name);
	}
}
