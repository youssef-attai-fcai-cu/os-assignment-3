public class Partition {
    public static int maxNumber = 0;
    final public int number;
    final public int size;
    private Process allocated;

    public Partition(int _number, int _size) {
        number = _number;
        size = _size;

        maxNumber = Math.max(maxNumber, number);
    }

    public String name() {
        return "Partition" + number;
    }

    public boolean isAllocated() {
        return allocated != null;
    }

    public boolean allocate(Process process) {
        if (process.size > size) return false;
        allocated = process;
        process.setAllocated();
        return true;
    }

    public int getRemaining() {
        return size - allocated.size;
    }

    public Process getProcess() {
        return allocated;
    }

    public boolean canAllocate(Process process) {
        return size >= process.size;
    }
}
