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
	    	path = path.substring(0, path.length()-4); // '...'앞에있는 ' '공백을 없애기 위해 -4

	    return path;
	}
	
	/** 영소문, 숫자만 입력 특수문자 제한 **/
	public static InputFilter filterAlphaNum = new InputFilter() {
		public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
			Pattern ps = Pattern.compile("^[a-z0-9]+$");//a-zA-Z0-9 :: 영대까지 입력 시
			if (!ps.matcher(source).matches()) {
				return "";
			}
			return null;
		}
	};
	
	/** 한글만 받기 **/
	public static InputFilter filterKor = new InputFilter() {
		public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
			Pattern ps = Pattern.compile("^[ㄱ-ㅎ가-흐]+$");
			if (!ps.matcher(source).matches()) {
				return "";
			}
			return null;
		}
	};

	
}


