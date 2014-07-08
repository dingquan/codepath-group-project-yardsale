package com.codepath.yardsale.adapter;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.yardsale.model.Post;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.codepath.yardsale.R;
public class AdArrayAdapter extends ArrayAdapter<Post> {

	public AdArrayAdapter(Context context, List<Post> objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Post post = getItem(position);
		
		View v;
		if (convertView == null) {
			v = LayoutInflater.from(getContext()).inflate(R.layout.ads_view, parent, false);
		}
		else{
			v = convertView;
		}
		
		ImageView ivImage = (ImageView)v.findViewById(R.id.ivImage);
		TextView tvTitle = (TextView)v.findViewById(R.id.tvAdsTitle);
		TextView tvDescription = (TextView)v.findViewById(R.id.tvAdDescription);
		TextView tvStatus = (TextView)v.findViewById(R.id.tvAdsStatus);
		TextView tvDate = (TextView)v.findViewById(R.id.tvAdsDateCreated);
		TextView tvCategory = (TextView)v.findViewById(R.id.tvAdsCategory);

		ImageLoader imageLoader = ImageLoader.getInstance();
		tvTitle.setText(post.getTitle());
		tvDescription.setText(post.getDescription());
		tvStatus.setText(post.getStatus().toString());
		tvCategory.setText(post.getCategory().toString());
		
		Date date = new Date(post.getCreatedAt());
		String dateStr = DateFormat.getDateFormat(v.getContext()).format(date);
		tvDate.setText(dateStr);

		return v;
	}
}
