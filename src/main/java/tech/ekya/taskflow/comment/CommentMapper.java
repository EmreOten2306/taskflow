package tech.ekya.taskflow.comment;

import org.springframework.stereotype.Component;
import tech.ekya.taskflow.comment.dto.CommentResponse;
import tech.ekya.taskflow.comment.dto.CreateCommentRequest;
import tech.ekya.taskflow.comment.dto.UpdateCommentRequest;
import tech.ekya.taskflow.user.dto.UserSummary;

@Component
public class CommentMapper {

    public Comment toEntity(CreateCommentRequest request) {
        Comment comment = new Comment();

        comment.setContent(request.content());

        return comment;
    }

    public CommentResponse toResponse(Comment comment) {

        UserSummary authorSummary = new UserSummary(
                comment.getAuthor().getId(),
                comment.getAuthor().getFullName(),
                comment.getAuthor().getEmail()
        );

        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                authorSummary,
                comment.getTask().getId(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}