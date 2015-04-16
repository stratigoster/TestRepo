package com.TorRoadCond.db;

import java.util.ArrayList;
import java.util.List;

import com.TorRoadCond.EmergencyNews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

//add a separate wrapper for datbase
//add log statements for testing without using adb shell
public class NewsDataSource {
	public static final String LOGTAG="TOR";
	
	NewsDbOpenHelper dbhelper;
	SQLiteDatabase database;
	
	//a string of all columns
	private static final String[] allColumns = { 
		NewsDbOpenHelper.COLUMN_ID, 
		NewsDbOpenHelper.COLUMN_AT_ROAD,
		NewsDbOpenHelper.COLUMN_DESCRIPTION,
		NewsDbOpenHelper.COLUMN_DISTRICT,
		NewsDbOpenHelper.COLUMN_END_DATE_TIME,
		NewsDbOpenHelper.COLUMN_FROM_ROAD,
		NewsDbOpenHelper.COLUMN_LATITUDE,
		NewsDbOpenHelper.COLUMN_LONGITUDE,
		NewsDbOpenHelper.COLUMN_MAIN_ROAD,
		NewsDbOpenHelper.COLUMN_NOTE,
		NewsDbOpenHelper.COLUMN_ROAD_TYPE,
		NewsDbOpenHelper.COLUMN_START_DATE_TIME
	};
	
	public NewsDataSource(Context context) {
		dbhelper = new NewsDbOpenHelper(context);
	}
	
	public void open() {
		Log.i(LOGTAG, "Database opened");
		database = dbhelper.getWritableDatabase();
	}
	
	public void close() {
		Log.i(LOGTAG, "Database closed");
		dbhelper.close();
	}
	
	public EmergencyNews create(EmergencyNews news) {
		ContentValues values = new ContentValues();
		
		values.put(NewsDbOpenHelper.COLUMN_ID, news.getIssueId());
		values.put(NewsDbOpenHelper.COLUMN_AT_ROAD, news.getAtRoad());
		values.put(NewsDbOpenHelper.COLUMN_DESCRIPTION, news.getDescription());
		values.put(NewsDbOpenHelper.COLUMN_DISTRICT, news.getDistrict());
		values.put(NewsDbOpenHelper.COLUMN_END_DATE_TIME, news.getEndLocal());
		values.put(NewsDbOpenHelper.COLUMN_FROM_ROAD, news.getFromRoad());
		values.put(NewsDbOpenHelper.COLUMN_LATITUDE, news.getLatitude());
		values.put(NewsDbOpenHelper.COLUMN_LONGITUDE, news.getLongitude());
		values.put(NewsDbOpenHelper.COLUMN_MAIN_ROAD, news.getMainRoad());
		values.put(NewsDbOpenHelper.COLUMN_ROAD_TYPE, news.getRoadType());
		values.put(NewsDbOpenHelper.COLUMN_START_DATE_TIME, news.getStartLocal());
		
		// this is the sql name
		long insertId = database.insert(NewsDbOpenHelper.TABLE_NEWS, null, values);
		Log.i("tor", "" + insertId);
		//news.setIssueId(insertId);
		
		return news;
	}
	
	public List<EmergencyNews> findAll() {
		List<EmergencyNews> news = new ArrayList<EmergencyNews>();
		//getting a join uses rawQuery
		//Cursor is like JDBC ResultSet class. Reference to result set query. 
		
		Cursor cursor = database.query(NewsDbOpenHelper.TABLE_NEWS, allColumns, null, null, null, null, null);
		
		Log.i(LOGTAG, "Returned: " + cursor.getCount() + " rows");
		//ResultSet starts before the first row. 
		if (cursor.getCount() > 0) {
			while(cursor.moveToNext()) { //start from the first row
				EmergencyNews emergency = new EmergencyNews();
				emergency.setId(cursor.getLong(cursor.getColumnIndex(NewsDbOpenHelper.COLUMN_ID)));
				emergency.setDescription(cursor.getString(cursor.getColumnIndex(NewsDbOpenHelper.COLUMN_DESCRIPTION)));
				emergency.setAtRoad(cursor.getString(cursor.getColumnIndex(NewsDbOpenHelper.COLUMN_AT_ROAD)));
				emergency.setDistrict(cursor.getString(cursor.getColumnIndex(NewsDbOpenHelper.COLUMN_DISTRICT)));
				emergency.setEndLocal(cursor.getString(cursor.getColumnIndex(NewsDbOpenHelper.COLUMN_END_DATE_TIME)));
				emergency.setFromRoad(cursor.getString(cursor.getColumnIndex(NewsDbOpenHelper.COLUMN_FROM_ROAD)));
				emergency.setLatitude(cursor.getString(cursor.getColumnIndex(NewsDbOpenHelper.COLUMN_LATITUDE)));
				emergency.setLongitude(cursor.getString(cursor.getColumnIndex(NewsDbOpenHelper.COLUMN_LONGITUDE)));
				emergency.setMainRoad(cursor.getString(cursor.getColumnIndex(NewsDbOpenHelper.COLUMN_MAIN_ROAD)));
				//emergency.se(cursor.getString(cursor.getColumnIndex(NewsDbOpenHelper.COLUMN_NOTE)));
				emergency.setRoadType(cursor.getString(cursor.getColumnIndex(NewsDbOpenHelper.COLUMN_ROAD_TYPE)));
				emergency.setStartLocal(cursor.getString(cursor.getColumnIndex(NewsDbOpenHelper.COLUMN_START_DATE_TIME)));
				news.add(emergency);
			}
		}
		return news;
	}
	
}
