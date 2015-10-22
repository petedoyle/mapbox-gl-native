package com.mapbox.mapboxsdk.annotations;


import android.graphics.Color;

import com.mapbox.mapboxsdk.geometry.LatLng;

/**
 * UNIMPLEMENTED: Needs implementation in Native.
 * <p>
 * https://github.com/mapbox/mapbox-gl-native/issues/1882
 * https://github.com/mapbox/mapbox-gl-native/issues/1726
 */
class Circle extends Annotation {

	private LatLng center;
	private int fillColor = Color.BLACK;
	private double radius;
	private int   strokeColor = Color.BLACK;
	private float strokeWidth = 10; // Google Maps API defaults to 10

	public LatLng getCenter() {
		return center;
	}

	void setCenter(LatLng center) {
		this.center = center;
	}

	public int getFillColor() {
		return fillColor;
	}

	void setFillColor(int color) {
		fillColor = color;
	}

	/**
	 * Returns the circle's radius, in meters.
	 *
	 * @return radius in meters
	 */
	public double getRadius() {
		return radius;
	}

	void setRadius(double radius) {
		this.radius = radius;
	}

	public int getStrokeColor() {
		return strokeColor;
	}

	void setStrokeColor(int color) {
		strokeColor = color;
	}

	public float getStrokeWidth() {
		return strokeWidth;
	}

	void setStrokeWidth(float width) {
		strokeWidth = width;
	}

}
