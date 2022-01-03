package pro.ianchen;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 枚举描述注解操作
 * 操作使用枚举描述注解的枚举
 */
public class IanEnumLabelOpt {
    /**
     * 获取枚举（使用枚举描述注解）的整型值和描述组成的列表
     * @param t 使用枚举描述注解的枚举
     * @param <T>  使用枚举描述注解的枚举
     * @return 使用枚举描述注解的枚举成员的整型值和描述组成的列表
     * @throws NoSuchFieldException
     */
    public static <T extends Enum<T>> List<IanLongName> GetEnumLabels(Class<T> t) throws NoSuchFieldException {
        List<IanLongName> ds=new ArrayList<IanLongName>();
        for(Enum<T> v:t.getEnumConstants()){
            Field f =t.getDeclaredField(v.name());
            IanEnumLabel l=f.getAnnotation(IanEnumLabel.class);
            if(l!=null){
                IanLongName d=new IanLongName();
                d.id=l.value();
                d.name=l.label();
                ds.add(d);
            }
        }
        return ds;
    }
    /**
     * 获取枚举（使用枚举描述注解）的字符串值和描述组成的列表
     * @param t 使用枚举描述注解的枚举
     * @param <T>  使用枚举描述注解的枚举
     * @return 使用枚举描述注解的枚举成员的字符串值和描述组成的列表
     * @throws NoSuchFieldException
     */
    public static <T extends Enum<T>> List<IanStringName> GetEnumStringValueLabels(Class<T> t) throws NoSuchFieldException {
        List<IanStringName> ds=new ArrayList<IanStringName>();
        for(Enum<T> v:t.getEnumConstants()){
            Field f =t.getDeclaredField(v.name());
            IanEnumLabel l=f.getAnnotation(IanEnumLabel.class);
            if(l!=null){
                IanStringName d=new IanStringName();
                d.id=l.svalue();
                d.name=l.label();
                ds.add(d);
            }
        }
        return ds;
    }

    /**
     * 根据枚举获取对应的描述
     * @param t 使用枚举描述注解的枚举
     * @param a 枚举成员
     * @param <T> 使用枚举描述注解的枚举
     * @return 枚举对应的描述
     * @throws NoSuchFieldException
     */
    public static <T extends Enum<T>> String GetEnumLabel(Class<T> t, T a) throws NoSuchFieldException {
        for(Enum<T> v:t.getEnumConstants()){
            if(v==a){
                Field f =t.getDeclaredField(v.name());
                IanEnumLabel l=f.getAnnotation(IanEnumLabel.class);
                if(l!=null){
                    return l.label();
                }
            }
        }
        return a.name();
    }

    /**
     * 根据整型值获取对应的枚举描述
     * @param t 使用枚举描述注解的枚举
     * @param val 代表枚举成员的整型值
     * @param <T> 使用枚举描述注解的枚举
     * @return 枚举描述
     * @throws Exception
     */
    public static <T extends Enum<T>> String GetEnumLabelByValue(Class<T> t, int val) throws Exception {
        for(Enum<T> v:t.getEnumConstants()){
            Field f =t.getDeclaredField(v.name());
            IanEnumLabel l=f.getAnnotation(IanEnumLabel.class);
            if(l!=null&&l.value()==val){
                return l.label();
            }
        }
        throw new Exception("未找到指定值"+val+"的枚举");
    }

    /**
     * 根据字符串值获取对应的枚举描述
     * @param t 使用枚举描述注解的枚举
     * @param val 代表枚举成员的整型值
     * @param <T> 使用枚举描述注解的枚举
     * @return 枚举描述
     * @throws Exception
     */
    public static <T extends Enum<T>> String GetEnumLabelByValue(Class<T> t, String val) throws Exception {
        for(Enum<T> v:t.getEnumConstants()){
            Field f =t.getDeclaredField(v.name());
            IanEnumLabel l=f.getAnnotation(IanEnumLabel.class);
            if(l!=null&& l.svalue().equals(val)){
                return l.label();
            }
        }
        throw new Exception("未找到指定值"+val+"的枚举");
    }

