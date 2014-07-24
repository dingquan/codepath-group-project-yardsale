package com.codepath.yardsale;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.yardsale.adapter.ImageArrayAdapter;
import com.codepath.yardsale.dao.PostDao;
import com.codepath.yardsale.dao.parse.ParseImages;
import com.codepath.yardsale.model.Category;
import com.codepath.yardsale.model.Contact;
import com.codepath.yardsale.model.GeoLocation;
import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.model.SearchCriteria;
import com.codepath.yardsale.util.JsonUtil;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParsePush;
public class CreatePostActivity extends BaseActivity {

	public final static int PICK_PHOTO_CODE = 1046;
	public final static String REQUEST_CODE_EDIT_ADS ="0";
	public final String APP_TAG = "TradingPost";
	public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
	public String photoFileName ;
	private Spinner spinner;
	private TextView title;
	private TextView description;
	private TextView location;
	private TextView price;
	private TextView phone;
	private TextView tvUploading;
	@SuppressWarnings("unused")
	private Gallery gallery;
	private ProgressBar pbLoading;
	private MenuItem miDelete;

	private String userId;
	private SharedPreferences prefs;
	private PostDao postDao = PostDao.getInstance();
	private Post post;
	
	private ArrayList<String> names;
	private List<ParseImages> parseImages;
	private GeoLocation geoLocation;
	
	private boolean isNewPost = false;
	private boolean imagesChanged = false;
	private Integer position;
	
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
		
