package com.codepath.yardsale.model;

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
	
	private final String name;
	
	Category(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
}
