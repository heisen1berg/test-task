package core.handle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.data.CalculateRequestEntity;
import core.handle.calculate.Calculator;
import core.handle.calculate.CalculatorPicker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ComponentScan(value = "core.handle.calculate")
@Component
public class CalculateWebSocketHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        Flux<WebSocketMessage> output = Flux.just(webSocketSession.textMessage("session start"));
        output = output.mergeWith(webSocketSession.receive().flatMap(value -> {
            try {
                CalculateRequestEntity calculateRequestEntity = new ObjectMapper().readValue(value.getPayloadAsText(), CalculateRequestEntity.class);
                Calculator calculator = CalculatorPicker.pickCalculator(calculateRequestEntity, webSocketSession);
                return calculator.getOutput();
            } catch (JsonProcessingException e) {
                return Flux.just(webSocketSession.textMessage("couldn't read calculation request"));
            }
        }).doOnError(Throwable::printStackTrace)).doOnError(Throwable::printStackTrace);
        return webSocketSession.send(output);
    }


}
