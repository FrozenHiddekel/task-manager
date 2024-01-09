package Gihon.task.manager.services;

import Gihon.task.manager.DTO.CommentPostRequest;
import Gihon.task.manager.models.Comment;
import Gihon.task.manager.models.Task;
import Gihon.task.manager.repositories.CommentRepository;
import Gihon.task.manager.util.SpringUtil;
import Gihon.task.manager.util.error.CommentNotCreatedException;
import Gihon.task.manager.util.error.TaskNotFoundException;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskService taskService;
    private final UserService userService;

    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    public Comment findById(int id){
        Optional<Comment> foundComment = commentRepository.findById(id);
        return foundComment.orElseThrow(TaskNotFoundException::new);
    }

    @Transactional
    public void save(Comment comment){
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteById(int id){
        Optional<Comment> foundComment = commentRepository.findById(id);
        commentRepository.delete(foundComment.orElseThrow(TaskNotFoundException::new));
    }

    @Transactional
    public void create(int id,
                       Principal principal,
                       CommentPostRequest request,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasErrors()) {
                String errorMsg = SpringUtil.BindingResultGetError(bindingResult);
                throw new CommentNotCreatedException(errorMsg);
            }
        }
        Task task = taskService.findById(id);

        Comment comment = new Comment();
        comment.setMessage(request.getCommentText());
        comment.setAuthor(userService.findByEmail(principal.getName()));
        comment.setTask(task);
    }
}
