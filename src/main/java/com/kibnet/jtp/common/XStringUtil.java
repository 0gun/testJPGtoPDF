package com.kibnet.jtp.common;

import java.util.regex.Pattern;

import android.text.InputFilter;
import android.text.Spanned;

public class XStringUtil {
	
	public static String filteringFilePath(String path) {
		if (path.contains("/"))
			path = path.replace("/", " ");
	    if (path.contains("<"))
	    	path = path.replace("<", "(");
	    if (path.contains(">"))
	    	path = path.replace(">", ")");
	    if (path.contains(":"))
	    	path = path.replace(":", "-");
	    if (path.endsWith("..."))
	    	path = path.substring(0, path.length()-4); // '...'�տ��ִ� ' '������ ���ֱ� ���� -4

	    return path;
	}
	
	/** ���ҹ�, ���ڸ� �Է� Ư������ ���� **/
	public static InputFilter filterAlphaNum = new InputFilter() {
		public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
			Pattern ps = Pattern.compile("^[a-z0-9]+$");//a-zA-Z0-9 :: ������� �Է� ��
			if (!ps.matcher(source).matches()) {
				return "";
			}
			return null;
		}
	};
	
	/** �ѱ۸� �ޱ� **/
	public static InputFilter filterKor = new InputFilter() {
		public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
			Pattern ps = Pattern.compile("^[��-����-��]+$");
			if (!ps.matcher(source).matches()) {
				return "";
			}
			return null;
		}
	};

	
}


