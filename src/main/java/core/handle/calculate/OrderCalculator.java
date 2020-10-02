package core.handle.calculate;

import core.data.CalculateRequestEntity;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;


public class OrderCalculator extends Calculator {

    private int iterationsToDo;
    public OrderCalculator(CalculateRequestEntity calculateRequestEntity, WebSocketSession webSocketSession) {
        super(calculateRequestEntity, webSocketSession);
        iterationsToDo = calculateRequestEntity.getIterationCount();
    }


    public Flux<WebSocketMessage> getOutput() {
        final CustomFlux customFlux1 = new CustomFlux(iterationsToDo, calculateRequestEntity.getFirstFun());
        final CustomFlux customFlux2 = new CustomFlux(iterationsToDo, calculateRequestEntity.getSecondFun());

        return Flux.zip(customFlux1.getFlux(), customFlux2.getFlux(), (mes1, mes2) -> {
            final int iterationNum = Math.min(customFlux1.iterationsDone, customFlux2.iterationsDone);
            final String iterationString = "Iteration #" + iterationNum;
            final int iterDifference = customFlux1.iterationsDone - customFlux2.iterationsDone;
            final String result1 = "result:" + mes1.getPayloadAsText();
            final String result2 = "result:" + mes2.getPayloadAsText();
            final String time1 = "time:" + customFlux1.executionTime[iterationNum - 1] + "ms";
            final String time2 = "time:" + customFlux2.executionTime[iterationNum - 1] + "ms";
            final String ahead1 = "ahead:" + (iterDifference > 0 ? Integer.toString(iterDifference) : "0");
            final String ahead2 = "ahead:" + (iterDifference < 0 ? Integer.toString(-iterDifference) : "0");

            return webSocketSession.textMessage(iterationString + "," +
                    "Function1{" + result1 + "," + time1 + "," + ahead1 + "}," +
                    "Function2{" + result2 + "," + time2 + "," + ahead2 + "}");
        });
    }

    private class CustomFlux {
        public Integer iterationsRemain;
        public int argument = 1;
        public int iterationsDone = 0;
        public long[] executionTime = new long[10];

        private String function;

        public CustomFlux(int iterationsRemain, String function) {
            this.iterationsRemain = iterationsRemain;
            this.function = function;
        }

        public Flux<WebSocketMessage> getFlux() {
            return Flux.create(sink -> {
                new CalculatorListener() {
                    @Override
                    public void iterate() {
                        final long startTime = System.currentTimeMillis();

                        Object funResult = (FunctionExecutor.execute(function, argument++));

                        final long endTime = System.currentTimeMillis();
                        executionTime[iterationsDone] = endTime - startTime;

                        iterationsRemain--;
                        iterationsDone++;
                        if (funResult == null) funResult = "null";

                        sink.next(webSocketSession.textMessage(funResult.toString()));


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
