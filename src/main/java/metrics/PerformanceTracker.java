package metrics;

public class PerformanceTracker {
    public long comparisons = 0;
    public long swaps = 0;
    public long arrayAccesses = 0;
    public long allocations = 0;
    public long resizes = 0;

    public void reset() {
        comparisons = swaps = arrayAccesses = allocations = resizes = 0;
    }

    public long getComparisons()   { return comparisons; }
    public long getSwaps()         { return swaps; }
    public long getArrayAccesses() { return arrayAccesses; }
    public long getAllocations()   { return allocations; }
    public long getResizes()       { return resizes; }

    @Override
    public String toString() {
        return "comparisons=" + comparisons +
                ", swaps=" + swaps +
                ", arrayAccesses=" + arrayAccesses +
                ", allocations=" + allocations +
                ", resizes=" + resizes;
    }
}
