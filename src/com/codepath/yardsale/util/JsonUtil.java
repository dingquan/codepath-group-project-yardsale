package com.codepath.yardsale.util;

import com.google.gson.Gson;

public class JsonUtil {
	private static final Gson gson = new Gson();
	
	public static String toJson(Object o){
		return gson.toJson(o);
	}
	
	@SuppressWarnings("unchecked")
	public static Object fromJson(String json, Class clazz){
		return gson.fromJson(json, clazz);
	}
}
