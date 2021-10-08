package com.scrm.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis相关操作类
 *
 * @author liuKevin
 * @date 2021年10月08日 17:52
 */
@SuppressWarnings({"unchecked", "ConstantConditions"})
@Component
public class RedisCache {

    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * redis递增
     *
     * @param key 缓存的键
     * @return 递增结果
     */
    public Long incr(final String key) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        return entityIdCounter.getAndIncrement();
    }

    /**
     * redis递减
     *
     * @param key 缓存的键
     * @return 递减结果
     */
    public Long decr(final String key) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        return entityIdCounter.getAndDecrement();
    }

    /**
     * 缓存ZSet数据
     *
     * @param key    缓存的键
     * @param value  缓存的值
     * @param weight 权重
     */
    public <T> Boolean setCacheZSet(final String key, final T value, final double weight) {
        return redisTemplate.opsForZSet().add(key, value, weight);
    }

    /**
     * 删除指定value的ZSet数据
     *
     * @param key   缓存的键
     * @param value 缓存的值
     */
    public <T> void delCacheZSet(final String key, final T value) {
        redisTemplate.opsForZSet().remove(key, value);
    }

    /**
     * 根据区间获取集合
     *
     * @param key     缓存的键
     * @param weightS 开始
     * @param weightE 结束
     */
    public <T> Set<T> getZSet(final String key, final double weightS, final double weightE) {
        return redisTemplate.opsForZSet().rangeByScore(key, weightS, weightE);
    }

    /**
     * 获取ZSet集合大小
     *
     * @param key 缓存的键
     * @return set大小
     */
    public Long getZSetSize(final String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取指定的分数
     *
     * @param key   缓存的键
     * @param value 缓存的值
     * @return 分数
     */
    public <T> Double getScore(final String key, final T value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 设置分数
     *
     * @param key    缓存的键
     * @param value  缓存的值
     * @param weight 权重
     */
    public <T> double incrementScore(final String key, final T value, final double weight) {
        return redisTemplate.opsForZSet().incrementScore(key, value, weight);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key     缓存的键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     缓存的键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存的键
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key 缓存的键
     */
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     */
    public long deleteObject(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 缓存List数据的size
     *
     * @param key 缓存的键值
     * @return 缓存对象的size
     */
    public <T> long getCacheListCount(final String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 获得缓存的list对象
     *
     * @param key   redisKey
     * @param start 开始位
     * @param end   结束位
     */
    public <T> List<T> getCacheList(final String key, Integer start, Integer end) {
        return redisTemplate.opsForList().range(key, start, end);
    }


    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> long setCacheSet(final String key, final Set<T> dataSet) {
        long count = 0;
        for (T data : dataSet) {
            count += Optional.ofNullable(redisTemplate.opsForSet().add(key, data)).orElse(0L);
        }
        return count;
    }


    /**
     * 删除Set中的元素
     *
     * @param key     缓存键值
     * @param dataSet 需要删除的对象
     * @return 删除数量
     */
    public <T> long removeMember(final String key, final Set<T> dataSet) {
        long count = 0;
        for (T data : dataSet) {
            count += Optional.ofNullable(redisTemplate.opsForSet().remove(key, data)).orElse(0L);
        }
        return count;
    }

    /**
     * 判断 Set 中是否存在该成员
     *
     * @param key    缓存键值
     * @param member 判断是否存在的对象
     * @return 是否存在
     */
    public <T> boolean memberExist(final String key, final T member) {
        Boolean flag = redisTemplate.opsForSet().isMember(key, member);
        return flag != null && flag;
    }


    /**
     * 获得缓存的set
     *
     * @param key 缓存的键
     * @return 集合
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key     缓存的键
     * @param dataMap 缓存的数据
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key 缓存的键
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param key 字符串前缀
     * @return 对象列表
     */
    public Boolean hasKey(final String key) {
        return redisTemplate.hasKey(key);
    }

    public Boolean setNx(final String key, final Object value, final Long expire) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit.MINUTES);
    }

}
