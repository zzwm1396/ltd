package com.lb.core.compiler;

/**
 * 自定义编译器接口
 * Created by libo on 2017/4/17.
 */
public interface Compiler {

    Class<?> compile(String code);
}
