package task.manager.cofig;

public enum Topics {
    TASK("taskTopic"),
    SUMMARY("summaryTopic");

    private final String realTopicName;

    Topics(String realTopicName) {
        this.realTopicName = realTopicName;
    }

    public String getRealTopicName() {
        return realTopicName;
    }
}
