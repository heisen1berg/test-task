package core.handle.calculate;

import org.springframework.web.reactive.socket.WebSocketMessage;
import reactor.core.publisher.Flux;


public abstract class Calculator {
   public abstract Flux<WebSocketMessage> getOutput();
}
