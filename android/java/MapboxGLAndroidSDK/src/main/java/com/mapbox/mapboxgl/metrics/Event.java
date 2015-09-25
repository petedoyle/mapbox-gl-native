package com.mapbox.mapboxgl.metrics;

public class Event {

    // mapbox-events stock attributes
    private String event;
    private String version;
    private String created;
    private String instance;
    private String advertiserId;
    private String vendorId;
    private String appBundleId;

    // mapbox-events-android stock attributes
    private String model;
    private String operatingSystem;
    private String orientation;
    private String batteryLevel;
    private String resolution;
    private String carrier;
    private String cellularNetworkType;
    private String wifi;
    private String accessibilityFontScale;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(String advertiserId) {
        this.advertiserId = advertiserId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getAppBundleId() {
        return appBundleId;
    }

    public void setAppBundleId(String appBundleId) {
        this.appBundleId = appBundleId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(String batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getCellularNetworkType() {
        return cellularNetworkType;
    }

    public void setCellularNetworkType(String cellularNetworkType) {
        this.cellularNetworkType = cellularNetworkType;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getAccessibilityFontScale() {
        return accessibilityFontScale;
    }

    public void setAccessibilityFontScale(String accessibilityFontScale) {
        this.accessibilityFontScale = accessibilityFontScale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event1 = (Event) o;

        if (event != null ? !event.equals(event1.event) : event1.event != null) return false;
        if (version != null ? !version.equals(event1.version) : event1.version != null)
            return false;
        if (created != null ? !created.equals(event1.created) : event1.created != null)
            return false;
        if (instance != null ? !instance.equals(event1.instance) : event1.instance != null)
            return false;
        if (advertiserId != null ? !advertiserId.equals(event1.advertiserId) : event1.advertiserId != null)
            return false;
        if (vendorId != null ? !vendorId.equals(event1.vendorId) : event1.vendorId != null)
            return false;
        if (appBundleId != null ? !appBundleId.equals(event1.appBundleId) : event1.appBundleId != null)
            return false;
        if (model != null ? !model.equals(event1.model) : event1.model != null) return false;
        if (operatingSystem != null ? !operatingSystem.equals(event1.operatingSystem) : event1.operatingSystem != null)
            return false;
        if (orientation != null ? !orientation.equals(event1.orientation) : event1.orientation != null)
            return false;
        if (batteryLevel != null ? !batteryLevel.equals(event1.batteryLevel) : event1.batteryLevel != null)
            return false;
        if (resolution != null ? !resolution.equals(event1.resolution) : event1.resolution != null)
            return false;
        if (carrier != null ? !carrier.equals(event1.carrier) : event1.carrier != null)
            return false;
        if (cellularNetworkType != null ? !cellularNetworkType.equals(event1.cellularNetworkType) : event1.cellularNetworkType != null)
            return false;
        if (wifi != null ? !wifi.equals(event1.wifi) : event1.wifi != null) return false;
        return !(accessibilityFontScale != null ? !accessibilityFontScale.equals(event1.accessibilityFontScale) : event1.accessibilityFontScale != null);

    }

    @Override
    public int hashCode() {
        int result = event != null ? event.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (instance != null ? instance.hashCode() : 0);
        result = 31 * result + (advertiserId != null ? advertiserId.hashCode() : 0);
        result = 31 * result + (vendorId != null ? vendorId.hashCode() : 0);
        result = 31 * result + (appBundleId != null ? appBundleId.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (operatingSystem != null ? operatingSystem.hashCode() : 0);
        result = 31 * result + (orientation != null ? orientation.hashCode() : 0);
        result = 31 * result + (batteryLevel != null ? batteryLevel.hashCode() : 0);
        result = 31 * result + (resolution != null ? resolution.hashCode() : 0);
        result = 31 * result + (carrier != null ? carrier.hashCode() : 0);
        result = 31 * result + (cellularNetworkType != null ? cellularNetworkType.hashCode() : 0);
        result = 31 * result + (wifi != null ? wifi.hashCode() : 0);
        result = 31 * result + (accessibilityFontScale != null ? accessibilityFontScale.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "event='" + event + '\'' +
                ", version='" + version + '\'' +
                ", created='" + created + '\'' +
                ", instance='" + instance + '\'' +
                ", advertiserId='" + advertiserId + '\'' +
                ", vendorId='" + vendorId + '\'' +
                ", appBundleId='" + appBundleId + '\'' +
                ", model='" + model + '\'' +
                ", operatingSystem='" + operatingSystem + '\'' +
                ", orientation='" + orientation + '\'' +
                ", batteryLevel='" + batteryLevel + '\'' +
                ", resolution='" + resolution + '\'' +
                ", carrier='" + carrier + '\'' +
                ", cellularNetworkType='" + cellularNetworkType + '\'' +
                ", wifi='" + wifi + '\'' +
                ", accessibilityFontScale='" + accessibilityFontScale + '\'' +
                '}';
    }
}
