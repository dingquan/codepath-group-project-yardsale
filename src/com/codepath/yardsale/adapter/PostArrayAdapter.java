package com.codepath.yardsale.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.yardsale.BaseActivity;
import com.codepath.yardsale.R;
import com.codepath.yardsale.model.Post;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PostArrayAdapter extends ArrayAdapter<Post> {
	

	public PostArrayAdapter(Context context, List<Post> objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Post post = getItem(position);
		
		View v;
		if (convertView == null) {
			v = LayoutInflater.from(getContext()).inflate(R.layout.post_item, parent, false);
		}
		else{
			v = convertView;
		}
		
		ImageView ivImage = (ImageView)v.findViewById(R.id.ivImage);
		TextView tvTitle = (TextView)v.findViewById(R.id.tvTitle);
		TextView tvDescription = (TextView)v.findViewById(R.id.tvDescription);
		TextView tvPrice = (TextView)v.findViewById(R.id.tvPrice);
		TextView tvDate = (TextView)v.findViewById(R.id.tvDate);
		TextView tvLocation = (TextView)v.findViewById(R.id.tvLocation);
		ArrayList<String> postUrl = post.getImageUrl();
		if(postUrl !=null){
			Log.d("PosrArrayAdapter posrtUrl --->>>","null");
		}else{
			Log.d("PosrArrayAdapter posrtUrl --->>>","not null");

		}
		ImageLoader imageLoader = ImageLoader.getInstance();
		if(postUrl != null && postUrl.size()>0){
			String url =postUrl.get(0);
			Log.d("PostArrayAdapter url string--->>",url);
			imageLoader.displayImage(url, ivImage);
			
			
		}
		
		
		//ImageLoader imageLoader = ImageLoader.getInstance();
		Log.d("PostArrayAdapter title-->>",post.getTitle());
		tvTitle.setText(post.getTitle());
		String description = post.getDescription();
		if (description.length() > 80){
			description = description.substring(0, 80) + "...";
		}
		tvDescription.setText(description);
		tvPrice.setText("$" + post.getPrice().toString());
		tvLocation.setText(post.getContact().getAddress());
		
		Date date = new Date(post.getCreatedAt());
		String dateStr = DateFormat.getDateFormat(v.getContext()).format(date);
		tvDate.setText(dateStr);

		return v;
	}
}
