package com.example.jsondemo;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class PersonUtils {
	static class PersonJSONParser {
		public static ArrayList<Person> getPersons(String in)
				throws JSONException {
			JSONObject root = new JSONObject(in);
			JSONArray jsonArray = root.getJSONArray("persons");
			ArrayList<Person> personList = new ArrayList<Person>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Person person = Person.createPerson(jsonObject);
				personList.add(person);
			}
			System.out.println("JSONDemo: " + personList.toString());
			return personList;
		}
	}
}
