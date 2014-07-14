package com.codepath.yardsale.dao.parse;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("PostImages")
public class ParseImages extends ParseObject {
	public ParseImages(){
		super();
	}
	
	public ParseImages(ParseFile imageFile, String name){
		super();
		setImageFile(imageFile);
		setName(name);
	}
	
	public void setImageFile(ParseFile imagefile) {
		put ("imageFile",imagefile);
		
	}
	
	public ParseFile getImageFile(){
		return (ParseFile) get("imageFile");
	}
	
	public void setName(String name){
		put ("name",name);
	}
	
	public void getName(){
		getString("name");
	}
}
