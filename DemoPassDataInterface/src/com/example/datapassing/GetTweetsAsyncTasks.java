package com.example.datapassing;

import java.util.LinkedList;

import android.os.AsyncTask;

public class GetTweetsAsyncTasks extends
		AsyncTask<String, Void, LinkedList<String>> {
	IData activity;

	public GetTweetsAsyncTasks(IData activity) {
		this.activity = activity;
	}

	@Override
	protected LinkedList<String> doInBackground(String... params) {
		LinkedList<String> tweets = new LinkedList<String>();
		tweets.add("Tweet 0");
		tweets.add("Tweet 1");
		tweets.add("Tweet 2");
		tweets.add("Tweet 3");

		return tweets;
	}

	@Override
	protected void onPostExecute(LinkedList<String> result) {
		activity.setUpData(result);
		super.onPostExecute(result);
	}

	static public interface IData {
		public void setUpData(LinkedList<String> result);
	}
}
