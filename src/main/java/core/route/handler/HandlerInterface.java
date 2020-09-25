package core.route.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


public interface HandlerInterface {
    Mono<ServerResponse> calculate(ServerRequest req);
}
