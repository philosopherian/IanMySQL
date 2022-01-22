package pro.ianchen;

import java.lang.annotation.*;

/**
 * 枚举描述注解
 * Enum information annotation
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Inherited
public @interface IanEnumLabel {
    /**
     * 枚举对应的整型值
     * the int value to Enum value
     * 与字符串值只能二选一
     * must choose between int value and string value
     * @return 枚举对应的整型值 the int value to Enum value
     */
    int value() default 0;

    /**
     * 枚举对应的字符串值
     * the string value to Enum value
     * 与整型值只能二选一
     * must choose between int value and string value
     * @return 枚举对应的字符串值 the string value to Enum value
     */
    String svalue() default "";

    /**
     * 描述信息
     * description information of Enum
     * @return 描述信息 description information of Enum
     */
    String label() default "描述";
}
