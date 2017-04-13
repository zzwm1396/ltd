package com.lb.core.spi;

import java.lang.annotation.*;

/**
 * Created by libo on 2017/4/13.
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
