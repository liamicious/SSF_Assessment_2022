package sg.nus.iss.ssftest.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.logging.Logger;

import static sg.nus.iss.ssftest.Constants.*;

@Configuration
public class BookRedisConfig {

    public static final String ENV_REDIS_PASSWORD = System.getenv("REDIS_PW");

    private final Logger logger = Logger.getLogger(BookRedisConfig.class.getName());

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.database}")
    private Integer redisDatabase;

    public RedisTemplate<String, String> redisTemplateFactory() {

        final RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisHost);
        redisConfig.setPort(redisPort);
        if (null != ENV_REDIS_PASSWORD) {
            redisConfig.setPassword(ENV_REDIS_PASSWORD);
            logger.info("Set Redis password");
        }
        redisConfig.setDatabase(redisDatabase);

        final JedisClientConfiguration jedisConfig = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(redisConfig, jedisConfig);
        jedisFac.afterPropertiesSet();

        final RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        return template;
    }

}