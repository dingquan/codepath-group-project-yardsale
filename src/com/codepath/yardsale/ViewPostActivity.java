package com.codepath.yardsale;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ViewPostActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_post);
		
	}
	public void onReply(View view) {
        Toast.makeText(this, "Reply Clicked", Toast.LENGTH_SHORT).show();
        finish();
    }
	public void onCancel(View view) {
        Toast.makeText(this, "Cancel Clicked", Toast.LENGTH_SHORT).show();
        finish();
    }
}
