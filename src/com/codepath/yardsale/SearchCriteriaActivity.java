package com.codepath.yardsale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.yardsale.model.Category;
import com.codepath.yardsale.model.SearchCriteria;
import com.codepath.yardsale.util.JsonUtil;

public class SearchCriteriaActivity extends Activity {
	private EditText etKeyword;
	private EditText etCity;
	private Spinner spCategory;
	private EditText etMinPrice;
	private EditText etMaxPrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_criteria);

		setupView();
	}

	private void setupView() {
		etKeyword = (EditText) findViewById(R.id.etKeyword);
		etCity = (EditText) findViewById(R.id.etCity);
		spCategory = (Spinner) findViewById(R.id.spCategory);
		etMinPrice = (EditText) findViewById(R.id.etMinPrice);
		etMaxPrice = (EditText) findViewById(R.id.etMaxPrice);

		String[] names = Category.getNames();
		Arrays.sort(names);
		List<String> categories = new ArrayList<String>();
		categories.add("Select Category"); //the "Any" option
		categories.addAll(Arrays.asList(names));
		ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
		categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCategory.setAdapter(categoryAdapter);
	}

	public void searchPosts(View v) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.setKeyword(etKeyword.getText().toString());
		if (etMinPrice.getText().length() > 0)
			criteria.setMinPrice(Integer.valueOf(etMinPrice.getText().toString()));
		if (etMaxPrice.getText().length() > 0)
			criteria.setMaxPrice(Integer.valueOf(etMaxPrice.getText().toString()));

		criteria.setCategory(Category.fromName(spCategory.getSelectedItem().toString()));
		String city = etCity.getText().toString();
		criteria.setNearCity(city);
		
		Intent i = new Intent();
		i.putExtra("search_criteria", JsonUtil.toJson(criteria));
		setResult(RESULT_OK, i);
		finish();
	}
}
