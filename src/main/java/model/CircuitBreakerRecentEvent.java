package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CircuitBreakerRecentEvent {

    @SerializedName("circuitBreakerName")
    @Expose
    private String circuitBreakerName;
    @SerializedName("eventType")
    @Expose
    private String eventType;

    public String getCircuitBreakerName() {
        return circuitBreakerName;
    }

    public void setCircuitBreakerName(String circuitBreakerName) {
        this.circuitBreakerName = circuitBreakerName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
