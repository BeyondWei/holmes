package com.shuzheng.holmes.sdk.flume.log4j;

import com.shuzheng.holmes.sdk.flume.common.FlumeAvroManager;
import com.shuzheng.holmes.sdk.flume.common.LoggingAdapterFactory;
import com.shuzheng.holmes.sdk.flume.common.RemoteFlumeAgent;
import com.shuzheng.holmes.sdk.flume.common.StringUtils;
import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.*;

public class HolmesLog4jV1Appender extends AppenderSkeleton {

  protected static final Charset UTF_8 = Charset.forName("UTF-8");

  private FlumeAvroManager flumeManager;

  private String flumeAgents;

  private String flumeProperties;

  private Long reportingWindow;

  private Integer batchSize;

  private Integer reporterMaxThreadPoolSize;

  private Integer reporterMaxQueueSize;

  private Map<String, String> additionalAvroHeaders;

  private String application;

  private String hostname;

  private String type;

  private static final Logger logger = Logger.getLogger(HolmesLog4jV1Appender.class);

  private static final LoggingAdapterFactory loggingFactory = new Log4JAdapterFactory();

  private boolean activated = false;

  @Override
  public void activateOptions() {
    try {
      if (getLayout() == null) {
        logger.warn("Layout was not defined, will only log the message, no stack traces or custom layout");
      }
      if (StringUtils.isEmpty(application)) {
        application = resolveApplication();
      }

      if (StringUtils.isNotEmpty(flumeAgents)) {
        String[] agentConfigs = flumeAgents.split(",");

        List<RemoteFlumeAgent> agents = new ArrayList<RemoteFlumeAgent>(agentConfigs.length);
        for (String conf : agentConfigs) {
          RemoteFlumeAgent agent = RemoteFlumeAgent.fromString(conf.trim(), loggingFactory);
          if (agent != null) {
            agents.add(agent);
          } else {
            logger.warn("Cannot build a Flume agent config for '" + conf + "'");
          }
        }
        Properties overrides = new Properties();
        overrides.putAll(extractProperties(flumeProperties));
        flumeManager = FlumeAvroManager.create(agents, overrides,
            batchSize, reportingWindow, reporterMaxThreadPoolSize, reporterMaxQueueSize,
            loggingFactory);
      } else {
        logger.warn("Cannot configure a flume agent with an empty configuration");
      }
      super.activateOptions();
      activated = true;
    } catch (Exception e) {
      logger.error(e.getLocalizedMessage(),e);
    }
  }

  @Override
  protected void append(LoggingEvent eventObject) {
    try {
      if (!activated) {
        activateOptions();
      }
      String body = getLayout() != null ? getLayout().format(eventObject) : eventObject.getMessage().toString();
      Map<String, String> headers = new HashMap<String, String>();
      if (additionalAvroHeaders != null) {
        headers.putAll(additionalAvroHeaders);
      }
      headers.putAll(extractHeaders(eventObject));

      Event event = EventBuilder.withBody(body.trim(), UTF_8, headers);

      flumeManager.send(event);
    } catch (Exception e) {
        e.printStackTrace(System.err);
    }
  }

  @Override
  public void close() {
    flumeManager.stop();
  }

  @Override
  public boolean requiresLayout() {
    return false;
  }


  private Map<String, String> extractHeaders(LoggingEvent eventObject) {
    Map<String, String> headers = new HashMap<String, String>(10);
    headers.put("timestamp", Long.toString(eventObject.getTimeStamp()));
    headers.put("type", eventObject.getLevel().toString());
    headers.put("logger", eventObject.getLoggerName());
//    headers.put("message", eventObject.getMessage().toString());
    headers.put("level", eventObject.getLevel().toString());
    try {
      headers.put("host", resolveHostname());
    } catch (UnknownHostException e) {
      logger.warn(e.getMessage());
    }
    headers.put("thread", eventObject.getThreadName());
    if (StringUtils.isNotEmpty(application)) {
      headers.put("application", application);
    }

    if (StringUtils.isNotEmpty(type)) {
      headers.put("type", type);
    }

    return headers;
  }


  private Map<String, String> extractProperties(String propertiesAsString) {
    final Map<String, String> props = new HashMap<String, String>();
    if (StringUtils.isNotEmpty(propertiesAsString)) {
      final String[] segments = propertiesAsString.split(",");
      for (final String segment : segments) {
        final String[] pair = segment.split("=");
        if (pair.length == 2) {
          final String key = pair[0].trim();
          final String value = pair[1].trim();
          if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
            props.put(key, value);
          } else {
            logger.warn("Empty key or value not accepted: " + segment);
          }
        } else {
          logger.warn("Not a valid {key}:{value} format: " + segment);
        }
      }
    } else {
      logger.warn("Not overriding any flume agent properties");
    }

    return props;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public void setFlumeAgents(String flumeAgents) {
    this.flumeAgents = flumeAgents;
  }

  public void setFlumeProperties(String flumeProperties) {
    this.flumeProperties = flumeProperties;
  }

  public void setAdditionalAvroHeaders(String additionalHeaders) {
    this.additionalAvroHeaders = extractProperties(additionalHeaders);
  }

  public void setBatchSize(String batchSizeStr) {
    try {
      this.batchSize = Integer.parseInt(batchSizeStr);
    } catch (NumberFormatException nfe) {
      logger.warn("Cannot set the batchSize to " + batchSizeStr, nfe);
    }
  }

  public void setReportingWindow(String reportingWindowStr) {
    try {
      this.reportingWindow = Long.parseLong(reportingWindowStr);
    } catch (NumberFormatException nfe) {
      logger.warn("Cannot set the reportingWindow to " + reportingWindowStr, nfe);
    }
  }


  public void setReporterMaxThreadPoolSize(String reporterMaxThreadPoolSizeStr) {
    try {
      this.reporterMaxThreadPoolSize = Integer.parseInt(reporterMaxThreadPoolSizeStr);
    } catch (NumberFormatException nfe) {
      logger.warn("Cannot set the reporterMaxThreadPoolSize to " + reporterMaxThreadPoolSizeStr, nfe);
    }
  }

  public void setReporterMaxQueueSize(String reporterMaxQueueSizeStr) {
    try {
      this.reporterMaxQueueSize = Integer.parseInt(reporterMaxQueueSizeStr);
    } catch (NumberFormatException nfe) {
      logger.warn("Cannot set the reporterMaxQueueSize to " + reporterMaxQueueSizeStr, nfe);
    }
  }

  private String resolveApplication() {
    return System.getProperty("application.name");
  }

  private String resolveHostname() throws UnknownHostException {
    return hostname != null ? hostname : InetAddress.getLocalHost().getHostName();
  }

}