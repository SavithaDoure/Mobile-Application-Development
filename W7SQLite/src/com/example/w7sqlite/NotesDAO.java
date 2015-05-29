package com.example.w7sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NotesDAO {
	private SQLiteDatabase db;

	public NotesDAO(SQLiteDatabase db) {
		this.db = db;
	}

	public long save(Note note) {
		ContentValues values = new ContentValues();
		values.put(NotesTable.COLUMN_SUBJECT, note.getSubject());
		values.put(NotesTable.COLUMN_TEXT, note.getText());
		return db.insert(NotesTable.TABLENAME, null, values);

	}

	public boolean update(Note note) {
		ContentValues values = new ContentValues();
		values.put(NotesTable.COLUMN_SUBJECT, note.getSubject());
		values.put(NotesTable.COLUMN_TEXT, note.getText());
		return db.update(NotesTable.TABLENAME, values, NotesTable.COLUMN_ID
				+ "=?", new String[] { note.get_id() + "" }) > 0;
	}

	public boolean delete(Note note) {
		return db.delete(NotesTable.TABLENAME, NotesTable.COLUMN_ID + "=?",
				new String[] { note.get_id() + "" }) > 0;
	}

	public Note getNote(long id) {
		Note note = null;
		Cursor c = db.query(true, NotesTable.TABLENAME, new String[] {
				NotesTable.COLUMN_ID, NotesTable.COLUMN_SUBJECT,
				NotesTable.COLUMN_TEXT }, NotesTable.COLUMN_ID + "=" + id,
				null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			note = buildNoteFromCursor(c);
		}
		if (!c.isClosed()) {
			c.close();
		}
		return note;
	}

	public List<Note> getAll() {
		List<Note> notesList = new ArrayList<Note>();

		Cursor c = db.query(NotesTable.TABLENAME, new String[] {
				NotesTable.COLUMN_ID, NotesTable.COLUMN_SUBJECT,
				NotesTable.COLUMN_TEXT }, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			do {
				Note note = this.buildNoteFromCursor(c);
				if (note != null) {
					notesList.add(note);
				}
			} while (c.moveToNext());
		}
		if (!c.isClosed()) {
			c.close();
		}
		return notesList;
	}

	private Note buildNoteFromCursor(Cursor c) {
		Note note = null;
		if (c != null) {
			note = new Note();
			note.set_id(c.getLong(0));
			note.setSubject(c.getString(1));
			note.setText(c.getString(2));
		}
		return note;
	}

}
