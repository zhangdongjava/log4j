package com.zzz.log4j.util;

import com.alibaba.fastjson.JSON;
import com.zzz.log4j.domain.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Created by dell_2 on 2016/9/6.
 */
public class RedisUtil implements Cache {

    private RedisTemplate<String, Object> redisTemplate;

    private RedisSerializer<String> stringSeria;

    private String name;

    private Logger logger = Logger.getLogger(getClass());

    public void setName(String name) {
        this.name = name;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        stringSeria = redisTemplate.getStringSerializer();
    }

    public String getName() {
        return name;
    }

    public Object getNativeCache() {
        return redisTemplate;
    }

    public ValueWrapper get(final Object key) {
        ValueWrapper result = null;
        User user = redisTemplate.execute(new RedisCallback<User>() {
            public User doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] bytes = connection.get(serialize(name+":"+key.toString()));
                String userJson = deserialize(bytes);
                try {
                    return JSON.parseObject(userJson, User.class);
                }catch (Exception e){
                    return null;
                }

            }
        });
        if (user != null) {
            user.setPassword("from mycache:" + name);
            result = new SimpleValueWrapper(user);
        }
        return result;
    }

    public void put(final Object key, final Object value) {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    connection.set(serialize(name+":"+ key.toString()),serialize(JSON.toJSONString(value)));
                } catch (Exception e) {
                    logger.error(e.toString());
                    return Boolean.FALSE;
                }
                return Boolean.TRUE;
            }
        });
    }



    public void evict(final Object key) {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.del(serialize(name+":"+key.toString()));
                return Boolean.TRUE;
            }
        });
    }

    public void clear() {

    }
    
    public byte[] serialize(String key){
        return stringSeria.serialize(key);
    }
    public String deserialize(byte[] keys){
        return stringSeria.deserialize(keys);
    }
}
