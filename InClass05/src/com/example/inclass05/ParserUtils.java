package com.example.inclass05;

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

	static public class SAXParser extends DefaultHandler {
		ArrayList<String> imagesArray;
		StringBuilder xmlInnerText;
		String url;

		static public ArrayList<String> parseForImageSAX(InputStream in)
				throws IOException, SAXException {
			SAXParser parser = new SAXParser();
			Xml.parse(in, Xml.Encoding.UTF_8, parser);

			return parser.getImagesArray();
		}

		public ArrayList<String> getImagesArray() {
			return imagesArray;
		}

		@Override
		public void startDocument() throws SAXException {
			// xmlInnerText = new StringBuilder();
			imagesArray = new ArrayList<String>();
			super.startDocument();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);

			if (localName.equals("photo")) {
				url = attributes.getValue("url_m");
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			super.endElement(uri, localName, qName);
			if (localName.equals("photo")) {
				imagesArray.add(url);
			}

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
			// xmlInnerText.append(ch, start, length);
		}
	}

	// Pull Parser

	static public class PullParser {
		static ArrayList<String> imagesArray = new ArrayList<String>();
		StringBuilder xmlInnerText;
		static String url;

		static ArrayList<String> parseForImagePull(InputStream in)
				throws XmlPullParserException, IOException {

			XmlPullParser parser = XmlPullParserFactory.newInstance()
					.newPullParser();
			parser.setInput(in, "UTF-8");

			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_TAG:
					if (parser.getName().equals("photo")) {
						url = parser.getAttributeValue(null, "url_m");
					}
					break;
				case XmlPullParser.END_TAG:
					imagesArray.add(url);
					break;
				default:
					break;
				}
				event = parser.next();
			}

			return imagesArray;
		}

	}

}
