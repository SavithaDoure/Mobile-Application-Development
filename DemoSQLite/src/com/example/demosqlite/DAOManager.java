package com.example.demosqlite;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DAOManager {
	Context mContext;
	DatabaseHelper dbOpenHelper;
	SQLiteDatabase db;
	NotesDAO noteDao;

	public DAOManager(Context mContext) {
		this.mContext = mContext;
		dbOpenHelper = new DatabaseHelper(mContext, null, null, 0);
		db = dbOpenHelper.getWritableDatabase();
		noteDao = new NotesDAO(db);
	}

	public void close() {
		if (db != null) {
			db.close();
		}
	}

	public long saveNote(Note note) {
		return noteDao.save(note);
	}

	public boolean updateNote(Note note) {
		return noteDao.update(note);
	}

	public boolean deleteNote(Note note) {
		return noteDao.delete(note);
	}

	public Note getNote(long id) {
		return noteDao.getNote(id);
	}

	public List<Note> getAllNotes() {
		return noteDao.getAll();
	}
}
