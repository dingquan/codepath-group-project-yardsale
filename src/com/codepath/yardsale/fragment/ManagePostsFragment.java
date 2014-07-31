package com.codepath.yardsale.fragment;

import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;

import com.codepath.yardsale.CreatePostActivity;
import com.codepath.yardsale.R;
import com.codepath.yardsale.dao.PostDao;
import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.model.SearchCriteria;
import com.codepath.yardsale.util.JsonUtil;

public class ManagePostsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener  {
	private static final int REQUEST_CODE_EDIT_POST = 3;
	private static ManagePostsFragment managePostsFragment;

	private String userId;
	private SharedPreferences prefs;
	private SwipeRefreshLayout slSwipeLayout;

	// newInstance constructor for creating fragment with arguments
	public static ManagePostsFragment newInstance(int page, String title) {
		if (managePostsFragment == null){
			managePostsFragment = new ManagePostsFragment();
			Bundle args = new Bundle();
			args.putInt("someInt", page);
			args.putString("someTitle", title);
			managePostsFragment.setArguments(args);
		}
		return managePostsFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		slSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
		slSwipeLayout.setOnRefreshListener(this);
		slSwipeLayout.setColorScheme(android.R.color.holo_blue_bright, 
                android.R.color.holo_green_light, 
                android.R.color.holo_orange_light, 
                android.R.color.holo_red_light);

		setupHandlers();
		lookupOwnUserId();
		loadOwnPosts(true);
		return view;
	}
	
	@Override 
	public void onRefresh() {
        new Handler().post(new Runnable() {
            @Override public void run() {
            	loadOwnPosts(false);
            	slSwipeLayout.setRefreshing(false);
            }
        });
    }
	private void setupHandlers() {
		lvPosts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getActivity(), CreatePostActivity.class);
				Post post = posts.get(position);
				String postStr = JsonUtil.toJson(post);
				Log.d("DEBUG", "edit details of post: " + postStr);
				i.putExtra("post", postStr);
				i.putExtra("position", position);
				startActivityForResult(i, REQUEST_CODE_EDIT_POST);
				getActivity().overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
			}

		});

	}

	private void lookupOwnUserId(){
		prefs = getActivity().getSharedPreferences("com.codepath.yardsale", Context.MODE_PRIVATE);
		userId = prefs.getString("userId", "");
		if (userId.isEmpty()){
			userId = UUID.randomUUID().toString();
			prefs.edit().putString("userId", userId).commit();
		}
	}
	
	/**
	 * load posts created by self
	 */
	private void loadOwnPosts(boolean showProgressBar) {
		if (!isNetworkAvailable()){
			Toast.makeText(getActivity(), "Oops! Looks like you have zero bar. Please check your network settings.", Toast.LENGTH_SHORT).show();
			return;
		}

		SearchCriteria criteria = new SearchCriteria();
		criteria.setUserId(userId);
		
		if (showProgressBar)
			pbLoading.setVisibility(ProgressBar.VISIBLE);
		else
			pbLoading.setVisibility(ProgressBar.INVISIBLE);
		
        new SearchPostTask().execute(criteria); 
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_EDIT_POST){
			if (resultCode == getActivity().RESULT_OK){
				if (data == null){
					return;
				}
				String postStr = data.getExtras().getString("post");
				int position = data.getExtras().getInt("position");
				String action = data.getExtras().getString("action");
				if ("save".equals(action)){
					if (postStr == null)
						return;
					Post post = (Post) JsonUtil.fromJson(postStr, Post.class);
					posts.set(position, post);
				}
				else if ("delete".equals(action)){
					posts.remove(position);
				}
				aPosts.notifyDataSetChanged();
			}
		}
	}

	private class SearchPostTask extends AsyncTask<SearchCriteria, Void, List<Post>> {

		@Override
		protected void onPostExecute(List<Post> posts) {
			aPosts.clear();
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

	public void addPost(Post newPost) {
		aPosts.insert(newPost, 0);
		aPosts.notifyDataSetChanged();
	}
}
