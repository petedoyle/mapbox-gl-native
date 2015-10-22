package com.mapbox.mapboxsdk;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import com.mapbox.mapboxsdk.views.MapView;

public class MapActivity extends Activity {

    //
    // Static members
    //

    // Tag used for logging
    private static final String TAG = "MapActivity";

    //
    // Instance members
    //

    // The access token
    private String mAccessToken;

    // The map
    private MapView mMap;

    //
    // Static methods
    //

    @UiThread
    @NonNull
    public static MapActivity newInstance(@NonNull String accessToken) {
        MapActivity activity = new MapActivity();
        activity.mAccessToken = accessToken;
        return activity;
    }

    //
    // Constructors
    //

    /**
     * Do not call from code.
     */
    public MapActivity() {
    }

    //
    // Lifecycle events
    //

    // Called when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the map
        setContentView(R.layout.mapview);
        mMap = (MapView) findViewById(R.id.mapView);
        mMap.setAccessToken(mAccessToken);

        // Need to pass on any saved state to the map
        mMap.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMap.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMap.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMap.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMap.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMap.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMap.onLowMemory();
    }

    //
    // Property methods
    //

    @Nullable
    public MapView getMap() {
        return mMap;
    }
}
