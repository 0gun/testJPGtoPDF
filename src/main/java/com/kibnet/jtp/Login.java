package com.kibnet.jtp;

import com.kibnet.jtp.db.Events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements OnClickListener {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		EditText mUserId = (EditText) findViewById(R.id.user_id);
		EditText mUserPW = (EditText) findViewById(R.id.user_pass);
		Button loginBtn = (Button) findViewById(R.id.login_btn);

		loginBtn.setOnClickListener(this);
		mUserId.setOnClickListener(this);
		mUserPW.setOnClickListener(this);
		
	}	

	public void onClick(View v) {
		// TODO Auto-generated method stub

		int id = v.getId();
		switch(id){
		case R.id.login_btn:
			Intent i = new Intent(Login.this, Events.class);
			startActivity(i);
			finish();
			break;
		
		}

	}	

}