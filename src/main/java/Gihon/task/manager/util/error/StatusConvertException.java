package Gihon.task.manager.util.error;

public class StatusConvertException extends RuntimeException{
    @Override
    public String getMessage() {
        return "The status should be equal PENDING, ONGOING or COMPLETED";
    }
}
