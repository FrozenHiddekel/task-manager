package Gihon.task.manager.util.error;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(String message) {
        super(message);
    }
}
