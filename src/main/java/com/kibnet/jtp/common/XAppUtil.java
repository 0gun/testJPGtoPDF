package com.kibnet.jtp.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;

public class XAppUtil {
	private final static int MAX_SDK_VERSION = 8;

	public static void endProcess(final Context context, Activity act) {
		int sdk_ver = Build.VERSION.SDK_INT;
		final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		if (sdk_ver < 8) {
			am.restartPackage(context.getPackageName());
		} else {
			if (act != null)
				act.finish();
			// am.killBackgroundProcesses(context.getPackageName());
		}
	}

	public static AlertDialog.Builder getDefaultAlertDialogBuilder(Context context, String title, String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (title != null && !title.equals(""))
			builder.setTitle(title);
		if (msg != null && !msg.equals(""))
			builder.setMessage(msg);

		return builder;
	}

	public static String getCapturePath(Activity act, boolean isVideo) {
		final String[] proj = { isVideo ? MediaStore.Video.VideoColumns.DATA : MediaStore.Images.ImageColumns.DATA };
		String szDateTop = "";
		final Uri uris = isVideo ? MediaStore.Video.Media.EXTERNAL_CONTENT_URI : MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		try {
			final Cursor cursorImages = act.managedQuery(uris, proj, null, null, null);
			if (cursorImages != null && cursorImages.moveToLast()) {
				szDateTop = cursorImages.getString(0);
				cursorImages.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return szDateTop;
	}

	public static String getRealPathFromURI(Activity act, Uri contentUri, boolean isVideo) {
		final String column = isVideo ? MediaStore.Video.Media.DATA : MediaStore.Images.Media.DATA;
		final String [] proj = { column };
		Cursor cursor = act.managedQuery( contentUri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(column);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public static boolean checkTelecom(final Context context) {
		TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		String SOName = manager.getSimOperatorName();
		String NOName = manager.getNetworkOperatorName();
		String[] kt = {"olleh", "KT", "KTF"};
		for(int i = 0; i<kt.length; i++){
			if(kt[i].equalsIgnoreCase(NOName) || kt[i].equalsIgnoreCase(SOName))
				return true;
		}
		// Æù ¸ðµ¨ 
//		String[] models = getResources().getStringArray(R.array.medel_name);
//		for(int i=0; i<models.length; i++) {
//			if( modelName.equals(models[i]) )
//				return true;
//		}
		return false;
	}

	public static boolean checkSDKVersion(final Context context) {
		int sdk_int = Build.VERSION.SDK_INT;
		if( sdk_int > MAX_SDK_VERSION )
			return false;
		return true;
	}

	/*public static void clearApplicationCache(XActivity act, File dir) {
		if (dir == null)
			dir = act.getCacheDir();

		if (dir == null)
			return;

		File[] files = dir.listFiles();
		if (files == null)
			return;

		try {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory())
					clearApplicationCache(act, files[i]);
				else
					files[i].delete();
			}
		} catch (Exception e) {
		}
	}*/
}
