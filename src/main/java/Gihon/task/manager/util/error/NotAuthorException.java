package Gihon.task.manager.util.error;

public class NotAuthorException extends RuntimeException{
    @Override
    public String getMessage() {
        return "You are not the author of this task";
    }
}
