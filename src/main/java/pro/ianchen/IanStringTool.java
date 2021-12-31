package pro.ianchen;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Ian的字符串操作类
 */
public class IanStringTool {
    /**
     * 字符表
     */
    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 字符表(排除0O1il容易混淆的字符)
     */
    private static final String SYMBOLS2 = "23456789abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
    /**
     * 数字字符表
     */
    private static final String SYMBOLS3 = "012345678910987654321";

    /**
     * 随机数
     */
    private static final Random RANDOM = new SecureRandom();

    /**
     * 获取当前时间戳，单位秒
     * @return
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis()/1000;
    }

    /**
     * 获取当前时间戳，单位毫秒
     * @return
     */
    public static long getCurrentTimestampMs() {
        return System.currentTimeMillis();
    }

    /**
     * 与JS兼容的最大日期3000-1-1
     * @return
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
     * @return 与JS兼容的最大日期MySQL写入值
     */
    public static String getMySQLJavascriptMaxDateTime(){
        return IanConvert.FormatMySQLDate(IanStringTool.getJavascriptMaxDateTime());
    }

    /**
     *与JS兼容的最小日期1970-1-1
     * @return
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
     * @return 与JS兼容的最小日期MySQL写入值
     */
    public static String getMySQLJavascriptMinDateTime(){
        return IanConvert.FormatMySQLDate(IanStringTool.getJavascriptMinDateTime());
    }

    /**
     * 按时间获取该天零点
     * @param d 输入时间
     * @return 输入时间对应的该天零点
     * @throws Exception
     */
    public static Date GetDate(Date d) throws Exception {
        return IanConvert.ToDate(IanConvert.FormatDate(d,"yyyy-MM-dd")+" 00:00:00");
    }

    /**
     * 获取当前时间
     * @return 当前时间
     */
    public static Date Now(){
        return new Date(IanStringTool.getCurrentTimestampMs());
    }

    /**
     * 获取当天零点
     * @return 当天零点
     * @throws Exception
     */
    public static Date Today() throws Exception {
        return IanStringTool.GetDate(IanStringTool.Now());
    }

    /**
     * 获取当前时间的MySQL写入值
     * @return 当前时间的MySQL写入值
     */
    public static String MySQLNow(){
        return IanConvert.FormatMySQLDate(IanStringTool.Now());
    }

    /**
     *
     * @return 获取当天零点的MySQL写入值
     * @throws Exception
     */
    public static String MySQLToday() throws Exception { return IanConvert.FormatMySQLDate(IanStringTool.Today());}

