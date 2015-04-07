package com.williamzhao;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class NewsDetail extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); //you must add this before setContentView
		Log.i("tor", "DetailsPage");
		
		String issueId = getIntent().getStringExtra("key");
		String description = getIntent().getStringExtra("description");
		setContentView(R.layout.activity_info);
		
		TextView des = (TextView) findViewById(R.id.description);
		des.setText(description);
	}
}
