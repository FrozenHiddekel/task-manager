package Gihon.task.manager.DTO;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentPostRequest {
    @Size(min = 2, max = 1000, message = "The comment must be greater than 2 and less than 1000")
    String commentText;
}
