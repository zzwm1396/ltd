package com.lb.core.annotation;

import java.lang.annotation.*;

/**
 * 表明字段不允许为空
 * Created by libo on 2017/4/7.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface NotNull {
}
