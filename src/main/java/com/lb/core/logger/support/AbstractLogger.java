package com.lb.core.logger.support;

import com.lb.core.logger.Logger;

/**
 * Created by libo on 2017/4/10.
 */
public abstract class AbstractLogger implements Logger {
    @Override
    public void trace(String format, Object... arguments) {
        if (isTraceEnabled()){

        }
    }

    @Override
    public void debug(String format, Object... arguments) {

    }

    @Override
    public void info(String format, Object... arguments) {

    }

    @Override
    public void warn(String format, Object... arguments) {

    }

    @Override
    public void error(String format, Object... objects) {

    }
}
