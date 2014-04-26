package cn.liuyb.app.common.utils;

import org.slf4j.Logger;
import org.slf4j.Marker;

import static cn.liuyb.app.common.utils.Slf4jLogUtils.cleanLogMessage;

public class LoggerWrapper implements Logger {

    protected final Logger logger;

    public LoggerWrapper(Logger logger) {
        this.logger = logger;
    }

    public String getName() {
        return logger.getName();
    }

    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    public void trace(String msg) {
        if (logger.isTraceEnabled()) {
            logger.trace(cleanLogMessage(msg));
        }
    }

    public void trace(String format, Object arg) {
        if (logger.isTraceEnabled()) {
            logger.trace(format, cleanLogMessage(arg));
        }
    }

    public void trace(String format, Object arg1, Object arg2) {
        if (logger.isTraceEnabled()) {
            logger.trace(format, cleanLogMessage(arg1), cleanLogMessage(arg2));
        }
    }

    public void trace(String format, Object[] argArray) {
        if (logger.isTraceEnabled()) {
            logger.trace(format, cleanLogMessage(argArray));
        }
    }

    public void trace(String msg, Throwable t) {
        if (logger.isTraceEnabled()) {
            logger.trace(cleanLogMessage(msg), t);
        }
    }

    public boolean isTraceEnabled(Marker marker) {
        return logger.isTraceEnabled(marker);
    }

    public void trace(Marker marker, String msg) {
        if (logger.isTraceEnabled(marker)) {
            logger.trace(marker, cleanLogMessage(msg));
        }
    }

