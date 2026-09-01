package tech.ekya.taskflow.report.dto;

import java.time.LocalDateTime;

public record TaskOverdueResponse(
        Long id,
        String title,
        LocalDateTime dueDate,
        Long delay_days

) {
}
