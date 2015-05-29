package com.example.inclass07.db;

import java.util.List;

import com.example.inclass07.Photo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DAOManager {
	Context mContext;
	DatabaseHelper dbOpenHelper;
	SQLiteDatabase db;
	PhotoDAO photoDAO;

	public DAOManager(Context mContext) {
		this.mContext = mContext;
		dbOpenHelper = new DatabaseHelper(mContext, null, null, 0);
		db = dbOpenHelper.getWritableDatabase();
		photoDAO = new PhotoDAO(db);
	}

	public void close() {
		if (db != null) {
			db.close();
		}
	}

	public long savePhoto(Photo photo) {
		return photoDAO.save(photo);
	}

	public int deletePhoto(Photo photo) {
		return photoDAO.delete(photo);
	}

	public Photo getPhoto(Photo photo) {
		return photoDAO.getPhoto(photo);
	}

}
