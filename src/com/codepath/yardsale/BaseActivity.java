package com.codepath.yardsale;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseActivity extends Activity {
	 
    protected ImageLoader imageLoader = ImageLoader.getInstance();
 
}
