package pro.ianchen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Ian的转换类
 * Ian's change type class
 */
public class IanConvert {
    /**
     * 对象转换为字符串
     * Object to String
     * @param v 对象值 Object Value
     * @return 字符串 String
     */
    public static String ToString(Object v){
        if(v==null) return null;
        return String.valueOf(v);
    }

    /**
     * 字节转换为字符串
     * byte to String
     * @param v 字节值 byte Value
     * @return 字符串 String
     */
    public static String ToString(byte v){
        return String.valueOf(v);
    }

    /**
     * 整型转换为字符串
     * int to String
     * @param v 整型值 int Value
     * @return 字符串 String
     */
    public static String ToString(int v){
        return String.valueOf(v);
    }

    /**
     * 长整型转换为字符串
     * long to String
     * @param v 长整型值 long Value
     * @return 字符串 String
     */
    public static String ToString(long v){
        return String.valueOf(v);
    }

    /**
     * 浮点型转换为字符串
     * float to String
     * @param v 浮点型值 float Value
     * @return 字符串 String
     */
    public static String ToString(float v){
        return String.valueOf(v);
    }

    /**
     * 双精度转换为字符串
     * double to String
     * @param v 双精度值 double Value
     * @return 字符串 String
     */
    public static String ToString(double v){
        return String.valueOf(v);
    }

    /**
     * 对象转换为整型
     * Object to int
     * @param v 对象值 Object Value
     * @return 整型值 int
     */
    public static int ToInt(Object v){
        return IanConvert.ToInt(v,0);
    }

    /**
     * 对象转换为整型，如果转换失败则返回默认值
     * Object to int
     * if fail to convert
     * then return default value
     * @param v 对象值 Object Value
     * @param defVal 默认值 default Value
     * @return 整型值 int
     */
    public static int ToInt(Object v,int defVal){
        try{
            String s=String.valueOf(v);
            if(IanStringTool.IsInt(s)){
                return Integer.valueOf(s);
            }else{
                return (int)Double.parseDouble(s);
            }
        }catch(Exception e){
            return defVal;
        }
    }

    /**
     * 浮点型转换为整型
     * float to int
     * @param f 浮点型值 float Value
     * @return 整型值 int
     */
    public static int ToInt(float f){
        return (new Float(f)).intValue();
    }

    /**
     * 双精度转换为整型
     * double to int
     * @param d 双精度值 double Value
     * @return 整型值 int
     */
    public static int ToInt(double d){
        return (new Double(d)).intValue();
    }


    /**
     * 对象转换为长整型
     * Object to long
     * @param v 对象值 Object Value
     * @return 长整型值 long
     */
    public static long ToLong(Object v){
        return IanConvert.ToLong(v,0);
    }

    /**
     * 对象转换为长整型，如果转换失败，则返回默认值
     * Object to long
     * if fail to convert
     * then return default value
     * @param v 对象值 Object Value
     * @param defVal 默认值 default Value
     * @return 长整型值 long
     */
    public static long ToLong(Object v,long defVal) {
        try{
            String s=String.valueOf(v);
            if(IanStringTool.IsInt(s)){
                return Long.valueOf(s);
            }else{
                return (long)Double.parseDouble(s);
            }
        }catch(Exception e){
            return defVal;
        }
    }

    /**
     * 浮点型转换为长整型
     * float to long
     * @param f 浮点型值 float Value
     * @return 长整型值 long
     */
    public static long ToLong(float f){
        return (long)f;
    }

    /**
     * 双精度转换为长整型
     * double to long
     * @param d 双精度值 double Value
     * @return 长整型值 long
     */
    public static long ToLong(double d){
        return (new Double(d)).longValue();
    }

    /**
     * 对象转换为双精度
     * Object to double
     * @param v 对象值 Object Value
     * @return 双精度值 double
     */
    public static double ToDouble(Object v){
        return IanConvert.ToDouble(v,0);
    }

    /**
     * 对象转换为双精度，如果转换失败，则返回默认值
     * Object to double
     * if fail to convert
     * then return default value
     * @param v 对象值 Object Value
     * @param defVal 默认值 default Value
     * @return 双精度值 double
     */
    public static double ToDouble(Object v,double defVal) {
        try{
            return Double.valueOf(String.valueOf(v));
        }catch(Exception e){
            return defVal;
        }
    }

