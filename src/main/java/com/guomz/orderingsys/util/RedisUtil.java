package com.guomz.orderingsys.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public boolean setObject(String key, Object value, Long timeOut){
        try {
            String str = objectMapper.writeValueAsString(value);
            stringRedisTemplate.opsForValue().set(key, str, timeOut, TimeUnit.MILLISECONDS);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            log.error("redis操作失败");
        }
        return false;
    }

    public <T> T getObject(String key, Class<T> c){
        try {
            String str = stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.isBlank(str)){
                return null;
            }
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(str, c);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("redis操作失败");
        }
        return null;
    }

    public <T> T getObject(String key, TypeReference<T> typeReference){
        try {
            String str = stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.isBlank(str)){
                return null;
            }
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(str, typeReference);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("redis操作失败");
        }
        return null;
    }

    public boolean delKey(String key){
        try {
            stringRedisTemplate.delete(key);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            log.error("redis操作失败");
        }
        return false;
    }
}
