import java.util.ArrayList;
import java.util.List;

public class FirstFit implements AllocationAlgorithm {
    @Override
    public Pair allocate(List<Partition> partitions, List<Process> processes) {
        List<Process> notAllocated = new ArrayList<>();
        for (Process process : processes) {
            boolean allocated = false;
            for (int i = 0; i < partitions.size(); i++) {
                Partition partition = partitions.get(i);
                if (!partition.isAllocated() && partition.canAllocate(process) && partition.allocate(process)) {
                    if (partition.getRemaining() > 0)
                        partitions.add(i + 1, new Partition(Partition.maxNumber + 1, partition.getRemaining()));
                    allocated = true;
                    break;
                }
            }
            if (!allocated) notAllocated.add(process);
        }
        return new Pair(partitions, notAllocated);
    }
}
