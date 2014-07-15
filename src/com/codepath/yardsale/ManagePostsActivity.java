package com.codepath.yardsale;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codepath.yardsale.adapter.AdArrayAdapter;
import com.codepath.yardsale.dao.PostDao;
import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.model.SearchCriteria;
import com.codepath.yardsale.util.JsonUtil;


public class ManagePostsActivity extends Activity {
	private static final int REQUEST_CODE_EDIT_POST = 1;
	private List<Post> posts;
	private ArrayAdapter<Post> aPosts;
	private ListView lvAds;
	private String userId;
	private SharedPreferences prefs;
	private PostDao postDao;
	private ProgressBar pbLoading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_posts);
		postDao = new PostDao();

		posts = new ArrayList<Post>();
		//aPosts = new AdArr;
		aPosts = new AdArrayAdapter(this, posts);
		lvAds = (ListView) findViewById(R.id.lvAds);
		lvAds.setAdapter(aPosts);
		pbLoading = (ProgressBar)findViewById(R.id.pbLoading);

		lookupOwnUserId();
		setupHandlers();
		loadOwnPosts();
	}
	
	private void lookupOwnUserId(){
		prefs = getSharedPreferences("com.codepath.yardsale", Context.MODE_PRIVATE);
		userId = prefs.getString("userId", "");
		if (userId.isEmpty()){
			userId = UUID.randomUUID().toString();
			prefs.edit().putString("userId", userId).commit();
		}
	}
	
	private void setupHandlers() {

		lvAds.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(ManagePostsActivity.this,
						CreatePostActivity.class);
				i.putExtra("post", JsonUtil.toJson(posts.get(position)));
				i.putExtra("position", position);
				startActivityForResult(i, REQUEST_CODE_EDIT_POST);
			}
		});
	}
	
	/**
	 * load posts created by self
	 */
	private void loadOwnPosts() {
		SearchCriteria criteria = new SearchCriteria();
		criteria.setUserId(userId);
		
        pbLoading.setVisibility(ProgressBar.VISIBLE);
        new SearchPostTask().execute(criteria); 
        
	}
	
	public void OnDelete(View view) {
		
		Toast.makeText(this, "Repost", Toast.LENGTH_SHORT).show();
		int position = lvAds.getPositionForView((View) view.getParent());
		Post post = posts.get(position);
        postDao.deletePost(post);
        aPosts.remove(post);        

    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == this.REQUEST_CODE_EDIT_POST){
			if (resultCode == RESULT_OK){
				if (data == null){
					return;
				}
				String postStr = data.getExtras().getString("post");
				int position = data.getExtras().getInt("position");
				if (postStr == null)
					return;
				Post post = (Post) JsonUtil.fromJson(postStr, Post.class);
				posts.set(position, post);
				aPosts.notifyDataSetChanged();
			}
		}
	}

	private class SearchPostTask extends AsyncTask<SearchCriteria, Void, List<Post>> {

		@Override
		protected void onPostExecute(List<Post> posts) {
			aPosts.addAll(posts);
	        pbLoading.setVisibility(ProgressBar.INVISIBLE);
		}

		@Override
		protected List<Post> doInBackground(SearchCriteria... criterias) {
	    	 PostDao postDao = new PostDao();
	         List<Post> posts = postDao.findPostsBySearchCriteria(criterias[0]);
	         return posts;
		}
	}
}
