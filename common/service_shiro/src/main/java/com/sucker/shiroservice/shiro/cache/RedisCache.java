package com.sucker.shiroservice.shiro.cache;

import com.sucker.shiroservice.shiro.utils.ApplicationContextUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collection;
import java.util.Set;

//自定义redis缓存的实现，需要实现Cache接口
public class RedisCache<k,v> implements Cache<k,v> {

//    @Autowired
//    private RedisTemplate redisTemplate;

    private String cacheName;

    public RedisCache(){ }

    public RedisCache(String cacheName){this.cacheName = cacheName;}

    @Override
    public v get(k k) throws CacheException {
        System.out.println("get key: "+k);
        //redisTemplate默认是对对象进行操作的，而key是一个字符串，value才是一个对象
        //所以把key的序列化方式改为String的序列化方式
        return (v) getRedisTemplate().opsForHash().get(this.cacheName,k.toString());
    }

    @Override
    public v put(k k, v v) throws CacheException {
        System.out.println("put key: "+k);
        System.out.println("put value: "+v);
        //redisTemplate默认是对对象进行操作的，而key是一个字符串，value才是一个对象
        //所以把key的序列化方式改为String的序列化方式
        getRedisTemplate().opsForHash().put(this.cacheName,k.toString(),v);
        return null;
    }

    @Override
    public v remove(k k) throws CacheException {
        return (v) getRedisTemplate().opsForHash().delete(this.cacheName,k.toString());
    }

    @Override
    public void clear() throws CacheException {
        getRedisTemplate().delete(this.cacheName);
    }

    @Override
    public int size() {
        return getRedisTemplate().opsForHash().size(this.cacheName).intValue();
    }

    @Override
    public Set<k> keys() {
        return getRedisTemplate().opsForHash().keys(this.cacheName);
    }

    @Override
    public Collection<v> values() {
        return getRedisTemplate().opsForHash().values(this.cacheName);
    }

    private RedisTemplate getRedisTemplate(){
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //内部的Map中的String序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }


}
