package com.williamzhao;

import java.io.IOException;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

//http://opendata.toronto.ca/transportation/front.yard.parking/frontyardparking.xml

public class TorRoadCondActivity extends ListActivity {
	private static String currentRoadRestrictions = "http://app.toronto.ca/opendata/cart/current_road_restrictions.xml?v=1.00";
	private static String futureRoadRestrictions = "http://app.toronto.ca/opendata/cart/future_road_restrictions.xml?v=1.00";
	private static String emergencyRoadRestrictions = "http://app.toronto.ca/opendata/cart/emergency_road_restrictions.xml?v=1.00";
	
	ProgressBar pb;
	List<EmergencyNews> display;
	DisplayAdapter adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("tor", "OnCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//TODO: test what happens when you rotate the phone while the page is loading. 
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);
		
		String url = futureRoadRestrictions;
		
		RoadConditionTask obtainTask = new RoadConditionTask();
		obtainTask.execute(url);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		EmergencyNews note = display.get(position);
		Intent intent = new Intent(this, NewsDetail.class);
		intent.putExtra("key", note.getIssueId());
		intent.putExtra("description", note.getDescription());
		startActivityForResult(intent, 1001);
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
		}

		@Override
		protected void onProgressUpdate(String... values) {
		}
	}
}