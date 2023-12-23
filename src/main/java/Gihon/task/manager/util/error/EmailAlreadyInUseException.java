package Gihon.task.manager.util.error;

public class EmailAlreadyInUseException extends RuntimeException{
    @Override
    public String getMessage() {
        return "The email is already in use";
    }
}
