package com.codepath.yardsale.model;

import java.util.Date;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Model class for a single Post item for sale
 * 
 * @author quding
 * 
 */
@ParseClassName("Ads")
public class Post extends ParseObject {
	// private String uid; //unique id of the post
	// private String userId; // id of owner of the post. for simplicity, use
	// the android device id to avoid
	// // a full blown User class
	// private String title;
	// private String description;
	// private Contact contact;
	// private Category category;
	// private Float price;
	// private GeoLocation location;
	// private Long createdAt; //time of the posting in milliseconds
	// private String status;
	//
	//
	//
	// public String getUserId() {
	// return userId;
	// }
	//
	// public void setUserId(String userId) {
	// this.userId = userId;
	// }

	public Post() {
		super();
	}

	public String getTitle() {
		return getString("title");
	}

	public void setTitle(String title) {
		put("title", title);
	}

	public String getDescription() {
		return getString("description");
	}

	public void setDescription(String description) {
		put("description", description);
	}

	public Contact getContact() {
		String phone = getString("phone");
		String location = getString("location");
		Contact contact = new Contact(phone, location);
		return contact;
	}

	public void setContact(Contact contact) {
		String phone = contact.getPhone();
		String location = contact.getAddress();
		put("phone", phone);
		put("location", location);
	}

	public Category getCategory() {
		return (Category) get("category");
	}

	public void setCategory(Category category) {
		put("category", category.toString());
	}

	public Float getPrice() {
		return (Float) getNumber("price");
	}

	public void setPrice(Float price) {
		put("price", price);
	}

	// public GeoLocation getLocation() {
	// return location;
	// }
	//
	// public void setLocation(GeoLocation location) {
	// this.location = location;
	// }
	//
	public String getStatus() {
		return getString("status");
	}

	public void setStatus(String status) {
		put("status", status);
	}
	public Date getCreatedAt() {
		return (Date) get("createdAt");
	}

	public void setCreatedAt(Long createdAt) {
		put("createdAt", createdAt);
	}

	// public String getUid() {
	// return uid;
	// }
	//
	// public void setUid(String uid) {
	// this.uid = uid;
	// }
}
