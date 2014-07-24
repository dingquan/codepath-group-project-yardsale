package com.codepath.yardsale;

import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.codepath.yardsale.adapter.WishListadapter;
import com.codepath.yardsale.dao.PostDao;

import com.codepath.yardsale.model.WishItems;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class WishListActivity extends Activity {
	EditText wishItem;
	private SharedPreferences prefs;
	String userId;
	PostDao dao;
	ArrayList<WishItems> items;
	ListView lv;
	WishListadapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wish_list);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		dao = new PostDao();
		items = new ArrayList<WishItems>();
		lv = (ListView) findViewById(R.id.lvWishItems);

		prefs = getSharedPreferences("com.codepath.yardsale", Context.MODE_PRIVATE);
		userId = prefs.getString("userId", "");
		if (userId.isEmpty()){
			userId = UUID.randomUUID().toString();
			prefs.edit().putString("userId", userId).commit();
		}
		wishItem = (EditText) findViewById(R.id.etWishItem);
		adapter = new WishListadapter(this, items);
		lv.setAdapter(adapter);
		getItems();	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_wishlist, menu);
		return super.onCreateOptionsMenu(menu);
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
	
	public void onDelete(MenuItem mi) {
		int count = adapter.getCount();
		 ArrayList<WishItems> adapterList = new ArrayList<WishItems>();
		 ArrayList<WishItems> itemsTodelete = new ArrayList<WishItems>();
		 for(int i=0;i<count;i++){
		    	WishItems item = adapter.getItem(i);
		        adapterList.add(item);
		 }
		 for(WishItems eachitem : adapterList){
			 System.out.println("wishListActivity onDelete object id=="+eachitem.getId());
			 if(eachitem.isSelected()){
				 itemsTodelete.add(eachitem);
				 System.out.println("WishListActivity item to delete"+eachitem.getItem());
				 items.remove(eachitem);
				 PushService.unsubscribe(getApplicationContext(), eachitem.getItem());

			 }else{
				 System.out.println(eachitem.getItem()+" not selected to delete");
			 }
		 }
		 dao.deleteWish(itemsTodelete);
		 adapter.clear();
		 adapter.addAll(items);
		 adapter.notifyDataSetChanged();

	}
	
	
	public void AddItem(View v){
		String item = wishItem.getText().toString().trim();
		if(ParseInstallation.getCurrentInstallation() == null){
			System.out.println("ParseInstallation is null");
		}else{
			System.out.println("parseInstallation not null"+ParseInstallation.getCurrentInstallation());
		}

		PushService.subscribe(getBaseContext(), item, ViewPostActivity.class);
		WishItems newwishItem = new WishItems();
		newwishItem.setItem(item);
		newwishItem.setUserId(userId);
		dao.saveWish(newwishItem);
		adapter.clear();
		items.add(newwishItem);
		adapter.addAll(items);
		wishItem.setText("");
		adapter.notifyDataSetChanged();
				
		
	}

	public void getItems() {
		try {
			items = dao.getWishListItems(userId);
			adapter.clear();
			for(WishItems eachItem : items){
				System.out.println("WishListActivity getItems-->"+eachItem.getId());
				System.out.println("WishListActivity eachWishItem -->>"+eachItem.getItem());
			}
			if(items.size()>0){
				adapter.addAll(items);
				adapter.notifyDataSetChanged();
			} else{
				Log.d("WishListActivity","items list is empty");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onBackPressed() {
 		finish();
 		overridePendingTransition(R.anim.slideinleft, R.anim.slideoutright);
 	};
}
