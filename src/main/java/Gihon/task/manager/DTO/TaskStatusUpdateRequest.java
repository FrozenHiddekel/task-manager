package Gihon.task.manager.DTO;

import Gihon.task.manager.util.enums.Status;
import Gihon.task.manager.util.error.StatusConvertException;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

//@Data
public class TaskStatusUpdateRequest {
    @Enumerated(EnumType.ORDINAL)
    Status status;

    public void setStatus(String input) {
        try {
            Status status = Status.valueOf(input);
            this.status = status;
        } catch (Exception e){
            throw new StatusConvertException();
        }
    }

    public Status getStatus() {
        return status;
    }
}
