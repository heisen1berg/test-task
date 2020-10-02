package core.handle.calculate;

abstract class CalculatorListener {
    private boolean finished = true;

    {
        CalculatorIterator.addListener(this);
    }

    public void listen() {
        if (finished) {
            finished = false;
            iterate();
            finished = true;
        }
    }

    public abstract void iterate();

    public void close() {
        CalculatorIterator.removeListener(this);
    }
}
