package com.codepath.yardsale.model;

public class Contact {
	private String phone;
	private String address; // for simplicity, just a flat address
	private String city;
	private String state;
	
	public Contact(){
		
	}
	
	public Contact(String phone, String address) {
		this.phone = phone;
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
