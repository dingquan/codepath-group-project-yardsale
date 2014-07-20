package com.codepath.yardsale;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.yardsale.adapter.ImageArrayAdapter;
import com.codepath.yardsale.dao.PostDao;
import com.codepath.yardsale.fragment.ManagePostsFragment;
import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.util.JsonUtil;

public class ViewPostActivity extends Activity {
	TextView title;
	TextView description;
	TextView location;
	TextView price;
	TextView phone;
	TextView category;
	String fprice;
	private PostDao postDao = PostDao.getInstance();
	private final int REQUEST_CODE_POST_AD =1;
	@SuppressWarnings("deprecation")
	Gallery gallery;
	
	Post post;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_post);
		String postJson = getIntent().getStringExtra("post");
		post = (Post) JsonUtil.fromJson(postJson, Post.class);
		
		setupViews();
		populateData();
	}
	
	private void setupViews() {
		title = (TextView) findViewById(R.id.tvAdsTitle);
		description = (TextView) findViewById(R.id.tvAdsDescription);
		location = (TextView) findViewById(R.id.tvAdsAddress);
		price = (TextView) findViewById(R.id.tvAdsPrice);
		phone = (TextView) findViewById(R.id.tvAdsPhone);
		category = (TextView) findViewById(R.id.tvAdsCategory);
		gallery = (Gallery) findViewById(R.id.gallery);
		
		List<String> imageUrls = post.getImageUrls();
		if (imageUrls != null && !imageUrls.isEmpty()){
			gallery.setAdapter(new ImageArrayAdapter(this,post.getImageUrls()));
		}
		else{
			gallery.setVisibility(View.GONE);
		}
		
	}
	//Send SMS
	public void onSMS(View v){
		
		String phoneNumber="14082035769";
		String message ="Title: "+ post.getTitle() +" Price: $"+fprice + " Location: "+post.getContact().getAddress();
		SmsManager sm = SmsManager.getDefault();
		sm.sendTextMessage(phoneNumber, null, message, null, null);
		Toast.makeText(this, "SMS is sent", Toast.LENGTH_SHORT).show();
	}
	private void populateData() {
		
		title.setText(post.getTitle());
		description.setText(post.getDescription());
		location.setText(post.getContact().getAddress());
		fprice= String.format("%.2f", post.getPrice());
		price.setText("$"+fprice);
		phone.setText(post.getContact().getPhone());
		category.setText(post.getCategory().toString());
	}

	public void onBackPressed() {
 		finish();
 		overridePendingTransition(R.anim.slideinleft, R.anim.slideoutleft);
 	};
}
