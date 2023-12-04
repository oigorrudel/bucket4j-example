package br.xksoberbado.bucket4jexample.config;

import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.grid.jcache.JCacheProxyManager;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;

@Configuration
public class RedisConfig {

    private static final String REDIS_PROTOCOL_PREFIX = "redis://";
    private static final String REDISS_PROTOCOL_PREFIX = "rediss://";
    private static final String CACHE_NAME = "bucket4j-cache";

    @Bean
    RedissonClient redissonClient(RedisProperties redisProperties) {
        var config = new Config();
        var prefix = REDIS_PROTOCOL_PREFIX;

        if (redisProperties.isSsl()) {
            prefix = REDISS_PROTOCOL_PREFIX;
        }

        config.useSingleServer()
            .setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
            .setConnectTimeout((int) redisProperties.getTimeout().toMillis())
            .setPassword(redisProperties.getPassword())
            .setDatabase(redisProperties.getDatabase())
            .setSslEnableEndpointIdentification(false);

        return Redisson.create(config);
    }

    @Bean
    public CacheManager cacheManager(final RedissonClient redissonClient) {
        final var manager = Caching.getCachingProvider().getCacheManager();
        manager.createCache(CACHE_NAME, RedissonConfiguration.fromConfig(redissonClient.getConfig()));

        return manager;
    }

    @Bean
    ProxyManager<String> proxyManager(final CacheManager cacheManager) {
        return new JCacheProxyManager<>(cacheManager.getCache(CACHE_NAME));
    }
}
