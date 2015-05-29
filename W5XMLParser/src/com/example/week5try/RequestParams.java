package com.example.week5try;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class RequestParams {
	String method, baseUrl;
	HashMap<String, String> params = new HashMap<String, String>();

	public RequestParams(String method, String baseUrl) {
		super();
		this.method = method;
		this.baseUrl = baseUrl;
	}

	public void addParam(String key, String value) {
		params.put(key, value);
	}

	public String getEncodedParams() {
		StringBuilder sb = new StringBuilder();
		for (String key : params.keySet()) {
			try {
				String value = URLEncoder.encode(params.get(key), "UTF-8");
				if (sb.length() > 0) {
					sb.append("&");
				}
				sb.append(key + "=" + value);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return sb.toString();

	}

	public String getEncodedUrl() {
		return this.baseUrl + "?" + getEncodedParams();
	}

	public HttpURLConnection setupConnection() throws IOException {
		if (method.equals("GET")) {
			URL url = new URL(getEncodedUrl());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			return con;
		} else {
			URL url = new URL(this.baseUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(
					con.getOutputStream());
			writer.write(getEncodedParams());
			writer.flush();
			return con;
		}
	}

}
