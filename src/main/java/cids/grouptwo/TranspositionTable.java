package cids.grouptwo;

public class TranspositionTable {
    
    private final TTEntry[] table;
    private final int size;

    public TranspositionTable(int maxEntriesPowerOfTwo) {
        this.size = 1 << maxEntriesPowerOfTwo;
        this.table = new TTEntry[size];
    }

    private int index(long hash) {
        return (int) (hash & (size - 1));
    }

    public void store(long hash, double evaluation, int depth) {
        int idx = index(hash);
        TTEntry current = table[idx];
        if (current == null || current.depth <= depth) {
            table[idx] = new TTEntry(evaluation, depth);
        }
    }

    public TTEntry retrieve(long hash, int currentDepth) {
        TTEntry entry = table[index(hash)];
        return (entry != null && entry.depth >= currentDepth) ? entry : null;
    }
}
