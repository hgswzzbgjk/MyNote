package com.dgpt.mynote;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dgpt.mynote.bean.Note;
import com.dgpt.mynote.utils.Uris;


/** 此类用于新建Note和修改Note */
public class AddNoteActivity extends Activity implements OnClickListener {
	/** 标记 true为修改模式，false为添加模式 */
	private boolean flags = false;
	private Note note;
	private EditText mTitleET;
	private EditText mContentET;
	private String time;
	private TextView mCreateTime;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		Intent intent = getIntent();
		int mode = intent.getIntExtra("mode", 0);
		if (mode != 1) {
			flags = false;
			Date date  = new Date();
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			time = "创建时间："+sd.format(date);
		} else {
			flags = true;
			note = (Note) intent.getSerializableExtra("note");
			time ="上次修改时间： "+ note.getDate();
			
		}
		initView();
	}

	/** 初始化控件 */
	private void initView() {
		findViewById(R.id.imgv_back).setOnClickListener(this);
		findViewById(R.id.imgv_ok).setOnClickListener(this);
		mCreateTime = (TextView) findViewById(R.id.tv_create_time);
		mCreateTime.setText(time);
		if (flags) {
			findViewById(R.id.imgv_delete).setOnClickListener(this);
		} else {
			findViewById(R.id.imgv_delete).setVisibility(View.GONE);
		}
		mTitleET = (EditText) findViewById(R.id.et_title);
		mContentET = (EditText) findViewById(R.id.et_content);
		if(note != null){
			mTitleET.setText(note.getTitle());
			mContentET.setText(note.getContent());
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.imgv_back:
				finish();
				break;
			case R.id.imgv_delete:
				if (flags) {
					// 修改模式则删除此条记录，并返回主页面
					int id = getContentResolver().delete(Uris.DELETE, "_id=?",
							new String[] { note.getId() + "" });
					if (id == -1) {
						Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
					} else {
						// 删除成功 回到主页面
						Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
						finish();
					}
				}
				break;
			case R.id.imgv_ok:
				if (flags) {
					// 修改模式，修改此条数据,并返回主页面
					if (!TextUtils.isEmpty(mTitleET.getText().toString().trim())
							& !TextUtils.isEmpty(mContentET.getText().toString()
							.trim())) {
						ContentValues values = new ContentValues();
						values.put("note_title", mTitleET.getText().toString().trim());
						values.put("note_content", mContentET.getText().toString().trim());
						Date date  = new Date();
						SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm");
						values.put("date",sd.format(date));
						int id = getContentResolver().update(Uris.UPDATE, values, "_id=?", new String[]{note.getId()+""});
						if(id !=-1){
							Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
						}
						finish();
					}else{
						Toast.makeText(this, "标题和内容不能为空", Toast.LENGTH_SHORT).show();
					}
				} else {
					// 添加模式，添加一条数据
					if (!TextUtils.isEmpty(mTitleET.getText().toString().trim())
							& !TextUtils.isEmpty(mContentET.getText().toString()
							.trim())) {
						ContentValues values = new ContentValues();
						values.put("note_title", mTitleET.getText().toString().trim());
						values.put("note_content", mContentET.getText().toString().trim());
						Date date  = new Date();
						SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm");
						values.put("date",sd.format(date));
						getContentResolver().insert(Uris.INSERT, values);
						finish();
					}else{
						Toast.makeText(this, "标题和内容不能为空", Toast.LENGTH_SHORT).show();
					}
				}
				break;
		}
	}
}
