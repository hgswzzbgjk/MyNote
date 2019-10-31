package com.dgpt.mynote;



import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dgpt.mynote.bean.Note;
import com.dgpt.mynote.utils.Uris;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements OnClickListener {

	/** 用来显示已添加便签的ListView */
	private ListView mNoteListView;
	private ContentResolver contentResolver;
	private List<Note> mNotes = new ArrayList<Note>();
	private static final String TITLE = "note_title";
	private static final String CONTENT = "note_content";
	private static final String DATE = "date";
	private static final String ID = "_id";
	private ArrayAdapter<Note> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		contentResolver = getContentResolver();
		initView();
		searchData();
		contentResolver.registerContentObserver(Uris.AUTHORITY, true,
				new ContentObserver(new Handler()) {
					@Override
					public void onChange(boolean selfChange) {
						super.onChange(selfChange);
						mNotes.clear();
						searchData();
					}
				});
	}



	/** 填充数据 */
	private void searchData() {
		Cursor cursor = contentResolver.query(Uris.QUERY, null, null, null,
				null);
		if (cursor != null) {
			if (cursor.getCount() >= 1) {
				while (cursor.moveToNext()) {
					final Note note = new Note();
					note.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
					note.setId(cursor.getInt(cursor.getColumnIndex(ID)));
					note.setContent(cursor.getString(cursor.getColumnIndex(CONTENT)));
					note.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
					mNotes.add(note);
				}
			}
			if(adapter != null){
				adapter.notifyDataSetChanged();
			}
		}
	}

	/** 初始化控件 */
	private void initView() {
		findViewById(R.id.imgv_addnote).setOnClickListener(this);
		mNoteListView = (ListView) findViewById(R.id.lv_notes);
		adapter = new ArrayAdapter<Note>(this, R.layout.list_item,
				R.id.tv_content, mNotes);
		mNoteListView.setAdapter(adapter);
		mNoteListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent intent = new Intent(MainActivity.this,
						AddNoteActivity.class);
				intent.putExtra("mode", 1);
				intent.putExtra("note", mNotes.get(position));
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.imgv_addnote:
				Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
				intent.putExtra("mode", 2);
				startActivity(intent);
				break;
		}
	}
}

