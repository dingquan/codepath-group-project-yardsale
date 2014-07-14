package com.codepath.yardsale;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.yardsale.adapter.ImageArrayAdapter;
import com.codepath.yardsale.dao.PostDao;
import com.codepath.yardsale.dao.parse.ParseImages;
import com.codepath.yardsale.model.Category;
import com.codepath.yardsale.model.Contact;
import com.codepath.yardsale.model.GeoLocation;
import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.util.JsonUtil;
import com.parse.ParseFile;
public class CreatePostActivity extends BaseActivity {

	public final static int PICK_PHOTO_CODE = 1046;
	public final static String REQUEST_CODE_EDIT_ADS ="0";
	private Spinner spinner;
	private TextView title;
	private TextView description;
	private TextView location;
	private TextView price;
	private TextView phone;
	@SuppressWarnings("unused")
	private Gallery gallery;

	private String userId;
	private SharedPreferences prefs;
	private PostDao postDao;
	private Post post;
	
	private ArrayList<String> names;
	private List<ParseImages> parseImages;
	private GeoLocation geoLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_post);
		
		prefs = getSharedPreferences("com.codepath.yardsale", Context.MODE_PRIVATE);
		userId = prefs.getString("userId", "");
		if (userId.isEmpty()){
			userId = UUID.randomUUID().toString();
			prefs.edit().putString("userId", userId).commit();
		}
		
		postDao = new PostDao();

		setUpViews();

		// Create an ArrayAdapter using the string array and a default spinner layout
		String[] categoryNames = Category.getNames();
		Arrays.sort(categoryNames);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categoryNames);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		String locationJson = getIntent().getStringExtra("geo_location");

		//Edit an Ad
		populateData();

		if (locationJson != null && !locationJson.isEmpty()){
			geoLocation = (GeoLocation) JsonUtil.fromJson(locationJson, GeoLocation.class);
		}
	}

	public void populateData()
	{
		//Existing ad
		String postJson = getIntent().getStringExtra("post");
		if (postJson == null || postJson.isEmpty())
		{
			post=null;
			gallery.setVisibility(View.INVISIBLE);
			return;
		}

		post = (Post) JsonUtil.fromJson(postJson, Post.class);

		title.setText(post.getTitle());
		description.setText(post.getDescription());
		location.setText(post.getContact().getAddress());
		Double  dValue= post.getPrice();

		if(!dValue.isNaN())
		{
			price.setText(dValue.toString());
		} 
		
		phone.setText(post.getContact().getPhone());
		String category = post.getCategory().toString();
		spinner.setSelection(getIndex(spinner, category));
		
		List<String> imageUrls = post.getImageUrls();
		if (imageUrls != null && !imageUrls.isEmpty()){
			gallery.setAdapter(new ImageArrayAdapter(this,post.getImageUrls()));
		}
		else{
			gallery.setVisibility(View.GONE);
		}
	}
	
	//Get Spinner Index position
	 
	private int getIndex(Spinner spinner, String myString) {

		int index = 0;

		for (int i = 0; i < spinner.getCount(); i++) {
			if (spinner.getItemAtPosition(i).equals(myString)) {
				index = i;
			}
		}
		// Toast.makeText(this, "Read post: " +
		// index,Toast.LENGTH_SHORT).show();
		return index;

	}
	 
	public void setUpViews() {
		spinner = (Spinner) findViewById(R.id.spCategory);
		title = (TextView) findViewById(R.id.etTitle);
		description = (TextView) findViewById(R.id.etDescription);
		location = (TextView) findViewById(R.id.etLocation);
		price = (TextView) findViewById(R.id.etPrice);
		phone = (TextView) findViewById(R.id.etPhone);
		names = new ArrayList<String>();
		parseImages = new ArrayList<ParseImages>();
		gallery = (Gallery) findViewById(R.id.gallery);
	}
	
	// Trigger gallery selection for a photo
	public void onPickPhoto(View view) {
		Intent i = new Intent(this,MultiPhotoSelectActivity.class);
		startActivityForResult(i, 1);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				ArrayList<String> result = (ArrayList<String>) data.getSerializableExtra("result");
				for (int i = 0; i < result.size(); i++) {
					Bitmap bitmap = BitmapFactory.decodeFile(result.get(i));
					// Convert it to byte
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					// Compress image to lower quality scale 1 - 100
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
					byte[] image = stream.toByteArray();
					String name = UUID.randomUUID().toString() + ".png";
					names.add(name);

					// Create the ParseFile
					ParseFile file = new ParseFile(name, image);
					ParseImages parseImage = new ParseImages(file, name);
					// Upload the image into Parse Cloud
					parseImage.saveInBackground();
					parseImages.add(parseImage);
				}
			}
			if (resultCode == RESULT_CANCELED) {
				// Write your code if there's no result
			}
		}
	}
	
	public void onSave(View v) {
		//check if the ad is an edit or new
		if (post == null)
		{
			post = new Post();
		}
		post.setUserId(userId);
		post.setCategory(Category.fromName(spinner.getSelectedItem().toString()));
		Contact contact = new Contact(phone.getText().toString(), location.getText().toString());
		post.setContact(contact);
		post.setTitle(title.getText().toString());
		post.setDescription(description.getText().toString());
		post.setPrice(Double.valueOf(price.getText().toString()));
		Date date = new Date();
		
		post.setCreatedAt((new Timestamp(date.getTime())).getTime());
		post.setStatus("Active");
		String locationStr = location.getText().toString();
		if (locationStr != null && !locationStr.isEmpty()){
			geoLocation = getGeoFromAddress(locationStr);
		}
		post.setLocation(geoLocation);
		List<String> urls = new ArrayList<String>();
		for(ParseImages parseImage :parseImages){
			urls.add(parseImage.getParseFile("imageFile").getUrl());
		}
		post.setImageNames(names);
		post.setImageUrls(urls);
		postDao.savePost(post);
		
		// Prepare data intent
		Intent data = new Intent();
		// Pass relevant data back as a result
		data.putExtra("post", JsonUtil.toJson(post));
		// Activity finished ok, return the data
		setResult(RESULT_OK, data); // set result code and bundle data for response
		finish(); // closes the activity, pass data to parent
	}

}
