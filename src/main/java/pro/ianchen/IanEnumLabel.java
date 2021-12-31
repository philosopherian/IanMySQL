package pro.ianchen;

import java.lang.annotation.*;

/**
 * 枚举描述注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Inherited
public @interface IanEnumLabel {
    int value() default 0;
    String svalue() default "";
    String label() default "描述";
}
