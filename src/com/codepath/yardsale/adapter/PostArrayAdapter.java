package com.codepath.yardsale.adapter;

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
		
		View v = convertView;
		ViewHolder holder = null;
		if (convertView == null) {
			v = LayoutInflater.from(getContext()).inflate(R.layout.post_item, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView)v.findViewById(R.id.ivImage);
			holder.title = (TextView)v.findViewById(R.id.tvTitle);
			holder.description =  (TextView)v.findViewById(R.id.tvDescription);
			holder.location = (TextView)v.findViewById(R.id.tvLocation);
			holder.date = (TextView)v.findViewById(R.id.tvDate);
			holder.price = (TextView)v.findViewById(R.id.tvPrice);
			v.setTag(holder);
		}
		else{
			holder = (ViewHolder) v.getTag();
		}
		
		holder.image.setImageResource(R.color.transparent);
		
		List<String> postUrl = post.getImageUrls();
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		if(postUrl != null && postUrl.size()>0){
			String url =postUrl.get(0);
			Log.d("PostArrayAdapter url string--->>",url);
			imageLoader.displayImage(url, holder.image);
			
			
		}else{
			holder.image.setImageResource(R.drawable.ic_no_photo);
//			String ctgry = post.getCategory().name();
//			System.out.println("PostArrayAdapter-->>"+ctgry);
//			if(ctgry.equals("TOYS_GAMES")){
//				holder.image.setImageResource(R.drawable.ic_toys);
//			}else if(ctgry.equals("FURNITURE")){
//				holder.image.setImageResource(R.drawable.ic_furniture);
//			}else if(ctgry.equals("ELECTRONICS")){
//				holder.image.setImageResource(R.drawable.ic_electronics);
//			}else if(ctgry.equals("CLOTHING_ACCESSRIES")){
//				holder.image.setImageResource(R.drawable.ic_clothing);
//			}else if(ctgry.equals("BOOKS_MAGAZINES")){
//				holder.image.setImageResource(R.drawable.ic_books);
//			}else if(ctgry.equals("COMPUTERS")){
//				holder.image.setImageResource(R.drawable.ic_computers);
//			}else if(ctgry.equals("APPLIANCES")){
//				holder.image.setImageResource(R.drawable.ic_appliances);
//			}else if(ctgry.equals("CARS")){
//				holder.image.setImageResource(R.drawable.ic_cars);
//			}else{
//				holder.image.setImageResource(R.drawable.ic_cells);
//			}
		}
		
		
		//ImageLoader imageLoader = ImageLoader.getInstance();
		String title = post.getTitle();
		if (title.length() > 25){
			title = title.substring(0, 25) + "...";
		}
		holder.title.setText(title);
		String description = post.getDescription();
		if (description.length() > 60){
			description = description.substring(0, 60) + "...";
		}
		holder.description.setText(description);
		String fprice= String.format("%.2f", post.getPrice());
		holder.price.setText("$" + fprice);
		String city = post.getContact().getCity();
		if (city != null && !city.isEmpty())
			holder.location.setText(post.getContact().getCity());
		else
			holder.location.setText(post.getContact().getAddress());
		
		Date date = new Date(post.getCreatedAt());
		String dateStr = DateFormat.getDateFormat(v.getContext()).format(date);
		holder.date.setText(dateStr);

		return v;
	}
	
	static class ViewHolder {
		  ImageView image;
		  TextView title;
		  TextView description;
		  TextView location;
		  TextView date;
		  TextView price;
		 }

	
}
