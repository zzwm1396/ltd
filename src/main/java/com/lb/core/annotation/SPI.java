package com.lb.core.annotation;

import java.lang.annotation.*;

/**
 * SPI声明
 * Created by libo on 2017/4/15.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPI {
    /**
     * config中的键值
     */
    String key() default "";

    /**
     * 默认扩展实现
     */
    String dftValue() default "";
}
