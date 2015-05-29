package com.example.w6json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {
	static class ApplicationJSONParser {
		public static ArrayList<Application> getApplications(String in)
				throws JSONException {
			JSONObject root = new JSONObject(in);
			JSONObject feed = root.getJSONObject("feed");
			JSONArray jsonArray = feed.getJSONArray("entry");
			ArrayList<Application> applicationList = new ArrayList<Application>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Application application = Application
						.createApplication(jsonObject);
				applicationList.add(application);
			}
			System.out.println("JSONDemo: " + applicationList.toString());
			return applicationList;
		}
	}
}
