package Gihon.task.manager.DTO;

import lombok.Data;

@Data
public class TaskUserSignInRequest {
    private String email;
    private String password;
}
