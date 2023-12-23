package Gihon.task.manager.DTO;

import Gihon.task.manager.models.TaskUser;
import Gihon.task.manager.util.enums.Priority;
import Gihon.task.manager.util.enums.Status;
import Gihon.task.manager.util.error.PriorityConvertException;
import Gihon.task.manager.util.error.StatusConvertException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskCreateRequest {
    @Size(min = 3, max = 100, message = "The title must be greater than 3 and less than 100")
    @NotNull(message = "Title should not be empty")
    String title;

    @Size(min = 2, max = 1000, message = "The description must be greater than 2 and less than 100")
    String description;
    TaskUser executor;
    Priority priority;
    Status status;

    public void setStatus(String input) {
        try {
            Status status = Status.valueOf(input);
            this.status = status;
        } catch (Exception e){
            throw new StatusConvertException();
        }
    }

    public void setPriority(String input) {
        try {
            Priority priority = Priority.valueOf(input);
            this.priority = priority;
        } catch (Exception e){
            throw new PriorityConvertException();
        }
    }
}
