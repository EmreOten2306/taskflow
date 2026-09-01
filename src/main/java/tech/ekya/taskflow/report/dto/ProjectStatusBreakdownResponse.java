package tech.ekya.taskflow.report.dto;

public record ProjectStatusBreakdownResponse(
        Long todoCount,
        Long inProgressCount,
        Long inReviewCount,
        Long doneCount
) {
}
