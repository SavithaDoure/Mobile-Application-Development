package com.example.pullparserdemo;

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

public class PersonsUtil {
	static public class PersonPullParser {

		static ArrayList<Person> parsePerson(InputStream in)
				throws XmlPullParserException, IOException {
			ArrayList<Person> personList = new ArrayList<Person>();
			Person person = null;
			
			XmlPullParser parser = XmlPullParserFactory.newInstance()
					.newPullParser();
			parser.setInput(in, "UTF-8");
			

			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_TAG:
					if (parser.getName().equals("person")) {
						person = new Person();
						person.setId(parser.getAttributeValue(null, "id"));
					} else if (parser.getName().equals("name")) {
						person.setName(parser.nextText().trim());
					} else if (parser.getName().equals("age")) {
						person.setAge(parser.nextText().trim());
					} else if (parser.getName().equals("department")) {
						person.setDepartment(parser.nextText().trim());
					}
					break;
				case XmlPullParser.END_TAG:
					if (parser.getName().equals("person")) {
						personList.add(person);
						person = null;
					}
					break;
				default:
					break;
				}
				event = parser.next();
			}

			return personList;
		}

	}
}
