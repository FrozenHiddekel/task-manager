package Gihon.task.manager.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "body")
    @Size(min = 2, max = 1000, message = "The message must be greater than 2 and less than 1000")
    String message;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnore
    @ToString.Exclude
    TaskUser author;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @JsonIgnore
    @ToString.Exclude
    Task task;

}
