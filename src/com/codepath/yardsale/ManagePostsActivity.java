package com.codepath.yardsale;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.yardsale.adapter.AdArrayAdapter;
import com.codepath.yardsale.dao.PostDao;
import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.model.SearchCriteria;
import com.codepath.yardsale.util.JsonUtil;


public class ManagePostsActivity extends Activity {
	private static final int REQUEST_CODE_CREATE_ADS = 1;
	private static final int REQUEST_CODE_SEARCH_ADS = 2;
	private List<Post> posts;
	private ArrayAdapter<Post> aPosts;
	private ListView lvAds;
	private String userId;
	private SharedPreferences prefs;
	private PostDao postDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_posts);
		postDao = new PostDao();

		posts = new ArrayList<Post>();
		aPosts = new AdArrayAdapter(this, posts);
		lvAds = (ListView) findViewById(R.id.lvAds);
		lvAds.setAdapter(aPosts);

		lookupOwnUserId();
		setupHandlers();
		loadOwnPosts();
		setupListViewListener();

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
				startActivity(i);
			}
		});
	}
	
	private void setupListViewListener() {
		//Delete ad
		lvAds.setOnItemLongClickListener(new OnItemLongClickListener() {
		    public boolean onItemLongClick(AdapterView<?> parent, View view,int position,long rowId)
			{
		    	Toast.makeText(ManagePostsActivity.this, "delete", Toast.LENGTH_SHORT).show();
		    	postDao = new PostDao();	    	
		    	Post post = posts.get(position);
		        postDao.deletePost(post);
		        aPosts.remove(post);        
				return true;
			}
		}); 
	}

	/**
	 * load posts created by self
	 */
	private void loadOwnPosts() {
		SearchCriteria criteria = new SearchCriteria();
		criteria.setUserId(userId);
		//todo, populate criteria with userId
		List<Post> ads = postDao.findPostsBySearchCriteria(criteria);
		aPosts.addAll(ads);
	}
	
	public void OnRepost(View view) {
		//
		Toast.makeText(this, "Repost", Toast.LENGTH_SHORT).show();
//		finish();

    }
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_CODE_CREATE_ADS) {
				Toast.makeText(this, "returned from create ads",
						Toast.LENGTH_SHORT).show();
			} else if (requestCode == REQUEST_CODE_SEARCH_ADS) {
				String searchStr = data.getExtras()
						.getString("search_ads");
				SearchCriteria criteria = (SearchCriteria) JsonUtil.fromJson(
						searchStr, SearchCriteria.class);
				Toast.makeText(
						this,
						"returned from search criteria, "
								+ criteria.getKeyword(), Toast.LENGTH_SHORT)
						.show();
				List<Post> results = postDao
						.findPostsBySearchCriteria(criteria);
				aPosts.clear();
				aPosts.addAll(results);
			}
		}
	}
}
