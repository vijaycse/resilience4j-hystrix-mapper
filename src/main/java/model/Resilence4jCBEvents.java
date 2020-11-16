package model;

public class Resilence4jCBEvents {
    private String circuitBreakerName;

    private String type;

    private String creationTime;

    private String errorMessage;

    private int durationInMs;

    private String stateTransition;

    private String currentState;
    private int failureRateThreshold;


    private int slowCallRateThreshold;
    private int numberOfNotPermittedCalls;
    private int failureRate;
    private int slowCallRate;
    private int numberOfSlowCalls;
    private int numberOfSlowSuccessfulCalls;
    private int numberOfSlowFailedCalls;
    private int numberOfBufferedCalls;
    private int numberOfFailedCalls;
    private int numberOfSuccessfulCalls;

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public int getFailureRateThreshold() {
        return failureRateThreshold;
    }

    public void setFailureRateThreshold(int failureRateThreshold) {
        this.failureRateThreshold = failureRateThreshold;
    }

    public int getSlowCallRateThreshold() {
        return slowCallRateThreshold;
    }

    public void setSlowCallRateThreshold(int slowCallRateThreshold) {
        this.slowCallRateThreshold = slowCallRateThreshold;
    }

    public int getNumberOfNotPermittedCalls() {
        return numberOfNotPermittedCalls;
    }

    public void setNumberOfNotPermittedCalls(int numberOfNotPermittedCalls) {
        this.numberOfNotPermittedCalls = numberOfNotPermittedCalls;
    }

    public int getFailureRate() {
        return failureRate;
    }

    public void setFailureRate(int failureRate) {
        this.failureRate = failureRate;
    }

    public int getSlowCallRate() {
        return slowCallRate;
    }

    public void setSlowCallRate(int slowCallRate) {
        this.slowCallRate = slowCallRate;
    }

    public int getNumberOfSlowCalls() {
        return numberOfSlowCalls;
    }

    public void setNumberOfSlowCalls(int numberOfSlowCalls) {
        this.numberOfSlowCalls = numberOfSlowCalls;
    }

    public int getNumberOfSlowSuccessfulCalls() {
        return numberOfSlowSuccessfulCalls;
    }

    public void setNumberOfSlowSuccessfulCalls(int numberOfSlowSuccessfulCalls) {
        this.numberOfSlowSuccessfulCalls = numberOfSlowSuccessfulCalls;
    }

    public int getNumberOfSlowFailedCalls() {
        return numberOfSlowFailedCalls;
    }

    public void setNumberOfSlowFailedCalls(int numberOfSlowFailedCalls) {
        this.numberOfSlowFailedCalls = numberOfSlowFailedCalls;
    }

    public int getNumberOfBufferedCalls() {
        return numberOfBufferedCalls;
    }

    public void setNumberOfBufferedCalls(int numberOfBufferedCalls) {
        this.numberOfBufferedCalls = numberOfBufferedCalls;
    }

    public int getNumberOfFailedCalls() {
        return numberOfFailedCalls;
    }

    public void setNumberOfFailedCalls(int numberOfFailedCalls) {
        this.numberOfFailedCalls = numberOfFailedCalls;
    }

    public int getNumberOfSuccessfulCalls() {
        return numberOfSuccessfulCalls;
    }

    public void setNumberOfSuccessfulCalls(int numberOfSuccessfulCalls) {
        this.numberOfSuccessfulCalls = numberOfSuccessfulCalls;
    }

    public String getCircuitBreakerName() {
        return this.circuitBreakerName;
    }

    public void setCircuitBreakerName(String circuitBreakerName) {
        this.circuitBreakerName = circuitBreakerName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getDurationInMs() {
        return this.durationInMs;
    }

    public void setDurationInMs(int durationInMs) {
        this.durationInMs = durationInMs;
    }

    public String getStateTransition() {
        return this.stateTransition;
    }

    public void setStateTransition(String stateTransition) {
        this.stateTransition = stateTransition;
    }

}
