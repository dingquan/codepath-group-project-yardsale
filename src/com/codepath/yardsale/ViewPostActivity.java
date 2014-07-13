package com.codepath.yardsale;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.util.JsonUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ViewPostActivity extends Activity {
	TextView title;
	TextView description;
	TextView location;
	TextView price;
	ImageView ivImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_post);
		
		setupViews();
		populateData();
	}
	
	private void setupViews() {
		title = (TextView) findViewById(R.id.tvAdsTitle);
		description = (TextView) findViewById(R.id.tvAdsDescription);
		location = (TextView) findViewById(R.id.tvAdsAddress);
		price = (TextView) findViewById(R.id.tvAdsPrice);
		ivImage = (ImageView) findViewById(R.id.ivAds);
	}
	
	private void populateData() {
		String postJson = getIntent().getStringExtra("post");
		Post post = (Post) JsonUtil.fromJson(postJson, Post.class);
//		Toast.makeText(this, "Read post: " + post.getTitle(), Toast.LENGTH_SHORT).show();
		
		title.setText(post.getTitle());
		description.setText(post.getDescription());
		location.setText(post.getContact().getAddress());
		price.setText("$"+post.getPrice().toString());
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		List<String> postUrl = post.getImageUrl();
		if(postUrl != null && postUrl.size()>0){
			String url =postUrl.get(0);
			imageLoader.displayImage(url, ivImage);			
		}else{
			String ctgry = post.getCategory().name();
			System.out.println("PostArrayAdapter-->>"+ctgry);
			if(ctgry.equals("TOYS_GAMES")){
				ivImage.setImageResource(R.drawable.ic_toys);
			}else if(ctgry.equals("FURNITURE")){
				ivImage.setImageResource(R.drawable.ic_furniture);
			}else if(ctgry.equals("ELECTRONICS")){
				ivImage.setImageResource(R.drawable.ic_electronics);
			}else if(ctgry.equals("CLOTHING_ACCESSRIES")){
				ivImage.setImageResource(R.drawable.ic_clothing);
			}else if(ctgry.equals("BOOKS_MAGAZINES")){
				ivImage.setImageResource(R.drawable.ic_books);
			}else if(ctgry.equals("COMPUTERS")){
				ivImage.setImageResource(R.drawable.ic_computers);
			}else if(ctgry.equals("APPLIANCES")){
				ivImage.setImageResource(R.drawable.ic_appliances);
			}else if(ctgry.equals("CARS")){
				ivImage.setImageResource(R.drawable.ic_cars);
			}else{
				ivImage.setImageResource(R.drawable.ic_cells);
			}
		}
	}

}
