package com.codepath.yardsale.model;

public class SearchCriteria {
	private Category category;
	private Integer minPrice;
	private Integer maxPrice;
	private GeoLocation location;
	private String keyword;
	private String userId;
	private String nearCity;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	public Integer getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}
	
	public GeoLocation getLocation() {
		return location;
	}

	public void setLocation(GeoLocation location) {
		this.location = location;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNearCity() {
		return nearCity;
	}

	public void setNearCity(String nearCity) {
		this.nearCity = nearCity;
	}
}
