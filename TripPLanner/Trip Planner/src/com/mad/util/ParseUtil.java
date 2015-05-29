package com.mad.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.mad.bean.Item;

import android.util.Log;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class ParseUtil {

	static public class SaxParser {
		static ArrayList<Item> itemArray = new ArrayList<Item>();
		StringBuilder xmlInnerText;
		static String url;

		static ArrayList<Item> parserXMl(InputStream in) throws IOException {
			Item item = null;
			XmlPullParser parser;
			try {
				parser = XmlPullParserFactory.newInstance().newPullParser();
				parser.setInput(in, "UTF-8");

				int event = parser.getEventType();

				while (event != XmlPullParser.END_DOCUMENT) {
					switch (event) {
					case XmlPullParser.START_TAG:
					if (parser.getName().equals("entry")) {
						item = new Item();
						}
					
					else if (parser.getName().equals("id")) {
							String id = parser.getAttributeValue(null, "im:id");
							if(id!=null){
								item.setId(Integer.parseInt(id));
							}
							
						}else if(item!=null&&parser.getName().equals("title")){
							item.setTitle(parser.nextText().trim());
						}else if(item!=null&&parser.getName().equals("link")){
							String url = parser.getAttributeValue(null, "href");
							item.setAppUrl(url);
						}else if(parser.getName().equals("im:artist")){
							String devName = parser.nextText().trim();
							item.setDevname(devName);
						}
						else if(parser.getName().equals("im:price")){
							String appPrice = parser.getAttributeValue(null, "amount");
							item.setPrice(Float.parseFloat(appPrice));
						}else if(parser.getName().equals("im:image")){
							int image = Integer.parseInt(parser.getAttributeValue(null, "height"));
							if(image==75){
								item.setImgUrl(parser.nextText().trim());	
							}if(image==100){
								item.setLargeImgUrl(parser.nextText().trim());	
							}
							
						}else if(parser.getName().equals("im:releaseDate")){
							String date = parser.getAttributeValue(null, "label");
							item.setDate(date);
							
						}
						break;
					case XmlPullParser.END_TAG:

						if (parser.getName().equals("entry")) {
							itemArray.add(item);
							item = null;
						}

						break;

					}
					event = parser.next();
				}
				return itemArray;

			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		}

	}


}
