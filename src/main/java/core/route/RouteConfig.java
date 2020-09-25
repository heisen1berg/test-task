package core.route;


import core.route.handler.HandlerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootConfiguration
@EnableWebFlux
@ComponentScan(value = "core.route.handler")
public class RouteConfig {

    @Autowired
    private HandlerInterface handler;

    @Bean
    public RouterFunction<ServerResponse> calculateRoute() {
        return route(GET("/calculate"), handler::calculate);
    }
}
