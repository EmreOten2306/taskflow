package tech.ekya.taskflow.comment.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateCommentRequest(

        @NotBlank
        String content
) {
}