package com.lb.core.logger.support;

import com.lb.core.commons.utils.NetUtils;
import com.lb.core.logger.Logger;
import com.lb.core.support.Version;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by libo on 2017/4/10.
 */
public class FailSafeLogger  extends AbstractLogger implements Logger{

    @Getter
    @Setter
    private Logger logger;

    public FailSafeLogger(Logger logger){
        this.logger = logger;
    }

    private String appendContextMessage(String msg){
        return "[LTD] " + msg + ", ltd version: " + Version.getVersion() + ", current host: " + NetUtils.getLocalHost();
    }


    @Override
    public void trace(String msg) {

    }

    @Override
    public void trace(Throwable e) {

    }

    @Override
    public void trace(String msg, Throwable e) {

    }

    @Override
    public void debug(String msg) {

    }

    @Override
    public void debug(Throwable e) {

    }

    @Override
    public void debug(String msg, Throwable e) {

    }

    @Override
    public void info(String msg) {

    }

    @Override
    public void info(String msg, Throwable e) {

    }

    @Override
    public void info(Throwable e) {

    }

    @Override
    public void warn(String msg) {

    }

    @Override
    public void warn(String msg, Throwable e) {

    }

    @Override
    public void warn(Throwable e) {

    }

    @Override
    public void error(String msg) {

    }

    @Override
    public void error(String msg, Throwable e) {

    }

    @Override
    public void error(Throwable e) {

    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }
}
