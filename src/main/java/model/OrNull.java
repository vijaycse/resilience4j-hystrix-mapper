package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrNull {

    @SerializedName("circuitBreakerRecentEvent")
    @Expose
    private CircuitBreakerRecentEvent circuitBreakerRecentEvent;
    @SerializedName("metrics")
    @Expose
    private Metrics metrics;
    @SerializedName("currentState")
    @Expose
    private String currentState;
    @SerializedName("failureRateThreshold")
    @Expose
    private Integer failureRateThreshold;
    @SerializedName("slowCallRateThreshold")
    @Expose
    private Integer slowCallRateThreshold;

    public CircuitBreakerRecentEvent getCircuitBreakerRecentEvent() {
        return circuitBreakerRecentEvent;
    }

    public void setCircuitBreakerRecentEvent(CircuitBreakerRecentEvent circuitBreakerRecentEvent) {
        this.circuitBreakerRecentEvent = circuitBreakerRecentEvent;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public Integer getFailureRateThreshold() {
        return failureRateThreshold;
    }

    public void setFailureRateThreshold(Integer failureRateThreshold) {
        this.failureRateThreshold = failureRateThreshold;
    }

    public Integer getSlowCallRateThreshold() {
        return slowCallRateThreshold;
    }

    public void setSlowCallRateThreshold(Integer slowCallRateThreshold) {
        this.slowCallRateThreshold = slowCallRateThreshold;
    }

}
