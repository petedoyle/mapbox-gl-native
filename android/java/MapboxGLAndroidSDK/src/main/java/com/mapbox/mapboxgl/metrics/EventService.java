package com.mapbox.mapboxgl.metrics;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class EventService extends IntentService {

    private static final String TAG = "EventService";

    public static final String ACTION_EVENT_STATUS = "com.mapbox.mapboxgl.metrics.EventService.ACTION_EVENT_STATUS";

    public static final String EXTRA_EVENT_DATA = "com.mapbox.mapboxgl.metrics.EventService.EXTRA_EVENT_DATA";
    public static final String EXTRA_EVENT_STATUS = "com.mapbox.mapboxgl.metrics.EventService.EXTRA_EVENT_STATUS";

    public static final String STATUS_SUCCESS = "SUCCESS";

    public EventService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String data = intent.getStringExtra(EXTRA_EVENT_DATA);
        Log.v(TAG, "event received: " + data);

        // Send event upload success status
        Intent statusIntent = new Intent(ACTION_EVENT_STATUS);
        statusIntent.putExtra(EXTRA_EVENT_STATUS, STATUS_SUCCESS);
        LocalBroadcastManager.getInstance(this).sendBroadcast(statusIntent);
    }
}
