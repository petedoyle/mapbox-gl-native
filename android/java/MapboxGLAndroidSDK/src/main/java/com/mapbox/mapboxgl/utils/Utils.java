package com.mapbox.mapboxgl.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

public class Utils {

    public static void validateAccessToken(@NonNull String accessToken) {

        if (TextUtils.isEmpty(accessToken) || (!accessToken.startsWith("pk.") && !accessToken.startsWith("sk."))) {
            throw new RuntimeException("Using MapView requires setting a valid access token. See the README.md");
        }
    }
}
