package com.codepath.yardsale.adapter;

import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.codepath.yardsale.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageArrayAdapter extends ArrayAdapter<String> {
	
	int mGalleryItemBackground;
	private Context mContext;

	private List<String> imageUrls;
	ImageLoader imageLoader = ImageLoader.getInstance();
	private Integer[] mImageIds = {
			R.drawable.ic_books,
			R.drawable.ic_appliances,
			R.drawable.ic_computers

	};

	public ImageArrayAdapter(Context context, List<String> imageUrls) {
		super(context, 0, imageUrls);
		mContext = context;
		this.imageUrls = imageUrls;
		TypedArray a = context.obtainStyledAttributes(R.styleable.Theme);
		mGalleryItemBackground = a.getResourceId(
				R.styleable.Theme_android_galleryItemBackground, 0);

		a.recycle();
	}


	@SuppressWarnings("deprecation")
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView i = new ImageView(mContext);
		Log.d("ViewpostActivity trying to set image for position",
				String.valueOf(position));
		String url = imageUrls.get(position);

		Log.d("ViewPostActivity", i.toString());

		if (url != null) {
			Log.d("ViewPostActivity url", url);

			Log.d("ViewPostACtivity Imageloader", imageLoader.toString());

			Log.d("Image set at ", String.valueOf(position));
			i.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.MATCH_PARENT, Gallery.LayoutParams.WRAP_CONTENT));
			i.setScaleType(ImageView.ScaleType.CENTER_CROP);
//			i.setBackgroundResource(mGalleryItemBackground);
			imageLoader.displayImage(url, i);
		}

		return i;
	}
}