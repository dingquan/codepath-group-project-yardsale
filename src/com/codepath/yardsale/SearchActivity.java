package com.codepath.yardsale;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.yardsale.adapter.PostArrayAdapter;
import com.codepath.yardsale.dao.PostDao;
import com.codepath.yardsale.model.Post;

public class SearchActivity extends Activity {
	private List<Post> posts;
	private ArrayAdapter<Post> aPosts;
	private ListView lvPosts;

	private PostDao postDao;

	private String query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		postDao = new PostDao();
		
		posts = new ArrayList<Post>();
		aPosts = new PostArrayAdapter(this, posts);
		lvPosts = (ListView) findViewById(R.id.lvPosts);
		lvPosts.setAdapter(aPosts);

		setupHandlers();
		loadMorePosts();
	}

	private void setupHandlers() {

//		lvPosts.setOnScrollListener(new EndlessScrollListener() {
//
//			@Override
//			public void onLoadMore(int page, int totalItemsCount) {
//				// Triggered only when new data needs to be appended to the list
//				// Add whatever code is needed to append new items to your
//				// AdapterView
//				loadMorePosts();
//			}
//
//		});

	}

	private void loadMorePosts() {
		List<Post> posts = postDao.findPostsBySearchCriteria(null);
		aPosts.addAll(posts);
	}
}
