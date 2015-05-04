package com.TorRoadCond;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

public class EmergencyNews {
	// NewsArticle
	private String issueType; // corresponds to note
	private long id;
	private String issueId;
	private String longitude;
	private String latitude;
	private String mainRoad;
	private String fromRoad;
	private String toRoad;
	private String atRoad; // either at_road empty or from_road to_road empty
	private String district;
	private String roadType;
	private boolean isEmergency; // corresponds to planned or not
	private String description;
	private String startLocal;
	private String endLocal;
	
	private static int index;
	
	public EmergencyNews() {
		
	}

	public EmergencyNews(String issueType, String issueId, String longitude,
			String latitude, String mainRoad, String fromRoad, String toRoad,
			String atRoad, String description, String startLocal,
			String endLocal, String roadType, String district,
			boolean isEmergency, long id) {
		checkIfNull(longitude); // also need to check if empty "" returned
		checkIfNull(latitude);
		checkIfNull(mainRoad);
		checkIfNull(fromRoad);
		checkIfNull(toRoad);
		checkIfNull(description);
		checkIfNull(startLocal);
		checkIfNull(endLocal);
		this.issueType = issueType;
		this.issueId = issueId;
		this.longitude = longitude;
		this.latitude = latitude;
		this.mainRoad = mainRoad;
		this.fromRoad = fromRoad;
		this.toRoad = toRoad;
		this.description = description;
		this.startLocal = startLocal;
		this.endLocal = endLocal;
		this.setAtRoad(atRoad);
		this.setDistrict(district);
		this.setRoadType(roadType);
		this.setEmergency(isEmergency);
	}
	
	public long setId(long id) {
		return this.id;
	}
	
	public long getId() {
		return id;
	}
	
	private void checkIfNull(String string) {
		if (string == null) {
			string = "NO DATA RETURNED";
		}
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getMainRoad() {
		return mainRoad;
	}

	public void setMainRoad(String mainRoad) {
		this.mainRoad = mainRoad;
	}

	public String getFromRoad() {
		return fromRoad;
	}

	public void setFromRoad(String fromRoad) {
		this.fromRoad = fromRoad;
	}

	public String getToRoad() {
		return toRoad;
	}

	public void setToRoad(String toRoad) {
		this.toRoad = toRoad;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartLocal() {
		return startLocal;
	}

	public void setStartLocal(String startLocal) {
		this.startLocal = startLocal;
	}

	public String getEndLocal() {
		return endLocal;
	}

	public void setEndLocal(String endLocal) {
		this.endLocal = endLocal;
	}
	
	public void setIssueId(String id) {
		this.issueId = id;
	}

	public String getIssueType() {
		return issueType;
	}

	public String getIssueId() {
		return issueId;
	}

	public String getAtRoad() {
		return atRoad;
	}

	public void setAtRoad(String atRoad) {
		this.atRoad = atRoad;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getRoadType() {
		return roadType;
	}

	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}

	public boolean isEmergency() {
		return isEmergency;
	}

	public void setEmergency(boolean isEmergency) {
		this.isEmergency = isEmergency;
	}

	public static InputStream getInputStreamFromUrl(String url) {
		InputStream content = null;
		try {
			URLConnection connection = new URL(url).openConnection();
			InputStream response = connection.getInputStream();
			content = response;
		} catch (Exception e) {
			Log.i("tor", e.getMessage());
		}
		return content;
	}


	public static List<EmergencyNews> parseFeed(final String url) throws IOException {
		final InputStream is = getInputStreamFromUrl(url);
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
								endLocal, roadType, district, isEmergency, ++index));
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
