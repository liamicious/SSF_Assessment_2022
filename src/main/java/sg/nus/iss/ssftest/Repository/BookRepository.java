package sg.nus.iss.ssftest.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class BookRepository {

    @Autowired
    private RedisTemplate<String, String> template;

    public void save(String key, String JsonValue) {
        template.opsForValue().set(normalize(key), JsonValue, 10L, TimeUnit.MINUTES);
    }

    public Optional<String> get(String key) {
        String value = template.opsForValue().get(normalize(key));
        return Optional.ofNullable(value);
    }

    private String normalize(String k) {
        return k.trim().toLowerCase();
    }
}