package com.kibnet.jtp.common;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class XDateUtil {

	/**
     * 시스템의 오늘의 날짜를 문자열로 얻는다.
     *
     * @param   strType  반환할 문자열 형태<br>
	 *  example - YYYY,YYYYMM,YYYYMMDD,YYYYMMDDHH,YYYYMMDDHHNN,YYYYMMDDHHNNSS 의 6가지 형태<br>
	 *  (YYYY: 년도, YYYYMM: 년월, YYYYMMDD: 년월일, YYYYMMDDHH: 년월일시, YYYMMDDHHNN: 년월일시분, YYYYMMDDHHNNSS: 년월일시분초)
     * @return 지정된 형태의 문자열
     */
    public static String getDate(String strDateType) {
        Calendar today= new GregorianCalendar();
        String strYear, strMonth, strDay, strHour, strMin, strSec;
        String strDate;
        int nYear, nMonth, nDay, nHour, nMin, nSec;

        nYear= (int)(today.get(Calendar.YEAR));
        strYear= "" + nYear;

        nMonth= (int)(today.get(Calendar.MONTH)+1);
        if(nMonth < 10) strMonth= "0" + nMonth;
        else strMonth= "" + nMonth;

        nDay= (int)(today.get(Calendar.DAY_OF_MONTH));
        if(nDay < 10) strDay= "0" + nDay;
        else strDay= "" + nDay;

        nHour= (int)today.get(Calendar.HOUR_OF_DAY);
        if(nHour < 10) strHour= "0" + nHour;
        else strHour= "" + nHour;

        nMin= (int)today.get(Calendar.MINUTE);
        if(nMin < 10) strMin= "0" + nMin;
        else strMin= "" + nMin;

        nSec= (int)today.get(Calendar.SECOND);
        if(nSec < 10) strSec= "0" + nSec;
        else strSec= "" + nSec;

        if(strDateType.equalsIgnoreCase("YYYY"))
            strDate= strYear;
        else if(strDateType.equalsIgnoreCase("YYYYMM"))
            strDate= strYear + strMonth;
        else if(strDateType.equalsIgnoreCase("YYYYMMDD"))
            strDate= strYear + strMonth + strDay;
        else if(strDateType.equalsIgnoreCase("YYYYMMDDHH"))
            strDate= strYear + strMonth + strDay + strHour;
        else if(strDateType.equalsIgnoreCase("YYYYMMDDHHNN"))
            strDate= strYear + strMonth + strDay + strHour + strMin;
        else if(strDateType.equalsIgnoreCase("YYYYMMDDHHNNSS"))
            strDate= strYear + strMonth + strDay + strHour + strMin + strSec;
        else if(strDateType.equalsIgnoreCase("MM"))
        	strDate= strMonth;
        else
        {
            return null;
        }
        return strDate;
    }
    
    public static long getLongDate(String strDate) {
    	String s = "YYYY-MM-DD AM HH:NN:SS";
    	int hLen = s.length() - strDate.length();

    	int i = 0;
    	int year = Integer.valueOf(strDate.substring(i, i=i+4));
    	int month = Integer.valueOf(strDate.substring(i=i+1, i=i+2));
    	int day = Integer.valueOf(strDate.substring(i=i+1, i=i+2));
    	String am = strDate.substring(i=i+1, i=i+2);
    	int hour = Integer.valueOf(strDate.substring(i=i+1, i=i+2-hLen)) + (am.equalsIgnoreCase("오후") ? 12 : 0);
    	int min = Integer.valueOf(strDate.substring(i=i+1, i=i+2));
    	int sec = Integer.valueOf(strDate.substring(i=i+1));
    	Calendar c = new GregorianCalendar();
    	c.set(year, month-1, day, hour==24?0:hour, min, sec);

    	long l = c.getTimeInMillis();
    	return l;
    }
}