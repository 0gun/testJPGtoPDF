package com.kibnet.jtp;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.kibnet.jtp.common.XDateUtil;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

public class JPGtoPDFActivity extends Activity implements OnTouchListener {
	
	private final int CAPTURE_IMG = 1000;
	private String path = "";
	Uri defaultUri, imageUri;
	public String NewPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/";//Capture/";
	String[] fileName = null;
	int index = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jtp);
        
        Button continueBtn = (Button) findViewById(R.id.button3);
        Button stopBtn = (Button) findViewById(R.id.button4);
        
        continueBtn.setOnTouchListener(this);
        stopBtn.setOnTouchListener(this);
        
        if(fileName == null)
        	takePicture();
    }
    
    @Override
	protected void onResume() {

    	super.onResume();
	}
    
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (v == null)
			return false;
		int id = v.getId();
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			switch(id){
			case R.id.button3:
				takePicture();
				break;	
			case R.id.button4:
			    String pdf = fileName[0].substring(2, fileName[0].length()-3) + "pdf";
//			    Log.i("pdf ::", pdf);
			    createPdf( fileName, NewPath + pdf, true);
			    Intent intent = new Intent(JPGtoPDFActivity.this, Preview.class);
			    intent.putExtra("path", NewPath);
			    intent.putExtra("pdf", pdf);
			    startActivity(intent);
				finish();
				break;	
			}
		}
		return false;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent i) {
		
		switch (resultCode) {
		case RESULT_OK:
			
			if (requestCode == CAPTURE_IMG) {
				
				path = getCapturePath(this);
				File nf = new File(NewPath);
				if (!nf.exists())
					nf.mkdirs();
				File of = new File(path);
				Log.i("original file path ::",  of.getPath() + " : " + of.getAbsolutePath());
				Log.i("new file path ::",  NewPath + fileName[index-1]);
				nf = new File(NewPath + fileName[index-1]);
//				if(!moveFile( path, NewPath + fileName[index-1]));
//					finish();
				of.renameTo(nf);
				if(of == null)
					finish();

			} 
			break;
			
		default:
			break;
		}

	    super.onActivityResult(requestCode, resultCode, i);
	}
	
	public void takePicture(){
		if(fileName == null) {
			fileName = new String[99];// 임시로 99개 만든다.. 몇 장을 찍을 지 알 수 없기 때문..
			fileName[0] = "01" + "scan_" + XDateUtil.getDate("YYYYMMDDHHNNSS") + ".jpg";
			index = 1;
		}
		else {
			if(index == 99){
				Toast.makeText(this, "스캔 할 문서는 99장을 초과할 수 없습니다.", Toast.LENGTH_SHORT).show();
				return;			
			}
			
			index = Integer.parseInt(fileName[index-1].substring(0, 2));
			index ++;
			if(index < 10)
				fileName[index-1] = "0" + index + fileName[0].substring(2);
			else
				fileName[index-1] = index + fileName[0].substring(2);
		}
		for(int i=0;i<index;i++)
			Log.i("fileName[" + i +"]::", fileName[i]);
//		Log.i("checkSDCard()::",  " "+ checkSDCard());
//		if( checkSDCard() )
//			defaultUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//		else
//			defaultUri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
		Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		ContentValues values = new ContentValues();  
//		values.put(MediaStore.Images.Media.TITLE, fileName[index-1]);
//		values.put(MediaStore.Images.Media., fileName[index-1]);
//		values.put(MediaStore.Images.Media.DESCRIPTION,"Image capture by camera");  
//		imageUri = getContentResolver().insert(defaultUri, values);  
//		cam.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); 
		startActivityForResult(cam, CAPTURE_IMG);
		
	}
	  
/*	public boolean moveFile(String source, String dest) {//파일을 해당위치로 복사하고 지운다.//Desire HD error 로 삭제
	    boolean result = false;   
	           
	    FileInputStream inputStream = null;   
	    DataOutputStream outputStream = null;   
	           
	    try {
	    	
	    	inputStream = new FileInputStream(source);   
	        outputStream = new DataOutputStream(new FileOutputStream(dest));   

		    byte[] buffer = new byte[1024];
		    int readByte;
		    while((readByte=inputStream.read(buffer))!=-1){
		    	outputStream.write(buffer, 0, readByte);
		    }
		    outputStream.flush();
	        outputStream.close();   
	        inputStream.close();   
               
	    } catch (FileNotFoundException e) {   
	        e.printStackTrace();   
	        result = false;   
	    } catch (IOException e) {   
	        e.printStackTrace();   
	        result = false;   
	    }   
	           
	    File f = new File(source);   
	    if (f.delete()) {   
	        result = true;   
	    }   
	    return result;   
		
	} */
	
	public static String getCapturePath(Activity act) {
		final String[] proj = { MediaStore.Images.ImageColumns.DATA };
		String szDateTop = "";
		Uri uris;
		
//		if( checkSDCard() ){
		uris = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//		}
//		else
//			uris = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
		
		try {
			final Cursor cursorImages = act.managedQuery(uris, proj, null, null, null);
			if (cursorImages != null && cursorImages.moveToLast()) {
				szDateTop = cursorImages.getString(0);
//				cursorImages.close();//여러 장 연속으로 찍을 때 닫으면 error. 어차피 finish 할 때 close 됨.
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		

		return szDateTop;
	}
	
	public static boolean checkSDCard() {
		String state = Environment.getExternalStorageState();
		if( state.equals(Environment.MEDIA_MOUNTED) )
			return true;
		return false;
	}

	 private void createPdf(String[] inputFile, String outputFile, boolean isPictureFile)
	 {
	     Rectangle pageSize = new Rectangle(1980, 2525);//Set the page pixel size for the image (width, height)
	     Document pdfDocument = new Document(pageSize);
	     String pdfFilePath = outputFile;
	     try
	     {
	         FileOutputStream fileOutputStream = new FileOutputStream(pdfFilePath);
	         PdfWriter writer = null;
	         writer = PdfWriter.getInstance(pdfDocument, fileOutputStream);
	         writer.open();
	         pdfDocument.open();

	         if (isPictureFile)
	         {
	        	 for(int i=0;i<index;i++) {
	        		 inputFile[i] = NewPath + inputFile[i];  
	        		 Log.i("inputFile ::", inputFile[i]);
	        		 pdfDocument.add(com.itextpdf.text.Image.getInstance(inputFile[i]));
	        	 }
	         }
	         else //if the given file is not image format ( other format document - .txt,.html,.doc etc)
	         {
//	        	 File file = new File(inputFile);
//	        	 pdfDocument.add(new Paragraph(org.apache.commons.io.FileUtils.readFileToString(file)));
	         }

	         pdfDocument.close();
	         writer.close();
	         fileOutputStream.close();
	     }
	     catch (Exception exception)
	     {
	    	 Log.i("PDF Document Making Exception!",  exception.getMessage());
	     }
	 }
	     
}