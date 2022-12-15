import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BestFit implements AllocationAlgorithm {
    @Override
    public Pair allocate(List<Partition> partitions, List<Process> processes) {
        List<Process> notAllocated = new ArrayList<>();

        for (Process process : processes) {
            Partition best = null;
            Optional<Partition> found = partitions
                    .stream()
                    .filter(partition -> !partition.isAllocated() && partition.canAllocate(process))
                    .min((o1, o2) -> o1.size - o2.size);
            if (found.isPresent()) best = found.get();

            if (best != null && !best.isAllocated() && best.allocate(process)) {
                if (best.getRemaining() > 0) {
                    partitions.add(partitions.indexOf(best) + 1, new Partition(Partition.maxNumber + 1, best.getRemaining()));
                }
            } else {
                notAllocated.add(process);
            }
        }
        return new Pair(partitions, notAllocated);
    }
}
