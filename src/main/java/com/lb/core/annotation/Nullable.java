package com.lb.core.annotation;

import java.lang.annotation.*;

/**
 * 标识字段可以为空
 * Created by libo on 2017/4/14.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface Nullable {
}
