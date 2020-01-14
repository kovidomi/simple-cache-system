package simple.cache;

import java.io.*;
import java.util.HashMap;

/**
 * Class for reading from, and saving cache contents into file
 */
public final class CacheFileHandler implements Serializable {

    /**
     * Saves the currently used cache contents into file
     * @param cache a cache object
     * @param filePath path to the cache file
     */
    public static void saveCacheToFile(HashMap<String, String> cache, String filePath) {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filePath))) {
            os.writeObject(cache);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Loads cache contents from file
     * @param filePath path to the cache file
     * @return a new cache object if the cache file was read, otherwise null
     */
    public static HashMap<String, String> readCacheFromFile(String filePath) {
        HashMap<String, String> ret = null;

        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(filePath))) {
            ret = (HashMap<String, String>) is.readObject();
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        finally {
            return ret;
        }
    }
}