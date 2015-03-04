package com.kibnet.jtp;

import static com.kibnet.jtp.db.Constants.CONTENT_URI;
import static com.kibnet.jtp.db.Constants.TIME;
import static com.kibnet.jtp.db.Constants.TITLE;

import java.io.File;

import com.kibnet.jtp.common.XDateUtil;
import com.kibnet.jtp.db.Events;

import com.kibnet.jtp.R;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

public class Preview extends Activity implements OnTouchListener {
	
	String pdf; 
	File file;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);
        
        Button previewBtn = (Button) findViewById(R.id.button5);
        Button sendBtn = (Button) findViewById(R.id.button6);
        
        previewBtn.setOnTouchListener(this);
        sendBtn.setOnTouchListener(this);

    }
    
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (v == null)
			return false;
		Intent i;
		int id = v.getId();
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			switch(id){
			case R.id.button5:
				Intent intent = getIntent();
				String path = intent.getStringExtra("path");
				pdf = intent.getStringExtra("pdf");
				Log.i("pdf path = ", path + pdf);
				file = new File(path, pdf);
				
				intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setAction(android.content.Intent.ACTION_VIEW);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setDataAndType(Uri.fromFile(file), "application/pdf");
				try{
				   startActivity(intent);
				}catch(ActivityNotFoundException e){

					e.printStackTrace();
				}
				break;	
			case R.id.button6:
				Toast.makeText(this, "전송이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
//				file.delete();
				addEvent(pdf);
				finish();
				break;	
			}
		}
		return false;
	}
	     
	public void addEvent(String string) {
	      ContentValues values = new ContentValues();
	      values.put(TIME, XDateUtil.getDate("YYYYMMDDHHNNSS"));
	      values.put(TITLE, string);
	      getContentResolver().insert(CONTENT_URI, values);
	}
	   
}