package com.example.w6json;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

public class Application implements Comparable<Application> {
	String title, category, price, summary, imageURL;

	Random random = new Random();

	public Application() {

		this.price = "" + random.nextInt(200);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	@Override
	public String toString() {
		/*
		 * return "Application [title=" + title + ", \ncategory=" + category +
		 * ", \nprice=" + price + ", \nsummary=" + summary + ", \nimageURL=" +
		 * imageURL + "]\n\n";
		 */
		
		return "Application [\nprice=" + price + "]\n";
	}

	@Override
	public int compareTo(Application another) {
		return this.getPrice().compareTo(another.getPrice());
	}

	public static Application createApplication(JSONObject jsonObject)
			throws JSONException {
		Application app = new Application();
		app.setTitle(jsonObject.getJSONObject("im:name").getString("label"));
		return app;
	}
}
