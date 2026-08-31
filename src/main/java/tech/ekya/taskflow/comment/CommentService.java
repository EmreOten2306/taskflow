package tech.ekya.taskflow.comment;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.ekya.taskflow.comment.dto.CommentResponse;
import tech.ekya.taskflow.comment.dto.CreateCommentRequest;
import tech.ekya.taskflow.exception.ResourceNotFoundException;
import tech.ekya.taskflow.task.Task;
import tech.ekya.taskflow.task.TaskRepository;
import tech.ekya.taskflow.user.AppUser;
import tech.ekya.taskflow.user.AppUserRepository;

import java.util.List;

@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final AppUserRepository appUserRepository;
    private final CommentMapper commentMapper;

    public CommentService(
            CommentRepository commentRepository,
            TaskRepository taskRepository,
            AppUserRepository appUserRepository,
            CommentMapper commentMapper
    ) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.appUserRepository = appUserRepository;
        this.commentMapper = commentMapper;
    }

    public CommentResponse createComment(
            Long taskId,
            CreateCommentRequest request
    ) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId
                ));

        AppUser author = appUserRepository.findById(request.authorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + request.authorId()
                ));

        Comment comment = commentMapper.toEntity(request);

        comment.setTask(task);
        comment.setAuthor(author);

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toResponse(savedComment);
    }

    /// GET COMMENTS BY TASK ID
    public List<CommentResponse> findCommentsByTaskId(Long taskId) {

        taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId
                ));

        return commentRepository.findByTaskId(taskId)
                .stream()
                .map(commentMapper::toResponse)
                .toList();
    }
    /// DELETE COMMENT
    public void deleteComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comment not found with id: " + commentId
                ));

        commentRepository.delete(comment);
    }
}