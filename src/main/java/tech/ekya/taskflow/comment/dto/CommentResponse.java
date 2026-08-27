package tech.ekya.taskflow.comment.dto;

import tech.ekya.taskflow.user.dto.UserSummary;

import java.time.LocalDateTime;

public record CommentResponse(

        Long id,
        String content,
        UserSummary author,
        Long taskId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}