package com.example.inclass07.db;

import java.util.ArrayList;
import java.util.List;

import com.example.inclass07.Photo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PhotoDAO {
	private SQLiteDatabase db;

	public PhotoDAO(SQLiteDatabase db) {
		this.db = db;
	}

	public long save(Photo photo) {
		ContentValues values = new ContentValues();
		values.put(PhotoTable.COLUMN_ID, photo.get_id());
		values.put(PhotoTable.COLUMN_NAME, photo.getName());
		values.put(PhotoTable.COLUMN_USERNAME, photo.getUserName());
		values.put(PhotoTable.COLUMN_IMAGEURL, photo.getImageUrl());
		return db.insert(PhotoTable.TABLENAME, null, values);

	}

	public int delete(Photo photo) {
		StringBuilder whereClause = new StringBuilder();
		return db.delete(PhotoTable.TABLENAME, PhotoTable.COLUMN_ID + "="
				+ photo.get_id(), null);
	}

	public Photo getPhoto(Photo fetch) {
		Photo photo = null;
		Cursor c = db.query(true, PhotoTable.TABLENAME, new String[] {
				PhotoTable.COLUMN_ID, PhotoTable.COLUMN_NAME,
				PhotoTable.COLUMN_USERNAME, PhotoTable.COLUMN_IMAGEURL },
				PhotoTable.COLUMN_ID + "=" + fetch.get_id(), null, null, null,
				null, null);
		if (c != null) {
			c.moveToFirst();
			photo = buildPhotoFromCursor(c);
		}
		if (!c.isClosed()) {
			c.close();
		}
		return photo;
	}

	private Photo buildPhotoFromCursor(Cursor c) {
		Photo photo = null;
		if (c != null) {
			photo = new Photo();
			photo.set_id(c.getLong(0));
			photo.setName(c.getString(1));
			photo.setUserName(c.getString(2));
			photo.setImageUrl(c.getString(3));
		}
		return photo;
	}

}
