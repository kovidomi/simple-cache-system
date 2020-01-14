# simple-cache-system

This project requires JDK version of at least 8, and Maven version 3.5.4

# Building the project

From the root of the project, simply run

`mvn compile`

# Usage

## Config file

After compilation the config file is found at

`target\classes\cache.cfg`

## Execution

From the root of the project, run

`mvn exec:java -Dexec.mainClass="Main" -Dexec.classpathScore=runtime`

## Application usage

To stop the application and save the contents of the cache to file, send a POST request to

`http://localhost:4567/cache/stop`

To insert the key "one" with value "1" into the cache, send a POST request to

`http://localhost:4567/cache/insert?key=one&value=1`

To receive the value of the key "one" from the cache, send a GET request to

`http://localhost:4567/cache/get/one`

To receive statistics about the cache system, send a GET request to

`http://localhost:4567/cache/stats`
