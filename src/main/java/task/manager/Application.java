package task.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import task.manager.cli.TaskManagerCliService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        TaskManagerCliService cliService = ctx.getBean(TaskManagerCliService.class);
        cliService.run();

        System.exit(0);
    }
}
