package com.codepath.yardsale.dao.parse;

import com.codepath.yardsale.model.Contact;
import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Contact")
public class ParseContact extends ParseObject {
	public ParseContact(){
		super();
	}
	
	public ParseContact(Contact contact){
		super();
		setPhone(contact.getPhone());
		setAddress(contact.getAddress());
		setCity(contact.getCity());
		setState(contact.getState());
	}

	public Contact toContact() {
		Contact contact = new Contact();
		contact.setAddress(getAddress());
		contact.setPhone(getPhone());
		contact.setState(getState());
		contact.setCity(getCity());
		return contact;
	}
	
	public String getPhone() {
		return getString("phone");
	}

	public void setPhone(String phone) {
		put("phone", phone);
	}

	public String getAddress() {
		return getString("address");
	}

	public void setAddress(String address) {
		put("address", address);
	}
	
	public String getCity(){
		return getString("city");
	}
	
	public void setCity(String city){
		if (city != null)
			put("city", city);
	}
	
	public String getState(){
		return getString("state");
	}
	
	public void setState(String state){
		if (state != null)
			put("state", state);
	}
}
