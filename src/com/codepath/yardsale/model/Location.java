package com.codepath.yardsale.model;

public class Location {

	private Float longitude;
	private Float latitude;
	private String address; // for simplicity, just a flat address instead of
							// geography node tree

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
