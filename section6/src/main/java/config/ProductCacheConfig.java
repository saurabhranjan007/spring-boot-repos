package config;

// NOTE:
//    #. caching basically allows an application to run a little faster.
//    #. So when you first hit the database from your application, the caching framework will create an object class for that
//       (if caching is enabled in the application) and the next time you make the same request. The caching framework will
//       first look into those objects and if there's any entry for those then it will return the result w/o having to hit the database.

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductCacheConfig {

//    this class will return a "Hazelcast" config object back..

    @Bean
    public Config cacheConfig() {
        return new Config()
                .setInstanceName("hazel-instance")  // this instance name is custom so it can be anything
                .addMapConfig(new MapConfig()    // creating a new "MapConfig", so that we can define properties on it to track the cache
                        .setName("product-cache")  // this returns the name of this cache, every cache can have a custom name
                        .setTimeToLiveSeconds(3000));  // this basically returns the time stamp till which the cache will live (-1: means forever)
    }

}
