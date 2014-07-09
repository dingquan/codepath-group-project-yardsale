package com.codepath.yardsale.model;

public class Contact {
	private String phone;
	private String address; // for simplicity, just a flat address

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
}
