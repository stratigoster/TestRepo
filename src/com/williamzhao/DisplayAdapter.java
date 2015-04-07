package com.williamzhao;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DisplayAdapter extends BaseAdapter {
	private List<EmergencyNews> newItems;
	private final Context context;

	public DisplayAdapter(final Context context, List<EmergencyNews> newItems) {
		this.newItems = newItems;
		this.context = context;
	}

	@Override
	public int getCount() {
		return newItems.size();
	}

	@Override
	public Object getItem(int position) {
		if (position >= 0 && position < newItems.size()) {
			return newItems.get(position);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		// if we have not inflated the rows
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.emergency_news_row,
					new LinearLayout(context));
			holder = new ViewHolder();
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		EmergencyNews newsItem = newItems.get(position);
		
		holder.description = (TextView) convertView
				.findViewById(R.id.description);
		holder.fromRoad = (TextView) convertView.findViewById(R.id.from_road);
		holder.issueType = (TextView) convertView.findViewById(R.id.issue_type);
		holder.latitude = (TextView) convertView.findViewById(R.id.latitude);
		holder.longitude = (TextView) convertView.findViewById(R.id.longitude);
		holder.mainRoad = (TextView) convertView.findViewById(R.id.main_road);
		holder.startLocal = (TextView) convertView
				.findViewById(R.id.start_time);
		holder.toRoad = (TextView) convertView.findViewById(R.id.to_road);
		holder.atRoad = (TextView) convertView.findViewById(R.id.at_road);
		
		holder.issueType.setText(newsItem.getIssueType());
		holder.description.setText(newsItem.getDescription());
		
		holder.latitude.setText(newsItem.getLatitude());
		holder.longitude.setText(newsItem.getLongitude());
		holder.mainRoad.setText(newsItem.getMainRoad());
		holder.startLocal.setText(newsItem.getStartLocal());
		
		holder.fromRoad.setText(newsItem.getFromRoad());
		  holder.toRoad.setText(newsItem.getToRoad());
		holder.atRoad.setText(newsItem.getAtRoad());
		
		holder.atRoad.setVisibility(View.VISIBLE);
		
		return convertView;
	}

	static class ViewHolder {
		TextView longitude;
		TextView latitude;
		TextView issueType;
		TextView mainRoad;
		TextView fromRoad;
		TextView toRoad;
		TextView description;
		TextView startLocal;
		TextView atRoad;
		TextView district;
		TextView roadType;
	}
}
