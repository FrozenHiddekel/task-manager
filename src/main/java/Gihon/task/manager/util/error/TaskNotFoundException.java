package Gihon.task.manager.util.error;

public class TaskNotFoundException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Task wasn't found";
    }
}
