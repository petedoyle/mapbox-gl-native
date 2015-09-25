package com.mapbox.mapboxgl.metrics;

public class EventManager {

    static final int VERSION = 1;
    static final String USER_AGENT = "MapboxEventsAndroid/1.0";
    static final String EVENTS_API_BASE = "https://api.tiles.mapbox.com";

    private static EventManager mInstance = null;

    private EventManager() {
        super();

    }

    public static EventManager getInstance() {
        if (mInstance == null) {
            mInstance = new EventManager();
        }

        return mInstance;
    }

    void pauseMetricsCollection() {

    }

    void resumeMetricsCollection() {

    }

    void pushEvent(Event event) {

    }

    boolean isPushEnabled() {
        return true;
    }

    void flush() {

    }

    void validate() {

    }

}
