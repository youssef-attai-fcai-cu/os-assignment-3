import java.util.List;

public interface AllocationAlgorithm {
    Pair allocate(List<Partition> partitions, List<Process> processes);
}
