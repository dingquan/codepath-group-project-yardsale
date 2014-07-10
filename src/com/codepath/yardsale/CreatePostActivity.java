package com.codepath.yardsale;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.yardsale.dao.PostDao;
import com.codepath.yardsale.model.Category;
import com.codepath.yardsale.model.Contact;
import com.codepath.yardsale.model.GeoLocation;
import com.codepath.yardsale.model.Post;
public class CreatePostActivity extends Activity {

	public final static int PICK_PHOTO_CODE = 1046;
	Spinner spinner;
	TextView title;
	TextView description;
	TextView location;
	TextView price;
	TextView phone;
	String selectedCategory = "Toys and Games";

	
	private String userId;

	private SharedPreferences prefs;
	
	private PostDao postDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_post);
		
		prefs = this.getSharedPreferences("com.codepath.yardsale", Context.MODE_PRIVATE);
		userId = prefs.getString("userId", "");
		if (userId.isEmpty()){
			userId = UUID.randomUUID().toString();
		}
		
		postDao = new PostDao();

		setUpViews();
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.categories_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
	}

	public void setUpViews() {
		spinner = (Spinner) findViewById(R.id.spCategory);
		title = (TextView) findViewById(R.id.etTitle);
		description = (TextView) findViewById(R.id.etDescription);
		location = (TextView) findViewById(R.id.etLocation);
		price = (TextView) findViewById(R.id.etPrice);
		phone = (TextView) findViewById(R.id.etPhone);
	}
	// Trigger gallery selection for a photo
	public void onPickPhoto(View view) {
		// Create intent for picking a photo from the gallery
		Intent intent = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		// Bring up gallery to select a photo
		startActivityForResult(intent, PICK_PHOTO_CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			try {
				Uri photoUri = data.getData();
				// Do something with the photo based on Uri
				Bitmap selectedImage;
				selectedImage = MediaStore.Images.Media.getBitmap(
						this.getContentResolver(), photoUri);
				// Load the selected image into a preview
				ImageView ivPreview = (ImageView) findViewById(R.id.ivPreview);
				ivPreview.setImageBitmap(selectedImage);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void onSave(View v) {
		Category c = Category.fromName(selectedCategory);
		System.out.println(c.toString());
		Post p = new Post();
		p.setUserId(userId);
		p.setCategory(c);
		Contact contact = new Contact(phone.getText().toString(), location
				.getText().toString());
		p.setContact(contact);
		p.setTitle(title.getText().toString());
		p.setDescription(description.getText().toString());
		p.setPrice(Double.valueOf(price.getText().toString()));
		Date date = new Date();
		p.setCreatedAt((new Timestamp(date.getTime())).getTime());
		p.setStatus("Active");
		GeoLocation location = new GeoLocation();
		location.setLatitude(30D);
		location.setLongitude(123D);
		p.setLocation(location);
		
		postDao.savePost(p);
		
		Intent i = new Intent(CreatePostActivity.this, SearchResultActivity.class);
		startActivity(i);

	}
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {

			selectedCategory = parent.getItemAtPosition(pos).toString();

			// make sure the country was already selected during the onCreate
			if (selectedCategory != null) {
				Toast.makeText(parent.getContext(),
						"Country you selected is " + selectedCategory,
						Toast.LENGTH_LONG).show();
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}
}
