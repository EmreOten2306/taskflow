package tech.ekya.taskflow.comment;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import tech.ekya.taskflow.comment.dto.CommentResponse;
import tech.ekya.taskflow.comment.dto.CreateCommentRequest;
import tech.ekya.taskflow.comment.dto.UpdateCommentRequest;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/tasks/{taskId}/comments")
    public CommentResponse createComment(
            @PathVariable Long taskId,
            @Valid @RequestBody CreateCommentRequest request
    ) {
        return commentService.createComment(taskId, request);
    }

    /// GET COMMENTS BY TASK ID
    @GetMapping("/tasks/{taskId}/comments")
    public List<CommentResponse> findByTaskId(
            @PathVariable Long taskId
    ) {
        return commentService.findCommentsByTaskId(taskId);
    }

    /// UPDATE COMMENT
    @PutMapping("/comments/{id}")
    public CommentResponse updateComment(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCommentRequest request
    ) {
        return commentService.updateComment(id, request);
    }

    /// DELETE COMMENT
    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}