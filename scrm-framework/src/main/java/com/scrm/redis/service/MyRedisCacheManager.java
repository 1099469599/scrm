package com.scrm.redis.service;

import com.scrm.annotation.CacheExpire;
import com.scrm.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * @author liuKevin
 * @date 2021年10月14日 15:35
 */
@Slf4j
public class MyRedisCacheManager extends RedisCacheManager implements ApplicationContextAware, InitializingBean {

    @Autowired
    private RedisCacheConfiguration redisCacheConfiguration;
    private ApplicationContext applicationContext;
    private final Map<String, RedisCacheConfiguration> initialCacheConfiguration = new LinkedHashMap<>();

    public MyRedisCacheManager(DefaultRedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    @Nullable
    public Cache getCache(@NonNull String name) {
        Cache cache = super.getCache(name);
        return new RedisCacheWrapper(cache);
    }


    @Override
    public void afterPropertiesSet() {
        String[] beanNames = applicationContext.getBeanNamesForType(Object.class);
        for (String beanName : beanNames) {
            final Class<?> klass = applicationContext.getType(beanName);
            add(klass);
        }
        super.afterPropertiesSet();
    }

    @Override
    @NonNull
    protected Collection<RedisCache> loadCaches() {
        List<RedisCache> caches = new LinkedList<>();
        for (Map.Entry<String, RedisCacheConfiguration> entry : initialCacheConfiguration.entrySet()) {
            caches.add(super.createRedisCache(entry.getKey(), entry.getValue()));
        }
        return caches;
    }


    private void add(final Class clazz) {
        ReflectionUtils.doWithMethods(clazz, method -> {
            ReflectionUtils.makeAccessible(method);
            // 方法上是否存在注解
            CacheExpire cacheExpire = AnnotationUtils.findAnnotation(method, CacheExpire.class);
            // 不存在直接返回
            if (cacheExpire == null) {
                return;
            }
            //
            Cacheable cacheable = AnnotationUtils.findAnnotation(method, Cacheable.class);
            if (cacheable != null) {
                add(cacheable.cacheNames(), cacheExpire);
                return;
            }
            Caching caching = AnnotationUtils.findAnnotation(method, Caching.class);
            if (caching != null) {
                Cacheable[] cs = caching.cacheable();
                if (cs.length > 0) {
                    for (Cacheable c : cs) {
                        if (c != null) {
                            add(c.cacheNames(), cacheExpire);
                        }
                    }
                }
            } else {
                CacheConfig cacheConfig = AnnotationUtils.findAnnotation(clazz, CacheConfig.class);
                if (cacheConfig != null) {
                    add(cacheConfig.cacheNames(), cacheExpire);
                }
            }
        }, method -> null != AnnotationUtils.findAnnotation(method, CacheExpire.class));
    }


    private void add(String[] cacheNames, CacheExpire cacheExpire) {
        for (String cacheName : cacheNames) {
            if (cacheName == null || "".equals(cacheName.trim())) {
                continue;
            }
            long expire = cacheExpire.ttl();
            log.info("cacheName: {}, expire: {}", cacheName, expire);
            if (expire >= 0) {
                // 缓存配置
                RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMillis(expire))
                        .disableCachingNullValues()
                        // .prefixKeysWith(cacheName)
                        .serializeKeysWith(redisCacheConfiguration.getKeySerializationPair())
                        .serializeValuesWith(redisCacheConfiguration.getValueSerializationPair());
                initialCacheConfiguration.put(cacheName, config);
            } else {
                log.warn("{} use default expiration.", cacheName);
            }
        }
    }


    protected static class RedisCacheWrapper implements Cache {
        private final Cache cache;

        RedisCacheWrapper(Cache cache) {
            this.cache = cache;
        }

        @Override
        @NonNull
        public String getName() {
            try {
                return cache.getName();
            } catch (Exception e) {
                log.error("getName ---> errMsg: {}", e.getMessage(), e);
                throw new CommonException("redis缓存获取失败", e);
            }
        }

        @Override
        @NonNull
        public Object getNativeCache() {
            try {
                return cache.getNativeCache();
            } catch (Exception e) {
                log.error("getNativeCache ---> errMsg: {}", e.getMessage(), e);
                throw new CommonException("redis缓存获取失败", e);
            }
        }

        @Override
        public ValueWrapper get(@NonNull Object key) {
            try {
                return cache.get(key);
            } catch (Exception e) {
                log.error("get ---> key: {}, errMsg: {}", key, e.getMessage(), e);
                return null;
            }
        }

        @Override
        @Nullable
        public <T> T get(@NonNull Object key, @Nullable Class<T> klass) {
            try {
                return cache.get(key, klass);
            } catch (Exception e) {
                log.error("get ---> key: {}, klass: {}, errMsg: {}", key, klass, e.getMessage(), e);
                return null;
            }
        }


        @Override
        @Nullable
        public <T> T get(@NonNull Object key, @NonNull Callable<T> callable) {
            try {
                return cache.get(key, callable);
            } catch (Exception e) {
                log.error("get ---> key: {}, errMsg: {}", key, e.getMessage(), e);
                return null;
            }
        }

        @Override
        public void put(@NonNull Object key, Object value) {
            try {
                cache.put(key, value);
            } catch (Exception e) {
                log.error("put ---> key: {}, value: {}, errMsg: {}", key, value, e.getMessage(), e);
            }
        }

        @Override
        public ValueWrapper putIfAbsent(@NonNull Object key, Object value) {
            try {
                return cache.putIfAbsent(key, value);
            } catch (Exception e) {
                log.error("putIfAbsent ---> key: {}, value: {}, errMsg: {}", key, value, e.getMessage(), e);
                return null;
            }
        }

        @Override
        public void evict(@NonNull Object key) {
            try {
                cache.evict(key);
            } catch (Exception e) {
                log.error("evict ---> key: {}, errMsg: {}", key, e.getMessage(), e);
            }
        }

        @Override
        public void clear() {
            try {
                cache.clear();
            } catch (Exception e) {
                log.error("clear ---> errMsg: {}", e.getMessage(), e);
            }
        }
    }
}
