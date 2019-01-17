package task.manager.cofig;

import task.manager.receiver.ReceiverService;
import task.manager.summary.SummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisSpringConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisSpringConfiguration.class);
    
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter summaryListenerAdapter,
                                            MessageListenerAdapter taskListenerAdapter) {
        String taskTopic = "taskTopic";
        String summaryTopic = "summaryTopic";
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(taskListenerAdapter, new PatternTopic(taskTopic));
        container.addMessageListener(summaryListenerAdapter, new PatternTopic(summaryTopic));
        System.out.println();
        LOGGER.info("Registered Redis topic with name: " + summaryTopic);
        LOGGER.info("Registered Redis topic with name: " + taskTopic);
        System.out.println();
        return container;
    }

    @Bean
    MessageListenerAdapter summaryListenerAdapter(SummaryService summaryService) {
        return new MessageListenerAdapter(summaryService, "printSummary");
    }
    
    @Bean
    MessageListenerAdapter taskListenerAdapter(ReceiverService receiver) {
        return new MessageListenerAdapter(receiver, "receiveTask");
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    SummaryService summaryService() {
        return new SummaryService();
    }

    @Bean
    ReceiverService receiverService(StringRedisTemplate template) {
        return new ReceiverService(template);
    }
}
