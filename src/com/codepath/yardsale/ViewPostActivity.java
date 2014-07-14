package com.codepath.yardsale;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.util.JsonUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ViewPostActivity extends Activity {
	TextView title;
	TextView description;
	TextView location;
	TextView price;
	ImageView ivImage;
	Post post;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_post);
		String postJson = getIntent().getStringExtra("post");
		post = (Post) JsonUtil.fromJson(postJson, Post.class);
		
		setupViews();
		populateData();
	}
	
	private void setupViews() {
		title = (TextView) findViewById(R.id.tvAdsTitle);
		description = (TextView) findViewById(R.id.tvAdsDescription);
		location = (TextView) findViewById(R.id.tvAdsAddress);
		price = (TextView) findViewById(R.id.tvAdsPrice);
		//ivImage = (ImageView) findViewById(R.id.ivAds);
	}
	
	private void populateData() {
		
//		Toast.makeText(this, "Read post: " + post.getTitle(), Toast.LENGTH_SHORT).show();
		
		title.setText(post.getTitle());
		description.setText(post.getDescription());
		location.setText(post.getContact().getAddress());
		price.setText("$"+post.getPrice().toString());
		//ivImage = (ImageView) findViewById(R.id.ivAds);
		//ivImage.setImageURI(post.get);
		
		if(post.getImageList()!=null){
			@SuppressWarnings("deprecation")
			Gallery g = (Gallery) findViewById(R.id.gallery);
		    g.setAdapter(new ImageAdapter(this,post.getImageList()));
		}
	}
	
	public class ImageAdapter extends ArrayAdapter {
		   int mGalleryItemBackground;
		   private Context mContext;

		   private ArrayList<String> imageUrls;
		   ImageLoader imageLoader = ImageLoader.getInstance();
		   private Integer[] mImageIds = {
		           R.drawable.ic_books,
		           R.drawable.ic_appliances,
		           R.drawable.ic_computers
		          
		   };

		   public ImageAdapter(Context context, ArrayList<String> objects) {
				super(context, 0, objects);
				 mContext = context;
				 imageUrls = objects;
			       TypedArray a = obtainStyledAttributes(R.styleable.Theme);
			       mGalleryItemBackground = a.getResourceId(
			         R.styleable.Theme_android_galleryItemBackground,
			                   0);
			       
			       a.recycle();
			}
		      
		   

		   public int getCount() {
		       return imageUrls.size();
			  //return mImageIds.length;
		   }

		   public Object getItem(int position) {
		       return position;
		   }

		   public long getItemId(int position) {
		       return position;
		   }

		   @SuppressWarnings("deprecation")
		public View getView(int position,
		       View convertView, ViewGroup parent) {
		       ImageView i = new ImageView(mContext);
		       Log.d("ViewpostActivity trying to set image for position",String.valueOf(position));
		       String url = imageUrls.get(position);
		       
		       Log.d("ViewPostActivity",i.toString());

		       if(url != null){
		    	   Log.d("ViewPostActivity url",url);
		    	   
		    	   Log.d("ViewPostACtivity Imageloader",imageLoader.toString());
		    	   
		    	   Log.d("Image set at ",String.valueOf(position));
		    	   i.setLayoutParams(new Gallery.LayoutParams(700,500));
			       i.setScaleType(ImageView.ScaleType.FIT_XY);
			       i.setBackgroundResource(mGalleryItemBackground);
			       imageLoader.displayImage(url,i);
		      }
		       

		       return i;
		   }
		}

}
