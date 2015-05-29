package com.example.inclass07;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {
	static class GalleryJSONParser {
		public static ArrayList<Photo> getGallery(String in)
				throws JSONException {
			JSONObject root = new JSONObject(in);
			JSONArray photosArray = root.getJSONArray("photos");
			ArrayList<Photo> galleryList = new ArrayList<Photo>();
			for (int i = 0; i < photosArray.length(); i++) {
				JSONObject jsonObject = photosArray.getJSONObject(i);
				Photo photo = Photo.createGallery(jsonObject);
				galleryList.add(photo);
			}
			return galleryList;
		}
	}
}
