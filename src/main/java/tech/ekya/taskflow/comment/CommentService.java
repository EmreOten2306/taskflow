package tech.ekya.taskflow.comment;

import org.springframework.stereotype.Service;
import tech.ekya.taskflow.exception.ResourceNotFoundException;
import tech.ekya.taskflow.task.Task;
import tech.ekya.taskflow.task.TaskRepository;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    public CommentService(CommentRepository commentRepository,
                          TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
    }

    ///CREATE COMMENT
    public Comment createComment(Long taskId,Comment comment ) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId
                ));
        comment.setTask(task);
        return commentRepository.save(comment);
    }
    /// GET COMMENT
    public List<Comment> findCommentsByTaskId(Long taskId) {
        taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId
                ));
        return commentRepository.findByTaskId(taskId);
    }

    ///DELETE COMMENT
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comment not found with id: " + commentId
                ));
        commentRepository.delete(comment);
    }

}
