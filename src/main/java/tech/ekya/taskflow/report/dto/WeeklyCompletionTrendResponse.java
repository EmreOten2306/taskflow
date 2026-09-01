package tech.ekya.taskflow.report.dto;

import java.time.LocalDate;

public record WeeklyCompletionTrendResponse(
        LocalDate weekStart,
        Long completedCount

) {
}
