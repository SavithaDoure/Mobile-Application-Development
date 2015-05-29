package com.example.inclass07.db;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PhotoTable {
	static final String TABLENAME = "photosdb";
	static final String COLUMN_ID = "_id";
	static final String COLUMN_NAME = "name";
	static final String COLUMN_USERNAME = "username";
	static final String COLUMN_IMAGEURL = "imageurl";

	static public void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();

		// CREATE TABLE tablename (columnname integer primary key autoincrement,
		// columnname text not null, columnname text not null);

		sb.append("CREATE TABLE " + TABLENAME + " (");
		sb.append(COLUMN_ID + " integer primary key, ");
		sb.append(COLUMN_NAME + " text not null, ");
		sb.append(COLUMN_USERNAME + " text not null, ");
		sb.append(COLUMN_IMAGEURL + " text not null);");
		try {
			db.execSQL(sb.toString());
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	static public void onUpdate(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
	}
}
