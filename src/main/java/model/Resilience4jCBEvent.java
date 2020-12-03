package model;

public class Resilience4jCBEvent {
    // The name of the circuit breaker
    public String name;
    // vizceral-hystrix is hard-coded to require that all report payloads include a "type": "HystrixCommand" field.
    // All other reports are ignored. See https://github.com/OskarKjellin/vizceral-hystrix/blob/6a57a7fa/src/main/java/vizceral/hystrix/HystrixReader.java#L92-L114
    public String type = "HystrixCommand";
    // The common group name to identify the endpoint between APIs in Vizceral
    public String group;
    public boolean isCircuitBreakerOpen = false;
    // Total requests in this period
    public int requestCount = 0;
    public int rollingCountFailure = 0;
    // Resilience4j does not use semaphores so this is ALWAYS zero.
    public int rollingCountSemaphoreRejected = 0;
    public int rollingCountShortCircuited = 0;
    public int rollingCountSuccess = 0;
    // Resilience4j does not use a thread pool so this is ALWAYS zero.
    public int rollingCountThreadPoolRejected = 0;
    // We're only counting read timeouts from a successful connection
    // The HttpClient can also return a ConnectTimeoutException
    public int rollingCountTimeout = 0;
}
