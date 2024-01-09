package Gihon.task.manager.controllers;

import Gihon.task.manager.DTO.CommentPostRequest;
import Gihon.task.manager.DTO.TaskStatusUpdateRequest;
import Gihon.task.manager.DTO.TaskCreateRequest;
import Gihon.task.manager.DTO.TaskExecutorRequest;
import Gihon.task.manager.models.Task;
import Gihon.task.manager.services.CommentService;
import Gihon.task.manager.services.TaskService;
import Gihon.task.manager.services.UserService;
import Gihon.task.manager.util.error.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    private final CommentService commentService;


    @GetMapping()
    @ApiResponse(description = "Вывод списка всех задач")
    public List<Task> findAll(Principal principal){
        return taskService.findAll();
    }

    @GetMapping("/created")
    @ApiResponse(description = "Вывод списка всех задач авторизованного автора")
    public List<Task> findAllByAuthor(Principal principal){
        return taskService.findByAuthorId(userService.findByEmail(principal.getName()).getId());
    }
    @GetMapping("/prescribed")
    @ApiResponse(description = "Вывод списка всех задач авторизованного исполнителя")
    public List<Task> findAllByExecutor(Principal principal){
        return taskService.findByExecutorId(userService.findByEmail(principal.getName()).getId());
    }

    @PostMapping("/save")
    @ApiResponse(description = "Сохранение задачи авторизованного пользователя при помощи jwt токена, приём в формате     {\n" +
            "        \"title\": \"title\",\n" +
            "        \"description\": \"description\",\n" +
            "        \"priority\": \"HIGH\", (может быть только HIGH, MEDIUM или LOW)\n" +
            "        \"status\": \"ONGOING\" (может быть только PENDING, ONGOING или COMPLETED)\n" +
            "    }")
    public ResponseEntity<?> create(Principal principal,
                                  @RequestBody @Valid TaskCreateRequest request,
                                  BindingResult bindingResult){
        try {
            taskService.create(principal, request, bindingResult);
        } catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok("Task creation was successful");

    }

    @PatchMapping("{id}/patch")
    @ApiResponse(description = "Обновление задачи её автором")
    public ResponseEntity<?> patch(@PathVariable int id, Principal principal,
                                   @RequestBody @Valid TaskCreateRequest request,
                                   BindingResult bindingResult){
        try {
            taskService.update(id, principal, request, bindingResult);
        } catch (Exception e){
            throw e;
        }

        return ResponseEntity.ok("Task update was successful");
    }

    @PostMapping("/{id}")
    @ApiResponse(description = "Написание комментария к задаче")
    public ResponseEntity<?> postComment(@PathVariable("id")int id,
                                         Principal principal,
                                         @RequestBody @Valid CommentPostRequest request,
                                         BindingResult bindingResult){
       try {
           commentService.create(id, principal, request, bindingResult);
       } catch (Exception e){
           throw e;
       }
       return ResponseEntity.ok("Comment post was successful");
    }

    @DeleteMapping("/{id}")
    @ApiResponse(description = "Удаление задачи")
    public ResponseEntity<?> deleteTask(@PathVariable("id") int id,Principal principal) {
        try {
            taskService.deleteTask(id, principal);
        } catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok("Task was deleted");
    }

    @PatchMapping("/{id}/status")
    @ApiResponse(description = "Обновление задачи автором или исполнителем")
    public ResponseEntity<?> updateStatus(@PathVariable("id")int id,
                                          Principal principal,
                                          @RequestBody @Valid TaskStatusUpdateRequest request,
                                          BindingResult bindingResult) {
        try {
            taskService.updateStatus(id , principal, request, bindingResult);
        } catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok("Task status has been updated");
    }

    @PatchMapping("/{id}/executor")
    @ApiResponse(description = "Обновление исполнителя задачи")
    public ResponseEntity<?> updateExecutor(@PathVariable("id")int id,
                                            Principal principal,
                                            @RequestBody @Valid TaskExecutorRequest request,
                                            BindingResult bindingResult) {
        try {
            taskService.updateExecutor(id, principal, request, bindingResult);
        } catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok("Task executor has been updated");
    }

    @GetMapping("/{id}")
    @ApiResponse(description = "Получение задачи по id")
    public Task getTask(@PathVariable("id")int id){
        return taskService.findById(id);
    }

    @ExceptionHandler
    private ResponseEntity<CustomErrorResponse> handleException(TaskNotFoundException e){
        CustomErrorResponse response = new CustomErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<CustomErrorResponse> handleException(TitleAlreadyInUseException e){
        CustomErrorResponse response = new CustomErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    private ResponseEntity<CustomErrorResponse> handleException(NotAuthorException e){
        CustomErrorResponse response = new CustomErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<CustomErrorResponse> handleException(NotAuthorOrExecutorException e){
        CustomErrorResponse response = new CustomErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<CustomErrorResponse> handleException(TaskNotCreatedException e){
        CustomErrorResponse response = new CustomErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<CustomErrorResponse> handleException(StatusConvertException e){
        CustomErrorResponse response = new CustomErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<CustomErrorResponse> handleException(PriorityConvertException e){
        CustomErrorResponse response = new CustomErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<CustomErrorResponse> handleException(NullPointerException e){
        CustomErrorResponse response = new CustomErrorResponse("incorrect data");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
