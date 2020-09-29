package core.handle.calculate;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class CalculatorIterator implements Runnable {

    private static final int tick = 200;
    private static List<CalculatorListener> activeCalculators = new LinkedList<>();

    public static void addListener(CalculatorListener calculatorListener) {
        activeCalculators.add(calculatorListener);
    }

    public static void removeListener(CalculatorListener calculatorListener) {
        activeCalculators.remove(calculatorListener);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                iterateCalculators();
                Thread.sleep(tick);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void iterateCalculators() {
        activeCalculators.forEach((calculatorListener -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    calculatorListener.listen();
                }
            }).start();
        }));
    }
}
