package com.example.demosqlite;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	DAOManager dm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dm = new DAOManager(this);

		/*
		 * Note note = new Note(); note.setSubject("Note 1");
		 * note.setText("This is the text for note 1"); dm.saveNote(note);
		 * 
		 * note = new Note(); note.setSubject("Note 2");
		 * note.setText("This is the text for note 2"); dm.saveNote(note);
		 * 
		 * note = new Note(); note.setSubject("Note 3");
		 * note.setText("This is the text for note 3"); dm.saveNote(note);
		 */

		// dm.deleteNote(dm.getNote(4));
		// dm.deleteNote(dm.getNote(5));
		// dm.deleteNote(dm.getNote(6));

		List<Note> notes = dm.getAllNotes();
		ListView myListView = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, notes);
		myListView.setAdapter(adapter);
	}

	@Override
	protected void onDestroy() {
		dm.close();
		super.onDestroy();
	}
}
