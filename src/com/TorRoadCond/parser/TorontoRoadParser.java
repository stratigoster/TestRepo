package com.TorRoadCond.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.TorRoadCond.EmergencyNews;
import com.TorRoadCond.ParserFactory;

public class TorontoRoadParser {
	public static List<EmergencyNews> parseFeed(InputStream is) throws IOException {
		List<EmergencyNews> newsItems = new ArrayList<EmergencyNews>();
		try {
			final XmlPullParser parser = ParserFactory.getParserFactory().newPullParser(); // why use a ParserFactory
			parser.setInput(is, null);
			String issueType = null;
			String issueId = null;
			String longitude = null;
			String latitude = null;
			String mainRoad = null;
			String fromRoad = null;
			String toRoad = null;
			String description = null;
			String startLocal = null;
			String endLocal = null;
			String atRoad = null;
			String urgent = null;
			String district = null;
			String roadType = null;
			boolean isEmergency = false;
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				final String tagName = parser.getName();
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if (tagName.equals("id")) {
						issueId = parser.nextText();
						Log.i("tor", "Issues" + issueId);
					} else if (tagName.equals("description")) {
						description = parser.nextText();
					} else if (tagName.equals("note")) {
						issueType = parser.nextText();
						Log.i("tor", "IssueType" + issueType);
					} else if (tagName.equals("to_road")) {
						toRoad = parser.nextText();
						Log.i("tor", "ToRoad" + toRoad);
					} else if (tagName.equals("from_road")) {
						fromRoad = parser.nextText();
						Log.i("tor", "fromRoad" + fromRoad);
					} else if (tagName.equals("main_road")) {
						mainRoad = parser.nextText();
						Log.i("tor", "MainRoad" + mainRoad);
					} else if (tagName.equals("start_date_time")) {
						startLocal = parser.nextText();
					} else if (tagName.equals("end_date_time")) {
						endLocal = parser.nextText();
					} else if (tagName.equals("longitude")) {
						longitude = parser.nextText();
					} else if (tagName.equals("latitude")) {
						latitude = parser.nextText();
						Log.i("tor", "Latitude" + latitude);
					} else if (tagName.equals("note")) {
						issueType = parser.nextText();
						Log.i("tor", "Note" + issueType);
					} else if (tagName.equals("district")) {
						district = parser.nextText();
						Log.i("tor", "District" + district);
					} else if (tagName.equals("at_road")) {
						atRoad = parser.nextText();
						Log.i("tor", "AtRoad" + atRoad);
					} else if (tagName.equals("urgent")) {
						urgent = parser.nextText();
						if (!urgent.equals("Planned")) {
							isEmergency = true;
						}
						Log.i("tor", "Urgent" + urgent);
					} else if (tagName.equals("road_type")) {
						roadType = parser.nextText();
						Log.i("tor", "RoadType" + roadType);
					}
					break;
				case XmlPullParser.END_TAG:
					if (tagName.equals("record")) {
						Log.i("tor", "longitude " + longitude);
						Log.i("tor", "latitude " + latitude);
						//add this to the database
						newsItems.add(new EmergencyNews(issueType, issueId,
								longitude, latitude, mainRoad, fromRoad,
								toRoad, atRoad, description, startLocal,
								endLocal, roadType, district, isEmergency, 0));
						//createData
						Log.i("tor", "Successfully created a new object");
					} else {
					}
					break;
				}
				eventType = parser.next();
			}
		} catch (final XmlPullParserException xppe) {
			Log.e("tor", "pullParser " + xppe.toString());
		} catch (final IOException ioe) {
			Log.e("tor", "ioException: " + ioe.toString());
		} catch (final Exception e) {
			Log.e("tor", "Exception: " + e.toString());
		}
		Log.i("tor", "finished parsing");
		return newsItems;
	}
}
