package Gihon.task.manager.services;

import Gihon.task.manager.models.Comment;
import Gihon.task.manager.models.Task;
import Gihon.task.manager.repositories.CommentRepository;
import Gihon.task.manager.util.error.TaskNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor

@Service
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;

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
}
