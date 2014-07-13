package com.codepath.yardsale.dao.parse;

import java.util.ArrayList;

import com.codepath.yardsale.model.Post;
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
		setImageFileName(name);
	}
	
	public void setImageFile(ParseFile imagefile) {
		put ("ImageFile",imagefile);
		
	}
	
	public ParseFile getImageFile(){
		return (ParseFile) get("ImageFile");
	}
	
	public void setImageFileName(String name){
		put ("ImageFileName",name);
	}
	
	public void getImageFileName(){
		getString("ImageFileName");
	}
}
