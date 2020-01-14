package simple.cache;

/**
 * Extension of the CacheStatistics class
 */
public class SimpleCacheStatistics extends CacheStatistics {

    public SimpleCacheStatistics(int cacheSize, int elementsCount) {
        super(cacheSize, elementsCount);
    }

    public String writeStatistics() {
        String statistics = String.format("Cache size: %d<br>", getCacheSize());
        statistics += String.format("Memory usage (bytes): %d<br>", getMemoryUsage());
        statistics += String.format("Number of elements in cache: %d<br>", getElementsCount());
        statistics += String.format("Uptime: %s<br>", getUpTime());
        statistics += String.format("Cache Hit Ratio: %.2f%%<br>", getCacheHitRatio());
        statistics += String.format("Total elements inserted: %d<br>", getInsertCount());
        statistics += String.format("Total elements removed: %d<br>", getRemoveCount());
        statistics += String.format("Hit count: %d<br>", getHitCount());
        statistics += String.format("Miss count: %d<br>", getMissCount());

        return statistics;
    }
}