		setUpViews();

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		String[] categoryNames = Category.getNames();
		Arrays.sort(categoryNames);
		List<String> categories = new ArrayList<String>();
		categories.addAll(Arrays.asList(categoryNames));

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categories);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		String locationJson = getIntent().getStringExtra("geo_location");

		fetchPostData();
		if (!isNewPost){
			getActionBar().setTitle("Edit Post");
			populateData();
		}
		if (locationJson != null && !locationJson.isEmpty()){
			geoLocation = (GeoLocation) JsonUtil.fromJson(locationJson, GeoLocation.class);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_ad, menu);
		miDelete = menu.findItem(R.id.action_delete);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (getIntent().getStringExtra("post") == null){
			miDelete.setVisible(false); //hide delete button when creating a new post
		}
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	public void fetchPostData(){
		//Existing ad
		String postJson = getIntent().getStringExtra("post");
		position = getIntent().getIntExtra("position", -1);
		if (postJson == null || postJson.isEmpty())
		{
			post = new Post();
			isNewPost = true;
			gallery.setVisibility(View.GONE);
		}
		else{
			isNewPost = false;
			post = (Post) JsonUtil.fromJson(postJson, Post.class);
		}
	}
	
	public void populateData()
	{
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
		
		renderImages();
	}
	
	public void renderImages(){
		List<String> imageUrls = post.getImageUrls();
		if (imageUrls != null && !imageUrls.isEmpty()){
			gallery.setAdapter(new ImageArrayAdapter(this,post.getImageUrls()));
			gallery.setVisibility(View.VISIBLE);
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
		pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
		tvUploading = (TextView) findViewById(R.id.tvUploading);
	}
	


	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		List<String> result = new ArrayList<String>();
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				result = (List<String>) data.getSerializableExtra("result");
				
			}
			if (resultCode == RESULT_CANCELED) {
				// Write your code if there's no result
			}
		}else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
		       if (resultCode == RESULT_OK) {
			         Uri takenPhotoUri = getPhotoFileUri(photoFileName);
			         result.add(takenPhotoUri.getPath());
			         // by this point we have the camera photo on disk
			         //Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
			         // Load the taken image into a preview
			         //ImageView ivPreview = (ImageView) findViewById(R.id.ivPreview);
			         //ivPreview.setImageBitmap(takenImage);   
			       } else { // Result was a failure
			    	   Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
			       }
			    }
		pbLoading.setVisibility(ProgressBar.VISIBLE);
		tvUploading.setVisibility(View.VISIBLE);
		gallery.setVisibility(View.INVISIBLE);
		new SaveImagesTask().execute(result);
	}
	
	private void savePost(){
		if (title.getText().toString().isEmpty()){
			//TODO: show alert dialog
			return;
		}
		//check if the ad is an edit or new
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
			geoLocation = getGeoFromAddress(contact);
		}
		post.setLocation(geoLocation);
		
		postDao.savePost(post);
		
	}
	
	// Trigger gallery selection for a photo
	public void onPickPhoto(MenuItem mi) {
		Intent i = new Intent(this,MultiPhotoSelectActivity.class);
		startActivityForResult(i, 1);
	}
	
	public void onDelete(MenuItem mi) {
		//Delete
        postDao.deletePost(post);
		Intent i = new Intent();
		i.putExtra("post", JsonUtil.toJson(post));
		i.putExtra("positoin", position);
		i.putExtra("action", "delete");
		setResult(RESULT_OK, i);
		finish();
	}
	
	public void onLaunchCamera(MenuItem mi) {
	    // create Intent to take a picture and return control to the calling application
		photoFileName = UUID.randomUUID().toString()+".png";
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName)); // set the image file name
	    // Start the image capture intent to take photo
	    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	

	// Returns the Uri for a photo stored on disk given the fileName
	public Uri getPhotoFileUri(String fileName) {
	    // Get safe storage directory for photos
	    File mediaStorageDir = new File(
	        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), APP_TAG);

	    // Create the storage directory if it does not exist
	    if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
	        Log.d(APP_TAG, "failed to create directory");
	    }

	    // Return the file target for the photo based on filename
	    return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
	}
	
	public void onSave(MenuItem mi) {
		
		savePost();
		
		// Prepare data intent
		Intent data = new Intent();
		// Pass relevant data back as a result
		if (post.getTitle() != null && !post.getTitle().isEmpty()){
			data.putExtra("post", JsonUtil.toJson(post));
			data.putExtra("position", position);
		}
		data.putExtra("action", "save");
		
//		overridePendingTransition(R.anim.slideinleft, R.anim.slideoutleft);
		// Activity finished ok, return the data
		setResult(RESULT_OK, data); // set result code and bundle data for response
		finish(); // closes the activity, pass data to parent
	}

	private class SaveImagesTask extends AsyncTask<List<String>, Void, Post>{

		@Override
		protected Post doInBackground(List<String>... args) {
			List<String> result = args[0];
			if(result.size()>0){
				Log.d("CreatePostActivity do in background","is not empty");
			}else{
				Log.d("CreatePostActivity do in background","is empty");
			}
			for (int i = 0; i < result.size(); i++) {
				Bitmap bitmap = BitmapFactory.decodeFile(result.get(i));
				// Convert it to byte
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				if (bitmap.getWidth() > 1024){
					bitmap = Bitmap.createScaledBitmap(bitmap, 1024, 768, false);
				}
				// Compress image to lower quality scale 1 - 100
				bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
				byte[] image = stream.toByteArray();
				String name = UUID.randomUUID().toString() + ".png";
				names.add(name);

				// Create the ParseFile
				ParseFile file = new ParseFile(name, image);
				ParseImages parseImage = new ParseImages(file, name);
				// Upload the image into Parse Cloud
				try {
					parseImage.save();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				parseImages.add(parseImage);			
				imagesChanged = true;
			}
			List<String> urls = new ArrayList<String>();
			for(ParseImages parseImage :parseImages){
				urls.add(parseImage.getParseFile("imageFile").getUrl());
			}
			post.setImageNames(names);
			post.setImageUrls(urls);			
			return post;
		}
		
		@Override
		protected void onPostExecute(Post result) {
			post = result;
			renderImages();
			pbLoading.setVisibility(ProgressBar.INVISIBLE);
			tvUploading.setVisibility(View.INVISIBLE);
			
		}
		
		public void onBackPressed() {
	 		finish();
	 		overridePendingTransition(R.anim.slideinleft, R.anim.slideoutright);
	 	};
	}
}
