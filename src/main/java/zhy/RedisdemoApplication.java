package zhy;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

@SpringBootApplication
public class RedisdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisdemoApplication.class, args);
	}

	/**
	 *  SpringBoot为我们自动配置了 RedisTemplate，而 RedisTemplate 使用的是 JdkSerializationRedisSerializer，这个对于演示 Redis Client很不直观
	 *  因为 JdkSerializationRedisSerializer 使用二级制形式存储数据，于是这里我们将自己配置 RedisTemplate 并定义 Serializer
	 */
	@Bean
	@SuppressWarnings({"rawtypes", "unchecked"})
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException{
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);

		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		//设置值(value)的序列化采用 Jackson2JsonRedisSerializer
		template.setValueSerializer(jackson2JsonRedisSerializer);
		//设置键(key)的序列化采用 StringRedisSerializer
		template.setKeySerializer(new StringRedisSerializer());

		template.afterPropertiesSet();
		return template;

	}
}
