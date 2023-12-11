package Gihon.task.manager.DTO;

import lombok.Data;

@Data
public class TaskUserSignupRequest {
    private String name;
    private String email;
    private String password;
}
