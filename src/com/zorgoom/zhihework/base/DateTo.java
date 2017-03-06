package com.zorgoom.zhihework.base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期格式转换
 * @author Administrator
 *
 */
public class DateTo {
    //设置i时间的格式
    private static String format = "yyyy-MM-dd HH:mm:ss";
    private static String format1 = "yyyyMMddHHmmss";
    private static String formatday = "yyyy-MM-dd";
    private static String formatMonth = "yyyy年MM月";
    private static String formatYear = "yyyy年";
    //将futile date 类型的数据转换成String
    public static String date2String(Date date)
    {
        if(null == date){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String result = sdf.format(date);
        return result;
    }
    
    public static String date2String2(Date date)
    {
        if(null == date){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format1);
        String result = sdf.format(date);
        return result;
    }
    
    public static String toStringDay(Date date)
    {
        DateFormat df = new SimpleDateFormat(formatday);
        return df.format(date);
    }
    
    public static Date toDate(String date) throws Exception
    {
        if(null == date){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(date);
    }

    public static Date toDateDay(String date) throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat(formatday);
        return sdf.parse(date);
    }
    
    public static Date toDateMonth(String date) throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat(formatMonth);
        return sdf.parse(date);
    }
    public static Date toDateYear(String date) throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat(formatYear);
        return sdf.parse(date);
    }

    public static Date theDateAfter(Date date,int nDayAfter) throws Exception
    {
        Calendar calendar = Calendar.getInstance();   
        calendar.setTime(date);  

        calendar.add(Calendar.DATE,nDayAfter);
        return calendar.getTime();
    }

    
    public static Date theDateBefore(Date dateNow,int nDayBefore) throws Exception
    {
        Calendar calendar = Calendar.getInstance();   
        calendar.setTime(dateNow);  

        calendar.add(Calendar.DATE,-nDayBefore);
        return calendar.getTime();
    }

    public static String theDateBeforeNow(int nDayBefore) throws Exception
    {
        Date dateNow = new Date();
        
        Calendar calendar = Calendar.getInstance();   
        calendar.setTime(dateNow);  

        calendar.add(Calendar.DATE,-nDayBefore);
        return DateTo.date2String(calendar.getTime());
         
    }
    
    
    public static String  sysDate()
    {
        DateFormat df = new SimpleDateFormat(format);
        Date javadate = new Date();
        
        
        return df.format(javadate);
    }

    
    public static String  sysDateDay()
    {
        DateFormat df = new SimpleDateFormat(formatday);
        Date javadate = new Date();
        
        return df.format(javadate);
    }
    
    public static String toStringMonth(Date date)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM");
        return df.format(date);
    }
    public static String toStringYear(Date date)
    {
        DateFormat df = new SimpleDateFormat("yyyy");
        return df.format(date);
    }   
}
