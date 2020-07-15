package com.shuzheng.holmes.sdk.flume.common;

public interface LoggingAdapterFactory {
  LoggingAdapter create(Class clazz);
}
