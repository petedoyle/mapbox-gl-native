package com.mapbox.mapboxsdk.annotations;

import android.graphics.Point;
import android.support.annotation.Nullable;
import android.view.View;

import com.mapbox.mapboxsdk.R;
import com.mapbox.mapboxsdk.geometry.LatLng;

public final class Marker extends Annotation {

	private float   anchorU;
	private float   anchorV;
	private boolean draggable;
	private boolean flat;
	private float   infoWindowAnchorU;
	private float   infoWindowAnchorV;
	private LatLng  position;
	private float   rotation;
	private String  snippet;
	private Sprite  icon;
	private String  title;
	private InfoWindow infoWindow = null;

	private boolean infoWindowShown = false;
	private int topOffsetPixels;

	/**
	 * Constructor
	 */
	Marker() {
		super();
	}

	/**
	 * If two markers have the same LatLng, they are equal.
	 *
	 * @param other object
	 * @return boolean - do they have the same LatLng
	 */
	public boolean equals(Object other) {
		if (!(other instanceof Marker)) {
			return false;
		}
		double lat = position.getLatitude();
		double lng = position.getLongitude();
		LatLng otherPosition = ((Marker) other).getPosition();
		double otherLat = otherPosition.getLatitude();
		double otherLng = otherPosition.getLongitude();
		return (lat == otherLat && otherLng == lng);
	}

	Point getAnchor() {
		return new Point((int) anchorU, (int) anchorV);
	}

	float getAnchorU() {
		return anchorU;
	}

	float getAnchorV() {
		return anchorV;
	}

	float getInfoWindowAnchorU() {
		return infoWindowAnchorU;
	}

	float getInfoWindowAnchorV() {
		return infoWindowAnchorV;
	}

	public LatLng getPosition() {
		return position;
	}

	void setPosition(LatLng position) {
		this.position = position;
	}

	float getRotation() {
		return rotation;
	}

	void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public String getSnippet() {
		return snippet;
	}

	void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public String getTitle() {
		return title;
	}

	void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Do not use this method. Used internally by the SDK.
	 */
	public void hideInfoWindow() {
		if (infoWindow != null) {
			infoWindow.close();
		}
		infoWindowShown = false;
	}

	boolean isDraggable() {
		return draggable;
	}

	void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}

	boolean isFlat() {
		return flat;
	}

	void setFlat(boolean flat) {
		this.flat = flat;
	}

	/**
	 * Do not use this method. Used internally by the SDK.
	 */
	public boolean isInfoWindowShown() {
		return infoWindowShown;
	}

	void setAnchor(float u, float v) {
		this.anchorU = u;
		this.anchorV = v;
	}

	void setInfoWindowAnchor(float u, float v) {
		infoWindowAnchorU = u;
		infoWindowAnchorV = v;
	}

	public Sprite getIcon() {
		return icon;
	}

	/**
	 * Do not use this method. Used internally by the SDK.
	 */
	public void setIcon(@Nullable Sprite icon) {
		this.icon = icon;
	}

	/**
	 * Do not use this method. Used internally by the SDK.
	 */
	public void showInfoWindow() {
		if (!isVisible() || getMapView() == null) {
			return;
		}

		getInfoWindow().adaptDefaultMarker(this);
		showInfoWindow(getInfoWindow());
	}

	/**
	 * Do not use this method. Used internally by the SDK.
	 */
	public void showInfoWindow(View view) {
		if (!isVisible() || getMapView() == null) {
			return;
		}

		infoWindow = new InfoWindow(view, getMapView());
		showInfoWindow(infoWindow);
	}

	private void showInfoWindow(InfoWindow iw) {
		iw.open(this, getPosition(), 0, topOffsetPixels);
		iw.setBoundMarker(this);
		infoWindowShown = true;
	}

	/**
	 * Use to set a custom OnTouchListener for the InfoWindow.
	 * By default the InfoWindow will close on touch.
	 *
	 * @param listener Custom OnTouchListener
	 */
	public void setInfoWindowOnTouchListener(View.OnTouchListener listener) {
		if (listener == null) {
			return;
		}
		getInfoWindow().setOnTouchListener(listener);
	}

	/**
	 * Common internal InfoWindow initialization method
	 *
	 * @return InfoWindow for Marker
	 */
	private InfoWindow getInfoWindow() {
		if (infoWindow == null) {
			infoWindow = new InfoWindow(R.layout.infowindow_view, getMapView());
		}
		return infoWindow;
	}

	@Override
	void setVisible(boolean visible) {
		super.setVisible(visible);
		if (!visible && infoWindowShown) {
			hideInfoWindow();
		}
	}

	//  TODO Method in Google Maps Android API
//    public int hashCode()

	/**
	 * Do not use this method. Used internally by the SDK.
	 */
	public int getTopOffsetPixels() {
		return topOffsetPixels;
	}

	/**
	 * Do not use this method. Used internally by the SDK.
	 */
	public void setTopOffsetPixels(int topOffsetPixels) {
		this.topOffsetPixels = topOffsetPixels;
	}
}