    public void trace(Marker marker, String format, Object arg) {
        if (logger.isTraceEnabled(marker)) {
            logger.trace(marker, format, cleanLogMessage(arg));
        }
    }

    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        if (logger.isTraceEnabled(marker)) {
            logger.trace(marker, format, cleanLogMessage(arg1), cleanLogMessage(arg2));
        }
    }

    public void trace(Marker marker, String format, Object[] argArray) {
        if (logger.isTraceEnabled(marker)) {
            logger.trace(marker, format, cleanLogMessage(argArray));
        }
    }

    public void trace(Marker marker, String msg, Throwable t) {
        if (logger.isTraceEnabled(marker)) {
            logger.trace(marker, cleanLogMessage(msg), t);
        }
    }

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public void debug(String msg) {
        if (logger.isDebugEnabled()) {
            logger.debug(cleanLogMessage(msg));
        }
    }

    public void debug(String format, Object arg) {
        if (logger.isDebugEnabled()) {
            logger.debug(format, cleanLogMessage(arg));
        }
    }

    public void debug(String format, Object arg1, Object arg2) {
        if (logger.isDebugEnabled()) {
            logger.debug(format, cleanLogMessage(arg1), cleanLogMessage(arg2));
        }
    }

    public void debug(String format, Object[] argArray) {
        if (logger.isDebugEnabled()) {
            logger.debug(format, cleanLogMessage(argArray));
        }
    }

    public void debug(String msg, Throwable t) {
        if (logger.isDebugEnabled()) {
            logger.debug(cleanLogMessage(msg), t);
        }
    }

    public boolean isDebugEnabled(Marker marker) {
        return logger.isDebugEnabled(marker);
    }

    public void debug(Marker marker, String msg) {
        if (logger.isDebugEnabled(marker)) {
            logger.debug(marker, cleanLogMessage(msg));
        }
    }

    public void debug(Marker marker, String format, Object arg) {
        if (logger.isDebugEnabled(marker)) {
            logger.debug(marker, format, cleanLogMessage(arg));
        }
    }

    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        if (logger.isDebugEnabled(marker)) {
            logger.debug(marker, format, cleanLogMessage(arg1), cleanLogMessage(arg2));
        }
    }

    public void debug(Marker marker, String format, Object[] argArray) {
        if (logger.isDebugEnabled(marker)) {
            logger.debug(marker, format, cleanLogMessage(argArray));
        }
    }

    public void debug(Marker marker, String msg, Throwable t) {
        if (logger.isDebugEnabled(marker)) {
            logger.debug(marker, cleanLogMessage(msg), t);
        }
    }

    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    public void info(String msg) {
        if (logger.isInfoEnabled()) {
            logger.info(cleanLogMessage(msg));
        }
    }

    public void info(String format, Object arg) {
        if (logger.isInfoEnabled()) {
            logger.info(format, cleanLogMessage(arg));
        }
    }

    public void info(String format, Object arg1, Object arg2) {
        if (logger.isInfoEnabled()) {
            logger.info(format, cleanLogMessage(arg1), cleanLogMessage(arg2));
        }
    }

    public void info(String format, Object[] argArray) {
        if (logger.isInfoEnabled()) {
            logger.info(format, cleanLogMessage(argArray));
        }
    }

    public void info(String msg, Throwable t) {
        if (logger.isInfoEnabled()) {
            logger.info(cleanLogMessage(msg), t);
        }
    }

    public boolean isInfoEnabled(Marker marker) {
        return logger.isInfoEnabled(marker);
    }

    public void info(Marker marker, String msg) {
        if (logger.isInfoEnabled(marker)) {
            logger.info(marker, cleanLogMessage(msg));
        }
    }

    public void info(Marker marker, String format, Object arg) {
        if (logger.isInfoEnabled(marker)) {
            logger.info(marker, format, cleanLogMessage(arg));
        }
    }

    public void info(Marker marker, String format, Object arg1, Object arg2) {
        if (logger.isInfoEnabled(marker)) {
            logger.info(marker, format, cleanLogMessage(arg1), cleanLogMessage(arg2));
        }
    }

    public void info(Marker marker, String format, Object[] argArray) {
        if (logger.isInfoEnabled(marker)) {
            logger.info(marker, format, cleanLogMessage(argArray));
        }
    }

    public void info(Marker marker, String msg, Throwable t) {
        if (logger.isInfoEnabled(marker)) {
            logger.info(marker, cleanLogMessage(msg), t);
        }
    }

    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    public void warn(String msg) {
        if (logger.isWarnEnabled()) {
            logger.warn(cleanLogMessage(msg));
        }
    }

    public void warn(String format, Object arg) {
        if (logger.isWarnEnabled()) {
            logger.warn(format, cleanLogMessage(arg));
        }
    }

    public void warn(String format, Object[] argArray) {
        if (logger.isWarnEnabled()) {
            logger.warn(format, cleanLogMessage(argArray));
        }
    }

    public void warn(String format, Object arg1, Object arg2) {
        if (logger.isWarnEnabled()) {
            logger.warn(format, cleanLogMessage(arg1), cleanLogMessage(arg2));
        }
    }

    public void warn(String msg, Throwable t) {
        if (logger.isWarnEnabled()) {
            logger.warn(cleanLogMessage(msg), t);
        }
    }

    public boolean isWarnEnabled(Marker marker) {
        return logger.isWarnEnabled(marker);
    }

    public void warn(Marker marker, String msg) {
        if (logger.isWarnEnabled(marker)) {
            logger.warn(marker, cleanLogMessage(msg));
        }
    }

    public void warn(Marker marker, String format, Object arg) {
        if (logger.isWarnEnabled(marker)) {
            logger.warn(marker, format, cleanLogMessage(arg));
        }
    }

    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        if (logger.isWarnEnabled(marker)) {
            logger.warn(marker, format, cleanLogMessage(arg1), cleanLogMessage(arg2));
        }
    }

    public void warn(Marker marker, String format, Object[] argArray) {
        if (logger.isWarnEnabled(marker)) {
            logger.warn(marker, format, cleanLogMessage(argArray));
        }
    }

    public void warn(Marker marker, String msg, Throwable t) {
        if (logger.isWarnEnabled(marker)) {
            logger.warn(marker, cleanLogMessage(msg), t);
        }
    }

    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    public void error(String msg) {
        if (logger.isErrorEnabled()) {
            logger.error(cleanLogMessage(msg));
        }
    }

    public void error(String format, Object arg) {
        if (logger.isErrorEnabled()) {
            logger.error(format, cleanLogMessage(arg));
        }
    }

    public void error(String format, Object arg1, Object arg2) {
        if (logger.isErrorEnabled()) {
            logger.error(format, cleanLogMessage(arg1), cleanLogMessage(arg2));
        }
    }

    public void error(String format, Object[] argArray) {
        if (logger.isErrorEnabled()) {
            logger.error(format, cleanLogMessage(argArray));
        }
    }

    public void error(String msg, Throwable t) {
        if (logger.isErrorEnabled()) {
            logger.error(cleanLogMessage(msg), t);
        }
    }

    public boolean isErrorEnabled(Marker marker) {
        return logger.isErrorEnabled(marker);
    }

    public void error(Marker marker, String msg) {
        if (logger.isErrorEnabled(marker)) {
            logger.error(marker, cleanLogMessage(msg));
        }
    }

    public void error(Marker marker, String format, Object arg) {
        if (logger.isErrorEnabled(marker)) {
            logger.error(marker, format, cleanLogMessage(arg));
        }
    }

    public void error(Marker marker, String format, Object arg1, Object arg2) {
        if (logger.isErrorEnabled(marker)) {
            logger.error(marker, format, cleanLogMessage(arg1), cleanLogMessage(arg2));
        }
    }

    public void error(Marker marker, String format, Object[] argArray) {
        if (logger.isErrorEnabled(marker)) {
            logger.error(marker, format, cleanLogMessage(argArray));
        }
    }

    public void error(Marker marker, String msg, Throwable t) {
        if (logger.isErrorEnabled(marker)) {
            logger.error(marker, cleanLogMessage(msg), t);
        }
    }

}