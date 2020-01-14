package simple.cache;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.concurrent.TimeUnit;

/**
 * Base class for the statistics about a cache system
 */
public abstract class CacheStatistics {
    private int cacheSize = 0;
    private int elementsCount = 0;
    private int insertCount = 0;
    private int removeCount = 0;
    private int hitCount = 0;
    private int missCount = 0;
    private long startTime = 0;

    public CacheStatistics(int cacheSize, int elementsCount) {
        this.cacheSize = cacheSize;
        this.elementsCount = elementsCount;
        startTime = System.nanoTime();
    }

    public abstract String writeStatistics();

    public String getUpTime() {
        long difference = System.nanoTime() - startTime;

        long seconds = TimeUnit.NANOSECONDS.toSeconds(difference);
        int day = (int)TimeUnit.SECONDS.toDays(seconds);
        long hour = TimeUnit.SECONDS.toHours(seconds) - (day *24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);

        return String.format("%d day %d hour %d minute %d second", day, hour, minute, second);
    }

    public float getCacheHitRatio() {
        if(this.hitCount > 0) {
            return (float)this.hitCount / (float)(this.hitCount + this.missCount) * 100.0f;
        }
        return 0.0f;
    }

    public long getMemoryUsage() {
        MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        return heapMemoryUsage.getUsed();
    }

    public int getCacheSize() {
        return this.cacheSize;
    }

    public int getElementsCount() {
        return this.elementsCount;
    }

    public int getInsertCount() {
        return this.insertCount;
    }

    public int getRemoveCount() {
        return this.removeCount;
    }

    public int getHitCount() {
        return this.hitCount;
    }

    public int getMissCount() {
        return this.missCount;
    }

    public void incrementElementsCount() {
        if(elementsCount < cacheSize) {
            this.elementsCount++;
        }
    }

    public void incrementInsertCount() {
        this.insertCount++;
    }

    public void incrementRemoveCount() {
        this.removeCount++;
    }

    public void incrementHitCount() {
        this.hitCount++;
    }

    public void incrementMissCount() {
        this.missCount++;
    }
}
