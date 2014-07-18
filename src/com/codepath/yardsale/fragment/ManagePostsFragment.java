package com.codepath.yardsale.fragment;

import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codepath.yardsale.dao.PostDao;
import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.model.SearchCriteria;
import com.codepath.yardsale.util.JsonUtil;

public class ManagePostsFragment extends BaseFragment {
	private static final int REQUEST_CODE_EDIT_POST = 1;
	private static ManagePostsFragment managePostsFragment;

	private String userId;
	private SharedPreferences prefs;
	
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
		

		return view;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		lookupOwnUserId();
		loadOwnPosts();

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
	private void loadOwnPosts() {
		SearchCriteria criteria = new SearchCriteria();
		criteria.setUserId(userId);
		
        pbLoading.setVisibility(ProgressBar.VISIBLE);
        new SearchPostTask().execute(criteria); 
        
	}
	
	public void OnDelete(View view) {
		
		Toast.makeText(getActivity(), "Repost", Toast.LENGTH_SHORT).show();
		int position = lvPosts.getPositionForView((View) view.getParent());
		Post post = posts.get(position);
		PostDao postDao = PostDao.getInstance();
        postDao.deletePost(post);
        aPosts.remove(post);        

    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == this.REQUEST_CODE_EDIT_POST){
			if (resultCode == getActivity().RESULT_OK){
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
