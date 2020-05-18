package com.automation.web.data;

/**
 * Data options class.
 * @author juan.montes
 */
public class Options {

    private final String destination;
    private final String duration;

    /**
     * Constructor.
     */
    public Options() {
        destination = "The Bahamas";
        duration = "6 - 9 Days";
    }

    /**
     * Get destination.
     * @return string
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Get duration.
     * @return string
     */
    public String getDuration() {
        return duration;
    }
}
