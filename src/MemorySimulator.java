import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemorySimulator {
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter number of partitions: ");
        int numberOfPartitions = Integer.parseInt(scanner.nextLine());

        List<Partition> partitions = new ArrayList<>();

        for (int i = 0; i < numberOfPartitions; i++) {
            System.out.println("Enter partition " + i + "'s size: ");
            int partitionSize = Integer.parseInt(scanner.nextLine());
            partitions.add(new Partition(i, partitionSize));
        }

        System.out.println("Enter number of processes: ");
        int numberOfProcesses = Integer.parseInt(scanner.nextLine());

        List<Process> processes = new ArrayList<>();

        for (int i = 0; i < numberOfProcesses; i++) {
            System.out.println("Enter process " + i + "'s size: ");
            int processSize = Integer.parseInt(scanner.nextLine());
            processes.add(new Process(i + 1, processSize));
        }

        run(partitions, processes);
    }

    public static void run(List<Partition> partitions, List<Process> processes) {
        AllocationAlgorithm allocationAlgorithm;

        System.out.println("Select the policy you want to apply: ");
        System.out.println("1. First fit");
        System.out.println("2. Worst fit");
        System.out.println("3. Best fit");
        System.out.println("Select policy: ");
        int algo = Integer.parseInt(scanner.nextLine());

        switch (algo) {
            case 2 -> allocationAlgorithm = new WorstFit();
            case 3 -> allocationAlgorithm = new BestFit();
            default -> allocationAlgorithm = new FirstFit();
        }

        Pair result = allocationAlgorithm.allocate(partitions, processes);

        showResults(result);

        System.out.println("Do you want to compact?\n1. yes\n2. no");
        int cmpt = Integer.parseInt(scanner.nextLine());

        if (cmpt == 1) {
            int totalRemaining = 0;
            List<Partition> afterCompaction = new ArrayList<>();

            for (Partition p : result.partitions) {
                if (!p.isAllocated())
                    totalRemaining += p.size;
                else
                    afterCompaction.add(p);
            }

            afterCompaction.add(new Partition(Partition.maxNumber + 1, totalRemaining));
            Pair newResults = allocationAlgorithm.allocate(
                    afterCompaction,
                    result.processes.stream().filter(process -> !process.isAllocated()).toList()
            );

            showResults(newResults);
        }
    }

    private static void showResults(Pair result) {
        for (Partition p : result.partitions) {
            if (p.isAllocated())
                System.out.println(p.name() + " (" + p.getProcess().size + " KB) => " + p.getProcess().name());
            else
                System.out.println(p.name() + " (" + p.size + " KB) => External fragment");
        }

        System.out.println();

        for (Process p : result.processes) {
            System.out.println(p.name() + " can not be allocated");
        }
    }
}
