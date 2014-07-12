package com.codepath.yardsale.dao.parse;

import java.util.ArrayList;

import com.codepath.yardsale.model.Category;
import com.codepath.yardsale.model.GeoLocation;
import com.codepath.yardsale.model.Post;
import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

@ParseClassName("Post")
public class ParsePost extends ParseObject {
	public ParsePost(){
		super();
	}
	
	public ParsePost(Post post){
		super();
		setObjectId(post.getId());
		setUserId(post.getUserId());
		setTitle(post.getTitle());
		setCategory(post.getCategory().name());
		setContact(new ParseContact(post.getContact()));
		setDescription(post.getDescription());
		GeoLocation loc = post.getLocation();
		if (loc != null){
			ParseGeoPoint pt = new ParseGeoPoint(loc.getLatitude(), loc.getLongitude());
			setLocation(pt);
		}
		setPrice(post.getPrice());
		setStatus(post.getStatus());
		setImageList(post.getImageList());
	}
	
	public void setImageList(ArrayList<String> imageList) {
		if (imageList != null)
			put ("ImageList",imageList);
		
	}

	public Post toPost(){
		Post post = new Post();
		post.setCategory(Category.valueOf(getCategory()));
		post.setContact(getContact().toContact());
		post.setCreatedAt(this.getCreatedAt().getTime());
		post.setDescription(getDescription());
		post.setId(getObjectId());
		ParseGeoPoint geoPt = getLocation();
		if (geoPt != null){
			GeoLocation location = new GeoLocation();
			location.setLatitude(geoPt.getLatitude());
			location.setLongitude(geoPt.getLongitude());
		}
		post.setPrice(getPrice());
		post.setStatus(getStatus());
		post.setTitle(getTitle());
		post.setUserId(getUserId());
		return post;
	}
	
	public String getUserId() {
		return getString("userId");
	}

	public void setUserId(String userId) {
		put("userId", userId);
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

	public ParseContact getContact() {
		return (ParseContact)getParseObject("contact");
	}

	public void setContact(ParseContact contact) {
		put("contact", contact);
	}

	public String getCategory() {
		return getString("category");
	}

	public void setCategory(String category) {
		put("category", category);
	}

	public Double getPrice() {
		return getDouble("price");
	}

	public void setPrice(Double price) {
		put("price", price);
	}

	public ParseGeoPoint getLocation() {
		return getParseGeoPoint("location");
	}

	public void setLocation(ParseGeoPoint location) {
		if (location != null)
			put("location", location);
	}

	public String getStatus() {
		return getString("status");
	}

	public void setStatus(String status) {
		put("status", status);
	}

}
