package pro.ianchen;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Ian的字符串操作类
 * Ian string operator class
 */
public class IanStringTool {
    /**
     * 获取当前时间戳，单位秒
     * get current timestamp, unit is second
     * @return timestamp's long value
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis()/1000;
    }

    /**
     * 获取当前时间戳，单位毫秒
     * get current timestamp, unit is millisecond
     * @return timestamp's long value
     */
    public static long getCurrentTimestampMs() {
        return System.currentTimeMillis();
    }

    /**
     * 与JS兼容的最大日期3000-1-1
     * the max date (3000-1-1), it is compatible with the javascript
     * @return the max date (3000-1-1)
     */
    public static Date getJavascriptMaxDateTime(){
        Calendar c= Calendar.getInstance();
        c.set(Calendar.YEAR,3000);
        c.set(Calendar.MONTH,0);
        c.set(Calendar.DATE,1);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        return c.getTime();
    }

    /**
     * 与JS兼容的最大日期3000-1-1 MySQL写入值
     * the max date (3000-1-1， MySQL format write value), it is compatible with the javascript
     * @return 与JS兼容的最大日期MySQL写入值 the max date (3000-1-1， MySQL format write value)
     */
    public static String getMySQLJavascriptMaxDateTime(){
        return IanConvert.FormatMySQLDate(IanStringTool.getJavascriptMaxDateTime());
    }

    /**
     *与JS兼容的最小日期1970-1-1
     * the min date (1970-1-1), it is compatible with the javascript
     * @return the min date (1970-1-1)
     */
    public static Date getJavascriptMinDateTime(){
        Calendar c= Calendar.getInstance();
        c.set(Calendar.YEAR,1970);
        c.set(Calendar.MONTH,0);
        c.set(Calendar.DATE,1);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        return c.getTime();
    }

    /**
     * 与JS兼容的最小日期1970-1-1 MySQL写入值
     * the min date (1970-1-1， MySQL format write value), it is compatible with the javascript
     * @return 与JS兼容的最小日期MySQL写入值 the min date (1970-1-1， MySQL format write value)
     */
    public static String getMySQLJavascriptMinDateTime(){
        return IanConvert.FormatMySQLDate(IanStringTool.getJavascriptMinDateTime());
    }

    /**
     * 按时间获取该天零点
     * get the date (the time is zero of the date)
     * @param d 输入时间 the input date value
     * @return 输入时间对应的该天零点 the time is zero of the input date
     * @throws Exception
     */
    public static Date GetDate(Date d) throws Exception {
        return IanConvert.ToDate(IanConvert.FormatDate(d,"yyyy-MM-dd")+" 00:00:00");
    }

    /**
     * 获取当前时间
     * get the current time
     * @return 当前时间 the current time
     */
    public static Date Now(){
        return new Date(IanStringTool.getCurrentTimestampMs());
    }

    /**
     * 获取当天零点
     * get the time zero of today
     * @return 当天零点 time zero of today
     * @throws Exception
     */
    public static Date Today() throws Exception {
        return IanStringTool.GetDate(IanStringTool.Now());
    }

    /**
     * 获取当前时间的MySQL写入值
     * get the current time that can be writed to MySQL
     * @return 当前时间的MySQL写入值 the current time that can be writed to MySQL
     */
    public static String MySQLNow(){
        return IanConvert.FormatMySQLDate(IanStringTool.Now());
    }

    /**
     * get the time zero of today that can be writed to MySQL
     * @return 获取当天零点的MySQL写入值 the time zero of today that can be writed to MySQL
     * @throws Exception
     */
    public static String MySQLToday() throws Exception { return IanConvert.FormatMySQLDate(IanStringTool.Today());}

