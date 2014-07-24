package com.codepath.yardsale;

import java.util.List;

import com.codepath.yardsale.model.Contact;
import com.codepath.yardsale.model.GeoLocation;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseActivity extends Activity {
	 
	protected GeoLocation getGeoFromAddress(Contact contact) {
		if (contact == null || contact.getAddress() == null
				|| contact.getAddress().isEmpty()) {
			Log.e("ERROR", "contact is empty");
			return null;
		}
		Geocoder coder = new Geocoder(this);
		List<Address> address;

		try {
			address = coder.getFromLocationName(contact.getAddress(), 1);
			if (address == null) {
				return null;
			}
			Address location = address.get(0);
			Log.d("DEBUG", "## location: " + location.getLatitude() + ", "
					+ location.getLongitude());
			contact.setCity(location.getLocality());
			contact.setState(location.getAdminArea());
			GeoLocation p = new GeoLocation();
			p.setLatitude(location.getLatitude());
			p.setLongitude(location.getLongitude());
			return p;
		} catch (Exception e) {
			Log.e("ERROR", e.getMessage());
		}
		return null;
	}
}
