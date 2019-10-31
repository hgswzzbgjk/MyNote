package com.dgpt.mynote.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "mynote.db";
	private static final int DB_VERSION = 1;

	public MySQLiteOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建便签表
		String sql = "create table notes(_id integer primary key autoincrement," +
				                         "note_title varchar(255)," +
				                         "note_content varchar(255)," +
				                         "date varchar(255));";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
