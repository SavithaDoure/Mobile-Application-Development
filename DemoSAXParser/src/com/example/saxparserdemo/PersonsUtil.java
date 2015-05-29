package com.example.saxparserdemo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Xml;

public class PersonsUtil {
	static public class PersonSAXParser extends DefaultHandler {
		ArrayList<Person> personList;
		Person person;
		StringBuilder xmlInnerText;

		public ArrayList<Person> getPersonList() {
			return personList;
		}

		static public ArrayList<Person> parsePerson(InputStream in)
				throws IOException, SAXException {
			PersonSAXParser parser = new PersonSAXParser();
			Xml.parse(in, Xml.Encoding.UTF_8, parser);

			return parser.getPersonList();
		}

		@Override
		public void startDocument() throws SAXException {
			xmlInnerText = new StringBuilder();
			personList = new ArrayList<Person>();
			super.startDocument();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			if (localName.equals("person")) {
				person = new Person();
				person.setId(attributes.getValue("id"));
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			super.endElement(uri, localName, qName);
			if (localName.equals("person")) {
				personList.add(person);
			} else if (localName.equals("name")) {
				person.setName(xmlInnerText.toString());
			} else if (localName.equals("age")) {
				person.setAge(xmlInnerText.toString());
			} else if (localName.equals("department")) {
				person.setDepartment(xmlInnerText.toString());
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
}
