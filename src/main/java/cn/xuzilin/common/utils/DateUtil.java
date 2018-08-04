package cn.xuzilin.common.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static String getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String res = format.format(currentTime);
        return res;
    }
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
    public static long subDateByDay(String newDate,String oldDate){
        long oldDateTime = strToDate(oldDate).getTime();
        long newDateTime = strToDate(newDate).getTime();
        long res = (newDateTime - oldDateTime) / (1000 * 60 * 60 * 24);
        return res;
    }
}
