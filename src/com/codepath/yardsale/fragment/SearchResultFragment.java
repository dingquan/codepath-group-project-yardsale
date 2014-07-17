package com.codepath.yardsale.fragment;

import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codepath.yardsale.CreatePostActivity;
import com.codepath.yardsale.ManagePostsActivity;
import com.codepath.yardsale.SearchCriteriaActivity;
import com.codepath.yardsale.dao.PostDao;
import com.codepath.yardsale.model.GeoLocation;
import com.codepath.yardsale.model.Post;
import com.codepath.yardsale.model.SearchCriteria;
import com.codepath.yardsale.util.JsonUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;

public class SearchResultFragment extends BaseFragment implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {
	
	private static SearchResultFragment searchResultFragment;
	
	private static final int REQUEST_CODE_CREATE_POST = 1;
	private static final int REQUEST_CODE_SEARCH_CRITERIA = 2;

	private LocationClient locationClient;
	private Location lastKnownLocation;

	// newInstance constructor for creating fragment with arguments
	public static SearchResultFragment newInstance(int page, String title) {
		if (searchResultFragment == null){
			searchResultFragment = new SearchResultFragment();
			Bundle args = new Bundle();
			args.putInt("someInt", page);
			args.putString("someTitle", title);
			searchResultFragment.setArguments(args);
		}
		return searchResultFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		locationClient = new LocationClient(getActivity(), this, this);
	}

	@Override
	public void onStart() {
		super.onStart();
		// Connect the client.
		if (isGooglePlayServicesAvailable()) {
			locationClient.connect();
		}
		searchNearbyRecentPosts(lastKnownLocation);
	}

	@Override
	public void onStop() {
		// Disconnecting the client invalidates it.
		locationClient.disconnect();
		super.onStop();
	}



	private void searchNearbyRecentPosts(Location location) {
		SearchCriteria criteria = new SearchCriteria();
		if (location != null) {
			GeoLocation geoLocation = new GeoLocation();
			geoLocation.setLongitude(location.getLongitude());
			geoLocation.setLatitude(location.getLatitude());
			criteria.setLocation(geoLocation);
			Log.d("SearchResultActivity searchNearByPost", location.toString());
		} else {
			Log.d("SearchResultACtivity searchNearBypost", "location is null");
		}
		pbLoading.setVisibility(ProgressBar.VISIBLE);

		new SearchPostTask().execute(criteria);
	}

	public void onSearch(MenuItem mi) {
		// FragmentManager fm = getFragmentManager();
		// SearchCriteriaDialog diag = SearchCriteriaDialog.newInstance();
		// diag.show(fm, "fragment_search_criteria");
		Intent i = new Intent(getActivity(), SearchCriteriaActivity.class);
		startActivityForResult(i, REQUEST_CODE_SEARCH_CRITERIA);
	}

	public void onPost(MenuItem mi) {
		Intent i = new Intent(getActivity(), CreatePostActivity.class);
		if (lastKnownLocation != null) {
			GeoLocation geoLocation = new GeoLocation();
			geoLocation.setLatitude(lastKnownLocation.getLatitude());
			geoLocation.setLongitude(lastKnownLocation.getLongitude());
			i.putExtra("geo_location", JsonUtil.toJson(geoLocation));
		}
		startActivityForResult(i, REQUEST_CODE_CREATE_POST);
	}

