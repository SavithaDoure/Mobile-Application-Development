package com.example.w7sqlite;

public class NewsItem {
	// title, description, pubDate, thumbnailSmall and thumbnailLarge.!
	String title, description, pubDate, imageURL;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	@Override
	public String toString() {
		return "NewsItem [title=" + title + ", description=" + description
				+ ", pubDate=" + pubDate + ", imageURL=" + imageURL + "]";
	}
}
