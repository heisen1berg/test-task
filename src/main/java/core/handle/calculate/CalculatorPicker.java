package core.handle.calculate;

import core.data.CalculateRequestEntity;
import org.springframework.web.reactive.socket.WebSocketSession;

public class CalculatorPicker {
    public static Calculator pickCalculator(CalculateRequestEntity calculateRequestEntity, WebSocketSession webSocketSession) {
        switch (calculateRequestEntity.getOutputMode()) {
            case "no_order":
                return new NoOrderCalculator(calculateRequestEntity, webSocketSession);
            default:
            case "order":
                return new OrderCalculator(calculateRequestEntity, webSocketSession);
        }

    }
}
