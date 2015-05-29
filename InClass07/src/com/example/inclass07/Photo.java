package com.example.inclass07;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Photo implements Serializable {
	long _id;

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	String name, userName, imageUrl;

	@Override
	public String toString() {
		return "Photo [_id=" + _id + ", name=" + name + ", userName="
				+ userName + ", imageUrl=" + imageUrl + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public static Photo createGallery(JSONObject jsonObject)
			throws JSONException {
		Photo photo = new Photo();
		photo.set_id(jsonObject.getLong("id"));
		photo.setName(jsonObject.getString("name"));
		photo.setImageUrl(jsonObject.getString("image_url"));
		photo.setUserName(jsonObject.getJSONObject("user")
				.getString("fullname"));
		return photo;
	}
}
