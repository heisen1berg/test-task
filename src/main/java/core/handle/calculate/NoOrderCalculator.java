package core.handle.calculate;

import core.data.CalculateRequestEntity;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;

public class NoOrderCalculator extends Calculator {
    private int iterationsToDo;

    public NoOrderCalculator(CalculateRequestEntity calculateRequestEntity, WebSocketSession webSocketSession) {
        super(calculateRequestEntity, webSocketSession);
        iterationsToDo = calculateRequestEntity.getIterationCount();
    }

    @Override
    public Flux<WebSocketMessage> getOutput() {
        final CustomFlux customFlux1 = new CustomFlux(iterationsToDo, calculateRequestEntity.getFirstFun(), 1);
        final CustomFlux customFlux2 = new CustomFlux(iterationsToDo, calculateRequestEntity.getSecondFun(), 2);

        return Flux.merge(customFlux1.getFlux(), customFlux2.getFlux());
    }

    private class CustomFlux {
        public Integer iterationsRemain;
        public int iterationsDone = 0;
        public int argument = 1;
        public int functionNumber;

        private String function;

        public CustomFlux(int iterationsRemain, String function, int functionNumber) {
            this.iterationsRemain = iterationsRemain;
            this.function = function;
            this.functionNumber = functionNumber;
        }

        public Flux<WebSocketMessage> getFlux() {
            return Flux.create(sink -> {
                new CalculatorListener() {
                    @Override
                    public void iterate() {
                        final long startTime = System.currentTimeMillis();

                        Object funResult = (FunctionExecutor.execute(function, argument++));

                        final long endTime = System.currentTimeMillis();

                        iterationsRemain--;
                        iterationsDone++;
                        if (funResult == null) funResult = "null";

                        final String iterationString = "Iteration #" + iterationsDone;
                        final String functionString = "Function #" + functionNumber;
                        final String resultString = "Result: " + funResult;
                        final String time = "Time: " + (endTime - startTime);
                        sink.next(webSocketSession.textMessage(iterationString + ", " + functionString + ", " + resultString + ", " + time));


                        if (iterationsRemain == 0) {
                            sink.complete();
                            this.close();
                        }
                    }
                };
            });
        }
    }
}