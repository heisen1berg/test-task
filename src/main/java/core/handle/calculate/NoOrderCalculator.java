package core.handle.calculate;

import core.data.CalculateRequestEntity;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;

public class NoOrderCalculator extends Calculator {
    private Integer iterationsRemain1;
    private Integer iterationsRemain2;
    private int iterationsDone1 = 0;
    private int iterationsDone2 = 0;
    private int argument1 = 1;
    private int argument2 = 1;

    public NoOrderCalculator(CalculateRequestEntity calculateRequestEntity, WebSocketSession webSocketSession) {
        super(calculateRequestEntity, webSocketSession);
        iterationsRemain1 = calculateRequestEntity.getIterationCount();
        iterationsRemain2 = calculateRequestEntity.getIterationCount();
    }

    @Override
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
                    if (funResult == null) funResult = "null";

                    final String iterationNum = "Iteration #" + iterationsDone1;
                    final String functionNumber = "Function #1";
                    final String resultString = "Result: " + funResult;
                    final String time = "Time: " + (endTime - startTime);
                    sink.next(webSocketSession.textMessage(iterationNum + ", " + functionNumber + ", " + resultString + ", " + time));


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
                    if (funResult == null) funResult = "null";

                    final String iterationNum = "Iteration #" + iterationsDone2;
                    final String functionNumber = "Function #2";
                    final String resultString = "Result: " + funResult;
                    final String time = "Time: " + (endTime - startTime);
                    sink.next(webSocketSession.textMessage(iterationNum + ", " + functionNumber + ", " + resultString + ", " + time));


                    if (iterationsRemain2 == 0) {
                        sink.complete();
                        CalculatorIterator.removeListener(this);
                    }
                }
            };
        });

        return Flux.merge(fluxForFun1, fluxForFun2);
    }
}
