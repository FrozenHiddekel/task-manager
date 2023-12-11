package Gihon.task.manager.services;

import Gihon.task.manager.models.Task;
import Gihon.task.manager.repositories.TaskRepository;
import Gihon.task.manager.util.error.TaskNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor

@Service
@Transactional(readOnly = true)
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public Task findById(int id){
        Optional<Task> foundTask = taskRepository.findById(id);
        return foundTask.orElseThrow(TaskNotFoundException::new);
    }

    public List<Task> findByAuthorId(int id){
        return taskRepository.findByAuthor_Id(id);
    }

    public List<Task> findByExecutorId(int id){
        return taskRepository.findByExecutor_id(id);
    }

    public boolean isTitleInUse(String title){
        Optional<Task> task = taskRepository.findByTitle(title);
        return task.isPresent();
    }

    @Transactional
    public void save(Task task){
        taskRepository.save(task);
    }

    @Transactional
    public void deleteById(int id){
        Optional<Task> foundTask = taskRepository.findById(id);
        taskRepository.delete(foundTask.orElseThrow(TaskNotFoundException::new));
    }

}
