package com.lb.core.logger.support;

import lombok.Getter;

/**
 * Created by libo on 2017/4/10.
 */
public class FormattingTuple {
    public static FormattingTuple Null = new FormattingTuple(null);

    @Getter
    private String message;

    @Getter
    private Throwable throwable;

    @Getter
    private Object[] argArray;

    public FormattingTuple(String message){
        this(message, null, null);
    }

    public FormattingTuple(String message, Object[] argArray, Throwable throwable){
        this.message = message;
        this.throwable = throwable;
        if (throwable == null){
            this.argArray = argArray;
        }else{
            this.argArray = trimmedCopy(argArray);
        }
    }

    static Object[] trimmedCopy(Object[] argArray){
        if (argArray == null || argArray.length == 0){
            throw new IllegalStateException("non-sensical empty or null argument array");
        }
        final int trimemdLen = argArray.length - 1;
        Object[] trimmed  = new Object[trimemdLen];
        System.arraycopy(argArray, 0, trimmed, 0, trimemdLen);
        return trimmed;
    }
}
