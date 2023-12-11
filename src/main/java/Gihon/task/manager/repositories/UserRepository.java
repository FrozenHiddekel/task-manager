package Gihon.task.manager.repositories;

import Gihon.task.manager.models.TaskUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<TaskUser, Integer> {
    Optional<TaskUser> findByEmail(String email);
    boolean existsByEmail(String email);
}
