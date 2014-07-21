package com.codepath.yardsale;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.astuetz.PagerSlidingTabStrip;
import com.codepath.yardsale.fragment.ManagePostsFragment;
import com.codepath.yardsale.fragment.SearchResultFragment;
import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.model.SearchCriteria;
import com.codepath.yardsale.util.JsonUtil;
import com.parse.ParseAnalytics;

public class SearchAndManageActivity extends FragmentActivity {
	private static final int REQUEST_CODE_CREATE_POST = 1;
	private static final int REQUEST_CODE_SEARCH_CRITERIA = 2;
	private static final int REQUEST_CODE_ADD_WISH = 3;

	FragmentPagerAdapter adapterViewPager;
	SearchResultFragment searchResultFragment;
	ManagePostsFragment managePostsFragment;
	MenuItem miRefresh;
	MenuItem miCreate;
	MenuItem miSearch;
	
	ViewPager vpPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_and_manage);
		
		vpPager = (ViewPager) findViewById(R.id.vpPager);
		adapterViewPager = new PagerAdapter(getSupportFragmentManager());
		vpPager.setAdapter(adapterViewPager);
		
		// Bind the tabs to the ViewPager
		PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		tabs.setShouldExpand(true);
		tabs.setViewPager(vpPager);
		tabs.setIndicatorHeight(7);
		tabs.setIndicatorColor(0xFF000099);
		tabs.setDividerColor(0x00ffffff);
		
		tabs.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				if (position == 0){
					if (miCreate != null){
						miCreate.setVisible(false);
					}
					if (miRefresh != null){
						miRefresh.setVisible(true);						
					}
					if (miSearch != null){
						miSearch.setVisible(true);
					}
				}
				else if (position == 1){
					if (miCreate != null){
						miCreate.setVisible(true);
					}
					if (miSearch != null){
						miSearch.setVisible(false);
					}
					if (miRefresh != null){
						miRefresh.setVisible(false);						
					}
					if (miSearch != null){
						miSearch.setVisible(false);
					}
				}
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
		});

		searchResultFragment = (SearchResultFragment)adapterViewPager.getItem(0);
		managePostsFragment = (ManagePostsFragment)adapterViewPager.getItem(1);
		ParseAnalytics.trackAppOpened(getIntent());

		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_search, menu);
		miRefresh = menu.findItem(R.id.action_refresh);
		miCreate = menu.findItem(R.id.action_post);
		miSearch = menu.findItem(R.id.action_search);
		miCreate.setVisible(false);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	
	public void onSearch(MenuItem mi) {
		// FragmentManager fm = getFragmentManager();
		// SearchCriteriaDialog diag = SearchCriteriaDialog.newInstance();
		// diag.show(fm, "fragment_search_criteria");
		Intent i = new Intent(SearchAndManageActivity.this, SearchCriteriaActivity.class);
		startActivityForResult(i, REQUEST_CODE_SEARCH_CRITERIA);
		overridePendingTransition(R.anim.slideinleft, R.anim.slideoutright);
	}

	public void onPost(MenuItem mi) {
		Intent i = new Intent(SearchAndManageActivity.this, CreatePostActivity.class);
//		if (lastKnownLocation != null){
//			GeoLocation geoLocation = new GeoLocation();
//			geoLocation.setLatitude(lastKnownLocation.getLatitude());
//			geoLocation.setLongitude(lastKnownLocation.getLongitude());
//			i.putExtra("geo_location", JsonUtil.toJson(geoLocation));
//		}
		startActivityForResult(i, REQUEST_CODE_CREATE_POST);
		Animator anim = AnimatorInflater.loadAnimator(this, R.animator.fadeout);
    	anim.setTarget(mi);
    	anim.start();
	}

   public void onAddingWish(MenuItem mi) {
		Intent i = new Intent(SearchAndManageActivity.this, WishListActivity.class);
		startActivityForResult(i, REQUEST_CODE_ADD_WISH);
	}
	
	public void onRefresh(MenuItem mi){
		searchResultFragment.refresh();
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_CREATE_POST) {
			if (resultCode == RESULT_OK){
				String postStr = data.getExtras().getString("post");
				Post newPost = (Post)JsonUtil.fromJson(postStr, Post.class);
				managePostsFragment.addPost(newPost);
				vpPager.setCurrentItem(1);
			}
		} else if (requestCode == REQUEST_CODE_SEARCH_CRITERIA) {
			if (data == null){
				return; //noop. user hit backbutton
			}
			String searchStr = data.getExtras().getString("search_criteria");
			Log.d("DEBUG", searchStr);
			SearchCriteria criteria = (SearchCriteria) JsonUtil.fromJson(searchStr, SearchCriteria.class);
			searchResultFragment.searchPostsByCriteria(criteria);
			vpPager.setCurrentItem(0);
		}
		else{
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
	
	public static class PagerAdapter extends FragmentPagerAdapter {
		private static int NUM_ITEMS = 2;

		public PagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		// Returns total number of pages
		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		// Returns the fragment to display for that page
		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return SearchResultFragment.newInstance(0, "Home");
			case 1: 
				return ManagePostsFragment.newInstance(1, "Manage Posts");
			default:
				return null;
			}
		}

		// Returns the page title for the top indicator
		@Override
		public CharSequence getPageTitle(int position) {
			if (position == 0)
				return "Home";
			else
				return "Manage Posts";
		}

	}
}
