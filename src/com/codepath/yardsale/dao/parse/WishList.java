package com.codepath.yardsale.dao.parse;

import java.util.ArrayList;
import java.util.List;

import com.codepath.yardsale.model.WishItems;
import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("WishList")

public class WishList extends ParseObject {
	public WishList(){
		super();
	}
	
	public WishList(String id, String item){
		super();
		setUserId(id);
		setItem(item);
	}
	
	public WishList(WishItems item){
		super();
		setObjectId(item.getId());
		setUserId(item.getUserId());
		setItem(item.getItem());
	}
	
	public WishItems toWishItems(){
		System.out.println("WishList getObjectId-->" + super.getObjectId());
		WishItems wishItem = new WishItems(super.getObjectId(), getUserId(), getItem());
		System.out.println("WishList toWishItems wishitem id-->" + wishItem.getId());
		return wishItem;
	}
	
	public void setUserId(String userId) {
		put("userId", userId);
	}
	
	public String getUserId(){
		return getString("userId");
	}

	public void setItem(String item) {
		put ("Item",item);
	}
	
	public String getItem(){
		return getString("Item");
	}
}
