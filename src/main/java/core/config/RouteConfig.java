package core.config;


import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootConfiguration
@EnableWebFlux
public class RouteConfig {
    @Bean
    public RouterFunction<ServerResponse> calculateRoute() {
        return route(GET("/calculate"), request -> ServerResponse.ok().body(Mono.just("Hello, Spring!"), String.class));
    }
}
