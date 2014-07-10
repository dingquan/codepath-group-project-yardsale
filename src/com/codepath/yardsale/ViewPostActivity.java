package com.codepath.yardsale;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.util.JsonUtil;

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
		
		populateData();
	}
	private void populateData() {
		String postJson = getIntent().getStringExtra("post");
		Post post = (Post) JsonUtil.fromJson(postJson, Post.class);
		Toast.makeText(this, "Read post: " + post.getTitle(),
				Toast.LENGTH_SHORT).show();
		
		title = (TextView) findViewById(R.id.tvAdsTitle);
		title.setText(post.getTitle());
		description = (TextView) findViewById(R.id.tvAdsDescription);
		String descr = post.getDescription();
		if (descr.length() > 80){
			descr = descr.substring(0, 80) + "...";
		}
		description.setText(descr);
		location = (TextView) findViewById(R.id.tvAdsAddress);
		location.setText(post.getContact().getAddress());
		price = (TextView) findViewById(R.id.tvAdsPrice);
		price.setText("$"+post.getPrice().toString());
		//ivImage = (ImageView) findViewById(R.id.ivAds);
		//ivImage.setImageURI(post.get);
	}

}
