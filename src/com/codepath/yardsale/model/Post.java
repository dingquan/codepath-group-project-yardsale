package com.codepath.yardsale.model;

/**
 * Model class for a single Post item for sale
 * 
 * @author quding
 * 
 */
public class Post {
	private String uid;  //unique id of the post
	private String userId; // id of owner of the post. for simplicity, use the android device id to avoid
							// a full blown User class
	private String title;
	private String description;
	private String contact; // for simplicity, a flat string instead of a user
							// object that holds phone numbers and other info
	private Category category;
	private Float price;
	private Location location;
	private Long createdAt; //time of the posting in milliseconds

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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