    /**
     * 指定时间增减豪秒数
     * get the time which increase or decrease the specified number of milliseconds
     * the specified number of milliseconds is int value
     * @param d1 the input time
     * @param millis the specified number of milliseconds, a positive number is an increase and a negative number is a decrease
     * @return the calculated result
     */
    public static Date AddMillis(Date d1, int millis){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.MILLISECOND,millis);
        return c.getTime();
    }

    /**
     * 指定时间增减豪秒数
     * get the time which increase or decrease the specified number of milliseconds
     * the specified number of milliseconds is long value
     * @param d1 the input time
     * @param millis the specified number of milliseconds, a positive number is an increase and a negative number is a decrease
     * @return the calculated result
     */
    public static Date AddMillis(Date d1, long millis){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.MILLISECOND,(int)millis);
        return c.getTime();
    }

    /**
     * 指定时间增减秒数
     * get the time which increase or decrease the specified number of seconds
     * the specified number of seconds is int value
     * @param d1 the input time
     * @param seconds the specified number of seconds, a positive number is an increase and a negative number is a decrease
     * @return the calculated result
     */
    public static Date AddSeconds(Date d1, int seconds){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.SECOND,seconds);
        return c.getTime();
    }

    /**
     * 指定时间增减秒数
     * get the time which increase or decrease the specified number of seconds
     * the specified number of seconds is long value
     * @param d1 the input time
     * @param seconds the specified number of seconds, a positive number is an increase and a negative number is a decrease
     * @return the calculated result
     */
    public static Date AddSeconds(Date d1, long seconds){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.SECOND,(int)seconds);
        return c.getTime();
    }

    /**
     * 指定时间增减分钟数
     * get the time which increase or decrease the specified number of minutes
     * the specified number of minutes is int value
     * @param d1 the input time
     * @param minutes the specified number of minutes, a positive number is an increase and a negative number is a decrease
     * @return the calculated result
     */
    public static Date AddMinutes(Date d1, int minutes){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.MINUTE,minutes);
        return c.getTime();
    }

    /**
     * 指定时间增减分钟数
     * get the time which increase or decrease the specified number of minutes
     * the specified number of minutes is long value
     * @param d1 the input time
     * @param minutes the specified number of minutes, a positive number is an increase and a negative number is a decrease
     * @return the calculated result
     */
    public static Date AddMinutes(Date d1, long minutes){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.MINUTE,(int)minutes);
        return c.getTime();
    }

    /**
     * 指定时间增减小时数
     * get the time which increase or decrease the specified number of hours
     * the specified number of hours is int value
     * @param d1 the input time
     * @param hours the specified number of hours, a positive number is an increase and a negative number is a decrease
     * @return the calculated result
     */
    public static Date AddHour(Date d1, int hours){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.HOUR,hours);
        return c.getTime();
    }

    /**
     * 指定时间增减小时数
     * get the time which increase or decrease the specified number of hours
     * the specified number of hours is long value
     * @param d1 the input time
     * @param hours the specified number of hours, a positive number is an increase and a negative number is a decrease
     * @return the calculated result
     */
    public static Date AddHour(Date d1, long hours){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.HOUR,(int)hours);
        return c.getTime();
    }

    /**
     * 指定时间增减天数
     * get the time which increase or decrease the specified number of days
     * the specified number of days is int value
     * @param d1 the input time
     * @param days the specified number of days, a positive number is an increase and a negative number is a decrease
     * @return the calculated result
     */
    public static Date AddDays(Date d1, int days){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.DAY_OF_YEAR,days);
        return c.getTime();
    }

    /**
     * 指定时间增减天数
     * get the time which increase or decrease the specified number of days
     * the specified number of days is long value
     * @param d1 the input time
     * @param days the specified number of days, a positive number is an increase and a negative number is a decrease
     * @return the calculated result
     */
    public static Date AddDays(Date d1, long days){
        return IanStringTool.AddDays(d1,(int)days);
    }

    /**
     * 指定时间增减月数
     * get the time which increase or decrease the specified number of months
     * the specified number of months is long value
     * @param d1 the input time
     * @param months the specified number of months, a positive number is an increase and a negative number is a decrease
     * @return the calculated result
     */
    public static Date AddMonths(Date d1, long months){
        return IanStringTool.AddMonths(d1,(int)months);
    }

    /**
     * 指定时间增减月数
     * get the time which increase or decrease the specified number of months
     * the specified number of months is int value
     * @param d1 the input time
     * @param months the specified number of months, a positive number is an increase and a negative number is a decrease
     * @return the calculated result
     */
    public static Date AddMonths(Date d1, int months){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.MONTH,months);
        return c.getTime();
    }

    /**
     * 指定时间增减年数
     * get the time which increase or decrease the specified number of years
     * the specified number of years is long value
     * @param d1 the input time
     * @param years the specified number of years, a positive number is an increase and a negative number is a decrease
     * @return the calculated result
     */
    public static Date AddYears(Date d1, long years){
        return IanStringTool.AddYears(d1,(int)years);
    }

    /**
     * 指定时间增减年数
     * get the time which increase or decrease the specified number of years
     * the specified number of years is int value
     * @param d1 the input time
     * @param years the specified number of years, a positive number is an increase and a negative number is a decrease
     * @return the calculated result
     */
    public static Date AddYears(Date d1, int years){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.YEAR,years);
        return c.getTime();
    }

    /**
     * 检测值
     * check whether the input value is empty
     * if the input value is empty then will throw out the exception with the message( the input value name can not be empty)
     * if the input value is not empty the the input value will trim the left and right blank.
     * @param value 值 the input value
     * @param valueName 值名称 the input value name
     * @return 检测处理后的值 the result of checked and dealed
     * @throws Exception
     */
    public static String Check(String value, String valueName) throws Exception {
        if(value==null) value="";
        value=value.trim();
        if(value.length()==0) throw new Exception(valueName+"不能为空");
        return value;
    }

    /**
     * 处理包含在一组值中的特定值
     * deal the input value
     * if no values is specified then will be handled according to the following logic.
     *   if the input value is empty then return default value (parameter defValue).
     *   if the input value is not empty then return the value after be trim the left and right blank.
     * if values is specified then will be handled according to the following logic.
     *   if the input value is not in the parameter values then return  the value after be trim the left and right blank.
     *   if the input value is in the parameter values then return the input value.
     * @param value 被处理特定值 the input value
     * @param defValue 特定值不在组值中时，默认返回的值，这个值必须也是组值中的一个 default value
     * @param values 组值 the value array which use to check the value range
     * @return 处理后的特定值 the value after be dealed
     * @throws Exception
     */
    public static String Deal(String value, String defValue, String... values) throws Exception {
        if(values==null) values=new String[0];
        {
            boolean b=false;
            for(String a:values){
                if(a.equals(defValue)){
                    b=true;
                    break;
                }
            }
            if(!b) throw new Exception("默认值不包含在可选值中");
        }
        {
            for(String a:values){
                if(a.equals(value)) return value;
            }
            return defValue;
        }
    }

    /**
     * 处理值
     * deal the input value
     * if the input value is empty then return default value (parameter defValue).
     * if the input value is not empty then return the value after be trim the left and right blank.
     * @param value 值 the input value
     * @param defValue 默认值 the default value
     * @return 处理后的值 the value after be dealed
     */
    public static String Deal(String value, String defValue){
        if(value==null) value="";
        value=value.trim();
        if(value.length()==0) value=defValue;
        return value;
    }

    /**
     * 生成Guid值
     * generate the global unique id
     * @return Guid the global unique id
     */
    public static String Guid(){
        UUID uuid= UUID.randomUUID();
        return String.valueOf(uuid);
    }

    /**
     * 生成Guid值(不含-)
     * generate the global unique id(exclude "-")
     * @return Guid(不含-) the global unique id(exclude "-")
     */
    public static String Guid2(){
        return IanStringTool.Guid().replace("-","");
    }

    /**
     * 生成为0的Guid值
     * generate the empty global unique id
     * @return 00000000-0000-0000-0000-000000000000
     */
    public static String GuidEmpty(){
        return "00000000-0000-0000-0000-000000000000";
    }

    /**
     * 是否是邮件格式
     * check the input value is a legal email address
     * @param v 字符串 the input value
     * @return 是否 check result
     */
    public static boolean IsMail(String v){
        v=IanStringTool.Deal(v,"");
        return Pattern.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$",v);
    }

    /**
     * 是否是整型
     * check the input value is a legal int value
     * @param v 整型字符串 the input value
     * @return 是否 check result
     * @throws Exception
     */
    public static boolean IsInt(String v) throws Exception {
        v=IanStringTool.Check(v,"参数值");
        return Pattern.matches("^(-)?\\d+$",v);
    }

    /**
     * 是否是浮点型
     * check the input value is a legal float value
     * @param v 浮点型字符串 the input value
     * @return 是否 check result
     * @throws Exception
     */
    public static boolean IsFloat(String v) throws Exception {
        v=IanStringTool.Check(v,"参数值");
        return Pattern.matches("^((-)?(\\d+\\.\\d+))$",v);
    }

    /**
     * 获取完整的错误信息
     * get the full exception information
     * @param ex 错误 exception
     * @return 错误信息 the exception informtation
     */
    public static String GetFullExceptionInfo(Exception ex){
        if(ex==null) return "";
        StringBuilder sb=new StringBuilder();
        sb.append(ex.getMessage()+"\r\n");
        sb.append(IanStringTool.GetExceptionStackInfo(ex));
        return sb.toString();
    }

    /**
     * 获取完整的错误堆栈信息
     * get the full exception stack information
     * @param ex 错误 exception
     * @return 错误堆栈信息 exception stack information
     */
    public static String GetExceptionStackInfo(Exception ex){
        if(ex==null) return "";
        StringBuilder sb=new StringBuilder();
        StackTraceElement[] sts=ex.getStackTrace();
        if(sts!=null&&sts.length>0){
            for(StackTraceElement st : sts){
                sb.append(st.toString()+"\r\n");
            }
        }
        return sb.toString();
    }
}
