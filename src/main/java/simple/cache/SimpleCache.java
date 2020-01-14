package simple.cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * LRU caching system implementation
 */
public class SimpleCache {

    private int cacheSize = 0;
    private String cacheFile;

    private Queue<String> queue = null;
    private HashMap<String,String> cache = null;
    private CacheStatistics cacheStatistics = null;

    /**
     * Initializes a new cache. If loading the cache from file fails, then a new one is allocated.
     * @param cacheSize maximum size of the cache
     * @param cacheFile file to load an existing cache from
     */
    public SimpleCache(int cacheSize, String cacheFile) {
        this.cacheSize = cacheSize;
        this.cacheFile = cacheFile;

        queue = new LinkedList<String>();
        cache = CacheFileHandler.readCacheFromFile(cacheFile);

        if(cache != null) {
            fillQueueWithCacheKeys();
            resizeCache(cacheSize);
        } else {
            cache = new HashMap<String,String>(cacheSize);
        }

        int elementsCount = cache.size();
        cacheStatistics = new SimpleCacheStatistics(cacheSize, elementsCount);
    }

    /**
     * @return CacheStatistics object containing the statistics
     */
    public CacheStatistics getCacheStatistics() {

        return cacheStatistics;
    }

    /**
     * Stops the cache system. Currently just writes the contents of the cache into file
     */
    public void stop() {

        CacheFileHandler.saveCacheToFile(cache, cacheFile);
    }

    /**
     * Inserts a new key-value pair into the cache
     * @param key
     * @param value
     * @return Success or failure
     */
    public boolean insert(String key, String value) {
        if(key.contentEquals("") || value.contentEquals("")) {
            return false;
        }

        removeLruElement(key);

        cache.put(key, value);
        queue.add(key);
        cacheStatistics.incrementInsertCount();
        cacheStatistics.incrementElementsCount();
        return true;
    }

    /**
     * Retrives the value of a key from the cache
     * @param key
     * @return the value of the key
     */
    public String get(String key) {
        queue.remove(key);
        queue.add(key);

        String cacheValue = cache.get(key);

        if(cacheValue != null) {
            cacheStatistics.incrementHitCount();
        } else {
            cacheStatistics.incrementMissCount();
        }

        return cacheValue;
    }

    /**
     * Resize the cache object.
     * If the new size is smaller, then elements are removed from head of the queue until finally fits the new size
     * @param newCacheSize the new size to resize to
     */
    private void resizeCache(int newCacheSize) {
        cacheSize = newCacheSize;

        while(queue.size() > cacheSize) {
            String lruKey = queue.poll();
            if(lruKey != null) {
                queue.remove(lruKey);
                cache.remove(lruKey);
            }
        }
    }

    private void removeLruElement(String key) {

        if(cache.containsKey(key)) {
            queue.remove(key);
            cacheStatistics.incrementHitCount();
        } else {
            cacheStatistics.incrementMissCount();
        }

        if(queue.size() >= cacheSize) {
            String lruKey = queue.poll();
            if(lruKey != null) {
                cache.remove(lruKey);
                cacheStatistics.incrementRemoveCount();
            }
        }
    }

    /**
     * Clears then refills the queue object with the new cache keys
     */
    private void fillQueueWithCacheKeys() {
        queue.clear();

        for ( String key : cache.keySet() ) {
            queue.add(key);
        }
    }
}
