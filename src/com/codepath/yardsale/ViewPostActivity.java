package com.codepath.yardsale;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.yardsale.adapter.ImageArrayAdapter;
import com.codepath.yardsale.dao.PostDao;
import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.util.JsonUtil;

public class ViewPostActivity extends Activity {
	TextView title;
	TextView description;
	TextView location;
	TextView price;
	TextView phone;
	TextView category;
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_delete, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void onDelete(MenuItem mi) {
		//Delete
		Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
        postDao.deletePost(post);
        Intent i = new Intent(this,
        		ManagePostsActivity.class);
		startActivityForResult(i, REQUEST_CODE_POST_AD);
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
