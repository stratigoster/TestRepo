//test
package com.TorRoadCond;

import com.williamzhao.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class NewsDetail extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); // you must add this before
											// setContentView
		Log.i("tor", "DetailsPage");

		// get detail from intent

		String issueId = getIntent().getStringExtra("key");
		String description = getIntent().getStringExtra("description");
		String issueType = getIntent().getStringExtra("issueType");
		// long id;
		String longitude = getIntent().getStringExtra("longitude");
		String latitude = getIntent().getStringExtra("latitude");

		String mainRoad = getIntent().getStringExtra("mainRoad");
		String fromRoad = getIntent().getStringExtra("fromRoad");
		String toRoad = getIntent().getStringExtra("toRoad");
		String atRoad = getIntent().getStringExtra("atRoad");
		String district = getIntent().getStringExtra("district");
		String roadType = getIntent().getStringExtra("roadType");
		// boolean isEmergency = getIntent().getStringExtra("isEmergency");
		String startLocal = getIntent().getStringExtra("startLocal");
		String endLocal = getIntent().getStringExtra("endLocal");

		setContentView(R.layout.detail);

		TextView des = (TextView) findViewById(R.id.description);
		des.setText(description);

		TextView issueTypeLayout = (TextView) findViewById(R.id.issueType);
		issueTypeLayout.setText(issueType);

		TextView mainRoadLayout = (TextView) findViewById(R.id.mainRoad);
		mainRoadLayout.setText(mainRoad);

		TextView fromRoadLayout = (TextView) findViewById(R.id.fromRoad);
		fromRoadLayout.setText(fromRoad);

		TextView atRoadLayout = (TextView) findViewById(R.id.atRoad);
		atRoadLayout.setText(atRoad);

		TextView districtLayout = (TextView) findViewById(R.id.district);
		districtLayout.setText(district);

		TextView roadTypeLayout = (TextView) findViewById(R.id.roadType);
		roadTypeLayout.setText(roadType);

		TextView startLocalLayout = (TextView) findViewById(R.id.startLocal);
		startLocalLayout.setText(startLocal);

		TextView endLocalLayout = (TextView) findViewById(R.id.endLocal);
		endLocalLayout.setText(endLocal);
		
		Log.i("testLatitude", latitude);
		Log.i("testLongitude", longitude);
		
		String labelLocation = "Jorgesys @ Bucharest";
		
		String urlAddress = "http://maps.google.com/maps?q="+ latitude  +"," + longitude + "&iwloc=A&hl=es";  
		Log.i("testURLAddress", urlAddress);
		
	    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlAddress));
		intent.setData(Uri.parse(urlAddress));
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}
	    
	    /*
		Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "(NamedMarker)");

		"geo:"+latitude+","+longitude+"?q=("+head+")@"+lat+","+lon
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(gmmIntentUri);
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}
		*/
	}

}
