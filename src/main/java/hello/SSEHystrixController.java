package hello;

import com.google.gson.Gson;
import model.HystrixCircuitBreakerEvent;
import model.Resilience4jCBEvent;
import model.Resilence4jSSEEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@RestController
@RequestMapping(value = "/consume-hystrix-sse")
public class SSEHystrixController {

    private static final Logger logger = LoggerFactory.getLogger(SSEHystrixController.class);
    private final WebClient client = WebClient.create("http://localhost:5050/circuitbreaker");


    private final Queue<Resilence4jSSEEvent> queue = new LinkedBlockingQueue<>();



//    @GetMapping("/fire-hystrix-sse-client")
//    public String launchHystrixSSEFromSSEWebClient() {
//        consumeHystrixSSE();
//        return "LAUNCHED hystrix EVENT CLIENT!!! Check the logs...";
//    }

    @Async
    public void consumeHystrixSSE() {
        ParameterizedTypeReference<ServerSentEvent<String>> type = new ParameterizedTypeReference<ServerSentEvent<String>>() {
        };
        Flux<ServerSentEvent<String>> eventStream = client.get()
                .uri("/hystrixStream/events")
                .retrieve()
                .bodyToFlux(type);
        Gson gson = new Gson();
        eventStream.subscribe(
                content -> consumeDataAndPublish(gson, content),
                //logger.info("Current time: {} - Received SSE: name[{}], id [{}], content[{}] ", LocalTime.now(), content.event(), content.id(), content.data()),
                error -> logger.error("Error receiving SSE: {}", error),
                () -> logger.info("Completed!!!"));
    }

    private final HashSet<String> openStates = new HashSet<>(Arrays.asList(
            "OPEN",
            "FORCED_OPEN"
    ));

    /**
     * Map the resilience4j data to hystrix equivalents and add aggregation such as rollup.
     *
     * @param gson
     * @param content
     */
    private void consumeDataAndPublish(Gson gson, ServerSentEvent<String> content) {
        logger.info("Received SSE event");
        HystrixCircuitBreakerEvent hystrixCircuitBreakerEvent = gson.fromJson(content.data(), HystrixCircuitBreakerEvent.class);

        Resilience4jCBEvent resilience4JCBEvent = new Resilience4jCBEvent();
        resilience4JCBEvent.name = hystrixCircuitBreakerEvent.getOrNull().getCircuitBreakerRecentEvent().getCircuitBreakerName();
        resilience4JCBEvent.group = resilience4JCBEvent.name;
        resilience4JCBEvent.isCircuitBreakerOpen = openStates.contains(hystrixCircuitBreakerEvent.getOrNull().getCurrentState());
        resilience4JCBEvent.rollingCountFailure = hystrixCircuitBreakerEvent.getOrNull().getMetrics().getNumberOfFailedCalls();
        resilience4JCBEvent.rollingCountShortCircuited = hystrixCircuitBreakerEvent.getOrNull().getMetrics().getNumberOfNotPermittedCalls();
        resilience4JCBEvent.rollingCountSuccess = hystrixCircuitBreakerEvent.getOrNull().getMetrics().getNumberOfSuccessfulCalls();
        resilience4JCBEvent.rollingCountTimeout = hystrixCircuitBreakerEvent.getOrNull().getMetrics().getNumberOfSlowFailedCalls();
        resilience4JCBEvent.requestCount = resilience4JCBEvent.rollingCountSuccess
                + resilience4JCBEvent.rollingCountFailure
                + resilience4JCBEvent.rollingCountShortCircuited
                + resilience4JCBEvent.rollingCountTimeout;

        queue.add(new Resilence4jSSEEvent(content.id(), content.event(), resilience4JCBEvent));
    }

    //emit new metrics..
    @GetMapping(path = "/stream-sse")
    public Flux<ServerSentEvent<String>> streamEvents() {
        consumeHystrixSSE();
        Gson gson = new Gson();
        return Flux.zip(Flux.fromStream(queue.stream()), Flux.interval(Duration.ofSeconds(1)))
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id((queue.peek().getId()) != null ? queue.peek().getId() : sequence.getT2() + "")
                        .event(queue.peek().getEvent())
                        .data(gson.toJson(queue.poll().getData()).replace("Resilence4jCBEvents", ""))
                        .build());
    }
}
