package com.codepath.yardsale.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class for a single Post item for sale
 * 
 * @author quding
 * 
 */
public class Post {
	private String id; // unique id of the post
	private String userId; // id of owner of the post. for simplicity, use the
							// android device id to avoid
							// a full blown User class
	private String title;
	private String description;
	private Contact contact;
	private Category category;
	private Double price;
	private GeoLocation location;
	private Long createdAt; // time of the posting in milliseconds
	private String status;
	private ArrayList<String> imageList;

	public ArrayList<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<Object> imageList) {
		if(imageList != null){
		List<String> images = new ArrayList<String>();
		for(int i=0; i<imageList.size();i++){
			String obj = (String) imageList.get(i);
			images.add(obj);
		}
		this.imageList = (ArrayList<String>) images;
		}
	}
	
	public void setImageList(ArrayList<String> imageList) {
		if(imageList != null){
		this.imageList = imageList;
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public GeoLocation getLocation() {
		return location;
	}

	public void setLocation(GeoLocation location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
