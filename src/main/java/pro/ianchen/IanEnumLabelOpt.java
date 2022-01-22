package pro.ianchen;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 枚举描述注解操作
 * deal the description information of Enum annotation
 * 操作使用枚举描述注解的枚举
 * deal the Enum with the description information annotation
 */
public class IanEnumLabelOpt {
    /**
     * 获取枚举（使用枚举描述注解）的整型值和描述组成的列表
     * get the int value and description string list of the Enum with description information annotation
     * @param t 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @param <T>  使用枚举描述注解的枚举 the Enum used with description information annotation
     * @return 使用枚举描述注解的枚举成员的整型值和描述组成的列表 the int value and description string list of the Enum with description information annotation
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
     * get the string value and description string list of the Enum with description information annotation
     * @param t 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @param <T>  使用枚举描述注解的枚举 the Enum used with description information annotation
     * @return 使用枚举描述注解的枚举成员的字符串值和描述组成的列表 the string value and description string list of the Enum with description information annotation
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
     * get the description of the Enum member
     * @param t 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @param a 枚举成员 the Enum member
     * @param <T> 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @return 枚举对应的描述 the description of the Enum member
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
     * get the Enum member description by int value
     * @param t 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @param val 代表枚举成员的整型值 the int value of the Enum member
     * @param <T> 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @return 枚举描述 the description of the Enum member
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
     * get the Enum member description by string value
     * @param t 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @param val 代表枚举成员的整型值 the string value of the Enum member
     * @param <T> 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @return 枚举描述 the description of the Enum member
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
     * get the Enum member description by long value
     * @param t 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @param val 代表枚举成员的长整型值 the long value of the Enum member
     * @param <T> 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @return 枚举描述 the description of the Enum member
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
     * determines whether the Enum with description information annotation cantains the specified int value
     * @param t 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @param a 整型值 int value
     * @param <T> 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @return 是否包含 cantains the specified value
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
     * determines whether the Enum with description information annotation cantains the specified string value
     * @param t 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @param a 字符串值 string value
     * @param <T> 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @return 是否包含 cantains the specified value
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
     * determines whether the Enum with description information annotation cantains the specified long value
     * @param t 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @param a 长整型值 long value
     * @param <T> 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @return 是否包含 cantains the specified value
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
     * get the Enum member by the name of the Enum with description information annotation
     * @param t 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @param name the name of the Enum with description information annotation
     * @param <T> 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @return 符合条件的枚举成员 the Enum member of the Enum name is the specified name value
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
     * get the Enum member by the label of the Enum with description information annotation
     * @param t 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @param label 描述 the label of the Enum with description information annotation
     * @param <T> 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @return 符合条件的枚举成员 the Enum member of the Enum name is the specified label value
     * @throws Exception
     */
    public static <T extends Enum<T>> T GetEnumByLabel(Class<T> t, String label) throws Exception {
        for(Enum<T> v:t.getEnumConstants()){
            Field f =t.getDeclaredField(v.name());
            IanEnumLabel l=f.getAnnotation(IanEnumLabel.class);
            if(l!=null&& l.label().equals(label)){
                return Enum.valueOf(t,v.name());
            }
        }
        throw new Exception("未找到指定标签"+label+"的枚举");
    }

    /**
     * 根据长整型值获取枚举成员
     * get the Enum member by long value
     * @param t 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @param val 长整型值 long value
     * @param <T> 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @return 符合条件的枚举成员 the Enum member of the Enum int value is the specified long value
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
     * 根据整型值获取枚举成员
     * get the Enum member by int value
     * @param t 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @param val 整型值 int value
     * @param <T> 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @return 符合条件的枚举成员 the Enum member of the Enum int value is the specified int value
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
     * 根据字符串值获取枚举成员
     * get the Enum member by string value
     * @param t 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @param val 字符串值 string value
     * @param <T> 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @return 符合条件的枚举成员 the Enum member of the Enum string value is the specified string value
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
     * convert the Enum to int value
     * @param t 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @param <T> 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @return 使用枚举描述注解的枚举对应的整型值 the specified int value of the Enum with the description information annotation
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
     * 枚举转换为字符串值
     * convert the Enum to string value
     * @param t 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @param <T> 使用枚举描述注解的枚举 the Enum used with description information annotation
     * @return 使用枚举描述注解的枚举对应的字符串值  the specified string value of the Enum with the description information annotation
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
