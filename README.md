=== How to run locally?

Run the app - `./gradlew bootRun`

Start event listener - `curl http://localhost:9080/consume-hystrix-sse/fire-hystrix-sse-client`

Start Consuming - `curl http://localhost:9080/consume-hystrix-sse/stream-sse`
 
=== How to run from docker?

Build with `docker build . -t resilience4j-hystrix-mapper`.

Run with `docker run -p 8080:8080 resilience4j-hystrix-mapper`.

