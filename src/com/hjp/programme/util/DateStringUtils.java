package com.hjp.programme.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类
 * @author cch
 *
 */
public class DateStringUtils {
	/**
	 * 将字符串转换成date
	 * @param dateString
	 * @param format
	 * @return
	 */
	public static Date getDateFromString(String dateString, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		Date date = new Date();
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			return new Date();
		}
		System.out.println(date.toString());
		return date;
	}
	
	/**
	 * 将时间转换成响应的字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getStringFromDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		String dateString = "";
		dateString = sdf.format(date);
		return dateString;
	}
	
	/**
	 * 两个double型的数相乘，避免精度丢失
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double mul(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.multiply(bd2).doubleValue(); 
	}
	
	/**
	 * 将两个数相除的结果转成满意的含小数的数值
	 * @param dividend	除数
	 * @param divsor	被除数
	 * @param pointNum	小数保留位数
	 * @return
	 */
	public static double getDoubleFormLong(long dividend, long divsor, int pointNum) {
		double result = 0.00;
		
		if (pointNum <= 0) {
			return 0;
		}
		
		if (divsor == 0) {
			return 0;
		}
		
		//余数
		//double remainder = dividend%divsor;
		result = (double)dividend/(double)divsor;
		//result = ib + remainder;
		
		String formatString = "###.";
		for (int i = 0; i < pointNum; i++) {
			formatString = formatString + "0";
		}
		
		DecimalFormat df = new DecimalFormat(formatString);
		result = Double.parseDouble(df.format(result));
		
		return result;
	}
}
