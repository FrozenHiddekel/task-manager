package Gihon.task.manager.util.error;



public class UserNotFoundException extends RuntimeException{
    @Override
    public String getMessage() {
        return "User wasn't found";
    }
}
