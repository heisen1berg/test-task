package core.route.handler;

import core.data.CalculateRequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ActualHandler implements HandlerInterface {
    public Mono<ServerResponse> calculate(ServerRequest req) {

        return ServerResponse.ok().body(req.bodyToMono(CalculateRequestEntity.class), CalculateRequestEntity.class);
    }
}
