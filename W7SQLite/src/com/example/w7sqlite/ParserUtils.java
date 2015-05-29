package com.example.w7sqlite;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Xml;


public class ParserUtils {
	static public class NewsItemPullParser {

		static ArrayList<NewsItem> parseNewsItem(InputStream in)
				throws XmlPullParserException, IOException {
			ArrayList<NewsItem> NewsItemList = new ArrayList<NewsItem>();
			NewsItem newsItem = null;

			XmlPullParser parser = XmlPullParserFactory.newInstance()
					.newPullParser();
			parser.setInput(in, "UTF-8");

			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_TAG:
					if (parser.getName().equals("title")) {
						newsItem = new NewsItem();
						//newsItem.setId(parser.getAttributeValue(null, "id"));
					} else if (parser.getName().equals("name")) {
						//newsItem.setName(parser.nextText().trim());
					} else if (parser.getName().equals("age")) {
						//newsItem.setAge(parser.nextText().trim());
					} else if (parser.getName().equals("department")) {
						//newsItem.setDepartment(parser.nextText().trim());
					}
					break;
				case XmlPullParser.END_TAG:
					if (parser.getName().equals("NewsItem")) {
						NewsItemList.add(newsItem);
						newsItem = null;
					}
					break;
				default:
					break;
				}
				event = parser.next();
			}

			return NewsItemList;
		}

	}
/*
	static public class NewsItemSAXParser extends DefaultHandler {
		ArrayList<NewsItem> NewsItemList;
		NewsItem NewsItem;
		StringBuilder xmlInnerText;

		public ArrayList<NewsItem> getNewsItemList() {
			return NewsItemList;
		}

		static public ArrayList<NewsItem> parseNewsItem(InputStream in)
				throws IOException, SAXException {
			NewsItemSAXParser parser = new NewsItemSAXParser();
			Xml.parse(in, Xml.Encoding.UTF_8, parser);

			return parser.getNewsItemList();
		}

		@Override
		public void startDocument() throws SAXException {
			xmlInnerText = new StringBuilder();
			NewsItemList = new ArrayList<NewsItem>();
			super.startDocument();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			if (localName.equals("NewsItem")) {
				NewsItem = new NewsItem();
				NewsItem.setId(attributes.getValue("id"));
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			super.endElement(uri, localName, qName);
			if (localName.equals("NewsItem")) {
				NewsItemList.add(NewsItem);
			} else if (localName.equals("name")) {
				NewsItem.setName(xmlInnerText.toString());
			} else if (localName.equals("age")) {
				NewsItem.setAge(xmlInnerText.toString());
			} else if (localName.equals("department")) {
				NewsItem.setDepartment(xmlInnerText.toString());
			}
			xmlInnerText.setLength(0);
		}

		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.endDocument();
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			super.characters(ch, start, length);
			xmlInnerText.append(ch, start, length);
		}
	}
*/
}
