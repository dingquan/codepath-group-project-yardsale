package com.codepath.yardsale;

import android.app.Application;

import com.activeandroid.util.Log;
import com.codepath.yardsale.dao.parse.ParseContact;
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
		ParseObject.registerSubclass(ParseImages.class);
		
		// Add your initialization code here
		Parse.initialize(this, "1dpxnDZNHPqwFLfdxBDnY2cvnmXadFi36e1HpjqP", "ZFxHYJjvQqvdf5EaQTcclrX2LUreooCN4I4aB314");
	}
}