    /**
     * 整型转换为双精度
     * int to double
     * @param v 整型值 int Value
     * @return 双精度值 double
     */
    public static double ToDouble(int v){
        return (double)v;
    }

    /**
     * 长整型转换为双精度
     * long to double
     * @param v 长整型值 long Value
     * @return 双精度值 double
     */
    public static double ToDouble(long v){
        return (double)v;
    }

    /**
     * 浮点型转换为双精度
     * float to double
     * @param v 浮点型值 float Value
     * @return 双精度值 double
     */
    public static double ToDouble(float v){
        return (double)v;
    }

    /**
     * 对象转换为浮点型
     * Object to float
     * @param v 对象值 Object Value
     * @return 浮点型值 float
     */
    public static float ToFloat(Object v){
        return IanConvert.ToFloat(v,0);
    }

    /**
     * 对象转换为浮点型，如果转换失败，则返回默认值
     * Object to float
     * if fail to convert
     * then return default value
     * @param v 对象值 Object Value
     * @param defVal 默认值 default Value
     * @return 浮点型值 float
     */
    public static float ToFloat(Object v,float defVal) {
        try{
            return Float.valueOf(String.valueOf(v));
        }catch(Exception e){
            return defVal;
        }
    }

    /**
     * 整型转换为浮点型
     * int to float
     * @param v 整型值 int Value
     * @return 浮点型值 float
     */
    public static float ToFloat(int v){
        return (float)v;
    }

    /**
     * 长整型转换为浮点型
     * long to flaot
     * @param v 长整型值 long Value
     * @return 浮点型值 float
     */
    public static float ToFloat(long v){
        return (float)v;
    }

    /**
     * 双精度转换为浮点型
     * double to float
     * @param v 双精度值 double Value
     * @return 浮点型值 float
     */
    public static float ToFloat(double v){
        return (float)v;
    }

    /**
     * 对象转换为字节
     * Object to byte
     * @param v 对象值 Object Value
     * @return 字节值 byte
     * @throws Exception
     */
    public static byte ToByte(Object v) throws Exception {
        int i=IanConvert.ToInt(v);
        if(i==-128) return -128;
        return (byte)(i%128);
    }

    /**
     * 对象转换为短整型
     * Object to short
     * @param v 对象值 Object Value
     * @return 短整型值 short
     * @throws Exception
     */
    public static short ToShort(Object v) throws Exception {
        int i=IanConvert.ToInt(v);
        if(i==-32768) return -32768;
        return (short)(i%32768);
    }

    /**
     * 对象转换为布尔型
     * Object to boolean
     * @param v 对象值 Object Value
     * @return 布尔型值 boolean
     */
    public static boolean ToBoolean(Object v){
        return IanConvert.ToBoolean(v,false);
    }

    /**
     * 对象转换为布尔型，如果转换失败，则返回默认值
     * Object to boolean
     * if fail to convert
     * then return default value
     * @param v 对象值 Object Value
     * @param defVal 默认值 default Value
     * @return 布尔型值 boolean
     */
    public static boolean ToBoolean(Object v,boolean defVal) {
        try{
            return (Boolean) v;
        }catch(Exception e){
            return defVal;
        }
    }

    /**
     * 整型转换为布尔型
     * int to boolean
     * @param v 整型值 int Value
     * @return 布尔型值 boolean
     */
    public static boolean ToBoolean(int v){
        return v>0;
    }

    /**
     * 长整型转换为布尔型
     * long to boolean
     * @param v 长整型值 long Value
     * @return 布尔型值 boolean
     */
    public static boolean ToBoolean(long v){
        return  v>0;
    }

    /**
     * 浮点型转换为布尔型
     * float to boolean
     * @param v 浮点型值 float Value
     * @return 布尔型值 boolean
     */
    public static boolean ToBoolean(float v){
        return v>0;
    }

    /**
     * 双精度转换为布尔型
     * double to boolean
     * @param v 双精度值 double Value
     * @return 布尔型值 boolean
     */
    public static boolean ToBoolean(double v){
        return  v>0;
    }

    /**
     * 对象转换为日期型
     * Object to Date
     * @param v 对象值 Object Value
     * @param isDateNullDeal 是否在参数v为null时进行处理 whether to deal when the parameter v is null
     * @return 日期 Date
     */
    public static Date ToDate(Object v, boolean isDateNullDeal) throws Exception {
        if(isDateNullDeal){
            if(v==null){
                v=new Date(Long.MAX_VALUE);
            }
        }
        return IanConvert.ToDate(v);
    }