    /**
     * 指定时间增减豪秒数
     * @param d1
     * @param millis
     * @return
     */
    public static Date AddMillis(Date d1, int millis){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.MILLISECOND,millis);
        return c.getTime();
    }

    /**
     * 指定时间增减豪秒数
     * @param d1
     * @param millis
     * @return
     */
    public static Date AddMillis(Date d1, long millis){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.MILLISECOND,(int)millis);
        return c.getTime();
    }

    /**
     * 指定时间增减秒数
     * @param d1
     * @param seconds
     * @return
     */
    public static Date AddSeconds(Date d1, int seconds){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.SECOND,seconds);
        return c.getTime();
    }

    /**
     * 指定时间增减秒数
     * @param d1
     * @param seconds
     * @return
     */
    public static Date AddSeconds(Date d1, long seconds){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.SECOND,(int)seconds);
        return c.getTime();
    }

    /**
     * 指定时间增减分钟数
     * @param d1
     * @param minutes
     * @return
     */
    public static Date AddMinutes(Date d1, int minutes){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.MINUTE,minutes);
        return c.getTime();
    }

    /**
     * 指定时间增减分钟数
     * @param d1
     * @param minutes
     * @return
     */
    public static Date AddMinutes(Date d1, long minutes){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.MINUTE,(int)minutes);
        return c.getTime();
    }

    /**
     * 指定时间增减小时数
     * @param d1
     * @param hours
     * @return
     */
    public static Date AddHour(Date d1, int hours){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.HOUR,hours);
        return c.getTime();
    }

    /**
     * 指定时间增减小时数
     * @param d1
     * @param hours
     * @return
     */
    public static Date AddHour(Date d1, long hours){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.HOUR,(int)hours);
        return c.getTime();
    }

    /**
     * 指定时间增减天数
     * @param d1 时间
     * @param days 天数
     * @return 计算结果
     */
    public static Date AddDays(Date d1, int days){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.DAY_OF_YEAR,days);
        return c.getTime();
    }

    /**
     * 指定时间增减天数
     * @param d1 时间
     * @param days 天数
     * @return 计算结果
     */
    public static Date AddDays(Date d1, long days){
        return IanStringTool.AddDays(d1,(int)days);
    }

    /**
     * 指定时间增减月数
     * @param d1 时间
     * @param months 月数
     * @return 计算结果
     */
    public static Date AddMonths(Date d1, long months){
        return IanStringTool.AddMonths(d1,(int)months);
    }

    /**
     * 指定时间增减月数
     * @param d1 时间
     * @param months 月数
     * @return 计算结果
     */
    public static Date AddMonths(Date d1, int months){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.MONTH,months);
        return c.getTime();
    }

    /**
     * 指定时间增减年数
     * @param d1 时间
     * @param years 年数
     * @return 计算结果
     */
    public static Date AddYears(Date d1, long years){
        return IanStringTool.AddYears(d1,(int)years);
    }

    /**
     * 指定时间增减年数
     * @param d1 时间
     * @param years 年数
     * @return 计算结果
     */
    public static Date AddYears(Date d1, int years){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.YEAR,years);
        return c.getTime();
    }

    /**
     * 指定时间增减特定时长
     * @param d1 时间
     * @param years 年
     * @param months 月
     * @param days 日
     * @param hours 小时
     * @param minutes 分
     * @param seconds 秒
     * @return 计算结果
     */
    public static Date AddTime(Date d1, int years, int months, int days, int hours, int minutes, int seconds){
        Calendar c= Calendar.getInstance();
        c.setTime(d1);
        c.add(Calendar.YEAR,years);
        c.add(Calendar.MONTH,months);
        c.add(Calendar.DAY_OF_MONTH,days);
        c.add(Calendar.HOUR,hours);
        c.add(Calendar.MINUTE,minutes);
        c.add(Calendar.SECOND,seconds);
        return c.getTime();
    }

    /**
     * 获取时间的年
     * @param d 时间
     * @return 年
     */
    public static int GetDateYear(Date d){
        Calendar c= Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取时间的月
     * @param d 时间
     * @return 月
     */
    public static int GetDateMonth(Date d){
        Calendar c= Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.MONTH);
    }

    /**
     * 获取时间的日
     * @param d 时间
     * @return 日
     */
    public static int GetDateDay(Date d){
        Calendar c= Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取时间的小时
     * @param d 时间
     * @return 小时
     */
    public static int GetDateHour(Date d){
        Calendar c= Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取时间的分钟
     * @param d 时间
     * @return 分钟
     */
    public static int GetDateMinute(Date d){
        Calendar c= Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 获取时间的秒
     * @param d 时间
     * @return 秒
     */
    public static int GetDateSecond(Date d){
        Calendar c= Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.SECOND);
    }

    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        return IanStringTool.generateNonceStr(32);
    }

    /**
     * 生成指定长度的随机串
     * @param len 长度
     * @return 随机串
     */
    public static String generateNonceStr(int len){
        if(len<=0) len=32;
        char[] nonceChars = new char[len];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }

    /**
     * 生成指定长度的随机串(排除0O1il容易混淆的字符)
     * @param len 长度
     * @return 随机串
     */
    public static String generateNonceStr2(int len){
        if(len<=0) len=32;
        char[] nonceChars = new char[len];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS2.charAt(RANDOM.nextInt(SYMBOLS2.length()));
        }
        return new String(nonceChars);
    }

    /**
     * 生成指定长度的随机数字串
     * @param len 长度
     * @return 随机串
     */
    public static String genNumbers(int len){
        if(len<=0) len=32;
        char[] nonceChars = new char[len];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS3.charAt(RANDOM.nextInt(SYMBOLS3.length()));
        }
        return new String(nonceChars);
    }

    /**
     * 检测值
     * @param value 值
     * @param valueName 值名称
     * @return 检测处理后的值
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
     * @param value 被处理特定值
     * @param defValue 特定值不在组值中时，默认返回的值，这个值必须也是组值中的一个
     * @param values 组值
     * @return 处理后的特定值
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
     * @param value 值
     * @param defValue 默认值
     * @return 处理后的值
     */
    public static String Deal(String value, String defValue){
        if(value==null) value="";
        value=value.trim();
        if(value.length()==0) value=defValue;
        return value;
    }

    /**
     * 生成Guid值
     * @return Guid
     */
    public static String Guid(){
        UUID uuid= UUID.randomUUID();
        return String.valueOf(uuid);
    }

    /**
     * 生成Guid值(不含-)
     * @return Guid(不含-)
     */
    public static String Guid2(){
        return IanStringTool.Guid().replace("-","");
    }

    /**
     * 生成为0的Guid值
     * @return 00000000-0000-0000-0000-000000000000
     */
    public static String GuidEmpty(){
        return "00000000-0000-0000-0000-000000000000";
    }

    /**
     * 系统编号
     */
    public static final long SystemOptID=0;
    /**
     * 系统名称
     */
    public static final String SystemOptName="系统";

    /**
     * 获取完整的错误信息
     * @param ex 错误
     * @return 错误信息
     */
    public static String GetFullExceptionInfo(Exception ex){
        if(ex==null) return "";
        StringBuilder sb=new StringBuilder();
        sb.append(ex.getMessage()+"\r\n");
        sb.append(IanStringTool.GetExceptionStackInfo(ex));
        return sb.toString();
    }

    /**
     * 获取完整的错误信息
     * @param ex 错误
     * @return 错误堆栈信息
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

    /**
     * 获取完整的错误信息
     * @param sts 错误堆栈
     * @return 错误堆栈信息
     */
    public static String GetExceptionStackInfo(StackTraceElement[] sts){
        if(sts==null) sts=new StackTraceElement[0];
        if(sts.length==0) return "";
        StringBuilder sb=new StringBuilder();
        for(StackTraceElement st : sts){
            sb.append(st.toString()+"\r\n");
        }
        return sb.toString();
    }

    /**
     * 是否是手机号
     * @param v 字符串
     * @return 是否
     */
    public static boolean IsMobile(String v){
        v=IanStringTool.Deal(v,"");
        return Pattern.matches("^(1[3-9])\\d{9}$",v);
    }

    /**
     * 是否是邮件格式
     * @param v 字符串
     * @return 是否
     */
    public static boolean IsMail(String v){
        v=IanStringTool.Deal(v,"");
        return Pattern.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$",v);
    }

    /**
     * 是否是整型
     * @param v 整型字符串
     * @return 是否
     * @throws Exception
     */
    public static boolean IsInt(String v) throws Exception {
        v=IanStringTool.Check(v,"参数值");
        return Pattern.matches("^(-)?\\d+$",v);
    }

    /**
     * 是否是整型
     * @param v 整型字符串
     * @return 是否
     * @throws Exception
     */
    public static boolean IsFloat(String v) throws Exception {
        v=IanStringTool.Check(v,"参数值");
        return Pattern.matches("^((-)?(\\d+\\.\\d+))$",v);
    }

    /**
     * 是否是中文单词
     * @param v 中文
     * @return 是否
     * @throws Exception
     */
    public static boolean IsChinese(String v) throws Exception {
        if(v==null) throw new Exception("参数值不能为空");
        return Pattern.matches("^[\u0391-\uFFE5]$",v);
    }
    /**
     * 是否是中文单词字符串
     * @param v 中文
     * @return 是否
     * @throws Exception
     */
    public static boolean IsChineses(String v) throws Exception {
        if(v==null) throw new Exception("参数值不能为空");
        return Pattern.matches("^[\u0391-\uFFE5]+$",v);
    }

    /**
     * 按中文2字节方式计算字符串长度
     * @param v 字符串
     * @return 长度
     * @throws Exception
     */
    public static int GetChineseLength(String v) throws Exception {
        if(v==null) throw new Exception("参数值不能为空");
        int len=0;
        for(int i=0;i<v.length();i++){
            String s=v.substring(i,i+1);
            if(IanStringTool.IsChinese(s)){
                len+=2;
            }else{
                len++;
            }
        }
        return len;
    }

    /**
     * 按中文2字节方式截取字符串，如果截取最后，有半个中文，则放弃该中文，如果开始位置是半个中文，则向前移动半个中文位置
     * @param v 字符串
     * @param pos 开始位置
     * @param len 长度
     * @return 截取结果
     * @throws Exception
     */
    public static String SubStringWithChinese(String v,int pos,int len) throws Exception {
        if(v==null) throw new Exception("参数值不能为空");
        if(pos<0) pos=0;
        int clen=IanStringTool.GetChineseLength(v);
        int pos1=0;
        int pos0=0;
        for(int i=0;i<v.length();i++){
            if(pos1==pos) {
                pos0=i;
                break;
            }
            if(pos1>pos){
                pos=pos - 1;
                pos0=i - 1;
                break;
            }
            String s=v.substring(i,i+1);
            if(IanStringTool.IsChinese(s)){
                pos1+=2;
            }else{
                pos1++;
            }
        }

        if(pos>clen - 1) throw new Exception("字符串截取起始位置不能大于长度 - 1");
        if(len<=0) len=clen;
        if(pos+len>clen) len=clen - pos;
        StringBuilder sb=new StringBuilder();
        int len1=0;
        while(pos0<v.length()){
            String s=v.substring(pos0,pos0+1);
            if(IanStringTool.IsChinese(s)){
                len1+=2;
            }else{
                len1++;
            }
            if(len1>len) break;
            sb.append(s);
            pos0++;
        }
        return sb.toString();
    }

    /**
     * 是否基本类型 int string float date double等
     * @param t 类型
     * @param <T> 泛型
     * @return 是否
     */
    public static <T> boolean IsBaseType(Class<T> t){
        boolean bIsBaseType=false;
        try{
            bIsBaseType=((Class)(t.getField("TYPE").get(null))).isPrimitive();
        }catch (Exception e1){

        }
        if(t.getTypeName().equals("java.util.Date")) bIsBaseType=true;
        return bIsBaseType;
    }

    /**
     * 以正则表达式判断值是否符合要求
     * @param v 值
     * @param reg 正则表达式
     * @return 是否符合
     * @throws Exception
     */
    public static boolean CheckRegexValue(String v,String reg) throws Exception {
        v=IanStringTool.Check(v,"参数值");
        return Pattern.matches(reg,v);
    }

    /**
     * 默认查询每页记录数
     */
    public static final int PageRows=20;

    /**
     * 系统操作员编号
     * @return
     */
    public static long getSystemOptID(){
        return 0;
    }

    /**
     * 系统操作员名称
     * @return
     */
    public static String getSystemOptName(){
        return "系统";
    }

    /**
     * 读取文件文本内容(UTF-8编码)
     * @param filePath 文件路径
     * @return 文件内容
     * @throws IOException
     */
    public static String readFile(String filePath) throws IOException {
        return  IanStringTool.readFile(filePath,"utf-8");
    }

    /**
     * 读取文件文本内容
     * @param filePath 文件路径
     * @param encode 编码
     * @return 文件内容
     * @throws IOException
     */
    public static String readFile(String filePath, String encode) throws IOException {
        File finfo=new File(filePath);
        if(!finfo.exists()) return null;
        FileInputStream stream=null;
        String s="";
        try{
            stream=new FileInputStream(finfo);
            int len=stream.available();
            byte[] bs=new byte[len];
            stream.read(bs);
            s=new String(bs,encode);
        }catch (Exception e){
            throw e;
        }finally {
            if(stream!=null){
                stream.close();
            }
        }
        return s;
    }

    /**
     * 写文件(utf-8)
     * @param filePath 文件路径
     * @param content 内容
     * @param isOverWrite 是否覆盖重写
     * @throws IOException
     */
    public static void writeFile(String filePath,String content,boolean isOverWrite) throws IOException {
        IanStringTool.writeFile(filePath,"utf-8",content,isOverWrite);
    }

    /**
     * 写文件
     * @param filePath 文件路径
     * @param encode 编码
     * @param content 内容
     * @param isOverWrite 是否覆盖重写
     * @throws IOException
     */
    public static void writeFile(String filePath,String encode,String content,boolean isOverWrite) throws IOException {
        content=IanStringTool.Deal(content,"");
        File finfo=new File(filePath);
        FileOutputStream stream=null;
        String s="";
        try{
            if(isOverWrite){
                stream=new FileOutputStream(finfo);
            }else{
                stream=new FileOutputStream(finfo,true);
            }
            byte[] bs=content.getBytes(encode);
            stream.write(bs);
        }catch (Exception e){
            throw e;
        }finally {
            if(stream!=null){
                stream.close();
            }
        }
    }

    private  static Random _rnd=new Random();

    /**
     * 取指定范围的整型随机数
     * @param min 最小值
     * @param max 最大值
     * @return 整型随机数
     */
    public static int GetRandom(int min,int max){
        return IanStringTool._rnd.nextInt(max - min) + min;
    }

    /**
     * 取长整型随机数
     * @return 长整型随机数
     */
    public static long GetRandom(){
        return IanStringTool._rnd.nextLong();
    }

    /**
     * 将字符串压缩成字节数组
     * @param s 被压缩字符串
     * @return 压缩后的字节数组
     * @throws IOException
     */
    public static byte[] StringZip(String s) throws IOException {
        if(s==null) return null;
        byte[] bs=s.getBytes(StandardCharsets.UTF_8);
        return IanStringTool.Zip(bs);
    }

    /**
     * 压缩字节数组
     * @param data 被压缩的字节数组
     * @return 压缩后的字节数组
     */
    public static byte[] Zip(byte[] data) throws IOException {
        if(data==null) data=new byte[0];
        byte[] bs=null;
        ByteArrayOutputStream bos=null;
        ZipOutputStream zip=null;
        try{
            bos=new ByteArrayOutputStream();
            zip=new ZipOutputStream(bos);
            ZipEntry ze=new ZipEntry("zip");
            ze.setSize(data.length);
            zip.putNextEntry(ze);
            zip.write(data);
            zip.closeEntry();
            bs=bos.toByteArray();
        }catch (Exception ex){
            throw ex;
        }finally {
            if(bos!=null){
                bos.close();
            }
            if(zip!=null){
                zip.close();
            }
        }
        return bs;
    }

    /**
     * 解压缩字节数组
     * @param data 被压缩字节数组
     * @return 解压缩后的字节数组
     * @throws IOException
     */
    public static byte[] UnZip(byte[] data) throws IOException {
        if(data==null) data=new byte[0];
        if(data.length==0) return new byte[0];
        byte[] bs=null;
        ByteArrayInputStream  bis=null;
        ByteArrayOutputStream bos=null;
        ZipInputStream zip=null;
        try{
            bis=new ByteArrayInputStream(data);
            bos=new ByteArrayOutputStream();
            zip=new ZipInputStream(bis);
            while(zip.getNextEntry()!=null){
                byte[] buf=new byte[1024];
                int n=-1;
                while((n=zip.read(buf,0,buf.length))!=-1){
                    bos.write(buf,0,n);
                }
            }
            bs=bos.toByteArray();
        }catch (Exception ex){
            throw ex;
        }finally {
            if(bos!=null){
                bos.close();
            }
            if(bis!=null){
                bis.close();
            }
            if(zip!=null){
                zip.close();
            }
        }
        return bs;
    }

    /**
     * 将压缩字节数组解压缩为字符串
     * @param data 字符串被压缩后的字节数组
     * @return 解压缩的字符串
     * @throws IOException
     */
    public static String StringUnZip(byte[] data) throws IOException {
        if(data==null) return null;
        byte[] bs=IanStringTool.UnZip(data);
        return new String(bs);
    }

    /**
     * 拆分成行
     * @param bs 字节数组
     * @return 字符串列表
     * @throws Exception
     */
    public static List<String> Splite2Lines(byte[] bs) throws Exception{
        List<String> ls=new ArrayList<String>();
        ByteArrayInputStream bin=null;
        BufferedReader buf=null;
        try{
            bin=new ByteArrayInputStream(bs);
            buf=new BufferedReader(new InputStreamReader(bin));
            String s=null;
            while((s=buf.readLine())!=null){
                ls.add(s);
            }
        }finally {
            if(buf!=null){
                buf.close();
                buf=null;
            }
            if(bin!=null){
                bin.close();
                bin=null;
            }
        }

        return ls;
    }

    /**
     * 拆分成行
     * @param v 字符串
     * @param encoding 编码
     * @return 字符串列表
     * @throws Exception
     */
    public static List<String> Splite2Lines(String v,String encoding) throws Exception {
        byte[] bs = v.getBytes(encoding);
        return IanStringTool.Splite2Lines(bs);
    }

    /**
     * 拆分成行
     * @param v 字符串
     * @return 字符串列表
     * @throws Exception
     */
    public static List<String> Splite2Lines(String v) throws Exception{
        return IanStringTool.Splite2Lines(v,"utf-8");
    }
}
