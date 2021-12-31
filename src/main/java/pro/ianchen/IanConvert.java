package pro.ianchen;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.attribute.FileTime;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
     * 对象转换为byte数组
     * @param obj 被转换对象
     * @return byte数组
     * @throws IOException
     */
    public static byte[] ToBytes(Object obj) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos =null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray ();
        }finally {
            if(oos!=null){
                oos.close();
                oos=null;
            }
            if(bos!=null){
                bos.close();
                bos=null;
            }
        }
        return bytes;
    }

    /**
     * 将byte数组转换为对象
     * @param bs byte数组
     * @return 对象
     * @throws Exception
     */
    public static Object FromBytes(byte[] bs) throws Exception{
        ByteArrayInputStream bis=null;
        ObjectInputStream ois=null;
        Object obj=null;
        try {
            bis = new ByteArrayInputStream (bs);
            ois = new ObjectInputStream (bis);
            obj = ois.readObject();
        }finally {
            if(ois!=null){
                ois.close();
                ois=null;
            }
            if(bis!=null){
                bis.close();
                bis=null;
            }
        }
        return obj;
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
     * 转化为日历类型
     * @param d 日期时间
     * @return 日历类型
     */
    public static Calendar ToCalendar(Date d){
        Calendar cld=Calendar.getInstance();
        cld.setTime(d);
        return cld;
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

    /**
     * 格式化金额（单位分）
     * 整数含千分逗号
     * @param v 金额（单位分）
     * @return 格式化后内容
     */
    public static String FormatMoney(long v){
        DecimalFormat fmt=new DecimalFormat("###,##0.00");
        double d=v/100.0;
        return fmt.format(d);
    }

    /**
     * 格式化金额（单位分）
     * @param v 金额（单位分）
     * @return 格式化后内容
     */
    public static String FormatMoney2(long v){
        DecimalFormat fmt=new DecimalFormat("0.00");
        double d=v/100.0;
        return fmt.format(d);
    }

    /**
     * 格式化金额（单位分）
     * 整数含千分逗号
     * @param v 金额（单位分）
     * @return 格式化后内容
     */
    public static String FormatMoney(int v){
        DecimalFormat fmt=new DecimalFormat("###,##0.00");
        double d=v/100.0;
        return fmt.format(d);
    }

    /**
     * 格式化金额（单位分）
     * @param v 金额（单位分）
     * @return 格式化后内容
     */
    public static String FormatMoney2(int v){
        DecimalFormat fmt=new DecimalFormat("0.00");
        double d=v/100.0;
        return fmt.format(d);
    }

    /**
     * 格式化金额（单位元）
     * 整数含千分逗号
     * @param v 金额（单位元）
     * @return 格式化后内容
     */
    public static String FormatMoney(float v){
        DecimalFormat fmt=new DecimalFormat("###,##0.00");
        double d=new Double(v);
        return fmt.format(d);
    }

    /**
     * 格式化金额（单位元）
     * @param v 金额（单位元）
     * @return 格式化后内容
     */
    public static String FormatMoney2(float v){
        DecimalFormat fmt=new DecimalFormat("0.00");
        double d=new Double(v);
        return fmt.format(d);
    }

    /**
     * 格式化金额（单位元）
     * 整数含千分逗号
     * @param v 金额（单位元）
     * @return 格式化后内容
     */
    public static String FormatMoney(double v){
        DecimalFormat fmt=new DecimalFormat("###,##0.00");
        return fmt.format(v);
    }

    /**
     * 格式化金额（单位元）
     * @param v 金额（单位元）
     * @return 格式化后内容
     */
    public static String FormatMoney2(double v){
        DecimalFormat fmt=new DecimalFormat("0.00");
        return fmt.format(v);
    }

    /**
     * 格式化数据
     * @param v 数据
     * @param minimumIntegerDigits 整数部分所允许的最小位数
     * @param maximumIntegerDigits 整数部分所允许的最大位数
     * @param minimumFractionDigits 最少小数点位数，不足的位数以0补位，超出的话按实际位数输出
     * @param maximumFractionDigits 最多保留小数位数，不足不补0。
     * @param isGroupingUsed 整数部是否每隔三个，就会有 " ,"
     * @return 格式化后内容
     */
    public static String FormatNumber(double v,int minimumIntegerDigits,int maximumIntegerDigits,int minimumFractionDigits,int maximumFractionDigits,boolean isGroupingUsed){
        NumberFormat fmt=NumberFormat.getNumberInstance(Locale.getDefault());
        fmt.setGroupingUsed(isGroupingUsed);
        fmt.setMinimumIntegerDigits(minimumIntegerDigits);
        fmt.setMaximumIntegerDigits(maximumIntegerDigits);
        fmt.setMinimumFractionDigits(minimumFractionDigits);
        fmt.setMaximumFractionDigits(maximumFractionDigits);
        return fmt.format(v);
    }

    /**
     * 格式化数据
     * @param v 数据
     * @param minimumIntegerDigits 整数部分所允许的最小位数
     * @param maximumIntegerDigits 整数部分所允许的最大位数
     * @param minimumFractionDigits 最少小数点位数，不足的位数以0补位，超出的话按实际位数输出
     * @param maximumFractionDigits 最多保留小数位数，不足不补0。
     * @param isGroupingUsed 整数部是否每隔三个，就会有 " ,"
     * @return 格式化后内容
     */
    public static String FormatNumber(float v,int minimumIntegerDigits,int maximumIntegerDigits,int minimumFractionDigits,int maximumFractionDigits,boolean isGroupingUsed){
        NumberFormat fmt=NumberFormat.getNumberInstance(Locale.getDefault());
        fmt.setGroupingUsed(isGroupingUsed);
        fmt.setMinimumIntegerDigits(minimumIntegerDigits);
        fmt.setMaximumIntegerDigits(maximumIntegerDigits);
        fmt.setMinimumFractionDigits(minimumFractionDigits);
        fmt.setMaximumFractionDigits(maximumFractionDigits);
        return fmt.format(v);
    }

    /**
     * 格式化数据
     * @param v 数据
     * @param minimumIntegerDigits 整数部分所允许的最小位数
     * @param maximumIntegerDigits 整数部分所允许的最大位数
     * @param minimumFractionDigits 最少小数点位数，不足的位数以0补位，超出的话按实际位数输出
     * @param maximumFractionDigits 最多保留小数位数，不足不补0。
     * @param isGroupingUsed 整数部是否每隔三个，就会有 " ,"
     * @return 格式化后内容
     */
    public static String FormatNumber(int v,int minimumIntegerDigits,int maximumIntegerDigits,int minimumFractionDigits,int maximumFractionDigits,boolean isGroupingUsed){
        NumberFormat fmt=NumberFormat.getNumberInstance(Locale.getDefault());
        fmt.setGroupingUsed(isGroupingUsed);
        fmt.setMinimumIntegerDigits(minimumIntegerDigits);
        fmt.setMaximumIntegerDigits(maximumIntegerDigits);
        fmt.setMinimumFractionDigits(minimumFractionDigits);
        fmt.setMaximumFractionDigits(maximumFractionDigits);
        return fmt.format(v);
    }

    /**
     * 格式化数据
     * @param v 数据
     * @param minimumIntegerDigits 整数部分所允许的最小位数
     * @param maximumIntegerDigits 整数部分所允许的最大位数
     * @param minimumFractionDigits 最少小数点位数，不足的位数以0补位，超出的话按实际位数输出
     * @param maximumFractionDigits 最多保留小数位数，不足不补0。
     * @param isGroupingUsed 整数部是否每隔三个，就会有 " ,"
     * @return 格式化后内容
     */
    public static String FormatNumber(long v,int minimumIntegerDigits,int maximumIntegerDigits,int minimumFractionDigits,int maximumFractionDigits,boolean isGroupingUsed){
        NumberFormat fmt=NumberFormat.getNumberInstance(Locale.getDefault());
        fmt.setGroupingUsed(isGroupingUsed);
        fmt.setMinimumIntegerDigits(minimumIntegerDigits);
        fmt.setMaximumIntegerDigits(maximumIntegerDigits);
        fmt.setMinimumFractionDigits(minimumFractionDigits);
        fmt.setMaximumFractionDigits(maximumFractionDigits);
        return fmt.format(v);
    }

    /**
     * 将List转换为类型列表
     * @param t 类型
     * @param dt List
     * @param colMaps 字段映射 dt中的列名和映射中的key一致，映射的value为T类型的属性名
     * @param <T> 类型
     * @return 类型列表
     * @throws Exception
     */
    public static <T> List<T> ToListT(Class<T> t, List<LinkedHashMap<String,String>> dt, LinkedHashMap<String,String> colMaps) throws Exception {
        return IanConvert.ToListT(t,dt,colMaps,null);
    }

    /**
     * 将List转换为类型列表
     * @param t 类型
     * @param dt List
     * @param colMaps 字段映射 dt中的列名和映射中的key一致，映射的value为T类型的属性名
     * @param fs 字段处理方法委托
     * @param <T> 类型
     * @return 类型列表
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> ToListT(Class<T> t,List<LinkedHashMap<String,String>> dt,LinkedHashMap<String,String> colMaps,HashMap<String, Function<LinkedHashMap<String, String>,String>> fs) throws Exception {
        if(fs==null) fs=new HashMap<String, Function<LinkedHashMap<String, String>, String>>();
        List<T> ls=new ArrayList<T>();
        if(dt.size()==0) return ls;
        if(colMaps==null) colMaps=new LinkedHashMap<String, String>();
        boolean bIsBaseType=false;
        try{
            bIsBaseType=((Class)(t.getField("TYPE").get(null))).isPrimitive();
        }catch (Exception e1){

        }
        if(t.getTypeName().equals("java.util.Date")) bIsBaseType=true;

        List<String> fieldNames= Arrays.stream(t.getFields()).map(a->a.getName()).collect(Collectors.toList());
        LinkedHashMap<String, String> cs=new LinkedHashMap<String, String>();
        for(String keyV:dt.get(0).keySet()){
            String key=keyV.trim();
            if(colMaps.size()==0){
                String fieldName="";
                if(fieldNames.parallelStream().anyMatch(a-> a.toLowerCase().equals(key.toLowerCase()))){
                    fieldName=fieldNames.parallelStream().filter(a->a.toLowerCase().equals(key.toLowerCase())).findFirst().get();
                }
                if(!fieldName.equals("")) cs.put(key,fieldName);
            }else{
                if(colMaps.containsKey(key)){
                    String cname=colMaps.get(key);
                    String fieldName="";
                    if(fieldNames.parallelStream().anyMatch(a-> a.toLowerCase().equals(cname.toLowerCase()))){
                        fieldName=fieldNames.parallelStream().filter(a->a.toLowerCase().equals(cname.toLowerCase())).findFirst().get();
                    }
                    if(!fieldName.equals("")) cs.put(key,fieldName);
                }
            }
        }
        if(cs.size()==0) return ls;
        for(int i=0;i<dt.size();i++){
            LinkedHashMap<String,String> rs=dt.get(i);
            for(String cname:rs.keySet()){
                if(fs.containsKey(cname)){
                    rs.put(cname,fs.get(cname).apply(rs));
                }
            }
            if(bIsBaseType){
                for(String colName:cs.keySet()){
                    T v;
                    String val=rs.get(colName);
                    if(t.getTypeName().equals("java.util.Date")){
                        v=(T)IanConvert.ToDate(val);
                    }else{
                        v=(T)val;
                    }
                    ls.add(v);
                    break;
                }
            }else{
                T v=t.newInstance();
                for(String colName:cs.keySet()){
                    String fieldName=cs.get(colName);
                    Field f=t.getField(fieldName);
                    String val=rs.get(colName);
                    if(f.getType().isEnum()){
                        String cname=f.getGenericType().getTypeName();
                        Class<Enum> ecls = (Class<Enum>) Class.forName(cname);
                        boolean islong=false;
                        boolean bfind=false;
                        for(Enum v1:ecls.getEnumConstants()){
                            Field f1 =ecls.getDeclaredField(v1.name());
                            IanEnumLabel el=f1.getAnnotation(IanEnumLabel.class);
                            if(el.svalue().length()>0){
                                if(el.svalue().equals(val)){
                                    islong=false;
                                    bfind=true;
                                    break;
                                }
                            }else{
                                if(el.value() == IanConvert.ToLong(val)){
                                    islong=true;
                                    bfind=true;
                                    break;
                                }
                            }
                        }
                        if(bfind){
                            if(islong){
                                f.set(v, IanEnumLabelOpt.GetEnumByValue(ecls,IanConvert.ToLong(val)));
                            }else{
                                f.set(v, IanEnumLabelOpt.GetEnumByValue(ecls,val));
                            }
                        }else{
                            continue;
                        }
                    }else if(!f.isSynthetic()){
                        f.setAccessible(true);
                        String ftype=f.getGenericType().getTypeName();
                        switch (ftype) {
                            case "java.lang.String":
                                f.set(v,val);
                                break;
                            case "java.lang.Integer":
                            case "java.lang.Character":
                            case "int":
                            case "char":
                                f.set(v,IanConvert.ToInt(val));
                                break;
                            case "java.lang.Long":
                            case "long":
                                f.set(v,IanConvert.ToLong(val));
                                break;
                            case "java.lang.Byte":
                            case "byte":
                                f.set(v,IanConvert.ToByte(val));
                                break;
                            case "java.lang.Short":
                            case "short":
                                f.set(v,IanConvert.ToShort(val));
                                break;
                            case "java.lang.Float":
                            case "float":
                                f.set(v,IanConvert.ToFloat(val));
                                break;
                            case "java.lang.Double":
                            case "double":
                                f.set(v,IanConvert.ToDouble(val));
                                break;
                            case "java.lang.Boolean":
                            case "boolean":
                                f.set(v,IanConvert.ToBoolean(val));
                                break;
                            case "java.util.Date":
                                f.set(v,IanConvert.ToDate(val));
                                break;
                            default:
                                break;
                        }
                        f.setAccessible(false);
                    }else{
                        continue;
                    }
                }
                ls.add(v);
            }
        }
        return ls;
    }

    /**
     * 将List转换为类型列表
     * @param t 类型
     * @param dt List
     * @param colMaps 字段映射 dt中的列名和映射中的key一致，映射的value为T类型的属性名
     * @param fs 字段处理方法委托
     * @param <T> 类型
     * @return 类型列表
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> ToListT2(Class<T> t,List<LinkedHashMap<String,String>> dt,LinkedHashMap<String,String> colMaps,HashMap<String,IIanAdditionalFunc> fs) throws Exception {
        if(fs==null) fs=new HashMap<String, IIanAdditionalFunc>();
        List<T> ls=new ArrayList<T>();
        if(dt.size()==0) return ls;
        if(colMaps==null) colMaps=new LinkedHashMap<String, String>();
        boolean bIsBaseType=false;
        try{
            bIsBaseType=((Class)(t.getField("TYPE").get(null))).isPrimitive();
        }catch (Exception e1){

        }
        if(t.getTypeName().equals("java.util.Date")) bIsBaseType=true;

        List<String> fieldNames= Arrays.stream(t.getFields()).map(a->a.getName()).collect(Collectors.toList());
        LinkedHashMap<String, String> cs=new LinkedHashMap<String, String>();
        for(String keyV:dt.get(0).keySet()){
            String key=keyV.trim();
            if(colMaps.size()==0){
                String fieldName="";
                if(fieldNames.parallelStream().anyMatch(a-> a.toLowerCase().equals(key.toLowerCase()))){
                    fieldName=fieldNames.parallelStream().filter(a->a.toLowerCase().equals(key.toLowerCase())).findFirst().get();
                }
                if(!fieldName.equals("")) cs.put(key,fieldName);
            }else{
                if(colMaps.containsKey(key)){
                    String cname=colMaps.get(key);
                    String fieldName="";
                    if(fieldNames.parallelStream().anyMatch(a-> a.toLowerCase().equals(cname.toLowerCase()))){
                        fieldName=fieldNames.parallelStream().filter(a->a.toLowerCase().equals(cname.toLowerCase())).findFirst().get();
                    }
                    if(!fieldName.equals("")) cs.put(key,fieldName);
                }
            }
        }
        if(cs.size()==0) return ls;
        for(int i=0;i<dt.size();i++){
            LinkedHashMap<String,String> rs=dt.get(i);
            for(String cname:rs.keySet()){
                if(fs.containsKey(cname)){
                    rs.put(cname,fs.get(cname).apply(rs));
                }
            }
            if(bIsBaseType){
                for(String colName:cs.keySet()){
                    T v;
                    String val=rs.get(colName);
                    if(t.getTypeName().equals("java.util.Date")){
                        v=(T)IanConvert.ToDate(val);
                    }else{
                        v=(T)val;
                    }
                    ls.add(v);
                    break;
                }
            }else{
                T v=t.newInstance();
                for(String colName:cs.keySet()){
                    String fieldName=cs.get(colName);
                    Field f=t.getField(fieldName);
                    String val=rs.get(colName);
                    if(f.getType().isEnum()){
                        String cname=f.getGenericType().getTypeName();
                        Class<Enum> ecls = (Class<Enum>) Class.forName(cname);
                        boolean islong=false;
                        boolean bfind=false;
                        for(Enum v1:ecls.getEnumConstants()){
                            Field f1 =ecls.getDeclaredField(v1.name());
                            IanEnumLabel el=f1.getAnnotation(IanEnumLabel.class);
                            if(el.svalue().length()>0){
                                if(el.svalue().equals(val)){
                                    islong=false;
                                    bfind=true;
                                    break;
                                }
                            }else{
                                if(el.value() == IanConvert.ToLong(val)){
                                    islong=true;
                                    bfind=true;
                                    break;
                                }
                            }
                        }
                        if(bfind){
                            if(islong){
                                f.set(v, IanEnumLabelOpt.GetEnumByValue(ecls,IanConvert.ToLong(val)));
                            }else{
                                f.set(v, IanEnumLabelOpt.GetEnumByValue(ecls,val));
                            }
                        }else{
                            continue;
                        }
                    }else if(!f.isSynthetic()){
                        f.setAccessible(true);
                        String ftype=f.getGenericType().getTypeName();
                        switch (ftype) {
                            case "java.lang.String":
                                f.set(v,val);
                                break;
                            case "java.lang.Integer":
                            case "java.lang.Character":
                            case "int":
                            case "char":
                                f.set(v,IanConvert.ToInt(val));
                                break;
                            case "java.lang.Long":
                            case "long":
                                f.set(v,IanConvert.ToLong(val));
                                break;
                            case "java.lang.Byte":
                            case "byte":
                                f.set(v,IanConvert.ToByte(val));
                                break;
                            case "java.lang.Short":
                            case "short":
                                f.set(v,IanConvert.ToShort(val));
                                break;
                            case "java.lang.Float":
                            case "float":
                                f.set(v,IanConvert.ToFloat(val));
                                break;
                            case "java.lang.Double":
                            case "double":
                                f.set(v,IanConvert.ToDouble(val));
                                break;
                            case "java.lang.Boolean":
                            case "boolean":
                                f.set(v,IanConvert.ToBoolean(val));
                                break;
                            case "java.util.Date":
                                f.set(v,IanConvert.ToDate(val));
                                break;
                            default:
                                break;
                        }
                        f.setAccessible(false);
                    }else{
                        continue;
                    }
                }
                ls.add(v);
            }
        }
        return ls;
    }

    /**
     * 将指定类型列表转换为逗号分隔的字符串
     * @param ts 指定类型列表
     * @param func 转换函数
     * @param <T> 指定类型
     * @return 逗号分隔的字符串
     */
    public static <T> String FromList(List<T> ts,Function<? super T, String> func){
        return IanConvert.FromList(ts,",",func);
    }

    /**
     * 将指定类型列表转换为指定分隔符分隔的字符串
     * @param ts 指定类型列表
     * @param symbol 分隔符
     * @param func 转换函数
     * @param <T> 指定类型
     * @return 指定分隔符分隔的字符串
     */
    public static <T> String FromList(List<T> ts,String symbol, Function<? super T, String> func){
        if(ts==null) ts=new ArrayList<T>();
        symbol=IanStringTool.Deal(symbol,",");
        StringBuilder s=new StringBuilder();
        int i=0;
        for(T t:ts){
            if(i==0){
                s.append(func.apply(t));
            }else{
                s.append(symbol+func.apply(t));
            }
            i++;
        }
        return s.toString();
    }

    /**
     * 将逗号分隔类型的字符串转换为指定类型列表
     * @param sids 逗号分隔类型的字符串
     * @param func 转换函数
     * @param <T> 指定类型
     * @return 指定类型列表
     */
    public static <T> List<T> ToList(String sids,Function<String, ? extends T> func){
        return IanConvert.ToList(sids,",",func);
    }

    /**
     * 将指定分隔符分隔类型的字符串转换为指定类型列表
     * @param sids 指定分隔符分隔类型的字符串
     * @param symbol 分隔符
     * @param func 类型转换函数
     * @param <T> 指定类型
     * @return 指定类型列表
     */
    public static <T> List<T> ToList(String sids,String symbol, Function<String, ? extends T> func){
        List<T> rs=new ArrayList<T>();
        sids=IanStringTool.Deal(sids,"");
        if(sids.length()==0) return rs;
        String[] ss=sids.split(symbol);
        for(String s:ss){
            rs.add(func.apply(s));
        }
        return rs;
    }

    /**
     * 将用逗号分隔的整型字符串转换整型列表
     * @param sids 逗号分隔的整型字符串
     * @return 整型列表
     * @throws Exception
     */
    public static List<Integer> ToIntList(String sids) throws Exception {
        Function<String,Integer> func=a-> {
            try {
                return IanConvert.ToInt(a);
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        };
        return IanConvert.ToList(sids,",",func);
    }

    /**
     * 将整型列表转换为用逗号分隔的整型字符串
     * @param ids 整型列表
     * @return 用逗号分隔的整型字符串
     * @throws Exception
     */
    public static String FromIntList(List<Integer> ids) throws Exception{
        Function<Integer,String> func=a->(IanConvert.ToString(a));
        return IanConvert.FromList(ids,",",func);
    }

    /**
     * 将用逗号分隔的长整型字符串转换长整型列表
     * @param sids 逗号分隔的长整型字符串
     * @return 长整型列表
     * @throws Exception
     */
    public static List<Long> ToLongList(String sids) throws Exception {
        Function<String,Long> func=a-> {
            try {
                return IanConvert.ToLong(a);
            } catch (Exception e) {
                e.printStackTrace();
                return (long)-1;
            }
        };
        return IanConvert.ToList(sids,",",func);
    }

    /**
     * 将长整型列表转换为用逗号分隔的长整型字符串
     * @param ids 长整型列表
     * @return 用逗号分隔的长整型字符串
     * @throws Exception
     */
    public static String FromLongList(List<Long> ids) throws Exception{
        Function<Long,String> func=a->(IanConvert.ToString(a));
        return IanConvert.FromList(ids,",",func);
    }

    /**
     * 将用逗号分隔的字符型字符串转换字符串列表
     * @param sids 逗号分隔的字符型字符串
     * @return 字符串列表
     * @throws Exception
     */
    public static List<String> ToStringList(String sids) throws Exception {
        sids= IanStringTool.Deal(sids,"");
        if(sids.length()==0) return new ArrayList<String>();
        String[] ss=sids.split(",");
        return Arrays.stream(ss).collect(Collectors.toList());
    }

    /**
     * 将字符串列表转换为用逗号分隔的字符型字符串
     * @param ids 字符串列表
     * @return 用逗号分隔的字符串
     * @throws Exception
     */
    public static String FromStringList(List<String> ids) throws Exception{
        if(ids==null) ids=new ArrayList<String>();
        if(ids.size()==0) return "";
        return String.join(",",ids);
    }

    /**
     * 判断字符串是否是JSON格式
     * @param v 字符串
     * @return 是否
     */
    public static boolean IsJsonFormat(String v){
        JsonElement jsonElement=null;
        try{
            jsonElement=(new JsonParser()).parse(v);
        }catch (Exception e){
            return false;
        }
        if(jsonElement==null) return false;
        if(!jsonElement.isJsonObject()&&!jsonElement.isJsonArray()) return false;
        return true;
    }

    /**
     * 对象转换为JSON
     * @param v 对象
     * @return JSON
     */
    public static String ToJson(Object v){
        return IanConvert.ToJson(v,true);
    }

    /**
     * 对象转换为JSON
     * @param v 对象
     * @param includeNull 是否包括null
     * @return JSON
     */
    public static String ToJson(Object v,boolean includeNull){
        Gson gson=null;
        if(includeNull){
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss SSS").serializeNulls().disableHtmlEscaping().create();
        }else{
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss SSS").disableHtmlEscaping().create();
        }
        return gson.toJson(v);
    }

    /**
     * JSON字符串转换为JSON对象
     * @param v JSON字符串
     * @return JSON对象
     */
    public static JsonObject ToJsonObject(String v){
        return new JsonParser().parse(v).getAsJsonObject();
    }

    /**
     * 获取JSON对象的Key列表
     * @param v JSON对象
     * @return Key列表
     */
    public static List<String> GetJsonObjectKeys(JsonObject v){
        List<String> ks=new ArrayList<String>();
        for(Map.Entry<String, JsonElement> ele:v.entrySet()){
            ks.add(ele.getKey());
        }
        return ks;
    }

    /**
     * JSON对象是否包含指定Key
     * @param v JSON对象
     * @param key Key
     * @return 是否
     */
    public static boolean IsJsonObjectContainKey(JsonObject v,String key){
        return v.has(key);
    }

    /**
     * 获取JSON对象指定Key对应Value的JsonElement
     * @param v JSON对象
     * @param key 指定Key
     * @return 对应Value的JsonElement
     */
    public static JsonElement GetJsonObjectElementValue(JsonObject v,String key){
        return v.get(key);
    }

    /**
     * JSON转换为对象
     * @param t 对象类型
     * @param v JSON
     * @param <T> 对象类
     * @return 对象实例
     */
    public static <T> T FromJson(Class<T> t,String v){
        if(v==null) return null;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss SSS").serializeNulls().disableHtmlEscaping().create();
        return gson.fromJson(v,t);
    }

    /**
     * JSON转换为对象
     * @param t 对象类型
     * @param v JSON
     * @param <T> 对象类
     * @return 对象实例
     */
    public static <T> List<T> FromJsonList(Class<T> t,String v){
        if(v==null) return  null;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss SSS").serializeNulls().disableHtmlEscaping().create();
        JsonArray jsonArray=new JsonParser().parse(v).getAsJsonArray();
        List<T> ls=new ArrayList<T>();
        for(JsonElement jsonElement:jsonArray){
            ls.add(gson.fromJson(jsonElement,t));
        }
        return ls;
    }

    /**
     * JSON转换为MAP对象
     * @param v JSON
     * @param <K> MAP键类型
     * @param <V> MAP值类型
     * @return
     */
    public static <K,V> Map<K,V> FromJsonMap(String v){
        if(v==null) return null;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss SSS").serializeNulls().disableHtmlEscaping().create();
        return gson.fromJson(v,new TypeToken<Map<K,V>>() {}.getType());
    }

    /**
     * JSON转换为HashMap对象
     * @param v JSON
     * @param <K> MAP键类型
     * @param <V> MAP值类型
     * @return
     */
    public static <K,V> Map<K,V> FromJsonHashMap(String v){
        if(v==null) return null;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss SSS").serializeNulls().disableHtmlEscaping().create();
        return gson.fromJson(v,new TypeToken<HashMap<K,V>>() {}.getType());
    }

    /**
     * JSON转换为LinkedHashMap对象
     * @param v JSON
     * @param <K> MAP键类型
     * @param <V> MAP值类型
     * @return
     */
    public static <K,V> Map<K,V> FromJsonLinkedHashMap(String v){
        if(v==null) return null;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss SSS").serializeNulls().disableHtmlEscaping().create();
        return gson.fromJson(v,new TypeToken<LinkedHashMap<K,V>>() {}.getType());
    }

    /**
     * JSON转换为List<MAP>>对象
     * @param v JSON
     * @param <K> MAP键类型
     * @param <V> MAP值类型
     * @return
     */
    public static <K,V> List<Map<K,V>> FromJsonListMap(String v){
        if(v==null) return null;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss SSS").serializeNulls().disableHtmlEscaping().create();
        return gson.fromJson(v,new TypeToken<List<Map<K,V>>>() {}.getType());
    }

    /**
     * JSON转换为List<HashMap>>对象
     * @param v JSON
     * @param <K> HashMap键类型
     * @param <V> HashMap值类型
     * @return List<HashMap<>>
     */
    public static <K,V> List<HashMap<K,V>> FromJsonListHashMap(String v){
        if(v==null) return null;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss SSS").serializeNulls().disableHtmlEscaping().create();
        return gson.fromJson(v,new TypeToken<List<HashMap<K,V>>>() {}.getType());
    }

    /**
     * JSON转换为List<LinkedHashMap>>对象
     * @param v JSON
     * @param <K> LinkedHashMap键类型
     * @param <V> LinkedHashMap值类型
     * @return List<LinkedHashMap<>>
     */
    public static <K,V> List<LinkedHashMap<K,V>> FromJsonListLinkedHashMap(String v){
        if(v==null) return null;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss SSS").serializeNulls().disableHtmlEscaping().create();
        return gson.fromJson(v,new TypeToken<List<LinkedHashMap<K,V>>>() {}.getType());
    }

    /**
     * 格式化JSON
     * @param v 对象
     * @return 格式化的json串
     */
    public static String FormatJson(Object v){
        if(v==null) return "";
        Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss SSS").serializeNulls().setPrettyPrinting().disableHtmlEscaping().create();
        return gson.toJson(v);
    }

    /**
     * 对象序列化为XML
     * @param t 对象
     * @param alia 别名
     * @param <T> 类型
     * @return XML
     * @throws Exception
     */
    public static <T> String ToXml(T t,String alia) throws Exception {
        return IanConvert.ToXml(t,alia,"utf-8");
    }
    /**
     * 对象序列化为XML
     * @param t 对象
     * @param alia 别名
     * @param encode 编码
     * @param <T> 类型
     * @return XML
     * @throws Exception
     */
    public static <T> String ToXml(T t,String alia,String encode) throws Exception {
        return IanConvert.ToXml(t,alia,null,encode);
    }

    /**
     * 对象序列化为XML
     * @param t 对象
     * @param alia 别名
     * @param clsDefs 类定义列表
     * @param encode 编码
     * @param <T> 类型
     * @return XML
     * @throws Exception
     */
    public static <T> String ToXml(T t,String alia,HashMap<String, Class<?>> clsDefs,String encode) throws Exception {
        if(clsDefs==null) clsDefs=new HashMap<String, Class<?>>();
        alia=IanStringTool.Check(alia,"Root别名");
        encode=IanStringTool.Deal(encode,"utf-8");
        XStream xStream = new XStream(new DomDriver(encode));
        // 设置类的别名，xml的root
        xStream.alias(alia, t.getClass());
        for(String d:clsDefs.keySet()){
            xStream.alias(d,clsDefs.get(d));
        }
        return xStream.toXML(t);
    }

    /**
     * XML反序列化为对象
     * @param t 类型
     * @param xml xml
     * @param alia 别名
     * @param <T> 类型
     * @return 对象
     * @throws Exception
     */
    public static <T> T FromXml(Class<T> t,String xml,String alia) throws Exception {
        return IanConvert.FromXml(t,xml,alia,"utf-8");
    }

    /**
     * XML反序列化为对象
     * @param t 类型
     * @param xml xml
     * @param alia 别名
     * @param encode 编码
     * @param <T> 类型
     * @return 对象
     * @throws Exception
     */
    public static <T> T FromXml(Class<T> t,String xml,String alia,String encode) throws Exception {
        return IanConvert.FromXml(t,xml,alia,null,encode);
    }

    /**
     * XML反序列化为对象
     * @param t 类型
     * @param xml xml
     * @param alia 别名
     * @param clsDefs 类定义列表
     * @param encode 编码
     * @param <T> 类型
     * @return 对象
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T FromXml(Class<T> t, String xml, String alia, HashMap<String, Class<?>> clsDefs,String encode) throws Exception {
        if(clsDefs==null) clsDefs=new HashMap<String, Class<?>>();
        xml=IanStringTool.Check(xml,"XML");
        encode=IanStringTool.Deal(encode,"utf-8");
        alia=IanStringTool.Check(alia,"Root别名");
        XStream xStream = new XStream(new DomDriver(encode));
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypesByRegExp(new String[] { ".*" });
        xStream.autodetectAnnotations(true);
        T o = (T) t.newInstance();
        xStream.toXML(o);
        xStream.alias(alia, t);
        for(String d:clsDefs.keySet()){
            xStream.alias(d,clsDefs.get(d));
        }
        // 设置类的别名，xml的root
        return (T)xStream.fromXML(xml);
    }

    /**
     * Json转换为Xml字符串
     * @param json json字符串
     * @return xml字符串
     */
    public static String Json2Xml(String json){
        XMLSerializer xmlSerializer = new XMLSerializer();
        return xmlSerializer.write(JSONSerializer.toJSON(json));
    }

    /**
     * Xml转换为Json字符串
     * @param xml xml字符串
     * @return Json字符串
     */
    public static String Xml2Json(String xml){
        XMLSerializer xmlSerializer = new XMLSerializer();
        JSON json = xmlSerializer.read(xml);
        return json.toString(1);
    }

    /**
     * 将字符串分割转换位类型列表
     * @param s 字符串
     * @param split 分隔符
     * @param cnv 转换接口方法
     * @param <T> 类型
     * @return 类型列表
     * @throws Exception
     */
    public static <T> List<T> GetIds(String s,String split,Function<String,T> cnv) throws Exception {
        if(cnv==null) throw new Exception("转换接口方法不能为空");
        split=IanStringTool.Deal(split,",");
        List<T> rs=new ArrayList<T>();
        s=IanStringTool.Deal(s,"");
        if(s.length()==0) return rs;
        String[] ss=s.split(split);
        for(String v:ss){
            rs.add(cnv.apply(v));
        }
        return rs;
    }

    /**
     * 将字符串按逗号分割转换位类型列表
     * @param s 字符串
     * @param cnv 转换接口方法
     * @param <T> 类型
     * @return 类型列表
     * @throws Exception
     */
    public static <T> List<T> GetIds(String s,Function<String,T> cnv) throws Exception {
        return IanConvert.GetIds(s,",",cnv);
    }

    /**
     * 将类型列表转换位字符串
     * @param ls 类型列表
     * @param split 分隔符
     * @param cnv 转换方法接口
     * @param <T> 类型
     * @return 字符串
     * @throws Exception
     */
    public static <T> String GetIds(List<T> ls,String split,Function<T,String> cnv) throws Exception {
        if(cnv==null) throw new Exception("转换接口方法不能为空");
        split=IanStringTool.Deal(split,",");
        StringBuilder s=new StringBuilder();
        if(ls==null) ls=new ArrayList<T>();
        if(ls.size()==0) return "";
        for(int i=0;i<ls.size();i++){
            if(i==0){
                s.append(cnv.apply(ls.get(i)));
            }else{
                s.append(split).append(cnv.apply(ls.get(i)));
            }
        }
        return s.toString();
    }


    /**
     * 将类型列表按逗号分隔符转换位字符串
     * @param ls 类型列表
     * @param cnv 转换方法接口
     * @param <T> 类型
     * @return 字符串
     * @throws Exception
     */
    public static <T> String GetIds(List<T> ls,Function<T,String> cnv) throws Exception {
        return IanConvert.GetIds(ls,",",cnv);
    }

    /**
     * 逗号分隔的长整型字符串转换为长整型列表
     * @param s 字符串
     * @return 列表
     * @throws Exception
     */
    public static List<Long> GetLongIds(String s) throws Exception{
        return IanConvert.GetIds(s, ",", IanConvert::ToLong);
    }

    /**
     * 将长整型列表转换为按逗号分隔的长整型字符串
     * @param ls 类型列表
     * @return 字符串
     * @throws Exception
     */
    public static String GetLongIds(List<Long> ls) throws Exception {
        return IanConvert.GetIds(ls, ",", IanConvert::ToString);
    }

    /**
     * 逗号分隔的长整型字符串转换为长整型列表
     * @param s 字符串
     * @return 列表
     * @throws Exception
     */
    public static List<Integer> GetIntIds(String s) throws Exception{
        return IanConvert.GetIds(s, ",", IanConvert::ToInt);
    }

    /**
     * 将长整型列表转换为按逗号分隔的长整型字符串
     * @param ls 类型列表
     * @return 字符串
     * @throws Exception
     */
    public static String GetIntIds(List<Integer> ls) throws Exception {
        return IanConvert.GetIds(ls, ",", IanConvert::ToString);
    }

    /**
     * 将日期时间类型转换为sql的时间戳类型
     * @param time 日期时间
     * @return sql时间戳
     */
    public static java.sql.Timestamp ToTimeStamp(Date time){
        return new Timestamp(time.getTime());
    }

    /**
     * 将类型转换为HashMap
     * @param t 类型，必须为非基本类型
     * @param <T> 泛型
     * @return 转换后的HashMap
     * @throws Exception
     */
    public static <T> HashMap<String,Object> ToHashMap(T t) throws Exception {
        if(IanStringTool.IsBaseType(t.getClass())) throw new Exception("只有非基本类型可以进行转换");
        Field[] fs=t.getClass().getFields();
        HashMap<String,Object> ms=new HashMap<String,Object>();
        for(Field f:fs){
            if(f.getType().isEnum()){
                String cname=f.getGenericType().getTypeName();
                Class<Enum> ecls = (Class<Enum>) Class.forName(cname);
                boolean islong=false;
                boolean bfind=false;
                String val="";
                long lval=0;
                for(Enum v1:ecls.getEnumConstants()){
                    Field f1 =ecls.getDeclaredField(v1.name());
                    Enum a=(Enum)f.get(t);
                    if(v1==a){
                        IanEnumLabel el=f1.getAnnotation(IanEnumLabel.class);
                        if(el.svalue().length()>0){
                            islong=false;
                            val=el.svalue();
                        }else{
                            islong=true;
                            lval=el.value();
                        }
                        bfind=true;
                        break;
                    }
                }
                if(bfind){
                    if(islong){
                        ms.put(f.getName(),lval);
                    }else{
                        ms.put(f.getName(),val);
                    }
                }else{
                    continue;
                }
            }else if(!f.isSynthetic()){
                f.setAccessible(true);
                String ftype=f.getGenericType().getTypeName();
                switch (ftype) {
                    case "java.lang.String":
                        ms.put(f.getName(),IanConvert.ToString(f.get(t)));
                        break;
                    case "java.lang.Integer":
                    case "int":
                        ms.put(f.getName(),f.getInt(t));
                        break;
                    case "java.lang.Character":
                    case "char":
                        ms.put(f.getName(),f.getChar(t));
                        break;
                    case "java.lang.Long":
                    case "long":
                        ms.put(f.getName(),f.getLong(t));
                        break;
                    case "java.lang.Byte":
                    case "byte":
                        ms.put(f.getName(),f.getByte(t));
                        break;
                    case "java.lang.Short":
                    case "short":
                        ms.put(f.getName(),f.getShort(t));
                        break;
                    case "java.lang.Float":
                    case "float":
                        ms.put(f.getName(),f.getFloat(t));
                        break;
                    case "java.lang.Double":
                    case "double":
                        ms.put(f.getName(),f.getDouble(t));
                        break;
                    case "java.lang.Boolean":
                    case "boolean":
                        ms.put(f.getName(),f.getBoolean(t));
                        break;
                    case "java.util.Date":
                        ms.put(f.getName(),IanConvert.ToDate(f.get(t)));
                        break;
                    default:
                        break;
                }
                f.setAccessible(false);
            }else{
                continue;
            }
        }
        return ms;
    }

    /**
     * unicode转中文
     * @param unicode unicode字符串
     * @return
     */
    public static String Unicode2Cn(String unicode){
        /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
        String[] strs = unicode.split("\\\\u");
        String returnStr = "";
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < strs.length; i++) {
            returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
        }
        return returnStr;
    }

    /**
     * 中文转Unicode
     * @param cn 中文
     * @return unicode
     */
    public static String Cn2Unicode(String cn){
        char[] chars = cn.toCharArray();
        String returnStr = "";
        for (int i = 0; i < chars.length; i++) {
            returnStr += "\\u" + Integer.toString(chars[i], 16);
        }
        return returnStr;
    }

    /**
     * 文件时间转换为Date
     * @param fileTime 文件时间
     * @return Date
     * @throws Exception
     */
    public static Date FileTime2Date(FileTime fileTime) throws Exception {
        long ms=fileTime.toMillis();
        return IanConvert.ToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(ms));
    }

    /**
     * 对象转换为字节数组
     * @param obj 对象
     * @return 字节数组
     * @throws IOException
     */
    public static byte[] Obejct2Bytes(Object obj) throws IOException {
        byte[] bs=null;
        ByteArrayOutputStream os=null;
        ObjectOutputStream bos=null;
        try{
            os=new ByteArrayOutputStream();
            bos=new ObjectOutputStream(os);
            bos.writeObject(obj);
            bs=os.toByteArray();
        }finally {
            if(bos!=null){
                bos.close();
            }
            if(os!=null){
                os.close();
            }
        }
        return bs;
    }

    /**
     * 字节数组转换为对象
     * @param bs 字节数组
     * @return 对象
     * @throws Exception
     */
    public static Object Bytes2Object(byte[] bs) throws Exception {
        Object obj=null;
        ByteArrayInputStream is=null;
        ObjectInputStream bis=null;
        try{
            is=new ByteArrayInputStream(bs);
            bis=new ObjectInputStream(is);
            obj=bis.readObject();
        }finally {
            if(bis!=null){
                bis.close();
            }
            if(is!=null){
                is.close();
            }
        }
        return obj;
    }

}
