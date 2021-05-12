package hello;

import com.google.gson.Gson;
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
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@RestController
@RequestMapping(value = "/consume-sse")
public class SSEController {

    private static final Logger logger = LoggerFactory.getLogger(SSEController.class);
    private final WebClient client = WebClient.create("http://localhost:9080/actuator/");


    private final Queue<String> queue = new LinkedBlockingQueue<>();


    //testing purpose
    @GetMapping(path = "/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux() {
        final String[] WORDS = "Eating an apple keeps doctor away.. Does it??.".split(" ");
        return Flux
                .zip(Flux.just(WORDS), Flux.interval(Duration.ofSeconds(1)))
                .map(Tuple2::getT1);
    }

    //testing purpose
    @GetMapping(path = "/stream-sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>>  streamEventsFlux() {
//        return Flux.just(queue)
//                .map(queue1 -> queue1.toString());
            return Flux.zip(Flux.fromStream(queue.stream()), Flux.interval(Duration.ofSeconds(1)))
                    .map(seq -> ServerSentEvent.<String>builder()
                            .id(seq.getT2().toString())
                    .event(seq.getT2()+"someevent")
                    .data(queue.poll())
                    .build());
        }

    //testing purpose
    @GetMapping("/stream-sse-event-size")
    public String streamEvent() {
        return queue.size() + "";
    }

    @GetMapping("/fire-sse-client")
    public String launchSSEFromSSEWebClient() {
        consumeSSE();
        return "LAUNCHED EVENT CLIENT!!! Check the logs...";
    }

    @Async
    public void consumeSSE() {
        ParameterizedTypeReference<ServerSentEvent<String>> type = new ParameterizedTypeReference<ServerSentEvent<String>>() {
        };
        Flux<ServerSentEvent<String>> eventStream = client.get()
                .uri("stream-circuitbreaker-events/backendA")
                .retrieve()
                .bodyToFlux(type)
                .repeat();
        Gson gson = new Gson();
        eventStream.subscribe(
                content -> consumeDataAndPublish(gson, content),
                //logger.info("Current time: {} - Received SSE: name[{}], id [{}], content[{}] ", LocalTime.now(), content.event(), content.id(), content.data()),
                error -> logger.error("Error receiving SSE: {}", error),
                () -> logger.info("Completed!!!"));
    }

    private void consumeDataAndPublish(Gson gson, ServerSentEvent<String> content) {
        String data = content.data()!=null ? content.data() : content.event();
        System.out.println(" data " + data);
        queue.add(data);
    }

}
