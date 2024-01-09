package Gihon.task.manager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "user_table")
public class TaskUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name")
    @NotNull(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name must be greater than 2 and less than 100")
    String name;

    @Column(name = "password")
    @NotNull(message = "Password should not be empty")
    @Size(min = 2, max = 100, message = "Password must be greater than 2 and less than 100")
    String password;

    @Column(name="email")
    @NotNull(message = "Email should not be empty")
    @Email(message = "This email is not correct")
    @Size(min = 2, max = 100, message = "Email must be greater than 2 and less than 100")
    String email;

    @OneToMany(mappedBy = "author")
    private List<Task> createdTasks;

    @OneToMany(mappedBy = "executor")
    private List<Task> performedTasks;

    @OneToMany(mappedBy = "author")
    private List<Comment> Comments;
}
