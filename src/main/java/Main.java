import simple.cache.*;

public class Main {

    public static void main(String[] args) {

        ConfigLoader cfg = new ConfigLoader();
        int port = Integer.parseInt(cfg.getProperty("PORT"));
        int cacheSize = Integer.parseInt(cfg.getProperty("CACHE_SIZE"));
        String cacheFile = cfg.getProperty("CACHE_FILE");

        SimpleCache simpleCache = new SimpleCache(cacheSize, cacheFile);
        SimpleCacheRestController restController = new SimpleCacheRestController(simpleCache, port, true);
    }
}
