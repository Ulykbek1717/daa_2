package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class MaxHeapTest {


    @Test
    public void testEmptyArray() {
        PerformanceTracker tracker = new PerformanceTracker();
        MaxHeap sorter = new MaxHeap(tracker);

        int[] arr = {};
        sorter.heapSort(arr);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    public void testSingleElement() {
        PerformanceTracker tracker = new PerformanceTracker();
        MaxHeap sorter = new MaxHeap(tracker);

        int[] arr = {42};
        sorter.heapSort(arr);
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    public void testDuplicates() {
        PerformanceTracker tracker = new PerformanceTracker();
        MaxHeap sorter = new MaxHeap(tracker);

        int[] arr = {5, 1, 5, 1, 5};
        sorter.heapSort(arr);
        assertTrue(isSortedAscending(arr));
    }

    @Test
    public void testAlreadySorted() {
        PerformanceTracker tracker = new PerformanceTracker();
        MaxHeap sorter = new MaxHeap(tracker);

        int[] arr = {1, 2, 3, 4, 5};
        sorter.heapSort(arr);
        assertTrue(isSortedAscending(arr));
    }

    @Test
    public void testReverseSorted() {
        PerformanceTracker tracker = new PerformanceTracker();
        MaxHeap sorter = new MaxHeap(tracker);

        int[] arr = {5, 4, 3, 2, 1};
        sorter.heapSort(arr);
        assertTrue(isSortedAscending(arr));
    }


    @Test
    public void testRandomArrays() {
        PerformanceTracker tracker = new PerformanceTracker();
        MaxHeap sorter = new MaxHeap(tracker);

        Random rand = new Random();
        for (int t = 0; t < 20; t++) {
            int[] arr = rand.ints(50, 0, 100).toArray();
            int[] expected = Arrays.copyOf(arr, arr.length);

            sorter.heapSort(arr);
            Arrays.sort(expected);

            assertArrayEquals(expected, arr);
        }
    }


    @Test
    public void testCrossValidation() {
        PerformanceTracker tracker = new PerformanceTracker();
        MaxHeap sorter = new MaxHeap(tracker);

        int[] arr = {7, 2, 9, 1, 3};
        int[] expected = Arrays.copyOf(arr, arr.length);

        sorter.heapSort(arr);
        Arrays.sort(expected);

        assertArrayEquals(expected, arr);
    }


    @Test
    public void testScalability() {
        int[] sizes = {100, 1000, 10000};
        for (int n : sizes) {
            PerformanceTracker tracker = new PerformanceTracker();
            MaxHeap sorter = new MaxHeap(tracker);

            int[] arr = new Random().ints(n, 0, 10000).toArray();

            long start = System.currentTimeMillis();
            sorter.heapSort(arr);
            long end = System.currentTimeMillis();

            System.out.println("n=" + n +
                    " time=" + (end - start) + "ms " +
                    "comparisons=" + tracker.getComparisons() +
                    " swaps=" + tracker.getSwaps() +
                    " accesses=" + tracker.getArrayAccesses());

            assertTrue(isSortedAscending(arr));
        }
    }


    @Test
    public void testInputDistributions() {
        PerformanceTracker tracker = new PerformanceTracker();
        MaxHeap sorter = new MaxHeap(tracker);

        int[] sorted = {1,2,3,4,5,6,7,8,9};
        int[] reversed = {9,8,7,6,5,4,3,2,1};
        int[] nearlySorted = {1,2,3,5,4,6,7,8,9};
        int[] random = {4,9,1,7,3,6,8,2,5};

        sorter.heapSort(sorted);
        sorter.heapSort(reversed);
        sorter.heapSort(nearlySorted);
        sorter.heapSort(random);

        assertTrue(isSortedAscending(sorted));
        assertTrue(isSortedAscending(reversed));
        assertTrue(isSortedAscending(nearlySorted));
        assertTrue(isSortedAscending(random));
    }


    @Test
    public void testMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();

        long before = runtime.totalMemory() - runtime.freeMemory();

        PerformanceTracker tracker = new PerformanceTracker();
        MaxHeap sorter = new MaxHeap(tracker);

        int[] arr = new Random().ints(50_000, 0, 1_000_000).toArray();
        sorter.heapSort(arr);

        long after = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("Memory used: " + (after - before) + " bytes");
        assertTrue(isSortedAscending(arr));
    }


    private boolean isSortedAscending(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) return false;
        }
        return true;
    }
}
