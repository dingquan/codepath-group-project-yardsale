package com.codepath.yardsale.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.codepath.yardsale.R;
import com.codepath.yardsale.ViewPostActivity;
import com.codepath.yardsale.adapter.PostArrayAdapter;
import com.codepath.yardsale.dao.PostDao;
import com.codepath.yardsale.model.GeoLocation;
import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.util.JsonUtil;

public abstract class BaseFragment extends Fragment {
	
	protected List<Post> posts;
	protected ArrayAdapter<Post> aPosts;
	protected ListView lvPosts;
	protected ProgressBar pbLoading;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		posts = new ArrayList<Post>();
		aPosts = new PostArrayAdapter(getActivity(), posts);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_ad_list, container, false);
		lvPosts = (ListView) view.findViewById(R.id.lvPosts);
		lvPosts.setAdapter(aPosts);
		pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);

		setupHandlers();
		return view;
	}
	
	private void setupHandlers() {
		lvPosts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getActivity(), ViewPostActivity.class);
				Post post = posts.get(position);
				String postStr = JsonUtil.toJson(post);
				Log.d("DEBUG", "view details of post: " + postStr);
				i.putExtra("post", postStr);
				i.putExtra("position", position);
				startActivity(i);
			}

		});

	}
	
	GeoLocation getGeoFromAddress(String strAddress){
		Geocoder coder = new Geocoder(getActivity());
		List<Address> address;

		try {
		    address = coder.getFromLocationName(strAddress,1);
		    if (address == null) {
		        return null;
		    }
		    Address location = address.get(0);
		    Log.d("DEBUG", "## location: " + location.getLatitude() + ", " + location.getLongitude());

		    GeoLocation p = new GeoLocation();
		    p.setLatitude(location.getLatitude());
		    p.setLongitude(location.getLongitude());
		    return p;
		}
		catch (Exception e){
			Log.e("ERROR", e.getMessage());
		}
		return null;
	}
}
