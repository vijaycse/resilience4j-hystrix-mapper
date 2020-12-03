package hello;

import com.google.gson.Gson;
import model.HystrixCircuitBreakerEvent;
import model.Resilence4jCBEvents;
import model.Resilence4jSSEEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.LocalTime;
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

    /**
     *  PLEASE add resilence4j to hystrix mapping here and add aggregation here as well
     *  such as rollup , aggregation here. I have added only sample of few data here
     * @param gson
     * @param content
     */
    private void consumeDataAndPublish(Gson gson, ServerSentEvent<String> content) {
        logger.info("Received SSE event");
        HystrixCircuitBreakerEvent hystrixCircuitBreakerEvent = gson.fromJson(content.data(), HystrixCircuitBreakerEvent.class);
        Resilence4jCBEvents resilence4jCBEvents = new Resilence4jCBEvents();
        resilence4jCBEvents.setCircuitBreakerName(hystrixCircuitBreakerEvent.getOrNull().getCircuitBreakerRecentEvent().getCircuitBreakerName());
        resilence4jCBEvents.setCurrentState(hystrixCircuitBreakerEvent.getOrNull().getCurrentState());
        resilence4jCBEvents.setFailureRateThreshold(hystrixCircuitBreakerEvent.getOrNull().getFailureRateThreshold());
        resilence4jCBEvents.setSlowCallRateThreshold(hystrixCircuitBreakerEvent.getOrNull().getSlowCallRateThreshold());
        resilence4jCBEvents.setNumberOfSuccessfulCalls(hystrixCircuitBreakerEvent.getOrNull().getMetrics().getNumberOfSuccessfulCalls());
        resilence4jCBEvents.setCreationTime(LocalTime.now().toString());
        queue.add(new Resilence4jSSEEvent(content.id(), content.event(), resilence4jCBEvents));
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
