package cli;

import algorithms.MaxHeap;
import metrics.PerformanceTracker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class BenchmarkRunner {

    public static void main(String[] args) {
        String sizesStr = getArg(args, "sizes", "100,1000,10000,100000");
        long seed = Long.parseLong(getArg(args, "seed", "1"));
        String csv = getArg(args, "csv", "");

        int[] sizes = parseSizes(sizesStr);
        if (sizes.length == 0) {
            System.err.println("No sizes provided.");
            return;
        }

        BufferedWriter bw = null;
        try {
            if (!csv.isEmpty()) {
                bw = new BufferedWriter(new FileWriter(csv));
                bw.write("n,time_ms,comparisons,swaps,arrayAccesses,allocations,resizes\n");
            }

            for (int n : sizes) {
                int[] arr = new Random(seed).ints(n, 0, 1_000_000).toArray();

                PerformanceTracker tracker = new PerformanceTracker();
                MaxHeap sorter = new MaxHeap(tracker);

                long t0 = System.nanoTime();
                sorter.heapSort(arr);
                long t1 = System.nanoTime();
                long ms = (t1 - t0) / 1_000_000;

                System.out.println(
                        "n=" + n +
                                " | time=" + ms + "ms" +
                                " | comparisons=" + tracker.getComparisons() +
                                " swaps=" + tracker.getSwaps() +
                                " accesses=" + tracker.getArrayAccesses()
                );

                if (bw != null) {
                    bw.write(String.format("%d,%d,%d,%d,%d,%d,%d%n",
                            n, ms,
                            tracker.getComparisons(),
                            tracker.getSwaps(),
                            tracker.getArrayAccesses(),
                            tracker.getAllocations(),
                            tracker.getResizes()));
                }
            }
        } catch (IOException e) {
            System.err.println("CSV error: " + e.getMessage());
        } finally {
            if (bw != null) try { bw.close(); } catch (IOException ignored) {}
        }
    }

    private static String getArg(String[] args, String key, String def) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals(key)) return args[i + 1];
        }
        return def;
    }

    private static int[] parseSizes(String csv) {
        String[] parts = csv.split(",");
        return java.util.Arrays.stream(parts)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
