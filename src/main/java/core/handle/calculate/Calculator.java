package core.handle.calculate;

import core.data.CalculateRequestEntity;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;


public abstract class Calculator {
   protected final CalculateRequestEntity calculateRequestEntity;
   protected final WebSocketSession webSocketSession;

   public Calculator(CalculateRequestEntity calculateRequestEntity, WebSocketSession webSocketSession) {
      this.calculateRequestEntity = calculateRequestEntity;
      this.webSocketSession = webSocketSession;
   }

   public abstract Flux<WebSocketMessage> getOutput();
}
