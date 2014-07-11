package com.codepath.yardsale;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.parse.ParseFile;
import com.parse.ParseObject;

public class MultiPhotoSelectActivity extends BaseActivity {
	 
    private ArrayList<String> imageUrls;
    private DisplayImageOptions options;
    private ImageAdapter imageAdapter;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_image_grid);
        imageLoader.init(ImageLoaderConfiguration.createDefault(getBaseContext()));

 
        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        @SuppressWarnings("deprecation")
		Cursor imagecursor = managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy + " DESC");
 
        this.imageUrls = new ArrayList<String>();
 
        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            imageUrls.add(imagecursor.getString(dataColumnIndex));
 
            System.out.println("=====> Array path => "+imageUrls.get(i));
        }
 
        options = new DisplayImageOptions.Builder()
            .showStubImage(R.drawable.stub_image)
            .showImageForEmptyUri(R.drawable.image_for_empty_url)
            .cacheInMemory()
            .cacheOnDisc()
            .build();
 
        imageAdapter = new ImageAdapter(this, imageUrls);
 
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(imageAdapter);
        /*gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startImageGalleryActivity(position);
            }
        });*/
    }
 
    @Override
    protected void onStop() {
        imageLoader.stop();
        super.onStop();
    }
 
    public void btnChoosePhotosClick(View v){
 
        ArrayList<String> selectedItems = imageAdapter.getCheckedItems();
//        Toast.makeText(MultiPhotoSelectActivity.this, "Total photos selected: "+selectedItems.size(), Toast.LENGTH_SHORT).show();
//        Log.d(MultiPhotoSelectActivity.class.getSimpleName(), "Selected Items: " + selectedItems.toString());
//        for(int i=0;i<selectedItems.size();i++){
//        Bitmap bitmap = BitmapFactory.decodeFile(selectedItems.get(i));
//        // Convert it to byte
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        // Compress image to lower quality scale 1 - 100
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] image = stream.toByteArray();
//
//        // Create the ParseFile
//        ParseFile file = new ParseFile("androidbegin.png", image);
//        // Upload the image into Parse Cloud
//        file.saveInBackground();
//
//        // Create a New Class called "ImageUpload" in Parse
//        ParseObject imgupload = new ParseObject("ImageUpload");
//
//        // Create a column named "ImageName" and set the string
//        imgupload.put("ImageName", "AndroidBegin Logo");
//
//        // Create a column named "ImageFile" and insert the image
//        imgupload.put("ImageFile", file);
//
//        // Create the class and the columns
//        imgupload.saveInBackground();
//        }
//
//        // Show a simple toast message
//        Toast.makeText(MainActivity.this, "Image Uploaded",
//                Toast.LENGTH_SHORT).show();
        
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",selectedItems);
        setResult(RESULT_OK,returnIntent);
        finish();
    	
    }
 
    /*private void startImageGalleryActivity(int position) {
        Intent intent = new Intent(this, ImagePagerActivity.class);
        intent.putExtra(Extra.IMAGES, imageUrls);
        intent.putExtra(Extra.IMAGE_POSITION, position);
        startActivity(intent);
    }*/
 
    public class ImageAdapter extends BaseAdapter {
 
        ArrayList<String> mList;
        LayoutInflater mInflater;
        Context mContext;
        SparseBooleanArray mSparseBooleanArray;
 
        public ImageAdapter(Context context, ArrayList<String> imageList) {
            // TODO Auto-generated constructor stub
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
            mSparseBooleanArray = new SparseBooleanArray();
            mList = new ArrayList<String>();
            this.mList = imageList;
 
        }
 
        public ArrayList<String> getCheckedItems() {
            ArrayList<String> mTempArry = new ArrayList<String>();
 
            for(int i=0;i<mList.size();i++) {
                if(mSparseBooleanArray.get(i)) {
                    mTempArry.add(mList.get(i));
                }
            }
 
            return mTempArry;
        }
 
        @Override
        public int getCount() {
            return imageUrls.size();
        }
 
        @Override
        public Object getItem(int position) {
            return null;
        }
 
        @Override
        public long getItemId(int position) {
            return position;
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
 
            if(convertView == null) {
                convertView = mInflater.inflate(R.layout.row_multiphoto_item, null);
            }
 
            CheckBox mCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
            final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);
 
            imageLoader.displayImage("file://"+imageUrls.get(position), imageView, options, new SimpleImageLoadingListener() {
            	@Override
                public void onLoadingComplete(String imageUri,View v,Bitmap loadedImage) {
//                    Animation anim = AnimationUtils.loadAnimation(MultiPhotoSelectActivity.this, R.anim.fade_in);
//                    imageView.setAnimation(anim);
//                    anim.start();
                }
            });
 
            mCheckBox.setTag(position);
            mCheckBox.setChecked(mSparseBooleanArray.get(position));
            mCheckBox.setOnCheckedChangeListener(mCheckedChangeListener);
 
            return convertView;
        }
 
        OnCheckedChangeListener mCheckedChangeListener = new OnCheckedChangeListener() {
 
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
            }
        };
    }
 
}
