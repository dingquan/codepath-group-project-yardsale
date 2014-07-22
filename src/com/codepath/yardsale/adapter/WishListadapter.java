package com.codepath.yardsale.adapter;

import java.util.ArrayList;

import com.codepath.yardsale.R;
import com.codepath.yardsale.model.WishItems;

import android.content.Context;
import android.text.style.EasyEditSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class WishListadapter extends ArrayAdapter<WishItems> {
	 
	  //private ArrayList<WishItems> itemsList;
	 
	  @SuppressWarnings("unchecked")
	public WishListadapter(Context context,  
	    ArrayList<WishItems> list) {
	   super(context,R.layout.wishlist_row, list);
	  
	  }
	 
	  private class ViewHolder {
	   TextView item;
	   CheckBox checkbox;
	  }
	 
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	 
	   ViewHolder holder = null;
	  // Log.v("ConvertView", String.valueOf(position));
	 
	   if (convertView == null) {
	   LayoutInflater inflater = LayoutInflater.from(getContext());
	  
	   convertView = inflater.inflate(R.layout.wishlist_row, parent, false);
	 
	   holder = new ViewHolder();
	   holder.item = (TextView) convertView.findViewById(R.id.tvItemName);
	   holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkBox1);
	   convertView.setTag(holder);
	 
	    holder.checkbox.setOnClickListener( new View.OnClickListener() {  
	     public void onClick(View v) {  
	      CheckBox cb = (CheckBox) v ;  
	      WishItems item =  (WishItems) cb.getTag();  
//	      Toast.makeText(getContext(),
//	       "Clicked on Checkbox: " + cb.getText() +
//	       " is " + cb.isChecked(), 
//	       Toast.LENGTH_LONG).show();
	      item.setSelected(cb.isChecked());
	     }  
	    });  
	   } 
	   else {
	    holder = (ViewHolder) convertView.getTag();
	   }
	  
	 
	   WishItems eachItem = getItem(position);
	   holder.item.setText(eachItem.getItem());
	   //holder.name.setText(country.getName());
	   holder.checkbox.setChecked(eachItem.isSelected());
	   holder.checkbox.setTag(eachItem);
		 
	   return convertView;
	 
	  }
	 
	 }
	 
//	 private void checkButtonClick() {
//	 
//	 
//	  Button myButton = (Button) findViewById(R.id.findSelected);
//	  myButton.setOnClickListener(new OnClickListener() {
//	 
//	   @Override
//	   public void onClick(View v) {
//	 
//	    StringBuffer responseText = new StringBuffer();
//	    responseText.append("The following were selected...\n");
//	 
//	    ArrayList<Country> countryList = dataAdapter.countryList;
//	    for(int i=0;i<countryList.size();i++){
//	     Country country = countryList.get(i);
//	     if(country.isSelected()){
//	      responseText.append("\n" + country.getName());
//	     }
//	    }
//	 
//	    Toast.makeText(getApplicationContext(),
//	      responseText, Toast.LENGTH_LONG).show();
//	 
//	   }
//	  });
//	 
//	 }
//	 
//	}