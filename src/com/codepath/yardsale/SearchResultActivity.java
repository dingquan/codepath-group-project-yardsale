package com.codepath.yardsale;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
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

public class SearchResultActivity extends Activity implements LocationListener {
	private static final int REQUEST_CODE_CREATE_POST = 1;
	private static final int REQUEST_CODE_SEARCH_CRITERIA = 2;

	private List<Post> posts;
	private ArrayAdapter<Post> aPosts;
	private ListView lvPosts;

	private PostDao postDao;

	private String query;
	private LocationManager locationManager;
	private String provider;

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
		checkIfLocationServiceEnabled();
		initLocationManager();
	}

	private void initLocationManager() {
		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Define the criteria how to select the locatioin provider -> use
		// default
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);
	}

	private void setupHandlers() {

		// lvPosts.setOnScrollListener(new EndlessScrollListener() {
		//
		// @Override
		// public void onLoadMore(int page, int totalItemsCount) {
		// // Triggered only when new data needs to be appended to the list
		// // Add whatever code is needed to append new items to your
		// // AdapterView
		// loadMorePosts();
		// }
		//
		// });

		lvPosts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(SearchResultActivity.this,
						ViewPostActivity.class);
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

	public void onSearch(MenuItem mi) {
		// FragmentManager fm = getFragmentManager();
		// SearchCriteriaDialog diag = SearchCriteriaDialog.newInstance();
		// diag.show(fm, "fragment_search_criteria");
		Intent i = new Intent(SearchResultActivity.this,
				SearchCriteriaActivity.class);
		startActivityForResult(i, REQUEST_CODE_SEARCH_CRITERIA);
	}

	public void onPost(MenuItem mi) {
		Intent i = new Intent(SearchResultActivity.this,
				CreatePostActivity.class);
		startActivityForResult(i, REQUEST_CODE_CREATE_POST);
	}

	public void onManage(MenuItem mi) {
		Intent i = new Intent(SearchResultActivity.this,
				ManagePostsActivity.class);
		startActivity(i);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_CODE_CREATE_POST) {
				Toast.makeText(this, "returned from create post",
						Toast.LENGTH_SHORT).show();
			} else if (requestCode == REQUEST_CODE_SEARCH_CRITERIA) {
				String searchStr = data.getExtras()
						.getString("search_criteria");
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

	private void checkIfLocationServiceEnabled() {
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		// check if enabled and if not send user to the GSP settings
		// Better solution would be to display a dialog and suggesting to
		// go to the settings
		// if (!enabled) {
		// Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		// startActivity(intent);
		// }
	}

	/* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d("DEBUG", "### location changed: " + location.toString());
	}

	@Override
	public void onProviderDisabled(String arg0) {
		Toast.makeText(this, "Disabled provider " + provider,
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String arg0) {
		Toast.makeText(this, "Enabled new provider " + provider,
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

	}
}
