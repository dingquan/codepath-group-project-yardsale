package com.codepath.yardsale;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;

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
	String fprice;
	MenuItem miSMS;

	@SuppressWarnings("deprecation")
	Gallery gallery;
	
	Post post;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_post);
		fetchPostData();
		
		setupViews();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		populateData();
	}
	
	/**
	 * The activity is launched either from the search result view or notification
	 * @return
	 */
	private void fetchPostData() {
		// check to see if it's launched from SearchAndManageActivity list view
		String postJson = getIntent().getStringExtra("post");
		if (postJson != null){
			post = (Post) JsonUtil.fromJson(postJson, Post.class);
			return;
		}
		String notificationData = getIntent().getStringExtra("com.parse.Data");
		if (notificationData != null){
			try {
				JSONObject json = new JSONObject(notificationData);
				post = (Post) JsonUtil.fromJson(json.getString("post"), Post.class);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_view, menu);
		miSMS = menu.findItem(R.id.action_sms);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        overridePendingTransition(R.anim.slideinleft, R.anim.slideoutright);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
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
			gallery.setVisibility(View.VISIBLE);
		}
		else{
			gallery.setVisibility(View.GONE);
		}
		
		phone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
        		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + post.getContact().getPhone()));
        		startActivity(intent);
            }
        });
		
	}
	
	//Share post
	public void onSMS(MenuItem mi)
	{
		String message = " Description: "+ post.getDescription()+" Price: $"+fprice + " Location: "+post.getContact().getAddress()+ " Phone:"+ post.getContact().getPhone();
		String subject = "Trader Post: " +  post.getTitle();
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("text/plain");
		share.putExtra(Intent.EXTRA_SUBJECT, subject);
		share.putExtra(Intent.EXTRA_TEXT, message);
		startActivity(Intent.createChooser(share, "Share Post"));
	}
	
	//Send SMS
	public void onSMS_notused(MenuItem mi){
		
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
 		overridePendingTransition(R.anim.slideinleft, R.anim.slideoutright);
 	};
}
