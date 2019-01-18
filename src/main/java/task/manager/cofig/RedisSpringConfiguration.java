package task.manager.cofig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import task.manager.summary.SummaryService;

import static task.manager.cofig.Topics.SUMMARY;

@Configuration
public class RedisSpringConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisSpringConfiguration.class);

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter summaryListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(summaryListenerAdapter, new PatternTopic(SUMMARY.getRealTopicName()));
        System.out.println();
        LOGGER.info("Registered Redis topic with name: " + SUMMARY.getRealTopicName());
        System.out.println();
        return container;
    }

    @Bean
    MessageListenerAdapter summaryListenerAdapter(SummaryService summaryService) {
        return new MessageListenerAdapter(summaryService, "printSummary");
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    SummaryService summaryService() {
        return new SummaryService();
    }
}
