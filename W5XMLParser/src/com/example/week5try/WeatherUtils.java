package com.example.week5try;

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

public class WeatherUtils {
	static public class WeatherSAXParser extends DefaultHandler {
		Weather weather;
		StringBuilder xmlInnerText;

		static public Weather parseWeather(InputStream in) throws IOException,
				SAXException {
			WeatherSAXParser parser = new WeatherSAXParser();
			Xml.parse(in, Xml.Encoding.UTF_8, parser);

			return parser.getWeather();
		}

		public Weather getWeather() {
			return weather;
		}

		@Override
		public void startDocument() throws SAXException {
			xmlInnerText = new StringBuilder();
			super.startDocument();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			if (localName.equals("cities")) {
				weather = new Weather();
			} else if (localName.equals("city")) {
				weather.setId(attributes.getValue("id"));
				weather.setName(attributes.getValue("name"));
			} else if (localName.equals("humidity")) {
				weather.setHumidity(attributes.getValue("value")
						+ attributes.getValue("unit"));
			} else if (localName.equals("pressure")) {
				weather.setPressure(attributes.getValue("value")
						+ attributes.getValue("unit"));
			} else if (localName.equals("clouds")) {
				weather.setClouds(attributes.getValue("name"));
			} else if (localName.equals("precipitation")) {
				weather.setPreciitation(attributes.getValue("mode"));
			} else if (localName.equals("temperature")) {
				weather.setTemperature(attributes.getValue("value")
						+ attributes.getValue("unit"));
			} else if (localName.equals("speed")) {
				weather.setWind(attributes.getValue("name"));
			} else if (localName.equals("temperature")) {
				weather.setWind(weather.getWind() + ", "
						+ attributes.getValue("name"));
			}

		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			super.endElement(uri, localName, qName);
			if (localName.equals("country")) {
				weather.setCountry(xmlInnerText.toString().trim());
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

	// Pull Parser

	static public class WeatherPullParser {

/*		static ArrayList<Weather> parseWeather(InputStream in)
				throws XmlPullParserException, IOException {
			ArrayList<Weather> WeatherList = new ArrayList<Weather>();
			Weather Weather = null;

			XmlPullParser parser = XmlPullParserFactory.newInstance()
					.newPullParser();
			parser.setInput(in, "UTF-8");

			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_TAG:
					if (parser.getName().equals("Weather")) {
						Weather = new Weather();
						Weather.setId(parser.getAttributeValue(null, "id"));
					} else if (parser.getName().equals("name")) {
						Weather.setName(parser.nextText().trim());
					} else if (parser.getName().equals("age")) {
						Weather.setAge(parser.nextText().trim());
					} else if (parser.getName().equals("department")) {
						Weather.setDepartment(parser.nextText().trim());
					}
					break;
				case XmlPullParser.END_TAG:
					if (parser.getName().equals("Weather")) {
						WeatherList.add(Weather);
						Weather = null;
					}
					break;
				default:
					break;
				}
				event = parser.next();
			}

			return WeatherList;
		}*/

	}

}
