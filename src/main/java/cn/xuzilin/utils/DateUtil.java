package cn.xuzilin.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getNowDateStr() {
        Date currentTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String res = format.format(currentTime);
        return res;
    }
    public static Date getNowDate() {
        return strToDate(getNowDateStr());
    }
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
    public static String dateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String res = format.format(date);
        return res;
    }
    public static long subDateByDay(String newDate,String oldDate){
        long oldDateTime = strToDate(oldDate).getTime();
        long newDateTime = strToDate(newDate).getTime();
        long res = (newDateTime - oldDateTime) / (1000 * 60 * 60 * 24);
        return res;
    }
    public static long subDateByDay(Date newDate,Date oldDate){
        long oldDateTime = oldDate.getTime();
        long newDateTime = newDate.getTime();
        long res = (newDateTime - oldDateTime) / (1000 * 60 * 60 * 24);
        return res;
    }
    public static long daysToNow(Date oldDate){
        Date currentTime = new Date();
        return subDateByDay(currentTime,oldDate);
    }
    public static String formatDate(Date date){
        return dateToStr(date);
    }
}
