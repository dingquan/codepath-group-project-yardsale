package com.codepath.yardsale.dao;

import java.util.ArrayList;
import java.util.List;

import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.model.SearchCriteria;

public class PostDao {
	public List<Post> findPostsBySearchCriteria(SearchCriteria criteria){
		List<Post> posts = new ArrayList<Post>();
		
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
}
