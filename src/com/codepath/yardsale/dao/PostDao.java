package com.codepath.yardsale.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.codepath.yardsale.dao.parse.ParseImages;
import com.codepath.yardsale.dao.parse.ParsePost;
import com.codepath.yardsale.dao.parse.WishList;
import com.codepath.yardsale.model.Category;
import com.codepath.yardsale.model.GeoLocation;
import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.model.SearchCriteria;
import com.codepath.yardsale.model.WishItems;
import com.codepath.yardsale.util.JsonUtil;
import com.google.gson.JsonObject;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;
import com.parse.SaveCallback;

public class PostDao {
	private static PostDao postDao;
	public static PostDao getInstance(){
		if (postDao == null){
			postDao = new PostDao();
		}
		return postDao;
	}

	public List<Post> findPostsBySearchCriteria(SearchCriteria criteria){
		List<Post> posts = new ArrayList<Post>();
		ParseQuery<ParsePost> query = buildQuery(criteria);
		try {
			List<ParsePost> results = query.find();
			for (ParsePost parsePost : results){
				//System.out.println(parsePost.toString());
				Post post = parsePost.toPost();
				posts.add(post);
			}
		} catch (ParseException e) {
			Log.e("ERROR", "Parse Exceptoin", e);
		}
		return posts;
	}

	private ParseQuery<ParsePost> buildQuery(SearchCriteria c) {
		ParseQuery<ParsePost> query = ParseQuery.getQuery(ParsePost.class);
		if (c != null) {
			String keyword = c.getKeyword();
			if (keyword != null && !keyword.isEmpty()) {
				// String[] keywords = c.getKeyword().split("\\s+");
				// query = query.whereContainedIn("description",
				// Arrays.asList(keywords));
				ParseQuery<ParsePost> titleQuery = ParseQuery.getQuery(
						ParsePost.class).whereContains("title", keyword);
				ParseQuery<ParsePost> descQuery = ParseQuery.getQuery(
						ParsePost.class).whereContains("description", keyword);
				List<ParseQuery<ParsePost>> queries = new ArrayList<ParseQuery<ParsePost>>();
				queries.add(titleQuery);
				queries.add(descQuery);
				query = ParseQuery.or(queries);
			}
			if(c.getTitle() != null){
				query = query.whereEqualTo("title", c.getTitle());
			}
			if(c.getDescription() !=null){
				query = query.whereEqualTo("description", c.getDescription());
			}
			if (c.getCategory() != null) {
				query = query.whereEqualTo("category", c.getCategory().name());
			}
			if (c.getMaxPrice() != null) {
				query = query.whereLessThan("price", c.getMaxPrice());
			}
			if (c.getMinPrice() != null) {
				query = query.whereGreaterThan("price", c.getMinPrice());
			}
			String userId = c.getUserId();
			if (userId != null && !userId.isEmpty()) {
				Log.d("PostDao buildquery", "userId is not null");
				query = query.whereEqualTo("userId", userId);
			}
			GeoLocation location = c.getLocation();
			// Log.d("PostDao buildquery",geoLocation.toString());
			if (location != null) {
				Log.d("PostDao buildquery", "geoLocation is not null");
				ParseGeoPoint geoPoint = new ParseGeoPoint(
						location.getLatitude(), location.getLongitude());
				query = query.whereWithinMiles("location", geoPoint, 10);
			} else {
				Log.d("Postdao Buildquery", "geoLocation is null");
			}
			query.addDescendingOrder("createdAt");
			query.include("contact");
		}
		return query;
	}

	public void savePosts(List<Post> posts){
		for (Post post: posts){
			savePost(post);
		}
	}

	public void savePost(Post post) {
		ParsePost parsePost = new ParsePost(post);
		parsePost.saveInBackground();		
		sendNotifications(post);
	}

	private void sendNotifications(Post post) {

		for (String keyword : post.getTitle().split("\\s+")){
			JSONObject data = new JSONObject();
			try {
				data.put("post", JsonUtil.toJson(post));
				data.put("alert", "Your wish list item " + keyword + " has just arrived !!");
				data.put("title", "Wish granted!");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ParsePush push = new ParsePush();
			push.setChannel(keyword);
			push.setData(data);
			push.sendInBackground();
		}
	}

	public void saveWish(WishItems item) {
		WishList wishItem = new WishList(item);
		wishItem.saveInBackground();

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
		} catch (ParseException e) {
			Log.e("ERROR", "Parse Exception", e);
		}
		return null;
	}

	public void deletePost(Post post){
		ParsePost parsePost = new ParsePost(post);
		parsePost.deleteInBackground();
	}

