package com.shuzheng.holmes.sdk.flume.logback;

import com.shuzheng.holmes.sdk.flume.common.LoggingAdapter;
import com.shuzheng.holmes.sdk.flume.common.LoggingAdapterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackAdapterFactory implements LoggingAdapterFactory {
  @Override
  public LoggingAdapter create(Class clazz) {
    return new LogbackAdapter(LoggerFactory.getLogger(clazz));
  }

  private static class LogbackAdapter implements LoggingAdapter {

    private final Logger logger;

    LogbackAdapter(Logger logger) {
      this.logger = logger;
    }

    @Override
    public void debug(String msg) {
      logger.debug(msg);
    }

    @Override
    public void debug(String msg, Object... objects) {
      logger.debug(msg, objects);
    }

    @Override
    public void info(String msg) {
      logger.info(msg);
    }

    @Override
    public void warn(String msg) {
      logger.warn(msg);
    }

    @Override
    public void warn(String msg, Throwable t) {
      logger.warn(msg, t);
    }

    @Override
    public void error(String msg) {
      logger.error(msg);
    }

    @Override
    public void error(String msg, Throwable t) {
      logger.error(msg, t);
    }
  }
}
