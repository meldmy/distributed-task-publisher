package task.manager.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

@Service
public class ReceiverService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiverService.class);
    public static final String ADD_COMMAND = "ADD ";
    public static final String REMOVE_COMMAND = "REMOVE ";
    public static final String SUMMARY_COMMAND = "SUMMARY";

    private final LinkedHashSet<String> availableTasks;
    private final StringRedisTemplate template;

    @Autowired
    public ReceiverService(StringRedisTemplate template) {
        this.template = template;
        availableTasks = new LinkedHashSet<>();
    }

    public void receiveTask(String taskMessage) {
        if (taskMessage.startsWith(ADD_COMMAND)) {
            String addTaskdescription = taskMessage.substring(ADD_COMMAND.length());
            System.out.println("Added task with description: \n<" + addTaskdescription + ">");
            availableTasks.add(addTaskdescription);
        } else if (taskMessage.startsWith(REMOVE_COMMAND)) {
            String removeTaskDescription = taskMessage.substring(REMOVE_COMMAND.length());
            if (availableTasks.contains(removeTaskDescription)) {
                System.out.println("Added remove task with description: \n<" + removeTaskDescription + ">");
                availableTasks.remove(removeTaskDescription);
            }
        } else if (taskMessage.startsWith(SUMMARY_COMMAND)) {
            LOGGER.info("Sending summary...");
            template.convertAndSend("summaryTopic", String.join("->-> ", availableTasks));
        } else {
            System.out.println("Can't recognize received command");
        }
    }
}
/*
ADD haircut
ADD car
ADD car2
REMOVE car2
SUMMARY


*/
