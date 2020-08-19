package com.java.until;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUntil  extends org.apache.commons.lang3.time.DateUtils{

    private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
            "yyyy.MM.dd HH:mm", "yyyy.MM","yyyy-MM-dd HH:mm:ss.SSS" };


    /**
     * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
     * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return  parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }
    
    
    /** 
     * 获取未来 第 past 天的日期  / 过去第 -past 天的日期
     * @param past 
     * @return 
     */  
    public static String getDateCalDay(int past) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);  
        Date today = calendar.getTime();  
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");  
        String result = format.format(today);  
        return result;  
    } 
    
    /** 
     * 获取未来 past月或者过去-past月
     * @param past 
     * @return 
     */  
    public static String getDateCalMonth(int past) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + past);  
        Date today = calendar.getTime();  
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");  
        String result = format.format(today);  
        return result;  
    }
}
