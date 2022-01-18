package sg.nus.iss.ssftest.Configuration;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import java.util.logging.Logger;

@Configuration
public class BookRedisConfig {

    private final Logger logger = Logger.getLogger(BookRedisConfig.class.getName());

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;

    @Value("${spring.redis.database}")
    private int redisDatabase;

}
