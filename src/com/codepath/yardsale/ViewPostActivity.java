package com.codepath.yardsale;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.util.JsonUtil;

public class ViewPostActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ads_view);

		populateData();
	}
	private void populateData() {
		String postJson = getIntent().getStringExtra("post");
		Post post = (Post) JsonUtil.fromJson(postJson, Post.class);
		Toast.makeText(this, "Read post: " + post.getTitle(),
				Toast.LENGTH_SHORT).show();

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
