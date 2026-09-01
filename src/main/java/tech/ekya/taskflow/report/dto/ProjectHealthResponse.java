package tech.ekya.taskflow.report.dto;

import java.math.BigDecimal;

public record ProjectHealthResponse(
        String projectName,
        BigDecimal completionPercentage,
        BigDecimal overduePercentage,
        BigDecimal averageCompletionDays
) {
}