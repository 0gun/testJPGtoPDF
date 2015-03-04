package com.kibnet.jtp.db;

import static android.provider.BaseColumns._ID;

import com.kibnet.jtp.JPGtoPDFActivity;
import com.kibnet.jtp.R;
import com.kibnet.jtp.common.XDateUtil;

import static com.kibnet.jtp.db.Constants.CONTENT_URI;
import static com.kibnet.jtp.db.Constants.TIME;
import static com.kibnet.jtp.db.Constants.TITLE;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Events extends ListActivity implements OnTouchListener  {
	
   private static String[] FROM = { _ID, TIME, TITLE, };
   private static int[] TO = { R.id.rowid, R.id.time, R.id.title, };
   private static String ORDER_BY = TIME + " DESC";
   private final int ADD_DB = 1000;
   Cursor cursor;
   
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.dblist);

      cursor = getEvents();
      showEvents(cursor);
	      
      Button scanBtn = (Button) findViewById(R.id.button2);
      
      scanBtn.setOnTouchListener(this);
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

			case R.id.button2:
				i = new Intent(Events.this, JPGtoPDFActivity.class);
				startActivity(i);
				break;	
			}
		}
		return false;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent i) {
		
		switch (resultCode) {
			case RESULT_OK:
				
				if (requestCode == ADD_DB) {
	
					Bundle extras = i.getExtras();
					String na = extras.getString("name");				
					addEvent(na);
				    cursor = getEvents();
				    showEvents(cursor);
				      
				} 
				break;
				
			default:
				break;
		}

	    super.onActivityResult(requestCode, resultCode, i);
	}

	protected void onListItemClick (ListView l, View v, int position, long id){

		super.onListItemClick(l, v, position, id);
		Toast.makeText(this, cursor.getString(2), Toast.LENGTH_SHORT).show();//세번째 컬럼인 파일명을 보여줌.

	}
	
   public void addEvent(String string) {
      // Insert a new record into the Events data source.
      // You would do something similar for delete and update.
      ContentValues values = new ContentValues();
      values.put(TIME, XDateUtil.getDate("YYYYMMDDHHNNSS"));
      values.put(TITLE, string);
      getContentResolver().insert(CONTENT_URI, values);
   }
   

   public Cursor getEvents() {
      // Perform a managed query. The Activity will handle closing
      // and re-querying the cursor when needed.
      return managedQuery(CONTENT_URI, FROM, null, null, ORDER_BY);
   }
   

   public void showEvents(Cursor cursor) {
      // Set up data binding
	   SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item, cursor, FROM, TO);
	   setListAdapter(adapter);
   }
   
}