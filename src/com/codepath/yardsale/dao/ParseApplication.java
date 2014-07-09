package com.codepath.yardsale.dao;

import android.app.Application;

import com.codepath.yardsale.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
		ParseObject.registerSubclass(Post.class);
		Parse.initialize(this, "56QaZaPm0iWuVDJQ2VIX5vPlqH9XyyjLfPvK2bGS",
				"vPzWNtbngfJ1Jb2BDUA4orsB7ggwUAkBXQxkNPiI");

		// ...
	}
}
