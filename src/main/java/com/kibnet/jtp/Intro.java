package com.kibnet.jtp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Intro extends Activity {

	private final int HANDLER_DELAY_MSG = 0x100;
	private final int DELAY_TIME = 1500;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
		mHandler.sendEmptyMessageDelayed(HANDLER_DELAY_MSG, DELAY_TIME);
	}

    Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			Intent i = new Intent(Intro.this, Login.class);
			startActivity(i);
			finish();
			super.handleMessage(msg);
		}
    };
    
}