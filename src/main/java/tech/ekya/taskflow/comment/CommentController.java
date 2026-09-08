package tech.ekya.taskflow.comment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import tech.ekya.taskflow.comment.dto.CommentResponse;
import tech.ekya.taskflow.comment.dto.CreateCommentRequest;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(
        name = "Comments",
        description = "Comment management operations"
)
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            summary = "Create a comment",
            description = "Creates a new comment for a task"
    )
    @ApiResponse(responseCode = "200", description = "Comment created successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @PostMapping("/tasks/{taskId}/comments")
    public CommentResponse createComment(
            @PathVariable Long taskId,
            @Valid @RequestBody CreateCommentRequest request
    ) {
        return commentService.createComment(taskId, request);
    }

    /// GET COMMENTS BY TASK ID
    @Operation(
            summary = "Get task comments",
            description = "Returns all comments belonging to a task"
    )
    @ApiResponse(responseCode = "200", description = "Comments retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @GetMapping("/tasks/{taskId}/comments")
    public List<CommentResponse> findByTaskId(
            @PathVariable Long taskId
    ) {
        return commentService.findCommentsByTaskId(taskId);
    }

    /// DELETE COMMENT
    @Operation(
            summary = "Delete a comment",
            description = "Deletes a comment by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Comment deleted successfully")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}

