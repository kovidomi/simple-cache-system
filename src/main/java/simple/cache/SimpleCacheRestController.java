package simple.cache;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * RESTful API service implementation for the simple caching system
 */
public class SimpleCacheRestController {

    private SimpleCache cacheObj;

    /**
     * @param cacheObj SimpleCache object
     * @param port Listening port
     * @param start Start the service right away
     */
    public SimpleCacheRestController(SimpleCache cacheObj, int port, boolean start) {
        this.cacheObj = cacheObj;
        if(start) {
            this.start(port);
        }
    }

    /**
     * Starts the service, initializes the RESTful API URLs
     * @param port Listening port
     */
    public void start(int port) {
        Spark.port(port);
        Spark.init();
        this.Get();
        this.Insert();
        this.Stats();
        this.Stop();
    }

    /**
     * Stops the service
     */
    public void stop() {
        Spark.stop();
    }

    /**
     * Handles the GET request of receiving the value of a key from the cache
     */
    private void Get() {
        Spark.get("/cache/get/:key", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                String key = request.params("key");
                String value = cacheObj.get(key);

                if(value != null) {
                    response.status(200);
                    return "Key found!<br>" + key + "=" + value;
                } else {
                    response.status(404);
                    return "Key not found";
                }
            }
        });
    }

    /**
     * Handles the POST request of a new key-value insertion into the cache system
     */
    private void Insert() {
        Spark.post("/cache/insert", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                String key = request.queryParams("key");
                String value = request.queryParams("value");

                if(cacheObj.insert(key, value)) {

                    response.status(201); // 201 Created
                    return "Insert success<br>" + key + "=" + value;
                }

                response.status(500);
                return "Insert unsuccessful";
            }
        });
    }

    /**
     * Handles the GET request of the cache system statistics listing
     */
    private void Stats() {
        Spark.get("/cache/stats", new Route() {
            @Override
            public Object handle(Request request, Response response) {

                response.status(200);
                String statistics = cacheObj.getCacheStatistics().writeStatistics();
                return statistics;
            }
        });
    }

    /**
     * Handles the POST request of remote service shutdown
     */
    private void Stop() {
        Spark.post("/cache/stop", new Route() {
            @Override
            public Object handle(Request request, Response response) {

                response.status(200);
                cacheObj.stop();
                Spark.stop();
                return "Cache stopped";
            }
        });
    }
}
