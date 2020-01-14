package simple.cache;

import java.util.Properties;

/**
 * Configure file loader, for retrieving values from the cache.cfg file
 */
public class ConfigLoader
{
    Properties configFile;
    public ConfigLoader()
    {
        configFile = new java.util.Properties();
        try {
            configFile.load(this.getClass().getClassLoader().
                    getResourceAsStream("cache.cfg"));
        }catch(Exception eta){
            eta.printStackTrace();
        }
    }

    public String getProperty(String key)
    {
        String value = this.configFile.getProperty(key);
        return value;
    }
}