	public List<Post> getDummyPosts(){
		String postsData = 
				"[\n" + 
						"    {\n" + 
						"        \"userId\": \"1234567\",\n" + 
						"        \"title\": \"Thomas Train Set\",\n" + 
						"        \"description\": \"Choo choo! All aboard! This imaginative activity table with train set makes a fantastic gift for your little conductor. Compatible with Chuggington and Thomas & Friends train sets, this wooden activity table comes with a handy storage bin to collect all the bits and pieces after playtime is over — perfect for busy moms! Since the activity table is conveniently double-sided, kids can build their train sets on one side, and use the other side to color and play with arts and crafts. Easy to assemble, the 45-piece wooden train and activity table offers a great way to keep playtime centrally placed in one easy-to-clean location. The activity table with train set includes three trains, numerous buildings, trees, one police car and one ambulance. The train tracks are adjustable.\",\n" + 
						"        \"contact\": {\n" + 
						"            \"phone\": \"650-123-4567\",\n" + 
						"            \"address\": \"San Mateo, CA\"\n" + 
						"        },\n" + 
						"        \"price\": \"30\",\n" + 
						"        \"category\": \"TOYS_GAMES\",\n" + 
						"        \"status\": \"Active\",\n" + 
						"        \"location\": {\n" + 
						"            \"latitude\": 37,\n" + 
						"            \"longitude\": -122\n" + 
						"        },\n" + 
						"        \"createdAt\": 1404198000000\n" + 
						"    },\n" + 
						"    {\n" + 
						"        \"userId\": \"1234568\",\n" + 
						"        \"title\": \"2012 Toyota Camry\",\n" + 
						"        \"description\": \"I am selling my grandmother's 2005 Toyota Camry. My grandmother is the original owner of the vehicle and has being driving it since she bought it in 2005. The Camry currently has 169,000 miles. It has a clean title and has never been in an accident. The car has been well maintained as my grandmother had it regularly serviced at her local mechanic. I have the past two years worth of service records to show some of the work and maintenance that has been done. The car has also been serviced recently to ensure that it has no problems and is ready to sell. I have a copy of the carfax vehicle history report if you'd like to see it. The car has passed smog and I have the title in hand so it's ready to be sold. If you have any questions or would like to make an appointment to see the vehicle, please contact me at by call or text. If I do not answer, feel free to leave a message and I will get back to you. Thanks for looking.\",\n" + 
						"        \"contact\": {\n" + 
						"            \"phone\": \"650-123-4567\",\n" + 
						"            \"address\": \"San Francisco, CA\"\n" + 
						"        },\n" + 
						"        \"price\": \"15000\",\n" + 
						"        \"category\": \"CARS\",\n" + 
						"        \"status\": \"Active\",\n" + 
						"        \"location\": {\n" + 
						"            \"latitude\": 37,\n" + 
						"            \"longitude\": -122\n" + 
						"        },\n" + 
						"        \"createdAt\": 1404284400000\n" + 
						"    },\n" + 
						"    {\n" + 
						"        \"userId\": \"1234569\",\n" + 
						"        \"title\": \"***Whirlpool commercial Washer & Electric Dryer with hoses***\",\n" + 
						"        \"description\": \"Selling a perfect working Newer Style Whirlpool Commercial Washer & Electric dryer set both are Super capacity. Clean and ready to go. In immaculate condition only 16 months old. Still has plastic on it. with all hoses still hooked up in so you can see they Function properly and never been worked on.\\nAsking $380 for the set will only sell as a set only\",\n" + 
						"        \"contact\": {\n" + 
						"            \"phone\": \"650-123-4567\",\n" + 
						"            \"address\": \"San Mateo, CA\"\n" + 
						"        },\n" + 
						"        \"price\": \"380\",\n" + 
						"        \"category\": \"APPLIANCES\",\n" + 
						"        \"status\": \"Active\",\n" + 
						"        \"location\": {\n" + 
						"            \"latitude\": 37,\n" + 
						"            \"longitude\": -122\n" + 
						"        },\n" + 
						"        \"createdAt\": 1404198000000\n" + 
						"    },\n" + 
						"    {\n" + 
						"        \"userId\": \"1234570\",\n" + 
						"        \"title\": \"Apple 2008 BLACK Macbook-Newer 500GB@7K, NEW(Battery, OEM Chgr)\",\n" + 
						"        \"description\": \"This is a black Macbook 2008 2.1(Penryn 45nm)GHz 13.3\\\" Macbook with 2Gb Ram, with a NEW 5500mah Battery(w/3cycles), and a New Apple OEM L-Magsafe Charger. This one has the faster Bus, Louder Sound System, & Better Video & can utilize up to 6GB of Ram. Do Not confuse this with the older 2.16GHz(Late 2006-Mid 2007) models.\",\n" + 
						"        \"contact\": {\n" + 
						"            \"phone\": \"650-123-4567\",\n" + 
						"            \"address\": \"San Mateo, CA\"\n" + 
						"        },\n" + 
						"        \"price\": \"300\",\n" + 
						"        \"category\": \"COMPUTERS\",\n" + 
						"        \"status\": \"Active\",\n" + 
						"        \"location\": {\n" + 
						"            \"latitude\": 37,\n" + 
						"            \"longitude\": -122\n" + 
						"        },\n" + 
						"        \"createdAt\": 1404198000000\n" + 
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

	public ArrayList<WishItems> getWishListItems(String userId) throws ParseException {
		ArrayList<WishList>wishList = new ArrayList<WishList>();
		ArrayList<WishItems> result = new ArrayList<WishItems>();
		ParseQuery<WishList> query = ParseQuery.getQuery(WishList.class);
		query.whereEqualTo("userId", userId);
		wishList = (ArrayList<WishList>) query.find();

		for(WishList eachWish : wishList){
			System.out.println("PostDao eachWish-->>" + eachWish.getItem() + eachWish.getObjectId());
			WishItems eachItem = eachWish.toWishItems();
			System.out.println("PostDao getWishlistItems eachitem-->> "+ eachItem.getId());
			result.add(eachItem);
		}
		return result;
	}

	public void deleteWish(ArrayList<WishItems> itemsTodelete) {
		for(WishItems eachitem : itemsTodelete){
			System.out.println("PostDao Item to delete-->"+eachitem.getItem()+eachitem.getId());
			WishList item = new WishList(eachitem);
			System.out.println("postDao wishlist item to delete"+ item.getItem()+item.getObjectId());
			item.deleteInBackground(new DeleteCallback() {

				@Override
				public void done(ParseException arg0) {
					System.out.println(arg0);
				}
			});
		}

	}
}
