package Gihon.task.manager.util.error;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomErrorResponse {
    private String message;

    private String time;

    public CustomErrorResponse(String message) {
        this.message = message;
        java.util.Date date = new java.util.Date();
        this.time = date.toString();
    }


}
