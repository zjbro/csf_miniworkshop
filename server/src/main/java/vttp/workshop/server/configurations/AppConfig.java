package vttp.workshop.server.configurations;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class AppConfig {
    
    private Logger logger = Logger.getLogger(AppConfig.class.getName());

    @Value("${cors.pathMapping}")
    String pathMapping;

    @Value("${cors.origins}")
    String origins;

    @Bean 
    public WebMvcConfigurer webMvcConfigurer(){
        logger.info("CORS: pathMapping=%s, origins=%s".formatted(pathMapping, origins));
        return new CORSConfiguration(pathMapping, origins);
    }

    @Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private Integer redisPort;

	// @Value("${spring.redis.database}")
	// private Integer redisDatabase;

	// @Value("${spring.redis.password}")
	// private String redisPassword;


	@Bean(name="games")
	public RedisTemplate<String, String> createRedis() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
		config.setHostName(redisHost);
		config.setPort(redisPort);
		// config.setDatabase(redisDatabase);
		// config.setPassword(redisPassword);

		JedisClientConfiguration jedisConfig = JedisClientConfiguration.builder().build();
		JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisConfig);
		jedisFac.afterPropertiesSet();

		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisFac);

		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new StringRedisSerializer());

		return template;
	}


}
