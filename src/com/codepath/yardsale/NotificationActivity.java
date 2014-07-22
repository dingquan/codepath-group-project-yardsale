package com.codepath.yardsale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class NotificationActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		String receivedIntent = getIntent().getStringExtra("post");
		Intent i = new Intent(this,ViewPostActivity.class);
		i.putExtra("post", receivedIntent);		
		
		
		
	}
}
