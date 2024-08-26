package springbootlearn.springbootlearn.services;


import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RedisService {

  @Autowired
  private RedisTemplate redisTemplate;

  public <T> T get(String key, Class<T> weatherResponseClass) {
   
      try {
        Object o = redisTemplate.opsForValue().get(key);
        if (o == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(o.toString(), weatherResponseClass);
    } catch (Exception e) {
        throw new Error(e.getMessage());
    }
  

  }

  public Boolean set(String key, Object o, Long ttl) {
    try {
      ObjectMapper objectMapper=new ObjectMapper();
      String jsonValue=objectMapper.writeValueAsString(o);
      redisTemplate.opsForValue().set(key, jsonValue, ttl,TimeUnit.SECONDS);

      return true;
    } catch (Exception e) {
      throw new Error(e.getMessage());
    }

  }

}
