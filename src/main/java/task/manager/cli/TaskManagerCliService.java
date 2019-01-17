package task.manager.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import task.manager.Application;

import java.util.Scanner;

@Service
public class TaskManagerCliService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskManagerCliService.class);

    private final StringRedisTemplate template;

    @Autowired
    public TaskManagerCliService(StringRedisTemplate template ) {
        this.template = template;
    }
    
    public void run() {
        System.out.println();
        System.out.println();
        LOGGER.info("EXECUTING : Spring command line runner");
        boolean isExitProgram = false;
        while (!isExitProgram) {
            LOGGER.info("Enter your task:\n");
            Scanner scan = new Scanner(System.in);
            String messageToSend = scan.nextLine();
            if (messageToSend.equals("exit")) {
                isExitProgram = true;
            } else {
                LOGGER.info("Sending message...");
                template.convertAndSend("taskTopic", messageToSend);
            }
        }
    }
}
