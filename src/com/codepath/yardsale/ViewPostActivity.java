package com.codepath.yardsale;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Gallery;
import android.widget.TextView;

import com.codepath.yardsale.adapter.ImageArrayAdapter;
import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.util.JsonUtil;

public class ViewPostActivity extends Activity {
	TextView title;
	TextView description;
	TextView location;
	TextView price;
	TextView phone;
	TextView category;
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
	
	private void populateData() {
		
//		Toast.makeText(this, "Read post: " + post.getTitle(), Toast.LENGTH_SHORT).show();
		
		title.setText(post.getTitle());
		description.setText(post.getDescription());
		location.setText(post.getContact().getAddress());
		price.setText("$"+post.getPrice().toString());
		phone.setText(post.getContact().getPhone());
		category.setText(post.getCategory().toString());
	}

}