    /**
     * 对象转换为日期
     * Object to Date
     * @param v 对象值 Object Value
     * @return 日期 Date
     */
    public static Date ToDate(Object v) throws Exception {
        if(v==null) throw new Exception("转换对象不能为空");
        if(v instanceof String ){
            String s=IanConvert.ToString(v);
            if(s.indexOf("-")>0){
                if(s.length()==10){
                    return IanConvert.ToDate(s,"yyyy-MM-dd");
                }else if(s.length()==19){
                    return IanConvert.ToDate(s,"yyyy-MM-dd HH:mm:ss");
                }else if(s.length()==22){
                    return IanConvert.ToDate(s,"yyyy-MM-dd HH:mm:ssSSS");
                }else if(s.length()==23){
                    return IanConvert.ToDate(s,"yyyy-MM-dd HH:mm:ss SSS");
                }else{
                    try{
                        SimpleDateFormat sdf = new SimpleDateFormat();
                        return sdf.parse(IanConvert.ToString(v));
                    }catch (Exception e){
                        throw new Exception("未知的日期时间格式"+s);
                    }
                }
            }else if(s.length()==8){
                return IanConvert.ToDate(s,"yyyyMMdd");
            }else if(s.length()==14){
                return IanConvert.ToDate(s,"yyyyMMddHHmmss");
            }else if(s.length()==17){
                return IanConvert.ToDate(s,"yyyyMMddHHmmssSSS");
            }else if(IanStringTool.IsInt(s)){
                return IanConvert.ToDate(IanConvert.ToLong(s));
            }else if(IanStringTool.IsFloat(s)){
                return IanConvert.ToDate(IanConvert.ToDouble(s));
            }
            else{
                try{
                    SimpleDateFormat sdf = new SimpleDateFormat();
                    return sdf.parse(IanConvert.ToString(v));
                }catch (Exception e){
                    throw new Exception("未知的日期时间格式"+s);
                }
            }
        }else if(v instanceof Integer || v instanceof Long){
            long l=IanConvert.ToLong(v);
            return new Date(l);
        }else if(v instanceof java.sql.Date){
            return new Date(((java.sql.Date)v).getTime());
        }else if(v instanceof java.sql.Timestamp){
            return new Date(((java.sql.Timestamp)v).getTime());
        }else if(v instanceof java.util.Calendar){
            return ((Calendar)v).getTime();
        }else if(v instanceof Float || v instanceof Double){
            double value=0;
            int day=IanConvert.ToInt(v);
            double ts=(value - day)*3600*24;
            Date dt=new Date(0);
            dt=IanStringTool.AddDays(dt,day);
            dt=IanStringTool.AddSeconds(dt,IanConvert.ToInt(ts));
            return dt;
        }
        return (Date)v;
    }

    /**
     * 将日期转换为指定格式的字符串表示
     * 格式如 yyyy-MM-dd HH:mm:ss SSS等
     * Convert String to Date with special format
     * @param s String Value
     * @param format Date format like yyyy-MM-dd HH:mm:ss SSS
     * @return 日期 Date
     * @throws ParseException
     */
    public static Date ToDate(String s,String format) throws ParseException {
        SimpleDateFormat fmt=new SimpleDateFormat(format);
        return new Date(fmt.parse(s).getTime());
    }

    /**
     * 将指定格式的日期字符串转化为日期类型
     * 格式如 yyyy-MM-dd HH:mm:ss SSS等
     * Convert the Date to String by special format
     * @param dt 日期值 Date
     * @param format 指定的日期格式 如yyyy-MM-dd HH:mm:ss SSS
     *               Date format like yyyy-MM-dd HH:mm:ss SSS
     * @return 字符串 String with Date format
     */
    public static  String FormatDate(Date dt,String format){
        SimpleDateFormat fmt=new SimpleDateFormat(format);
        return fmt.format(dt);
    }

    /**
     * 将日期类型转换为可以在MySQL操作时使用的字符串格式
     * Convert Date to String Value with MySQL format
     * @param dt 日期值 Date Valye
     * @return 可以在MySQL操作时使用的字符串 String with MySQL format
     */
    public static String FormatMySQLDate(Date dt){
        return IanConvert.FormatDate(dt,"yyyy-MM-dd HH:mm:ss");
    }
}
