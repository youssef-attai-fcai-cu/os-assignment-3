public class Process {
    final public int number;
    final public int size;
    private boolean allocated;

    public Process(int _number, int _size) {
        number = _number;
        size = _size;
        allocated = false;
    }

    public final String name() {
        return "Process" + number;
    }

    public void setAllocated() {
        allocated = true;
    }

    public boolean isAllocated() {
        return allocated;
    }
}
