package com.codepath.yardsale.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.model.SearchCriteria;
import com.codepath.yardsale.util.JsonUtil;

public class PostDao {
	public List<Post> findPostsBySearchCriteria(SearchCriteria criteria){
		List<Post> posts = new ArrayList<Post>();
		posts = getDummyPosts();
		return posts;
	}
	
	public void savePosts(List<Post> posts){
		
	}
	
	public void savePost(Post post){
		
	}

	/**
	 * find a Post by its unique id
	 * @param uid
	 * @return
	 */
	public Post getPostById(String uid){
		return null;
	}
	
	private List<Post> getDummyPosts(){
		String postsData = 
"[\n" + 
"    {\n" + 
"        \"userId\": \"1234567\",\n" + 
"        \"title\": \"Thomas Train Set\",\n" + 
"        \"description\": \"Brand new set.\",\n" + 
"        \"contact\": \"650-123-4567\",\n" + 
"        \"price\": \"30\",\n" + 
"        \"category\": \"Toys & games\",\n" + 
"        \"createdAt\": 1404500625468,\n" + 
"        \"location\": {\n" + 
"            \"latitude\": 37,\n" + 
"            \"longitude\": -122,\n" + 
"            \"address\": \"San Mateo, CA\"\n" + 
"        }\n" + 
"    },\n" + 
"    {\n" + 
"        \"userId\": \"1234567\",\n" + 
"        \"title\": \"2012 Toyota Camry\",\n" + 
"        \"description\": \"Black, 42k miles. first owner.\",\n" + 
"        \"contact\": \"650-123-4567\",\n" + 
"        \"price\": \"15000\",\n" + 
"        \"category\": \"Cars & Trucks\",\n" + 
"        \"createdAt\": 1404198000000,\n" + 
"        \"location\": {\n" + 
"            \"latitude\": 37,\n" + 
"            \"longitude\": -122,\n" + 
"            \"address\": \"San Francisco, CA\"\n" + 
"        }\n" + 
"    },\n" + 
"    {\n" + 
"        \"userId\": \"1234567\",\n" + 
"        \"title\": \"Thomas Train Set\",\n" + 
"        \"description\": \"Brand new set.\",\n" + 
"        \"contact\": \"650-123-4567\",\n" + 
"        \"price\": \"30\",\n" + 
"        \"category\": \"Toys & games\",\n" + 
"        \"createdAt\": 1404500625468,\n" + 
"        \"location\": {\n" + 
"            \"latitude\": 37,\n" + 
"            \"longitude\": -122,\n" + 
"            \"address\": \"San Mateo, CA\"\n" + 
"        }\n" + 
"    },\n" + 
"        {\n" + 
"        \"userId\": \"1234567\",\n" + 
"        \"title\": \"Thomas Train Set\",\n" + 
"        \"description\": \"Brand new set.\",\n" + 
"        \"contact\": \"650-123-4567\",\n" + 
"        \"price\": \"30\",\n" + 
"        \"category\": \"Toys & games\",\n" + 
"        \"createdAt\": 1404500625468,\n" + 
"        \"location\": {\n" + 
"            \"latitude\": 37,\n" + 
"            \"longitude\": -122,\n" + 
"            \"address\": \"San Mateo, CA\"\n" + 
"        }\n" + 
"    },\n" + 
"        {\n" + 
"        \"userId\": \"1234567\",\n" + 
"        \"title\": \"Thomas Train Set\",\n" + 
"        \"description\": \"Brand new set.\",\n" + 
"        \"contact\": \"650-123-4567\",\n" + 
"        \"price\": \"30\",\n" + 
"        \"category\": \"Toys & games\",\n" + 
"        \"createdAt\": 1404500625468,\n" + 
"        \"location\": {\n" + 
"            \"latitude\": 37,\n" + 
"            \"longitude\": -122,\n" + 
"            \"address\": \"San Mateo, CA\"\n" + 
"        }\n" + 
"    },\n" + 
"        {\n" + 
"        \"userId\": \"1234567\",\n" + 
"        \"title\": \"Thomas Train Set\",\n" + 
"        \"description\": \"Brand new set.\",\n" + 
"        \"contact\": \"650-123-4567\",\n" + 
"        \"price\": \"30\",\n" + 
"        \"category\": \"Toys & games\",\n" + 
"        \"createdAt\": 1404500625468,\n" + 
"        \"location\": {\n" + 
"            \"latitude\": 37,\n" + 
"            \"longitude\": -122,\n" + 
"            \"address\": \"San Mateo, CA\"\n" + 
"        }\n" + 
"    },\n" + 
"        {\n" + 
"        \"userId\": \"1234567\",\n" + 
"        \"title\": \"Thomas Train Set\",\n" + 
"        \"description\": \"Brand new set.\",\n" + 
"        \"contact\": \"650-123-4567\",\n" + 
"        \"price\": \"30\",\n" + 
"        \"category\": \"Toys & games\",\n" + 
"        \"createdAt\": 1404500625468,\n" + 
"        \"location\": {\n" + 
"            \"latitude\": 37,\n" + 
"            \"longitude\": -122,\n" + 
"            \"address\": \"San Mateo, CA\"\n" + 
"        }\n" + 
"    },\n" + 
"        {\n" + 
"        \"userId\": \"1234567\",\n" + 
"        \"title\": \"Thomas Train Set\",\n" + 
"        \"description\": \"Brand new set.\",\n" + 
"        \"contact\": \"650-123-4567\",\n" + 
"        \"price\": \"30\",\n" + 
"        \"category\": \"Toys & games\",\n" + 
"        \"createdAt\": 1404500625468,\n" + 
"        \"location\": {\n" + 
"            \"latitude\": 37,\n" + 
"            \"longitude\": -122,\n" + 
"            \"address\": \"San Mateo, CA\"\n" + 
"        }\n" + 
"    },\n" + 
"        {\n" + 
"        \"userId\": \"1234567\",\n" + 
"        \"title\": \"Thomas Train Set\",\n" + 
"        \"description\": \"Brand new set.\",\n" + 
"        \"contact\": \"650-123-4567\",\n" + 
"        \"price\": \"30\",\n" + 
"        \"category\": \"Toys & games\",\n" + 
"        \"createdAt\": 1404500625468,\n" + 
"        \"location\": {\n" + 
"            \"latitude\": 37,\n" + 
"            \"longitude\": -122,\n" + 
"            \"address\": \"San Mateo, CA\"\n" + 
"        }\n" + 
"    },\n" + 
"        {\n" + 
"        \"userId\": \"1234567\",\n" + 
"        \"title\": \"Thomas Train Set\",\n" + 
"        \"description\": \"Brand new set.\",\n" + 
"        \"contact\": \"650-123-4567\",\n" + 
"        \"price\": \"30\",\n" + 
"        \"category\": \"Toys & games\",\n" + 
"        \"createdAt\": 1404500625468,\n" + 
"        \"location\": {\n" + 
"            \"latitude\": 37,\n" + 
"            \"longitude\": -122,\n" + 
"            \"address\": \"San Mateo, CA\"\n" + 
"        }\n" + 
"    },\n" + 
"        {\n" + 
"        \"userId\": \"1234567\",\n" + 
"        \"title\": \"Thomas Train Set\",\n" + 
"        \"description\": \"Brand new set.\",\n" + 
"        \"contact\": \"650-123-4567\",\n" + 
"        \"price\": \"30\",\n" + 
"        \"category\": \"Toys & games\",\n" + 
"        \"createdAt\": 1404500625468,\n" + 
"        \"location\": {\n" + 
"            \"latitude\": 37,\n" + 
"            \"longitude\": -122,\n" + 
"            \"address\": \"San Mateo, CA\"\n" + 
"        }\n" + 
"    }\n" + 
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
