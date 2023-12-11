package Gihon.task.manager.util.error;

public class PriorityConvertException extends RuntimeException{
    @Override
    public String getMessage() {
        return "The priority should be equal HIGH, MEDIUM or LOW";
    }
}
