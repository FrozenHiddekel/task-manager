package Gihon.task.manager.util.error;

public class TitleAlreadyInUseException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Title is already in use";
    }
}
