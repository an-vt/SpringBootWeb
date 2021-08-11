package com.vuan.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
	public static String DDHHMM = "ddHHmm";
	public static String DD_MM_YYYY = "dd/MM/yyyy";
	public static String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
	public static String DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";
	public static String MM_YYYY = "MM/yyyy";
	public static String HH_MM_SS = "HH:mm:ss";
	
	public static Date getDate() {
		java.util.Date  date=new java.util.Date();
		java.sql.Date buyDate=new java.sql.Date(date.getTime());
		return buyDate;
	}
	
	public static Date parseDate(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

//	public static String formatDate(Date date, String pattern) {
//		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//		return sdf.format(date);
//	}
}
