package com.cupk.snapshot.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * Created by Guo Tianyou on 2023/6/15.
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    ///////////////////////////////////////////////////////////////////////////
    // RedisKeyCommands
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @param key     must not be null.
     * @param timeout the key expiration timeout.
     * @param unit    must not be null.
     * @return null when used in pipeline / transaction.
     */
    public Boolean expire(String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * Get the time to live for key in seconds.
     *
     * @param key must not be null.
     * @return null when used in pipeline / transaction.
     */
    public Long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * Get the time to live for key in and convert it to the given TimeUnit.
     *
     * @param key      must not be null.
     * @param timeUnit must not be null.
     * @return null when used in pipeline / transaction.
     */
    public Long ttl(String key, final TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }


    /**
     * Determine if given key exists.
     *
     * @param key must not be null.
     * @return true if key exists. null when used in pipeline / transaction.
     */
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * Delete given keys.
     *
     * @param keys must not be null.
     * @return The number of keys that were removed. null when used in pipeline / transaction.
     */
    public Long del(String... keys) {
        return redisTemplate.delete(Arrays.asList(keys));
    }

    ///////////////////////////////////////////////////////////////////////////
    //  ValueOperations
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Get the value of key.
     *
     * @param key must not be null.
     * @return null when key does not exist or used in pipeline / transaction
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Set value for key.
     *
     * @param key   must not be null.
     * @param value must not be null.
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Set the value and expiration timeout for key.
     *
     * @param key     must not be null.
     * @param value   must not be null.
     * @param timeout the key expiration timeout.
     * @param unit    must not be null.
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * Increment an integer value stored as string value under key by one.
     *
     * @param key must not be null.
     * @return null when used in pipeline / transaction.
     */
    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * Increment a floating point number value stored as string value under key by increment.
     *
     * @param key       must not be null.
     * @param increment increment step
     * @return null when used in pipeline / transaction.
     */
    public Long incrby(String key, long increment) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * Decrement an integer value stored as string value under key by ont.
     *
     * @param key must not be null.
     * @return null when used in pipeline / transaction.
     */
    public Long decr(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * Decrement an integer value stored as string value under key by decrement.
     *
     * @param key       must not be null.
     * @param decrement decrement step
     * @return null when used in pipeline / transaction.
     */
    public Long decrby(String key, long decrement) {
        return redisTemplate.opsForValue().decrement(key, decrement);
    }

    ///////////////////////////////////////////////////////////////////////////
    //  HashOperations
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Get value for given field from hash at key.
     *
     * @param key   must not be null.
     * @param field must not be null.
     * @return null when key or hashKey does not exist or used in pipeline / transaction.
     */
    public Object hget(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * Get values for given field from hash at key.
     *
     * @param key   must not be null.
     * @param field must not be null.
     * @return null when used in pipeline / transaction.
     */
    public List<Object> hmget(String key, String... field) {
        return redisTemplate.opsForHash().multiGet(key, Arrays.asList(field));
    }

    /**
     * Set the value of a hash field.
     *
     * @param key   must not be null.
     * @param field must not be null.
     * @param value must not be null.
     */
    public void hset(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * Set multiple hash fields to multiple values using data provided in m.
     *
     * @param key must not be null.
     * @param m   must not be null.
     */
    public void hmset(String key, Map<String, Object> m) {
        redisTemplate.opsForHash().putAll(key, m);
    }

    /**
     * Delete given hash field.
     *
     * @param key   must not be null.
     * @param field must not be null.
     * @return null when used in pipeline / transaction.
     */
    public Long hdel(String key, String... field) {
        return redisTemplate.opsForHash().delete(key, Arrays.asList(field));
    }

    /**
     * Get key set (fields) of hash at key.
     *
     * @param key must not be null.
     * @return null when used in pipeline / transaction.
     */
    public Set<Object> hkeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * Get entry set (values) of hash at key.
     *
     * @param key must not be null.
     * @return null when used in pipeline / transaction.
     */
    public List<Object> hvals(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * Determine if given hash hashKey exists.
     *
     * @param key   must not be null.
     * @param field must not be null.
     * @return null when used in pipeline / transaction.
     */
    public boolean hexists(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    ///////////////////////////////////////////////////////////////////////////
    // SetOperations
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Get all elements of set at key.
     *
     * @param key must not be null.
     * @return null when used in pipeline / transaction.
     */
    public Set<Object> smembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * Check if set at key contains value
     *
     * @param key    must not be null.
     * @param member must not be null.
     * @return null when used in pipeline / transaction.
     */
    public Boolean sismember(String key, Object member) {
        return redisTemplate.opsForSet().isMember(key, member);
    }

    /**
     * Add given values to set at key.
     *
     * @param key    must not be null.
     * @param member must not be null.
     * @return null when used in pipeline / transaction.
     */
    public Long sadd(String key, Object... member) {
        return redisTemplate.opsForSet().add(key, member);
    }


    /**
     * Get size of set at key.
     *
     * @param key must not be null.
     * @return null when used in pipeline / transaction.
     */
    public Long scard(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * Remove given member from set at key and return the number of removed elements.
     *
     * @param key    must not be null.
     * @param member must not be null.
     * @return null when used in pipeline / transaction.
     */
    public Long srem(String key, Object... member) {
        return redisTemplate.opsForSet().remove(key, member);
    }


    ///////////////////////////////////////////////////////////////////////////
    // ListOperations
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Get elements between begin and end from list at key.
     *
     * @param key   must not be null.
     * @param start must not be null.
     * @param stop  must not be null.
     * @return null when used in pipeline / transaction.
     */
    public List<Object> lrange(String key, long start, long stop) {
        return redisTemplate.opsForList().range(key, start, stop);
    }

    /**
     * Get the size of list stored at key.
     *
     * @param key must not be null.
     * @return null when used in pipeline / transaction.
     */
    public Long llen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * Get element at index form list at key.
     *
     * @param key   must not be null.
     * @param index must not be null.
     * @return null when used in pipeline / transaction.
     */
    public Object lindex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * Append element to key.
     *
     * @param key     must not be null.
     * @param element must not be null.
     * @return null when used in pipeline / transaction.
     */
    public Long lpush(String key, Object... element) {
        return redisTemplate.opsForList().leftPushAll(key, element);
    }

    /**
     * Append element to key.
     *
     * @param key     must not be null.
     * @param element must not be null.
     * @return null when used in pipeline / transaction.
     */
    public Long rpush(String key, Object... element) {
        return redisTemplate.opsForList().rightPushAll(key, element);
    }


    /**
     * Set the value list element at index.
     *
     * @param key     must not be null.
     * @param index   must not be null.
     * @param element must not be null.
     */
    public void lset(String key, long index, Object element) {
        redisTemplate.opsForList().set(key, index, element);
    }

}
