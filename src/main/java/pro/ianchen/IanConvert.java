package pro.ianchen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Ian的转换类
 */
public class IanConvert {
    /**
     * 转换为字符串
     * @param v
     * @return
     */
    public static String ToString(Object v){
        if(v==null) return null;
        return String.valueOf(v);
    }

    /**
     * byte转换为字符串
     * @param v
     * @return
     */
    public static String ToString(byte v){
        return String.valueOf(v);
    }

    /**
     * byte转换为字符串
     * @param v
     * @return
     */
    public static String ToString(int v){
        return String.valueOf(v);
    }

    /**
     * byte转换为字符串
     * @param v
     * @return
     */
    public static String ToString(long v){
        return String.valueOf(v);
    }

    /**
     * byte转换为字符串
     * @param v
     * @return
     */
    public static String ToString(float v){
        return String.valueOf(v);
    }

    /**
     * byte转换为字符串
     * @param v
     * @return
     */
    public static String ToString(double v){
        return String.valueOf(v);
    }

    /**
     * 转换为整型
     * @param v
     * @return
     */
    public static int ToInt(Object v){
        return IanConvert.ToInt(v,0);
    }

    /**
     * 转换为整型
     * @param v
     * @param defVal 默认值
     * @return
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
     * 转换为整型
     * @param f
     * @return
     */
    public static int ToInt(float f){
        return (new Float(f)).intValue();
    }

    /**
     * 转换为整型
     * @param d
     * @return
     */
    public static int ToInt(double d){
        return (new Double(d)).intValue();
    }


    /**
     * 转换为长整型
     * @param v 值
     * @return
     */
    public static long ToLong(Object v){
        return IanConvert.ToLong(v,0);
    }

    /**
     * 转换为长整型
     * @param v 值
     * @param defVal 默认值
     * @return
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
     * 转换为整型
     * @param f
     * @return
     */
    public static long ToLong(float f){
        return (long)f;
    }

    /**
     * 转换为整型
     * @param d
     * @return
     */
    public static long ToLong(double d){
        return (new Double(d)).longValue();
    }

    /**
     * 转换为双精度
     * @param v 值
     * @return
     */
    public static double ToDouble(Object v){
        return IanConvert.ToDouble(v,0);
    }

    /**
     * 转换为双精度
     * @param v 值
     * @param defVal 默认值
     * @return
     */
    public static double ToDouble(Object v,double defVal) {
        try{
            return Double.valueOf(String.valueOf(v));
        }catch(Exception e){
            return defVal;
        }
    }

    /**
     * 转换为双精度
     * @param v
     * @return
     */
    public static double ToDouble(int v){
        return (double)v;
    }

    /**
     * 转换为双精度
     * @param v
     * @return
     */
    public static double ToDouble(long v){
        return (double)v;
    }

    /**
     * 转换为双精度
     * @param v
     * @return
     */
    public static double ToDouble(float v){
        return (double)v;
    }

    /**
     * 转换为浮点型
     * @param v 值
     * @return
     */
    public static float ToFloat(Object v){
        return IanConvert.ToFloat(v,0);
    }

    /**
     * 转换为浮点型
     * @param v 值
     * @param defVal 默认值
     * @return
     */
    public static float ToFloat(Object v,float defVal) {
        try{
            return Float.valueOf(String.valueOf(v));
        }catch(Exception e){
            return defVal;
        }
    }

    /**
     * 转换为浮点型
     * @param v
     * @return
     */
    public static float ToFloat(int v){
        return (float)v;
    }

    /**
     * 转换为浮点型
     * @param v
     * @return
     */
    public static float ToFloat(long v){
        return (float)v;
    }

    /**
     * 转换为浮点型
     * @param v
     * @return
     */
    public static float ToFloat(double v){
        return (float)v;
    }

    /**
     * 转换为byte
     * @param v Object
     * @return byte
     * @throws Exception
     */
    public static byte ToByte(Object v) throws Exception {
        int i=IanConvert.ToInt(v);
        if(i==-128) return -128;
        return (byte)(i%128);
    }

    /**
     * 转换为short
     * @param v Object
     * @return short
     * @throws Exception
     */
    public static short ToShort(Object v) throws Exception {
        int i=IanConvert.ToInt(v);
        if(i==-32768) return -32768;
        return (short)(i%32768);
    }

    /**
     * 转换为布尔型
     * @param v 值
     * @return
     */
    public static boolean ToBoolean(Object v){
        return IanConvert.ToBoolean(v,false);
    }

    /**
     * 转换为布尔型
     * @param v 值
     * @param defVal 默认值
     * @return
     */
    public static boolean ToBoolean(Object v,boolean defVal) {
        try{
            return (Boolean) v;
        }catch(Exception e){
            return defVal;
        }
    }

    /**
     * 转换为布尔型
     * @param v
     * @return
     */
    public static boolean ToBoolean(int v){
        return v>0;
    }

    /**
     * 转换为布尔型
     * @param v
     * @return
     */
    public static boolean ToBoolean(long v){
        return  v>0;
    }

    /**
     * 转换为布尔型
     * @param v
     * @return
     */
    public static boolean ToBoolean(float v){
        return v>0;
    }

    /**
     * 转换为布尔型
     * @param v
     * @return
     */
    public static boolean ToBoolean(double v){
        return  v>0;
    }

    /**
     * 转换为日期型
     * @param v 日期值
     * @param isDateNullDeal 是否处理Date值为null的情况
     * @return 日期
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
     * 转换为日期型
     * @param v 日期值
     * @return 日期
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
     * 将指定格式的字符串转换为日期
     * @param s 字符串
     * @param format 格式
     * @return 日期
     * @throws ParseException
     */
    public static Date ToDate(String s,String format) throws ParseException {
        SimpleDateFormat fmt=new SimpleDateFormat(format);
        return new Date(fmt.parse(s).getTime());
    }

    /**
     * 将指定日期时间转换为指定格式字符串
     * @param dt 日期时间
     * @param format 格式
     * @return 指定格式的字符串
     */
    public static  String FormatDate(Date dt,String format){
        SimpleDateFormat fmt=new SimpleDateFormat(format);
        return fmt.format(dt);
    }

    /**
     * 将指定日期时间转换为MYSQL写入值
     * @param dt 指定日期时间
     * @return 指定日期时间MYSQL写入值
     */
    public static String FormatMySQLDate(Date dt){
        return IanConvert.FormatDate(dt,"yyyy-MM-dd HH:mm:ss");
    }
}
