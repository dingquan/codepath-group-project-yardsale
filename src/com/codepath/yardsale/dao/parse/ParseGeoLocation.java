package com.codepath.yardsale.dao.parse;

import com.codepath.yardsale.model.GeoLocation;
import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Location")
public class ParseGeoLocation extends ParseObject {
	public ParseGeoLocation() {
		super();
	}

	public ParseGeoLocation(GeoLocation location){
		super();
		setLongitude(location.getLongitude());
		setLatitude(location.getLatitude());
	}

	public GeoLocation toLocation() {
		GeoLocation location = new GeoLocation();
		location.setLatitude(getLatitude());
		location.setLongitude(getLongitude());
		return location;
	}
	
	public Double getLongitude(){
		return getDouble("logitude");
	}
	
	public void setLongitude(Double longitude){
		put("longitude", longitude);
	}
	
	public Double getLatitude() {
		return getDouble("latitude");
	}

	public void setLatitude(Double latitude) {
		put("latitude", latitude);
	}
}
