package com.codepath.yardsale.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codepath.yardsale.dao.parse.ParsePost;
import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.model.SearchCriteria;
import com.codepath.yardsale.util.JsonUtil;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class PostDao {
	public List<Post> findPostsBySearchCriteria(SearchCriteria criteria){
		List<Post> posts = new ArrayList<Post>();
		ParseQuery<ParsePost> query = buildQuery(criteria);
		try {
			List<ParsePost> results = query.find();
			for (ParsePost parsePost : results){
				posts.add(parsePost.toPost());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return posts;
	}
	
	private ParseQuery<ParsePost> buildQuery(SearchCriteria c) {
		ParseQuery<ParsePost> query = ParseQuery.getQuery(ParsePost.class);
		
		String keyword = c.getKeyword();
		if (keyword != null && !keyword.isEmpty()){
//			String[] keywords = c.getKeyword().split("\\s+");
//			query = query.whereContainedIn("description", Arrays.asList(keywords));
			ParseQuery<ParsePost> titleQuery = ParseQuery.getQuery(ParsePost.class).whereContains("title", keyword);
			ParseQuery<ParsePost> descQuery = ParseQuery.getQuery(ParsePost.class).whereContains("description", keyword);
			List<ParseQuery<ParsePost>> queries = new ArrayList<ParseQuery<ParsePost>>();
			queries.add(titleQuery);
			queries.add(descQuery);
			query = ParseQuery.or(queries);
		}
		if (c.getCategory() != null){
			query = query.whereEqualTo("category", c.getCategory().name());
		}
		if (c.getMaxPrice() != null){
			query = query.whereLessThan("price", c.getMaxPrice());
		}
		if (c.getMinPrice() != null){
			query = query.whereGreaterThan("price", c.getMinPrice());
		}
		String userId = c.getUserId();
		if (userId != null && !userId.isEmpty()){
			query = query.whereEqualTo("userId", userId);
		}
		query.include("contact");
		query.include("location");
		return query;
	}

	public void savePosts(List<Post> posts){
		for (Post post: posts){
			savePost(post);
		}
	}
	
	public void savePost(Post post){
		ParsePost parsePost = new ParsePost(post);
		parsePost.saveInBackground();
	}

	/**
	 * find a Post by its unique id
	 * @param uid
	 * @return
	 */
	public Post getPostById(String id){
		ParseQuery<ParsePost> query = ParseQuery.getQuery(ParsePost.class);
		try {
			ParsePost parsePost = query.get(id);
			return parsePost.toPost();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	public void deltePost(Post post){
		ParsePost parsePost = new ParsePost(post);
		parsePost.deleteInBackground();
	}
}
