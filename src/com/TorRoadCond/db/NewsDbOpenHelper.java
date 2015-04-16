package com.TorRoadCond.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NewsDbOpenHelper extends SQLiteOpenHelper{
	
	private static final String LOGTAG = "NEWSARTICLE";
	
	private static final String DATABASE_NAME = "news.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_NEWS = "news";
	public static final String COLUMN_ID = "newsId";
	public static final String COLUMN_MAIN_ROAD = "main_road";
	public static final String COLUMN_AT_ROAD = "at_road";
	public static final String COLUMN_FROM_ROAD = "from_road";
	public static final String COLUMN_DISTRICT = "district";
	public static final String COLUMN_ROAD_TYPE = "road_type";
	public static final String COLUMN_LATITUDE = "latitude";
	public static final String COLUMN_LONGITUDE = "longitude";
	public static final String COLUMN_START_DATE_TIME = "start_date_time";
	public static final String COLUMN_END_DATE_TIME = "end_date_time";
	public static final String COLUMN_NOTE = "note";
	public static final String COLUMN_DESCRIPTION = "description";
	
	//sql statement used to create database tables
	//java version of the create database schema 
	private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NEWS + "  ("  
			 + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
             + COLUMN_MAIN_ROAD + " TEXT," 
             + COLUMN_AT_ROAD + " TEXT," 
             + COLUMN_FROM_ROAD + " TEXT," 
             + COLUMN_DISTRICT + " TEXT,"
             + COLUMN_ROAD_TYPE + " TEXT," 
             + COLUMN_LATITUDE + " TEXT," 
             + COLUMN_LONGITUDE + " TEXT,"
             + COLUMN_START_DATE_TIME + " TEXT,"
             + COLUMN_END_DATE_TIME + " TEXT,"
             + COLUMN_DESCRIPTION + " TEXT," 
             + COLUMN_NOTE + " TEXT" +
             
    ")";
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		//add code to create the table and also the data to the table
		db.execSQL(TABLE_CREATE);
		Log.i(LOGTAG, "Table has been created");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
		onCreate(db);
	}
	
	public NewsDbOpenHelper(Context context) {
		//what does the super method do?
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
}
