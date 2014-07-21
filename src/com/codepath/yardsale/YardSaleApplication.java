package com.codepath.yardsale;

import android.app.Application;
import android.util.Log;

import com.codepath.yardsale.dao.parse.ParseContact;
import com.codepath.yardsale.dao.parse.ParseImages;
import com.codepath.yardsale.dao.parse.ParsePost;
import com.codepath.yardsale.dao.parse.WishList;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.PushService;
import com.parse.SaveCallback;

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
	    Parse.initialize(this, "1dpxnDZNHPqwFLfdxBDnY2cvnmXadFi36e1HpjqP", "ZFxHYJjvQqvdf5EaQTcclrX2LUreooCN4I4aB314");
		
		ParseObject.registerSubclass(ParsePost.class);
		ParseObject.registerSubclass(ParseContact.class);
		ParseObject.registerSubclass(ParseImages.class);
		ParseObject.registerSubclass(WishList.class);

		
		// Add your initialization code here
		
		PushService.setDefaultPushCallback(this, SearchAndManageActivity.class);
		//ParseInstallation.getCurrentInstallation().saveInBackground();
		if(ParseInstallation.getCurrentInstallation()==null){
		ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
		    @Override
		    public void done(ParseException e) {
		        if (e == null)
		            Log.d("yardSaleapplication", "ParsePush: save installation ok");
		        else
		            Log.e("YardSaleApplication", "ParsePush: save installation failed - " + e.getMessage());
		    }
		});
		}
	}
}
