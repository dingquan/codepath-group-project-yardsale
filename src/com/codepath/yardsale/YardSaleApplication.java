package com.codepath.yardsale;

import android.app.Application;

import com.codepath.yardsale.dao.parse.ParseContact;
import com.codepath.yardsale.dao.parse.ParseImages;
import com.codepath.yardsale.dao.parse.ParsePost;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;
import com.parse.ParseObject;

public class YardSaleApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		.cacheInMemory().cacheOnDisc().build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(
				defaultOptions).build();

				ImageLoader.getInstance().init(config);

		
		ParseObject.registerSubclass(ParsePost.class);
		ParseObject.registerSubclass(ParseContact.class);
		ParseObject.registerSubclass(ParseImages.class);
		
		// Add your initialization code here
		Parse.initialize(this, "1dpxnDZNHPqwFLfdxBDnY2cvnmXadFi36e1HpjqP", "ZFxHYJjvQqvdf5EaQTcclrX2LUreooCN4I4aB314");
	}
}
