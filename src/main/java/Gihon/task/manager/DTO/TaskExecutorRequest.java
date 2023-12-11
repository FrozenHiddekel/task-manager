package Gihon.task.manager.DTO;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class TaskExecutorRequest {
    @Email(message = "This email is not correct")
    String email;
}
