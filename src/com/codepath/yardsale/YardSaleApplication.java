package com.codepath.yardsale;

import android.app.Application;

import com.codepath.yardsale.dao.parse.ParseContact;
import com.codepath.yardsale.dao.parse.ParseGeoLocation;
import com.codepath.yardsale.dao.parse.ParseImages;
import com.codepath.yardsale.dao.parse.ParsePost;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

public class YardSaleApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		
		ParseObject.registerSubclass(ParsePost.class);
		ParseObject.registerSubclass(ParseContact.class);
		ParseObject.registerSubclass(ParseGeoLocation.class);
		ParseObject.registerSubclass(ParseImages.class);
		
		// Add your initialization code here
		Parse.initialize(this, "1dpxnDZNHPqwFLfdxBDnY2cvnmXadFi36e1HpjqP", "ZFxHYJjvQqvdf5EaQTcclrX2LUreooCN4I4aB314");
	
		ParseGeoPoint point = new ParseGeoPoint(30, 100);
		ParseObject testObject = new ParseObject("TestObject");
		testObject.put("foo", "bar");
		testObject.put("location", point);
		testObject.saveInBackground();
		ParsePost post = new ParsePost();
		post.setLocation(point);
		try {
			post.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
