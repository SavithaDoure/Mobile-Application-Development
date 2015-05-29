package com.example.demosqlite;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NotesTable {
	static final String TABLENAME = "notes";
	static final String COLUMN_ID = "_id";
	static final String COLUMN_SUBJECT = "subject";
	static final String COLUMN_TEXT = "text";

	static public void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();

		// CREATE TABLE tablename (columnname integer primary key autoincrement,
		// columnname text not null, columnname text not null);

		sb.append("CREATE TABLE " + TABLENAME + " (");
		sb.append(COLUMN_ID + " integer primary key autoincrement, ");
		sb.append(COLUMN_SUBJECT + " text not null, ");
		sb.append(COLUMN_TEXT + " text not null);");
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
