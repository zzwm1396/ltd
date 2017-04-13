package com.lb.core.compiler;

/**
 * Created by libo on 2017/4/13.
 */
public interface Compiler {
    Class<?> compiler(String code);
}
