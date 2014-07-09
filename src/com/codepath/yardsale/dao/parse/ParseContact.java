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
	}

	public Contact toContact() {
		Contact contact = new Contact();
		contact.setAddress(getAddress());
		contact.setPhone(getPhone());
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
}
