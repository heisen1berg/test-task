package core.handle.calculate;

import core.data.CalculateRequestEntity;
import org.springframework.web.reactive.socket.WebSocketSession;

public class CalculatorPicker {
    public static Calculator pickCalculator(CalculateRequestEntity calculateRequestEntity, WebSocketSession webSocketSession) {
        return new OrderCalculator(calculateRequestEntity, webSocketSession);
    }
}
