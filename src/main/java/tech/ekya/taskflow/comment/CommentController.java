package tech.ekya.taskflow.comment;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

@PostMapping ("/tasks/{taskId}/comments")
    public Comment createComment(@PathVariable("taskId") Long taskId,
                                 @RequestBody Comment comment) {
        return commentService.createComment(taskId, comment);
}
@GetMapping ("/tasks/{taskId}/comments")
    public List<Comment> findByTaskId(@PathVariable("taskId") Long taskId) {
    return commentService.findCommentsByTaskId(taskId);
}

@DeleteMapping ("/comments/{id}")
    public void deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
}

}
