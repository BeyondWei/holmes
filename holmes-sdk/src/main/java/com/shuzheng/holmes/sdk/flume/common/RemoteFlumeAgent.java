package com.shuzheng.holmes.sdk.flume.common;

public final class RemoteFlumeAgent {

    private final String hostname;

    private final int port;

    public RemoteFlumeAgent(final String hostname, final int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public static RemoteFlumeAgent fromString(final String input, final LoggingAdapterFactory loggingAdapterFactory) {
        final LoggingAdapter logger = loggingAdapterFactory.create(RemoteFlumeAgent.class);

        if(StringUtils.isNotEmpty(input)) {

            String[] parts = input.split(":");
            if (parts.length == 2) {

                String portString = parts[1];
                try {
                    int port = Integer.parseInt(portString);
                    return new RemoteFlumeAgent(parts[0], port);
                } catch (NumberFormatException nfe) {
                    logger.error("Not a valid int: " + portString);
                }
            } else {
                logger.error("Not a valid [host]:[port] configuration: " + input);
            }
        } else {
            logger.error("Empty flume agent entry, an extra comma?");
        }
        return null;
    }
}
