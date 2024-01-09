package Gihon.task.manager.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskUserSignupRequest {

    @NotNull(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name must be greater than 2 and less than 100")
    private String name;

    @NotNull(message = "Email should not be empty")
    @Email(message = "This email is not correct")
    @Size(min = 2, max = 100, message = "Email must be greater than 2 and less than 100")
    private String email;

    @NotNull(message = "Password should not be empty")
    @Size(min = 2, max = 100, message = "Password must be greater than 2 and less than 100")
    private String password;
}
