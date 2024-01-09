package Gihon.task.manager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "task")
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "title")
    @Size(min = 2, max = 100, message = "The title must be greater than 2 and less than 100")
    String title;

    @Column(name = "description")
    @Size(min = 2, max = 1000, message = "The description must be greater than 2 and less than 100")
    String description;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnore
    @ToString.Exclude
    TaskUser author;

    @ManyToOne
    @JoinColumn(name = "executor_id")
    @JsonIgnore
    @ToString.Exclude
    TaskUser executor;

    @OneToMany(mappedBy = "task")
    private List<Comment> Comments;

    @Column(name = "priority")
    String priority;

    @Column(name = "status")
    String status;

}
