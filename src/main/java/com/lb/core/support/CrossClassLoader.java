package com.lb.core.support;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Vector;

/**
 * 用来处理跨classLoader
 * 共享class
 * Created by libo on 2017/4/17.
 */
@Slf4j
public class CrossClassLoader {
    private static Field classes;
    private static final Object LOCK = new Object();

    static {
        try {
            classes = ClassLoader.class.getDeclaredField("classes");
            classes.setAccessible(true);
        } catch (Throwable e) {
            log.error("get ClassLoader 'classes' Field Error", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static Class loadClass(String classname) throws ClassNotFoundException {

        if (classes == null) {
            return Thread.currentThread().getContextClassLoader().loadClass(classname);
        }

        try {
            synchronized (LOCK) {
                Vector v = (Vector) classes.get(CrossClassLoader.class.getClassLoader().getParent());
                for (int i = 0; i < v.size(); i++) {
                    Class o = (Class) v.get(i);
                    if (classname.equals(o.getName())) {
                        return o;
                    }
                }
                Class clazz = CrossClassLoader.class.getClassLoader().loadClass(classname);
                v.add(clazz);
                return clazz;
            }
        } catch (Exception e) {
            throw new ClassNotFoundException("load " + classname + " Error ", e);
        }
    }
}
