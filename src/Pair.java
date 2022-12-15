import java.util.List;

public class Pair {
    public final List<Partition> partitions;
    public final List<Process> processes;

    public Pair(List<Partition> partitions, List<Process> processes) {
        this.partitions = partitions;
        this.processes = processes;
    }
}
