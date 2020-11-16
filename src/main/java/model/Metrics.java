package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metrics {

    @SerializedName("numberOfNotPermittedCalls")
    @Expose
    private Integer numberOfNotPermittedCalls;
    @SerializedName("failureRate")
    @Expose
    private Integer failureRate;
    @SerializedName("slowCallRate")
    @Expose
    private Integer slowCallRate;
    @SerializedName("numberOfSlowCalls")
    @Expose
    private Integer numberOfSlowCalls;
    @SerializedName("numberOfSlowSuccessfulCalls")
    @Expose
    private Integer numberOfSlowSuccessfulCalls;
    @SerializedName("numberOfSlowFailedCalls")
    @Expose
    private Integer numberOfSlowFailedCalls;
    @SerializedName("numberOfBufferedCalls")
    @Expose
    private Integer numberOfBufferedCalls;
    @SerializedName("numberOfFailedCalls")
    @Expose
    private Integer numberOfFailedCalls;
    @SerializedName("numberOfSuccessfulCalls")
    @Expose
    private Integer numberOfSuccessfulCalls;

    public Integer getNumberOfNotPermittedCalls() {
        return numberOfNotPermittedCalls;
    }

    public void setNumberOfNotPermittedCalls(Integer numberOfNotPermittedCalls) {
        this.numberOfNotPermittedCalls = numberOfNotPermittedCalls;
    }

    public Integer getFailureRate() {
        return failureRate;
    }

    public void setFailureRate(Integer failureRate) {
        this.failureRate = failureRate;
    }

    public Integer getSlowCallRate() {
        return slowCallRate;
    }

    public void setSlowCallRate(Integer slowCallRate) {
        this.slowCallRate = slowCallRate;
    }

    public Integer getNumberOfSlowCalls() {
        return numberOfSlowCalls;
    }

    public void setNumberOfSlowCalls(Integer numberOfSlowCalls) {
        this.numberOfSlowCalls = numberOfSlowCalls;
    }

    public Integer getNumberOfSlowSuccessfulCalls() {
        return numberOfSlowSuccessfulCalls;
    }

    public void setNumberOfSlowSuccessfulCalls(Integer numberOfSlowSuccessfulCalls) {
        this.numberOfSlowSuccessfulCalls = numberOfSlowSuccessfulCalls;
    }

    public Integer getNumberOfSlowFailedCalls() {
        return numberOfSlowFailedCalls;
    }

    public void setNumberOfSlowFailedCalls(Integer numberOfSlowFailedCalls) {
        this.numberOfSlowFailedCalls = numberOfSlowFailedCalls;
    }

    public Integer getNumberOfBufferedCalls() {
        return numberOfBufferedCalls;
    }

    public void setNumberOfBufferedCalls(Integer numberOfBufferedCalls) {
        this.numberOfBufferedCalls = numberOfBufferedCalls;
    }

    public Integer getNumberOfFailedCalls() {
        return numberOfFailedCalls;
    }

    public void setNumberOfFailedCalls(Integer numberOfFailedCalls) {
        this.numberOfFailedCalls = numberOfFailedCalls;
    }

    public Integer getNumberOfSuccessfulCalls() {
        return numberOfSuccessfulCalls;
    }

    public void setNumberOfSuccessfulCalls(Integer numberOfSuccessfulCalls) {
        this.numberOfSuccessfulCalls = numberOfSuccessfulCalls;
    }

}