    /**
     * 根据长整型值获取对应的枚举描述
     * @param t 使用枚举描述注解的枚举
     * @param val 代表枚举成员的整型值
     * @param <T> 使用枚举描述注解的枚举
     * @return 枚举描述
     * @throws Exception
     */
    public static <T extends Enum<T>> String GetEnumLabelByValue(Class<T> t, long val) throws Exception {
        for(Enum<T> v:t.getEnumConstants()){
            Field f =t.getDeclaredField(v.name());
            IanEnumLabel l=f.getAnnotation(IanEnumLabel.class);
            if(l!=null&&l.value()==val){
                return l.label();
            }
        }
        throw new Exception("未找到指定值"+val+"的枚举");
    }

    /**
     * 判断使用枚举描述注解的枚举是否包含指定的整型值
     * @param t 使用枚举描述注解的枚举
     * @param a 整型值
     * @param <T> 使用枚举描述注解的枚举
     * @return 是否包含
     * @throws NoSuchFieldException
     */
    public static <T extends Enum<T>> Boolean IsContainValue(Class<T> t, int a) throws NoSuchFieldException {
        for(Enum<T> v:t.getEnumConstants()){
            Field f =t.getDeclaredField(v.name());
            IanEnumLabel l=f.getAnnotation(IanEnumLabel.class);
            if(l!=null&&l.value()==a){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断使用枚举描述注解的枚举是否包含指定的字符串值
     * @param t 使用枚举描述注解的枚举
     * @param a 整型值
     * @param <T> 使用枚举描述注解的枚举
     * @return 是否包含
     * @throws NoSuchFieldException
     */
    public static <T extends Enum<T>> Boolean IsContainValue(Class<T> t, String a) throws NoSuchFieldException {
        for(Enum<T> v:t.getEnumConstants()){
            Field f =t.getDeclaredField(v.name());
            IanEnumLabel l=f.getAnnotation(IanEnumLabel.class);
            if(l!=null&& l.svalue().equals(a)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断使用枚举描述注解的枚举是否包含指定的长整型值
     * @param t 使用枚举描述注解的枚举
     * @param a 整型值
     * @param <T> 使用枚举描述注解的枚举
     * @return 是否包含
     * @throws NoSuchFieldException
     */
    public static <T extends Enum<T>> Boolean IsContainValue(Class<T> t, long a) throws NoSuchFieldException {
        for(Enum<T> v:t.getEnumConstants()){
            Field f =t.getDeclaredField(v.name());
            IanEnumLabel l=f.getAnnotation(IanEnumLabel.class);
            if(l!=null&&l.value()==a){
                return true;
            }
        }
        return false;
    }

    /**
     * 根据名称获取枚举描述注解的枚举的枚举成员
     * @param t 使用枚举描述注解的枚举
     * @param name 名称
     * @param <T> 使用枚举描述注解的枚举
     * @return 符合条件的枚举成员
     * @throws Exception
     */
    public static <T extends Enum<T>> T GetEnumByName(Class<T> t, String name) throws Exception {
        for(Enum<T> v:t.getEnumConstants()){
            if(v.name().equals(name)) {
                return Enum.valueOf(t,name);
            }
        }
        throw new Exception("未找到指定名称"+name+"的枚举");
    }

    /**
     * 根据描述获取枚举描述注解的枚举的枚举成员
     * @param t 使用枚举描述注解的枚举
     * @param label 描述
     * @param <T> 使用枚举描述注解的枚举
     * @return 符合条件的枚举成员
     * @throws Exception
     */
    public static <T extends Enum<T>> T GetEnumByLabel(Class<T> t, String label) throws Exception {
        for(Enum<T> v:t.getEnumConstants()){
            Field f =t.getDeclaredField(v.name());
            IanEnumLabel l=f.getAnnotation(IanEnumLabel.class);
            if(l!=null&&l.label()==label){
                return Enum.valueOf(t,v.name());
            }
        }
        throw new Exception("未找到指定标签"+label+"的枚举");
    }

    /**
     * 根据值获取枚举成员
     * @param t 使用枚举描述注解的枚举
     * @param val 长整型值
     * @param <T> 使用枚举描述注解的枚举
     * @return 符合条件的枚举成员
     * @throws Exception
     */
    public static <T extends Enum<T>> T GetEnumByValue(Class<T> t, long val) throws Exception {
        for(Enum<T> v:t.getEnumConstants()){
            Field f =t.getDeclaredField(v.name());
            IanEnumLabel l=f.getAnnotation(IanEnumLabel.class);
            if(l!=null&&l.value()==val){
                return Enum.valueOf(t,v.name());
            }
        }
        throw new Exception("未找到指定值"+val+"的枚举");
    }

    /**
     * 根据值获取枚举成员
     * @param t 使用枚举描述注解的枚举
     * @param val 整型值
     * @param <T> 使用枚举描述注解的枚举
     * @return 符合条件的枚举成员
     * @throws Exception
     */
    public static <T extends Enum<T>> T GetEnumByValue(Class<T> t, int val) throws Exception {
        for(Enum<T> v:t.getEnumConstants()){
            Field f =t.getDeclaredField(v.name());
            IanEnumLabel l=f.getAnnotation(IanEnumLabel.class);
            if(l!=null&&l.value()==val){
                return Enum.valueOf(t,v.name());
            }
        }
        throw new Exception("未找到指定值"+val+"的枚举");
    }

    /**
     * 根据值获取枚举成员
     * @param t 使用枚举描述注解的枚举
     * @param val 字符串值
     * @param <T> 使用枚举描述注解的枚举
     * @return 符合条件的枚举成员
     * @throws Exception
     */
    public static <T extends Enum<T>> T GetEnumByValue(Class<T> t, String val) throws Exception {
        for(Enum<T> v:t.getEnumConstants()){
            Field f =t.getDeclaredField(v.name());
            IanEnumLabel l=f.getAnnotation(IanEnumLabel.class);
            if(l!=null&& l.svalue().equals(val)){
                return Enum.valueOf(t,v.name());
            }
        }
        throw new Exception("未找到指定值"+val+"的枚举");
    }

    /**
     * 枚举转换为整型值
     * @param t 使用枚举描述注解的枚举
     * @param <T> 使用枚举描述注解的枚举
     * @return 使用枚举描述注解的枚举对应的整型值
     * @throws Exception
     */
    public static <T extends Enum<T>> int GetEnumValue(T t) throws Exception {
        for(Enum v:t.getClass().getEnumConstants()){
            if(v==t){
                Field f =t.getClass().getDeclaredField(v.name());
                IanEnumLabel l=f.getAnnotation(IanEnumLabel.class);
                if(l!=null){
                    return l.value();
                }
            }
        }
        throw new Exception("此枚举没有设置value整型值");
    }

    /**
     * 枚举转换为整型值
     * @param t 使用枚举描述注解的枚举
     * @param <T> 使用枚举描述注解的枚举
     * @return 使用枚举描述注解的枚举对应的整型值
     * @throws Exception
     */
    public static <T extends Enum<T>> String GetEnumStringValue(T t) throws Exception {
        for(Enum v:t.getClass().getEnumConstants()){
            if(v==t){
                Field f =t.getClass().getDeclaredField(v.name());
                IanEnumLabel l=f.getAnnotation(IanEnumLabel.class);
                if(l!=null){
                    return l.svalue();
                }
            }
        }
        throw new Exception("此枚举没有设置value字符串值");
    }
}
