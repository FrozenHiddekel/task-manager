package Gihon.task.manager.repositories;

import Gihon.task.manager.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {

    List<Task> findByAuthor_Id(int id);
    List<Task> findByExecutor_id(int id);
    Optional<Task> findByTitle(String title);

}
