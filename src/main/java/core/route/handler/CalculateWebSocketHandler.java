package core.route.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class CalculateWebSocketHandler implements WebSocketHandler {
    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        //webSocketSession.textMessage("111111111111111111111111");
        //return webSocketSession.close();

        Flux<WebSocketMessage> output = webSocketSession.receive()
                .map(value -> webSocketSession.textMessage("Echo " + value));

        return webSocketSession.send(output);
    }
}
