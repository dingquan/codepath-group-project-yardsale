package com.codepath.yardsale.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.model.SearchCriteria;
import com.codepath.yardsale.util.JsonUtil;

public class PostAds {
	public List<Post> findAdsBySearchCriteria(SearchCriteria criteria){
		List<Post> posts = new ArrayList<Post>();
		posts = getDummyAds();
		return posts;
	}

	private List<Post> getDummyAds() {
		String postsData = 
				"[\n" + 
				"    {\n" + 
				"        \"userId\": \"1234567\",\n" + 
				"        \"title\": \"Ad1\",\n" + 
				"        \"description\": \"Brand new set.\",\n" + 
				"        \"status\": \"30\",\n" + 
				"        \"category\": \"Toys & Games\",\n" + 
				"        \"createdAt\": 1404198000000\n" + 
				"    },\n" + 
				"    {\n" + 
				"        \"userId\": \"1234567\",\n" + 
				"        \"title\": \"Ad2\",\n" + 
				"        \"description\": \"Black, 42k miles. first owner.\",\n" + 
				"        \"category\": \"Cars & Trucks\",\n" + 
				"        \"createdAt\": 1404284400000\n" + 
				"    },\n" + 
				"    {\n" + 
				"        \"userId\": \"1234567\",\n" + 
				"        \"title\": \"Ad3\",\n" + 
				"        \"description\": \"Brand new set.\",\n" + 
				"        \"category\": \"Toys & Games\",\n" + 
				"        \"createdAt\": 1404198000000\n" + 
				"    },\n" + 
				"    {\n" + 
				"        \"userId\": \"1234567\",\n" + 
				"        \"title\": \"Ad4\",\n" + 
				"        \"description\": \"Brand new set.\",\n" + 
				"        \"category\": \"Toys & Games\",\n" + 
				"        \"createdAt\": 1404198000000\n" + 
				"    },\n" + 
				"    {\n" + 
				"        \"userId\": \"1234567\",\n" + 
				"        \"title\": \"ad5\",\n" + 
				"        \"description\": \"Brand new set.\",\n" + 
				"        \"category\": \"Toys & Games\",\n" + 
				"        \"createdAt\": 1404198000000\n" + 
				"    },\n" + 
				"    {\n" + 
				"        \"userId\": \"1234567\",\n" + 
				"        \"title\": \"ad6\",\n" + 
				"        \"description\": \"Brand new set.\",\n" + 
				"        \"category\": \"Toys & Games\",\n" + 
				"        \"createdAt\": 1404198000000\n" + 
				"    },\n" + 
				"    {\n" + 
				"        \"userId\": \"1234567\",\n" + 
				"        \"title\": \"Ad7\",\n" + 
				"        \"description\": \"Brand new set.\",\n" + 
				"        \"status\": \"30\",\n" + 
				"        \"category\": \"Toys & Games\",\n" + 
				"        \"createdAt\": 1404198000000\n" + 
				"    },\n" + 
				"    {\n" + 
				"        \"userId\": \"1234567\",\n" + 
				"        \"title\": \"Ad8\",\n" + 
				"        \"description\": \"Black, 42k miles. first owner.\",\n" + 
				"        \"category\": \"Cars & Trucks\",\n" + 
				"        \"createdAt\": 1404284400000\n" + 
				"    },\n" + 
				"    {\n" + 
				"        \"userId\": \"1234567\",\n" + 
				"        \"title\": \"Ad9\",\n" + 
				"        \"description\": \"Brand new set.\",\n" + 
				"        \"category\": \"Toys & Games\",\n" + 
				"        \"createdAt\": 1404198000000\n" + 
				"    },\n" + 
				"    {\n" + 
				"        \"userId\": \"1234567\",\n" + 
				"        \"title\": \"Ad10\",\n" + 
				"        \"description\": \"Brand new set.\",\n" + 
				"        \"category\": \"Toys & Games\",\n" + 
				"        \"createdAt\": 1404198000000\n" + 
				"    },\n" + 
				"    {\n" + 
				"        \"userId\": \"1234567\",\n" + 
				"        \"title\": \"ad11\",\n" + 
				"        \"description\": \"Brand new set.\",\n" + 
				"        \"category\": \"Toys & Games\",\n" + 
				"        \"createdAt\": 1404198000000\n" + 
				"    },\n" + 
				"    {\n" + 
				"        \"userId\": \"1234567\",\n" + 
				"        \"title\": \"ad12\",\n" + 
				"        \"description\": \"Brand new set.\",\n" + 
				"        \"category\": \"Toys & Games\",\n" + 
				"        \"createdAt\": 1404198000000\n" + 
				"    },\n" + 
				"]";
						JSONArray list;
						List<Post> result = new ArrayList<Post>();
						try {
							list = new JSONArray(postsData);
							for (int i=0; i< list.length(); i++){
								JSONObject item = list.getJSONObject(i);
								Post post = (Post) JsonUtil.fromJson(item.toString(), Post.class);
								result.add(post);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return result;
			
	}
}
