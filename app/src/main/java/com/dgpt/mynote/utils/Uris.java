package com.dgpt.mynote.utils;

import android.net.Uri;

public class Uris {

	public  static final Uri AUTHORITY =Uri.parse("content://com.dgpt.mynote.provider.MyProvider");
	public  static final Uri QUERY =Uri.parse("content://com.dgpt.mynote.provider.MyProvider/mynote/query");
	public  static final Uri DELETE =Uri.parse("content://com.dgpt.mynote.provider.MyProvider/mynote/delete");
	public  static final Uri INSERT =Uri.parse("content://com.dgpt.mynote.provider.MyProvider/mynote/insert");
	public  static final Uri UPDATE =Uri.parse("content://com.dgpt.mynote.provider.MyProvider/mynote/update");
}
