package com.codepath.yardsale.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.codepath.yardsale.R;

public class SearchCriteriaDialog extends DialogFragment {
	public SearchCriteriaDialog(){};
	
	public static SearchCriteriaDialog newInstance(){
		SearchCriteriaDialog frag = new SearchCriteriaDialog();
		return frag;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_search_criteria, container);
		
		getDialog().getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
		WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();
	    p.width = LayoutParams.MATCH_PARENT;
	    getDialog().getWindow().setAttributes(p);
		return v;
	}
}
