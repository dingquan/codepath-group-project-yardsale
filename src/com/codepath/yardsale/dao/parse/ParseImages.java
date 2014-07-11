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
	
	public ParseImages(ParseFile imageFile){
		super();
		setImageFile(imageFile);
		
	}
	
	public void setImageFile(ParseFile imagefile) {
		put ("ImageFile",imagefile);
		
	}
}
