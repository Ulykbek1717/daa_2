package algorithms;

import metrics.PerformanceTracker;


public class MaxHeap {

    private final PerformanceTracker tracker;

    public MaxHeap() {
        this(new PerformanceTracker());
    }

    public MaxHeap(PerformanceTracker tracker) {
        this.tracker = (tracker != null) ? tracker : new PerformanceTracker();
    }

    public void heapSort(int[] a) {
        if (a == null) throw new IllegalArgumentException("array is null");
        int n = a.length;
        if (n <= 1) return;

        buildMaxHeap(a, n);
        for (int end = n - 1; end > 0; end--) {
            swap(a, 0, end);
            siftDown(a, 0, end);
        }
    }


    private void buildMaxHeap(int[] a, int size) {
        for (int i = (size / 2) - 1; i >= 0; i--) {
            siftDown(a, i, size);
        }
    }

    private void siftDown(int[] a, int i, int size) {
        while (true) {
            int left = 2 * i + 1;
            if (left >= size) break;

            int right = left + 1;
            int largest = i;

            tracker.comparisons++;
            tracker.arrayAccesses += 2;
            if (a[left] > a[largest]) largest = left;

            if (right < size) {
                tracker.comparisons++;
                tracker.arrayAccesses += 2;
                if (a[right] > a[largest]) largest = right;
            }

            if (largest != i) {
                swap(a, i, largest);
                i = largest;
            } else {
                break;
            }
        }
    }

    private void swap(int[] a, int i, int j) {
        int tmp = a[i];
        int vj  = a[j];
        a[i] = vj;
        a[j] = tmp;
        tracker.arrayAccesses += 4;
        tracker.swaps++;
    }
}
