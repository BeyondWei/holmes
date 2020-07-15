package com.shuzheng.holmes.sdk.flume.common;

public interface LoggingAdapter {
  void debug(String msg);
  void debug(String msg, Object... objects);
  void info(String msg);
  void warn(String msg);
  void warn(String msg, Throwable t);
  void error(String msg);
  void error(String msg, Throwable t);
}
