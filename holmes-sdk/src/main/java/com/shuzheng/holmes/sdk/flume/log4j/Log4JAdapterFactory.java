package com.shuzheng.holmes.sdk.flume.log4j;

import com.shuzheng.holmes.sdk.flume.common.LoggingAdapter;
import com.shuzheng.holmes.sdk.flume.common.LoggingAdapterFactory;
import org.apache.log4j.Logger;
import org.slf4j.helpers.MessageFormatter;

public class Log4JAdapterFactory implements LoggingAdapterFactory {

  @Override
  public LoggingAdapter create(Class clazz) {
    return new Log4JAdapter(Logger.getLogger(clazz));
  }

  private static class Log4JAdapter implements LoggingAdapter {

    private final Logger logger;

    public Log4JAdapter(Logger logger) {
      this.logger = logger;
    }

    @Override
    public void debug(String msg) {
      logger.debug(msg);
    }

    @Override
    public void debug(String msg, Object... objects) {
      if (logger.isDebugEnabled()) {
        logger.debug(MessageFormatter.format(msg, objects));
      }
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
