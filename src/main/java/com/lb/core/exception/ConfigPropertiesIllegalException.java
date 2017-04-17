package com.lb.core.exception;

/**
 * config参数异常
 * Created by libo on 2017/4/17.
 */
public class ConfigPropertiesIllegalException extends RuntimeException {
    public ConfigPropertiesIllegalException() {
        super();
    }

    public ConfigPropertiesIllegalException(String message) {
        super(message);
    }

    public ConfigPropertiesIllegalException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigPropertiesIllegalException(Throwable cause) {
        super(cause);
    }
}
