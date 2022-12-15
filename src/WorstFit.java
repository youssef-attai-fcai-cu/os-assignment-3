import java.util.ArrayList;
import java.util.List;

public class WorstFit implements AllocationAlgorithm {
    @Override
    public Pair allocate(List<Partition> partitions, List<Process> processes) {
        List<Process> notAllocated = new ArrayList<>();

        for (Process process : processes) {
            int worstIndex = 0;

            for (int i = 0; i < partitions.size(); i++) {
                if (!partitions.get(i).isAllocated() && partitions.get(i).canAllocate(process) && partitions.get(i).size > partitions.get(worstIndex).size) {
                    worstIndex = i;
                }
            }

            if (!partitions.get(worstIndex).isAllocated() && partitions.get(worstIndex).allocate(process)) {
                if (partitions.get(worstIndex).getRemaining() > 0) {
                    partitions.add(worstIndex + 1, new Partition(Partition.maxNumber + 1, partitions.get(worstIndex).getRemaining()));
                }
            } else {
                notAllocated.add(process);
            }
        }
        return new Pair(partitions, notAllocated);
    }
}
