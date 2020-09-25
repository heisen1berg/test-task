package core.route.handler;

import core.data.CalculateRequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ActualHandler implements HandlerInterface {
    public Mono<ServerResponse> calculate(ServerRequest req) {
        return req.bodyToMono(CalculateRequestEntity.class).flatMap((cre) -> {
            fun1(cre);
            return ServerResponse.ok().body(Mono.just("all good"), String.class);
        });
    }

    private void fun1(CalculateRequestEntity calculateRequestEntity) {
        System.out.println(calculateRequestEntity);
    }
}
