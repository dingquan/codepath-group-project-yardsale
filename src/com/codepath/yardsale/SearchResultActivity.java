package com.codepath.yardsale;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.yardsale.adapter.PostArrayAdapter;
import com.codepath.yardsale.dao.PostDao;
import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.model.SearchCriteria;
import com.codepath.yardsale.util.JsonUtil;

public class SearchResultActivity extends Activity {
	private static final int REQUEST_CODE_CREATE_POST = 1;
	private static final int REQUEST_CODE_SEARCH_CRITERIA = 2;
	
	private List<Post> posts;
	private ArrayAdapter<Post> aPosts;
	private ListView lvPosts;

	private PostDao postDao;

	private String query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);

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
		
		lvPosts.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent i = new Intent(SearchResultActivity.this, ViewPostActivity.class);
				i.putExtra("post", JsonUtil.toJson(posts.get(position)));
				i.putExtra("position", position);
				startActivity(i);
			}
			
		});

	}

	private void loadMorePosts() {
		List<Post> posts = postDao.findPostsBySearchCriteria(null);
		aPosts.addAll(posts);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }
	
	public void onSearch(MenuItem mi){
//		FragmentManager fm = getFragmentManager();
//		SearchCriteriaDialog diag = SearchCriteriaDialog.newInstance();
//		diag.show(fm, "fragment_search_criteria");
		Intent i = new Intent(SearchResultActivity.this, SearchCriteriaActivity.class);
		startActivityForResult(i, REQUEST_CODE_SEARCH_CRITERIA);
	}
	
	public void onPost(MenuItem mi){
		Intent i = new Intent(SearchResultActivity.this, CreatePostActivity.class);
		startActivityForResult(i, REQUEST_CODE_CREATE_POST);
	}
	
	public void onManage(MenuItem mi){
		Intent i = new Intent(SearchResultActivity.this, ManagePostsActivity.class);
		startActivity(i);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK){
			if (requestCode == REQUEST_CODE_CREATE_POST) {
				Toast.makeText(this, "returned from create post", Toast.LENGTH_SHORT).show();
			}
			else if (requestCode == REQUEST_CODE_SEARCH_CRITERIA){
				String searchStr = data.getExtras().getString("search_criteria");
				SearchCriteria criteria = (SearchCriteria) JsonUtil.fromJson(searchStr, SearchCriteria.class);
				Toast.makeText(this, "returned from search criteria, " + criteria.getKeyword(), Toast.LENGTH_SHORT).show();
				List<Post> results = postDao.findPostsBySearchCriteria(criteria);
				aPosts.clear();
				aPosts.addAll(results);
			}
		}
	}
}
