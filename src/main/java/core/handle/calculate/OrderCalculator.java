package core.handle.calculate;

import core.data.CalculateRequestEntity;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;


public class OrderCalculator extends Calculator {
    private final WebSocketSession webSocketSession;
    private final CalculateRequestEntity calculateRequestEntity;
    private Integer iterationsRemain1;
    private Integer iterationsRemain2;
    private int argument1 = 1;
    private int argument2 = 1;
    private int iterationsDone1 = 0;
    private int iterationsDone2 = 0;
    private long executionTime1 = 0;
    private long executionTime2 = 0;

    public OrderCalculator(CalculateRequestEntity calculateRequestEntity, WebSocketSession webSocketSession) {
        iterationsRemain1 = calculateRequestEntity.getIterationCount();
        iterationsRemain2 = calculateRequestEntity.getIterationCount();
        this.calculateRequestEntity = calculateRequestEntity;
        this.webSocketSession = webSocketSession;
    }


    public Flux<WebSocketMessage> getOutput() {
        Flux<WebSocketMessage> fluxForFun1 = Flux.create(sink -> {
            new CalculatorListener() {
                @Override
                public void iterate() {
                    final long startTime = System.currentTimeMillis();

                    Object funResult = (FunctionExecutor.execute(calculateRequestEntity.getFirstFun(), argument1++));

                    final long endTime = System.currentTimeMillis();

                    iterationsRemain1--;
                    iterationsDone1++;
                    executionTime1 = endTime - startTime;
                    if (funResult == null) funResult = "null";

                    sink.next(webSocketSession.textMessage(funResult.toString()));


                    if (iterationsRemain1 == 0) {
                        sink.complete();
                        CalculatorIterator.removeListener(this);
                    }
                }
            };
        });

        Flux<WebSocketMessage> fluxForFun2 = Flux.create(sink -> {
            new CalculatorListener() {
                @Override
                public void iterate() {
                    final long startTime = System.currentTimeMillis();

                    Object funResult = (FunctionExecutor.execute(calculateRequestEntity.getSecondFun(), argument2++));

                    final long endTime = System.currentTimeMillis();


                    iterationsRemain2--;
                    iterationsDone2++;
                    executionTime2 = endTime - startTime;
                    if (funResult == null) funResult = "null";

                    sink.next(webSocketSession.textMessage(funResult.toString()));


                    if (iterationsRemain2 == 0) {
                        sink.complete();
                        CalculatorIterator.removeListener(this);
                    }
                }
            };
        });

        return Flux.zip(fluxForFun1, fluxForFun2, (mes1, mes2) -> {
            final String iterationNum = "Iteration#" + Math.min(iterationsDone1, iterationsDone2);
            final int iterDifference = iterationsDone1 - iterationsDone2;
            final String result1 = "result:" + mes1.getPayloadAsText();
            final String result2 = "result:" + mes2.getPayloadAsText();
            final String time1 = "time:" + executionTime1 + "ms";
            final String time2 = "time:" + executionTime2 + "ms";
            final String ahead1 = "ahead:" + (iterDifference > 0 ? Integer.toString(iterDifference) : "0");
            final String ahead2 = "ahead:" + (iterDifference < 0 ? Integer.toString(-iterDifference) : "0");

            return webSocketSession.textMessage(iterationNum + "," +
                    "Function1{" + result1 + "," + time1 + "," + ahead1 + "}" +
                    "Function2{" + result2 + "," + time2 + "," + ahead2 + "}");
        });
    }
}
