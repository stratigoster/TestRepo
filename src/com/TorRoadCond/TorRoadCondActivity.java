package com.TorRoadCond;

import java.io.IOException;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.TorRoadCond.db.NewsDataSource;
import com.williamzhao.R;

//http://opendata.toronto.ca/transportation/front.yard.parking/frontyardparking.xml

public class TorRoadCondActivity extends ListActivity {
	private static String currentRoadRestrictions = "http://app.toronto.ca/opendata/cart/current_road_restrictions.xml?v=1.00";
	private static String futureRoadRestrictions = "http://app.toronto.ca/opendata/cart/future_road_restrictions.xml?v=1.00";
	private static String emergencyRoadRestrictions = "http://app.toronto.ca/opendata/cart/emergency_road_restrictions.xml?v=1.00";
	
	ProgressBar pb;
	List<EmergencyNews> display;
	DisplayAdapter adapter;
	
	NewsDataSource dataSource;

	/* Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("tor", "OnCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);
		
		dataSource = new NewsDataSource(this);
		dataSource.open();
		
		//if data does not exist in database, create a new database
		display = dataSource.findAll();
		if (display.size() == 0) {
			requestData();
		}    
		else {
			display = dataSource.findAll();
			updateDisplay();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.data_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.new_game) {
			if (isOnline()) {
    			requestData();
    		}
    		else {
    			Toast.makeText(this, "NETWORK ISN'T AVAILABLE", Toast.LENGTH_LONG).show();
    		}
		}
		else if (item.getItemId() == R.id.filter_name) {
			display = dataSource.findFiltered("main_road like \"%s\" ");
			updateDisplay();
		}
		return false;
	}
	
	private void requestData() {
		RoadConditionTask obtainTask = new RoadConditionTask();
		obtainTask.execute(futureRoadRestrictions);
	}
	
	protected boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		EmergencyNews note = display.get(position);
		
		Intent intent = new Intent(this, NewsDetail.class);
		
		intent.putExtra("issueId", note.getIssueId());
		intent.putExtra("description", note.getDescription());
		intent.putExtra("atRoad", note.getAtRoad());
		intent.putExtra("district", note.getDistrict());
		intent.putExtra("longitude", note.getLongitude());
		intent.putExtra("latitude", note.getLatitude());
		
		intent.putExtra("startLocal", note.getStartLocal());
		intent.putExtra("endLocal", note.getEndLocal());
		
		intent.putExtra("fromRoad", note.getFromRoad());
		intent.putExtra("toRoad", note.getToRoad());
		
		intent.putExtra("issueType", note.getIssueType());
		intent.putExtra("mainRoad", note.getMainRoad());
		intent.putExtra("roadType", note.getRoadType());
		
		startActivity(intent);
	}
	
	protected void updateDisplay() {
		if (adapter == null) {
			adapter = new DisplayAdapter(this, display);
		}
		else {
			adapter.notifyDataSetChanged();
		}
		setListAdapter(adapter);
	}

	private class RoadConditionTask extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
			pb.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				//how o turn string url into input stream
				//TorontoRoadParser.parseFeed(params[0]);
				display = EmergencyNews.parseFeed(params[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			pb.setVisibility(View.INVISIBLE);
			updateDisplay();
			for (EmergencyNews em: display) {
				dataSource.create(em);
			}
		}

		@Override
		protected void onProgressUpdate(String... values) {
		}
	
	}
	/*
	protected String doInBackground(URL... urls) {
		try {
			HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			@Override
				public void initialize(HttpRequest request) {
					request.setParser(new JsonObjectParser(JSON_FACTORY));
				}
			});

			GenericUrl url = new GenericUrl("http://maps.googleapis.com/maps/api/directions/json");
			url.put("origin", "Chicago,IL");
			url.put("destination", "Los Angeles,CA");
			url.put("sensor",false);

			HttpRequest request = requestFactory.buildGetRequest(url);
			HttpResponse httpResponse = request.execute();
			DirectionsResult directionsResult = httpResponse.parseAs(DirectionsResult.class);
			String encodedPoints = directionsResult.routes.get(0).overviewPolyLine.points;
			latLngs = PolyUtil.decode(encodedPoints);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
		}

		protected void onProgressUpdate(Integer... progress) {
		}

		protected void onPostExecute(String result) {
			clearMarkers();
			addMarkersToMap(latLngs);
		}
	}
	*/	
	
	@Override 
	protected void onPause() {
		super.onPause();
		dataSource.close();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		dataSource.open();
	}
		
}