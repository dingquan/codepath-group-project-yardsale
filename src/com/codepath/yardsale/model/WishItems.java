package com.codepath.yardsale.model;

import com.codepath.yardsale.dao.parse.WishList;

public class WishItems {
	String id;
    String userId;
    String item;
    
    public String getId() {
		return id;
	}
    
    public String setId(String id) {
		return this.id = id;
	}
	
    public void setObjectId(String id) {
		this.id = id;
	}

	boolean selected=false;
	
	public WishItems() {
		// TODO Auto-generated constructor stub
	}
    
    public WishItems(String id,String userId, String item){
    	this.id =id;
    	this.userId = userId;
    	this.item = item;
    }
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public boolean isSelected() {
		  return selected;
	}
    public void setSelected(boolean selected) {
		  this.selected = selected;
	}
    
    
    
    
}
