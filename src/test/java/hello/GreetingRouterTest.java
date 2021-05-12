package hello;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import model.CircuitBreakerRecentEvent;
import model.HystrixCircuitBreakerEvent;
import model.OrNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@WebFluxTest
public class GreetingRouterTest {

    // Spring Boot will create a `WebTestClient` for you,
    // already configure and ready to issue requests against "localhost:RANDOM_PORT"
    @Autowired
    private WebTestClient webTestClient;

    @Rule
    public WireMockRule wireMockServer = new WireMockRule(8060);

    private String payload() {
        String eventStream = "id:10\n" +
                "       event:random\n" +
                "       data:751025203\n" +
                "       \n" +
                "       id:11\n" +
                "       event:random\n" +
                "       data:-1591883873\n" +
                "\n" +
                "       id:12\n" +
                "       event:random\n" +
                "       data:-1899224227".trim();
        return eventStream;
    }
    

    @org.junit.Test
    public void testEvent() throws JsonProcessingException {



        WebClient webClient = WebClient.create();

        CircuitBreakerRecentEvent cbEvent = new CircuitBreakerRecentEvent();
        cbEvent.setCircuitBreakerName("test");

        OrNull orNull = new OrNull();
        orNull.setCurrentState("OPEN");
        orNull.setFailureRateThreshold(100);
        orNull.setSlowCallRateThreshold(100);
        orNull.setCircuitBreakerRecentEvent(new CircuitBreakerRecentEvent());
        HystrixCircuitBreakerEvent hystrixCircuitBreakerEvent = new HystrixCircuitBreakerEvent();
        hystrixCircuitBreakerEvent.setAsync(true);
        hystrixCircuitBreakerEvent.setEmpty(false);
        hystrixCircuitBreakerEvent.setOrNull(new OrNull());
        ObjectMapper objectMapper = new ObjectMapper();

        wireMockServer.stubFor(
                get(urlPathEqualTo("/sse"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "text/event-stream")
                                .withBody(objectMapper.writeValueAsString(hystrixCircuitBreakerEvent))));

        // post first
        List<HystrixCircuitBreakerEvent> clientResponse = webClient
                .get()
                .uri("http://localhost:8060/sse")
                .retrieve()
                .bodyToFlux(HystrixCircuitBreakerEvent.class)
                .collectList().block();
        System.out.println(clientResponse.get(0));
        // listen and assert
    }


    // copy from resilence4j testing patttern..
}