	public void onManage(MenuItem mi) {
		Intent i = new Intent(getActivity(), ManagePostsActivity.class);
		startActivity(i);
	}

//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (requestCode == REQUEST_CODE_CREATE_POST) {
//			// nothing much to do here really.
//
//			// String postStr = data.getExtras().getString("post");
//			// Toast.makeText(this, "returned from create post" +
//			// postStr,Toast.LENGTH_SHORT).show();
//		} else if (requestCode == REQUEST_CODE_SEARCH_CRITERIA) {
//			if (data == null) {
//				return; // noop. user hit backbutton
//			}
//			String searchStr = data.getExtras().getString("search_criteria");
//			String city = data.getExtras().getString("city");
//			GeoLocation geoLocation = getGeoFromAddress(city);
//			SearchCriteria criteria = (SearchCriteria) JsonUtil.fromJson(
//					searchStr, SearchCriteria.class);
//			criteria.setLocation(geoLocation);
//			Log.d("DEBUG", JsonUtil.toJson(criteria));
//			List<Post> results = postDao.findPostsBySearchCriteria(criteria);
//			aPosts.clear();
//			aPosts.addAll(results);
//		} else if (requestCode == CONNECTION_FAILURE_RESOLUTION_REQUEST) {
//			if (resultCode == getActivity().RESULT_OK) {
//
//			}
//		}
//	}

	public void searchPostsByCriteria(SearchCriteria criteria){
		if (criteria == null)
			return;
		String nearCity = criteria.getNearCity();
		if (nearCity != null && !nearCity.isEmpty()){
			GeoLocation geoLocation = getGeoFromAddress(nearCity);
			criteria.setLocation(geoLocation);
		}
		
		Log.d("DEBUG", JsonUtil.toJson(criteria));
		PostDao postDao = PostDao.getInstance();
		List<Post> results = postDao.findPostsBySearchCriteria(criteria);
		aPosts.clear();
		aPosts.addAll(results);

	}
	
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

	// Define a DialogFragment that displays the error dialog
	public static class ErrorDialogFragment extends DialogFragment {
		// Global field to contain the error dialog
		private Dialog mDialog;

		// Default constructor. Sets the dialog field to null
		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}

		// Set the dialog to display
		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}

		// Return a Dialog to the DialogFragment.
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}
	}

	private boolean isGooglePlayServicesAvailable() {
		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			Log.d("Location Updates", "Google Play services is available.");
			// Continue
			return true;
			// Google Play services was not available for some reason
		} else {
			// Get the error code
			Log.e("ERROR", "failed to connect: " + resultCode);
			// Get the error dialog from Google Play services
			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);

			// // If Google Play services can provide an error dialog
			// if (errorDialog != null) {
			// // Create a new DialogFragment for the error dialog
			// ErrorDialogFragment errorFragment = new ErrorDialogFragment();
			// // Set the dialog in the DialogFragment
			// errorFragment.setDialog(errorDialog);
			// // Show the error dialog in the DialogFragment
			// errorFragment.show(getSupportFragmentManager(),
			// "Location Updates");
			// }
			return false;
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
		 * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
			/*
			 * If no resolution is available, display a dialog to the user with
			 * the error.
			 */
			// showErrorDialog(connectionResult.getErrorCode());
		}

	}

	@Override
	public void onConnected(Bundle dataBundle) {
		lastKnownLocation = locationClient.getLastLocation();
		if (lastKnownLocation != null) {
			Toast.makeText(
					getActivity(),
					"GPS location was found! ("
							+ lastKnownLocation.getLatitude() + ", "
							+ lastKnownLocation.getLongitude() + ")",
					Toast.LENGTH_SHORT).show();

		} else {
			Toast.makeText(getActivity(),
					"Current location was null, enable GPS on emulator!",
					Toast.LENGTH_SHORT).show();
		}

		// searchNearbyRecentPosts(lastKnownLocation);
	}

	@Override
	public void onDisconnected() {
		// Display the connection status
		Toast.makeText(getActivity(), "Disconnected. Please re-connect.",
				Toast.LENGTH_SHORT).show();

	}

	private class SearchPostTask extends
			AsyncTask<SearchCriteria, Void, List<Post>> {

		@Override
		protected void onPostExecute(List<Post> posts) {
			aPosts.addAll(posts);
			pbLoading.setVisibility(ProgressBar.INVISIBLE);
		}

		@Override
		protected List<Post> doInBackground(SearchCriteria... criterias) {
			PostDao postDao = PostDao.getInstance();
			List<Post> posts = postDao.findPostsBySearchCriteria(criterias[0]);
			return posts;
		}
	}
}
