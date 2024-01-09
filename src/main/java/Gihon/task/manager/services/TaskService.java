package Gihon.task.manager.services;

import Gihon.task.manager.DTO.TaskCreateRequest;
import Gihon.task.manager.DTO.TaskExecutorRequest;
import Gihon.task.manager.DTO.TaskStatusUpdateRequest;
import Gihon.task.manager.models.Task;
import Gihon.task.manager.models.TaskUser;
import Gihon.task.manager.repositories.TaskRepository;
import Gihon.task.manager.util.SpringUtil;
import Gihon.task.manager.util.error.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor

@Service
@Transactional(readOnly = true)
public class TaskService {
    private final TaskRepository taskRepository;
    private final  UserService userService;

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
    public void create(Principal principal, TaskCreateRequest request, BindingResult bindingResult){
        if(this.isTitleInUse(request.getTitle())){
            throw new TitleAlreadyInUseException();
        }
        if (bindingResult.hasErrors()){
            String errorMsg =  SpringUtil.BindingResultGetError(bindingResult);
            throw new TaskNotCreatedException(errorMsg);
        }
        Task task = new Task();
        task.setAuthor(userService.findByEmail(principal.getName()));
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority()==null ? null : request.getPriority().toString());
        task.setStatus(request.getStatus()==null ? null : request.getStatus().toString());
        this.save(task);
    }

    @Transactional
    public void update(int id,
                       Principal principal,
                       TaskCreateRequest request,
                       BindingResult bindingResult) throws NotAuthorException{
        if (bindingResult.hasErrors()){
            String errorMsg =  SpringUtil.BindingResultGetError(bindingResult);
            throw new TaskNotCreatedException(errorMsg);
        }
        Task task = this.findById(id);
        int inputId = userService.findByEmail(principal.getName()).getId();
        boolean isNotAuthor = !(inputId==task.getAuthor().getId());
        if (isNotAuthor){
            throw new NotAuthorException();
        }
        if(this.isTitleInUse(request.getTitle())){
            throw new TitleAlreadyInUseException();
        }
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority()==null ? null : request.getPriority().toString());
        task.setStatus(request.getStatus()==null ? null : request.getStatus().toString());
        this.save(task);
    }

    @Transactional
    public void updateStatus(int id,
                             Principal principal,
                             TaskStatusUpdateRequest request,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            if (bindingResult.hasErrors()){
                String errorMsg =  SpringUtil.BindingResultGetError(bindingResult);
                throw new CommentNotCreatedException(errorMsg);
            }
        }
        Task task = this.findById(id);
        int inputId = userService.findByEmail(principal.getName()).getId();
        boolean isNotExecutor = task.getExecutor()==null ? true : inputId!=task.getExecutor().getId();
        boolean isNotAuthor = !(inputId==task.getAuthor().getId());
        if(isNotAuthor && isNotExecutor){
            throw new NotAuthorOrExecutorException();
        }

        task.setStatus(request.getStatus().toString());

    }

    @Transactional
    public void updateExecutor(int id,
                               Principal principal,
                               TaskExecutorRequest request,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            if (bindingResult.hasErrors()){
                String errorMsg =  SpringUtil.BindingResultGetError(bindingResult);
                throw new CommentNotCreatedException(errorMsg);
            }
        }
        Task task = this.findById(id);
        int inputId = userService.findByEmail(principal.getName()).getId();
        boolean isNotAuthor = !(inputId==task.getAuthor().getId());
        if(isNotAuthor){
            throw new NotAuthorException();
        }
        TaskUser newExecutor = userService.findByEmail(request.getEmail());
        task.setExecutor(newExecutor);
    }

    @Transactional
    public void deleteById(int id){
        Optional<Task> foundTask = taskRepository.findById(id);
        taskRepository.delete(foundTask.orElseThrow(TaskNotFoundException::new));
    }

    @Transactional
    public void deleteTask(int id, Principal principal){
        Task task = this.findById(id);
        int inputId = userService.findByEmail(principal.getName()).getId();
        boolean isNotAuthor = !(inputId==task.getAuthor().getId());
        if (isNotAuthor){
            throw new NotAuthorException();
        }
        this.deleteById(id);
    }

}
